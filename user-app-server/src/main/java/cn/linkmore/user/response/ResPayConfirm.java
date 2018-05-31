package cn.linkmore.user.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("确认支付")
public class ResPayConfirm {
	@ApiModelProperty(value = "支付金额")
	private String amount;
	@ApiModelProperty(value = "流水号")
	private String number;
	@ApiModelProperty(value = "支付分类[0账户余额，1支付宝，2微信，3银联]")
	private Short payType;
	@ApiModelProperty(value = "支付宝支付")
	private ResPayAlipay alipay;
	@ApiModelProperty(value = "微信支付")
	private ResPayWeixin weixin;
	@ApiModelProperty(value = "苹果支付")
	private ResPayApple apple;
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}  
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Short getPayType() {
		return payType;
	}
	public void setPayType(Short payType) {
		this.payType = payType;
	}
	public ResPayAlipay getAlipay() {
		return alipay;
	}
	public void setAlipay(ResPayAlipay alipay) {
		this.alipay = alipay;
	}
	public ResPayWeixin getWeixin() {
		return weixin;
	}
	public void setWeixin(ResPayWeixin weixin) {
		this.weixin = weixin;
	}
	public ResPayApple getApple() {
		return apple;
	}
	public void setApple(ResPayApple apple) {
		this.apple = apple;
	} 
}
