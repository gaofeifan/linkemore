package cn.linkmore.common.entity;

import cn.linkmore.annotation.Cron;
import cn.linkmore.annotation.MethodName;
import cn.linkmore.annotation.Path;
import cn.linkmore.annotation.TaskId;

/**
 * 定时调度
 * @author   GFF
 * @Date     2018年5月28日
 * @Version  v2.0
 */
public class TimingSchedule {

	/**
	 *  id
	 */ 
	@TaskId
	private Long id;
	
	/**
	 *  名称
	 */ 
	private String name;
	
	/**
	 * 	路径
	 */ 
	@Path
	private String path;
	
	/**
	 *  调用方法的名称
	 */ 
	@MethodName
	private String methodName;
	
	/**
	 *  任务时间
	 */ 
	@Cron
	private String cron;
	
	/**
	 *  描述
	 */ 
	private String description;
	
	/**
	 *  状态
	 */ 
	private Integer status;

	public TimingSchedule() {}

	public TimingSchedule(Long id, Integer status) {
		this.id = id;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}


	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}




	
}