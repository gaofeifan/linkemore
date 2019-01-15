package cn.linkmore.enterprise.controller.app.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="当前长租用户订单")
public class ResCurrentOwner {
	
	/**
	 *  有订单,无订单
	 */ 
	@ApiModelProperty(value="长租车位是否有订单 true有订单,false无订单")
	private Boolean status;
	
	/**
	 *  车位id
	 */ 
	@ApiModelProperty(value="车位id")
	private Long stallId;
	
	@ApiModelProperty(value="车位名称")
	private String stallName;

	@ApiModelProperty(value="车区id")
	private Long preId;

	@ApiModelProperty(value="车区名称")
	private String preName;

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
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

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}
}
