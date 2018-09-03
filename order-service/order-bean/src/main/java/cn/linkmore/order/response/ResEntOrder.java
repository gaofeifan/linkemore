package cn.linkmore.order.response;

import java.util.Date;

public class ResEntOrder {

	private Long orderId;
	
	private String orderNo;
	
	private Date beginTime;
	
	private Date endTime;
	
	private Date lockDownTime;

	private String plate;
	
	private String mobile;
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getLockDownTime() {
		return lockDownTime;
	}

	public void setLockDownTime(Date lockDownTime) {
		this.lockDownTime = lockDownTime;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
