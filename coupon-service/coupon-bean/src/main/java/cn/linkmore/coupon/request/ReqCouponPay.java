package cn.linkmore.coupon.request;

import java.math.BigDecimal;

public class ReqCouponPay {
	private Long couponId;
	private BigDecimal orderAmount;
	private BigDecimal usedAmount; 
	
	public ReqCouponPay(Long couponId,BigDecimal orderAmount,BigDecimal usedAmount) {
		this.couponId = couponId;
		this.orderAmount = orderAmount;
		this.usedAmount = usedAmount;
	}
	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public BigDecimal getUsedAmount() {
		return usedAmount;
	}
	public void setUsedAmount(BigDecimal usedAmount) {
		this.usedAmount = usedAmount;
	} 
	
}
