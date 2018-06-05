package cn.linkmore.common.config;

import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import cn.linkmore.common.entity.TimingSchedule;
import cn.linkmore.common.service.TimingScheduleService;
/**
 * 项目启动后加载数据
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
@Component
public class StartupRunner implements CommandLineRunner,ApplicationContextAware {
	@Autowired
	private DefaultSchedulingConfigurer defaultSchedulingConfigurer;
	@Autowired
	private TimingScheduleService timingScheduleService;
	@Override
	public void run(String... args) throws Exception {
		new Thread() {
			public void run() {
				try {
					// 等待任务调度初始化完成
					while (!defaultSchedulingConfigurer.inited()) {
						Thread.sleep(100);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				List<TimingSchedule> list = timingScheduleService.list(new TimingSchedule(null, 1));
				defaultSchedulingConfigurer.addTriggerTaskALL(list);;
			}
		}.start();

	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		DefaultSchedulingConfigurer.context = applicationContext;
	}

}
