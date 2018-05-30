package cn.linkmore.user.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("支付[支付宝]")
public class ResPayAlipay { 
	
	@ApiModelProperty(value = "订单信息")
	private String orderInfo;

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	} 
	
}
