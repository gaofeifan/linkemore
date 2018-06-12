package cn.linkmore.order.controller.app.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("支付结果校验")
public class ReqPayResult {
	@ApiModelProperty(value = "订单ID")
	private Long orderId;
	@ApiModelProperty(value = "流水号")
	private String number;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	} 
}
