package cn.linkmore.enterprise.controller.app.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车位信息")
public class OwnerStall {
	
	@ApiModelProperty(value = "车位Id")
	private Long stallId;
	
	@ApiModelProperty(value = "车位号")
	private String stallName;
	
	@ApiModelProperty(value = "绑定手机号")
	private String mobile;
	
	@ApiModelProperty(value = "车牌号")
	private String plate;
	
	@ApiModelProperty(value = "车位开始时间")
	private String startTime;

	@ApiModelProperty(value = "车位结束时间")
	private String endTime;

	

	public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}

	public String getStallName() {
		return stallName;
	}

	public void setStallName(String stallName) {
		this.stallName = stallName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


}
