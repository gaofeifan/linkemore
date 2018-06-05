package cn.linkmore.order.response;

import java.math.BigDecimal;
import java.util.Date;

public class ResOrderCheckout { 
	private Long orderId; 
	private String prefectureName; 
	private String stallName; 
	private String plateNumber; 
	private Date startTime; 
	private Date endTime; 
	private Integer parkingTime; 
	private Integer couponCount; 
	private Short payType; 
	private BigDecimal totalAmount; 
	private BigDecimal accountAmount;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getPrefectureName() {
		return prefectureName;
	}
	public void setPrefectureName(String prefectureName) {
		this.prefectureName = prefectureName;
	}
	public String getStallName() {
		return stallName;
	}
	public void setStallName(String stallName) {
		this.stallName = stallName;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
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
	public Integer getParkingTime() {
		return parkingTime;
	}
	public void setParkingTime(Integer parkingTime) {
		this.parkingTime = parkingTime;
	}
	public Integer getCouponCount() {
		return couponCount;
	}
	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}
	public Short getPayType() {
		return payType;
	}
	public void setPayType(Short payType) {
		this.payType = payType;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getAccountAmount() {
		return accountAmount;
	}
	public void setAccountAmount(BigDecimal accountAmount) {
		this.accountAmount = accountAmount;
	} 
}
