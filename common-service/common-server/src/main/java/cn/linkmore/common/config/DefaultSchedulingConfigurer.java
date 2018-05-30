package cn.linkmore.common.config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import cn.linkmore.annotation.Cron;
import cn.linkmore.annotation.MethodName;
import cn.linkmore.annotation.Path;
import cn.linkmore.annotation.TaskId;
import cn.linkmore.common.entity.TimingSchedule;
import cn.linkmore.util.BeanUtils;

/**
 * @Description  调度中心配置
 * @author  GFF
 * @Date     2018年3月19日
 *
 */
@EnableScheduling
@Component
public class DefaultSchedulingConfigurer implements SchedulingConfigurer {
	//	用于查询任务
	private final String FIELD_SCHEDULED_FUTURES = "scheduledTasks";
	//	任务id前缀
	private final String TASK = "TASK";
	private ScheduledTaskRegistrar taskRegistrar;
	//	上下文
	public static ApplicationContext context;
	private static Logger logger = Logger.getLogger(DefaultSchedulingConfigurer.class);
	private Set<ScheduledFuture<?>> scheduledFutures = null;
	// 索引map 用于更新，取消数据时查询对应的任务
	private Map<String, ScheduledFuture<?>> taskFutures = new ConcurrentHashMap<String, ScheduledFuture<?>>();

	@Override
	public void configureTasks(ScheduledTaskRegistrar tr) {
		taskRegistrar = tr;
	}

	@SuppressWarnings("unchecked")
	private Set<ScheduledFuture<?>> getScheduledFutures() {
		if (scheduledFutures == null) {
			try {
				scheduledFutures = (Set<ScheduledFuture<?>>) BeanUtils.getProperty(taskRegistrar,
						FIELD_SCHEDULED_FUTURES);
			} catch (NoSuchFieldException e) {
				throw new SchedulingException("not found scheduledFutures field.");
			}
		}
		return scheduledFutures;
	}

	
	/**
	 * @Description   添加调度任务（请使用addTriggerTask(Object obj)）
	 * @Author   GFF 
	 * @Date       2018年3月17日
	 * @Param    DefaultSchedulingConfigurer
	 * @Return   void
	 */
	@Deprecated
	public void addTriggerTask(String taskId, TimingSchedule ts) {
		//	判断任务是否已经存在  
		if (taskFutures.containsKey(taskId)) {
			throw new SchedulingException("the taskId[" + taskId + "] was added.");
		}
		TriggerTask triggerTask = getTriggerTask(ts);
		TaskScheduler scheduler = taskRegistrar.getScheduler();
		ScheduledFuture<?> future = scheduler.schedule(triggerTask.getRunnable(), triggerTask.getTrigger());
		getScheduledFutures().add(future);
		taskFutures.put(taskId, future);
	}

	/**
	 * @Description   将所有的调度任务启动
	 * @Author   GFF 
	 * @Date       2018年3月17日
	 * @Param    DefaultSchedulingConfigurer
	 * @Return   void
	 */
	public void  addTriggerTaskALL(List<?> list) {
		logger.info("添加所有的调度任务");
		for (Object obj : list) {
			logger.info("执行调度任务【" + obj.toString() + "】");
			addTriggerTask(obj);
			logger.info("调度任务执行完成【" + obj.toString() + "】");
		}
	}
	
	/**
	 * @Description   添加调度任务
	 * @Author   GFF 
	 * @Date       2018年3月17日
	 * @Param    DefaultSchedulingConfigurer
	 * @Return   void
	 */
	public void addTriggerTask(Object obj) {
			//	判断任务是否已经存在  
			Object taskId = BeanUtils.getAnnottionFieldValue(obj, TaskId.class);
			if(taskId != null){
				String taskNumber = TASK + taskId.toString();
				if (taskFutures.containsKey(taskId.toString())) {
					throw new SchedulingException("the taskId[" + taskNumber + "] was added.");
				}
				//	解析对象获取任务
				TriggerTask triggerTask = getTriggerTask(obj);
				TaskScheduler scheduler = taskRegistrar.getScheduler();
				ScheduledFuture<?> future = scheduler.schedule(triggerTask.getRunnable(), triggerTask.getTrigger());
				getScheduledFutures().add(future);
				taskFutures.put(taskNumber, future);
			}else{
				logger.error("id不存在【"+obj.toString()+"】");
			}
	}


	/**
	 * @Description  	通过调度任务获取TriggerTask对象
	 * @Author   GFF 
	 * @Date       2018年3月17日
	 * @Param    DefaultSchedulingConfigurer
	 * @Return   TriggerTask
	 */
	private TriggerTask getTriggerTask(Object obj) {
		return new TriggerTask(new Runnable() {
			@Override
			public void run() {
				logger.info("启动线程");
				// 根据类名获取spring容器中的对象
				if (context == null) {
					throw new RuntimeException("没有获取到容器对象");
				}
				try {
					Object path = BeanUtils.getAnnottionFieldValue(obj, Path.class);
					if(path != null ){
						//	通过类名找到对应的class
						Class<?> name = Class.forName(path.toString());
						Object bean = context.getBean(name);
						// 对象为空时的处理
						if (bean == null) {
							logger.error("类名没有找到 数据【" + path.toString() + "】");
						}
						// 通过反射调用调度方法
						Object object = BeanUtils.getAnnottionFieldValue(obj, MethodName.class);
						if(object != null){
							Method method = bean.getClass().getMethod(object.toString());
							method.invoke(bean);
						}else{
							logger.error("methodName 为空【"+obj.toString()+"】【"+object+"】");
						}
					}else{
						logger.error("path 为空【"+obj.toString()+"】【"+path+"】");
					}
					
				} catch (IllegalArgumentException |   BeansException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}, triggerContext -> {
			Object object = BeanUtils.getAnnottionFieldValue(obj, Cron.class);
			if(object != null){
				logger.info("解析cron【" + object.toString() + "】");
				// 获取定时任务时间的表达式
				String cron = object.toString();
				// 对cron表达式进行校验
				return new CronTrigger(cron).nextExecutionTime(triggerContext);
			}{
				logger.error("cron 为空【"+obj.toString()+"】");
				return null;
			}
		});
	}

	/**
	 * @Description  取消任务
	 * @Author   GFF 
	 * @Date       2018年3月17日
	 * @Param    DefaultSchedulingConfigurer
	 * @Return   void
	 */
	public void cancelTriggerTask(String taskId) {
		taskId = TASK + taskId;
		ScheduledFuture<?> future = taskFutures.get(taskId);
		if (future != null) {
			future.cancel(true);
		}
		taskFutures.remove(taskId);
		getScheduledFutures().remove(future);
	}

	/**
	 * @Description  重置任务
	 * @Author   GFF 
	 * @Date       2018年3月17日
	 * @Param    DefaultSchedulingConfigurer
	 * @Return   void
	 */
	public void resetTriggerTask(Object obj) {
		Object object = BeanUtils.getAnnottionFieldValue(obj, TaskId.class);
		if(object != null){
			cancelTriggerTask(object.toString());
			addTriggerTask(obj);
		}
	}


	/**
	 * @Description   任务编号
	 * @Author   GFF 
	 * @Date       2018年3月17日
	 * @Param    DefaultSchedulingConfigurer
	 * @Return   Set<String>
	 */
	public Set<String> taskIds() {
		return taskFutures.keySet();
	}


	/**
	 * @Description  是否已存在任务
	 * @Author   GFF 
	 * @Date       2018年3月17日
	 * @Param    DefaultSchedulingConfigurer
	 * @Return   boolean
	 */
	public boolean hasTask(String taskId) {
		return this.taskFutures.containsKey(taskId);
	}

	/**
	 * @Description  任务调度是否已经初始化完成
	 * @Author   GFF 
	 * @Date       2018年3月17日
	 * @Param    DefaultSchedulingConfigurer
	 * @Return   boolean
	 */
	public boolean inited() {
		return this.taskRegistrar != null && this.taskRegistrar.getScheduler() != null;
	}

	/**
	 * @Description  	通过调度任务获取TriggerTask对象（请使用getTriggerTask(Object obj)）
	 * @Author   GFF 
	 * @Date       2018年3月17日
	 * @Param    DefaultSchedulingConfigurer
	 * @Return   TriggerTask
	 */
	@Deprecated
	private TriggerTask getTriggerTask(TimingSchedule ts){
		return new TriggerTask(new Runnable() {
			@Override
			public void run() {
				logger.info("启动线程");
				// 根据类名获取spring容器中的对象
				if (context == null) {
					throw new RuntimeException("没有获取到容器对象");
				}
				try {
					//	通过类名找到对应的class
					Class<?> name = Class.forName(ts.getPath());
					Object bean = context.getBean(name);
					// 对象为空时的处理
					if (bean == null) {
						logger.error("类名有误 数据【" + ts.toString() + "】");
					}
					// 通过反射调用调度方法
					Method method = bean.getClass().getMethod(ts.getMethodName());
					method.invoke(bean.getClass().newInstance());
				} catch (IllegalArgumentException | InstantiationException | BeansException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}, triggerContext -> {
			logger.info("解析cron【" + ts.getCron() + "】");
			// 获取定时任务时间的表达式
			String cron = ts.getCron();
			// 对cron表达式进行校验
			return new CronTrigger(cron).nextExecutionTime(triggerContext);
		});
	}
}
