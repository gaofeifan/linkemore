package cn.linkmore.enterprise.controller.app.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车位锁信息")
public class OwnerStall {
	
	@ApiModelProperty(value = "车位Id")
	private Long stallId;
	
	@ApiModelProperty(value = "车位锁编码")
	private String lockSn;
	
	@ApiModelProperty(value = "车位号")
	private String stallName;
	
	@ApiModelProperty(value = "绑定手机号")
	private String mobile;
	
	@ApiModelProperty(value = "车牌号")
	private String plate;
	
	@ApiModelProperty(value = "车位锁操作状态")
	private Long lockStatus;
	
	@ApiModelProperty(value = "使用状态")
	private Long status;
	
	@ApiModelProperty(value = "车位开始时间")
	private String startTime;

	@ApiModelProperty(value = "车位结束时间")
	private String endTime;

	@ApiModelProperty(value = "车位位置描述")
	private String stallLocal;
	
	@ApiModelProperty(value = "路线指引")
	private String routeGuidance;
	
	@ApiModelProperty(value = "车位导航图")
	private String imageUrl;
	
	public String getLockSn() {
		return lockSn;
	}

	public void setLockSn(String lockSn) {
		this.lockSn = lockSn;
	}

	public Long getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(Long lockStatus) {
		this.lockStatus = lockStatus;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getStallLocal() {
		return stallLocal;
	}

	public void setStallLocal(String stallLocal) {
		this.stallLocal = stallLocal;
	}

	public String getRouteGuidance() {
		return routeGuidance;
	}

	public void setRouteGuidance(String routeGuidance) {
		this.routeGuidance = routeGuidance;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

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
