package cn.linkmore.order.controller.app.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("确认支付请求")
public class ReqPayConfirm {
	@ApiModelProperty(value = "订单ID")
	private Long orderId;
	
	@ApiModelProperty(value = "支付类型[1支付宝、2微信、3ApplePay、5银联云闪付、6华为Pay、7小米Pay]")
	private Short payType;
	
	@ApiModelProperty(value = "停车券ID")
	private Long couponId;
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Short getPayType() {
		return payType;
	}
	public void setPayType(Short payType) {
		this.payType = payType;
	}
	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	
}
