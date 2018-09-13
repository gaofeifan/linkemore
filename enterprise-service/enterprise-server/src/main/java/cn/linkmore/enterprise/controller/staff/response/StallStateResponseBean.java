package cn.linkmore.enterprise.controller.staff.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 状态车位列表响应
 * 
 * @author changlei
 * @date 2018年09月12日
 */

@ApiModel("状态车位列表响应封装Bean")
public class StallStateResponseBean implements Serializable,Comparable<StallStateResponseBean>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "车位id")
	private Long id;

	@ApiModelProperty(value = "订单id")
	private Long orderId;

	@ApiModelProperty(value = "车位名称")
	private String stallName;

	@ApiModelProperty(value = "锁状态(1升起、2降下)")
	private int lockStatus;

	@ApiModelProperty(value = "车位状态(1,2在线、 3预下线、4下线)")
	private int status;

	@ApiModelProperty(value = "车牌号")
	private String plateNum;

	@ApiModelProperty(value = "类型(全部的列表-判断类型[1,预约、2,空闲、3,异常])")
	private int type;
	
	@ApiModelProperty(value = "订单状态[0正常1挂起2关闭]")
	private Short orderStatus;
	
	@ApiModelProperty(value = "订单车位状态[0未降锁1已降锁]")
	private Short orderLockStatus = 0;
	
	@ApiModelProperty("车牌锁")
	private String lockSn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStallName() {
		return stallName;
	}

	public void setStallName(String stallName) {
		this.stallName = stallName;
	}

	public int getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(int lockStatus) {
		this.lockStatus = lockStatus;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPlateNum() {
		return plateNum;
	}

	public void setPlateNum(String plateNum) {
		this.plateNum = plateNum;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getLockSn() {
		return lockSn;
	}

	public void setLockSn(String lockSn) {
		this.lockSn = lockSn;
	} 
	@Override
	public int compareTo(StallStateResponseBean o) {
		return this.getStallName().compareTo(o.getStallName()); 
	}

	public Short getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Short orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Short getOrderLockStatus() {
		return orderLockStatus;
	}

	public void setOrderLockStatus(Short orderLockStatus) {
		this.orderLockStatus = orderLockStatus;
	} 
}
