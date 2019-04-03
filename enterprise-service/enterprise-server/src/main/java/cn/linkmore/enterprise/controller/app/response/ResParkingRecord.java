package cn.linkmore.enterprise.controller.app.response;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("停车记录")
public class ResParkingRecord {
	@ApiModelProperty("车区名称")
	private String preName;
	
	@ApiModelProperty("车区id")
	private Long preId;
	
	@ApiModelProperty("车位名称")
	private String stallName;
	
	@ApiModelProperty("车位id")
	private Long stallId;
	
	@ApiModelProperty("车位类型")
	private Short stallAuthType;
	
	@ApiModelProperty("开始时间")
	private Date startTime;
	
	@ApiModelProperty("结束时间")
	private Date endTime;
	
	@ApiModelProperty("使用时长")
	private String serviceTime;
	
	@ApiModelProperty("用户名称")
	private String username;
	
	@ApiModelProperty("用户手机号")
	private String mobile;

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public String getStallName() {
		return stallName;
	}

	public void setStallName(String stallName) {
		this.stallName = stallName;
	}

	public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}

 	public Short getStallAuthType() {
		return stallAuthType;
	}

	public void setStallAuthType(Short stallAuthType) {
		this.stallAuthType = stallAuthType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}

