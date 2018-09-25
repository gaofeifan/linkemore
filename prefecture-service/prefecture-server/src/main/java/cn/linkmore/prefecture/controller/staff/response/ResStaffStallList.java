package cn.linkmore.prefecture.controller.staff.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("管理版车位列表响应数据")
public class ResStaffStallList {

	@ApiModelProperty(value="车位id")
	private Long stallId;
	
	@ApiModelProperty(value="车位名称")
	private String stallName;
	
	@ApiModelProperty(value="车牌号")
	private String plateNo;
	
	@ApiModelProperty(value="车位状态 状态:1，空闲；2，使用中；4，下线  ")
	private Integer status;
	
	@ApiModelProperty(value="锁状态 1，升起；2，降下  3使用中")
	private Integer lockStatus;
	
	/**
	 *  车位锁异常状态  true 正常车位  false 异常车位
	 */ 
	@ApiModelProperty(value="车位锁异常状态  true 正常车位  false 异常车位")
	private boolean excStatus = true;
	
	@ApiModelProperty(value = "是否被指定 0已经指定，1未指定")
	private int assignStatus;

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

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}

	public boolean isExcStatus() {
		return excStatus;
	}

	public void setExcStatus(boolean excStatus) {
		this.excStatus = excStatus;
	}

	public int getAssignStatus() {
		return assignStatus;
	}

	public void setAssignStatus(int assignStatus) {
		this.assignStatus = assignStatus;
	}
}
