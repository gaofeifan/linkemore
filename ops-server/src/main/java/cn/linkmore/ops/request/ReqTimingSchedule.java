package cn.linkmore.ops.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author   GFF
 * @Date     2018年5月28日
 * @Version  v2.0
 */
@ApiModel("定时调度请求bean")
public class ReqTimingSchedule {
	/**
	 *  id
	 */ 
	@ApiModelProperty(value = "主键",required = false)
	private Long id;
	
	/**
	 *  名称
	 */ 
	@ApiModelProperty(value = "主键",required = false)
	private String name;
	
	/**
	 * 	路径
	 */ 
	@ApiModelProperty(value = "主键",required = false)
	private String path;
	
	/**
	 *  调用方法的名称
	 */ 
	@ApiModelProperty(value = "主键",required = false)
	private String methodName;
	
	/**
	 *  任务时间
	 */ 
	@ApiModelProperty(value = "主键",required = false)
	private String cron;
	
	/**
	 *  描述
	 */ 
	@ApiModelProperty(value = "主键",required = false)
	private String desc;
	
	/**
	 *  状态
	 */ 
	@ApiModelProperty(value = "状态 0 开启 1 禁用 ",required = true)
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
