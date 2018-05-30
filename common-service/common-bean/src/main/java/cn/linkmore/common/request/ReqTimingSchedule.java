package cn.linkmore.common.request;


/**
 * 
 * @author   GFF
 * @Date     2018年5月28日
 * @Version  v2.0
 */
public class ReqTimingSchedule {
	/**
	 *  id
	 */ 
	private Long id;
	
	/**
	 *  名称
	 */ 
	private String name;
	
	/**
	 * 	路径
	 */ 
	private String path;
	
	/**
	 *  调用方法的名称
	 */ 
	private String methodName;
	
	/**
	 *  任务时间
	 */ 
	private String cron;
	
	/**
	 *  描述
	 */ 
	private String desc;
	
	/**
	 *  状态
	 */ 
	private Integer status;

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
