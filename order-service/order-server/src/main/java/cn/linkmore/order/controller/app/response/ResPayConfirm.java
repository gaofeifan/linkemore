package cn.linkmore.order.controller.app.response;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("确认支付")
public class ResPayConfirm { 
	@ApiModelProperty(value = "支付金额")
	private Double amount;
	@ApiModelProperty(value = "流水号")
	private String number;
	@ApiModelProperty(value = "支付分类[0账户余额，1支付宝，2微信，3银联Apple Pay,4小程序,5银联云闪付,6华为Pay、7小米Pay]")
	private Short payType;
	@ApiModelProperty(value = "支付宝支付")
	private String alipay;
	@ApiModelProperty(value = "微信支付")
	private ResPayWeixin weixin;
	@ApiModelProperty(value = "苹果支付")
	private String apple;
	@ApiModelProperty(value = "银联支付")
	private String union;
	@ApiModelProperty(value = "小程序微信支付")
	private ResPayWeixinMini weixinMini;
	
	public Double getAmount() { 
		if(amount==null) {
			amount = new Double(0D);
		}
		BigDecimal b = new BigDecimal(this.amount); 
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
	}
	public void setAmount(Double amount) {
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
	public String getAlipay() {
		return alipay;
	}
	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}
	public ResPayWeixin getWeixin() {
		return weixin;
	}
	public void setWeixin(ResPayWeixin weixin) {
		this.weixin = weixin;
	}
	public String getApple() {
		return apple;
	}
	public void setApple(String apple) {
		this.apple = apple;
	}
	public ResPayWeixinMini getWeixinMini() {
		return weixinMini;
	}
	public void setWeixinMini(ResPayWeixinMini weixinMini) {
		this.weixinMini = weixinMini;
	}
	public String getUnion() {
		return union;
	}
	public void setUnion(String union) {
		this.union = union;
	} 
	
}
