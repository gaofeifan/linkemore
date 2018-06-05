package cn.linkmore.order.response;

import java.math.BigDecimal;

public class ResOrderConfirm { 
	private BigDecimal amount; 
	private String number; 
	private Short payType; 
	private String alipay; 
	private ResOrderWeixin weixin; 
	private String apple;
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
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
	public ResOrderWeixin getWeixin() {
		return weixin;
	}
	public void setWeixin(ResOrderWeixin weixin) {
		this.weixin = weixin;
	}
	public String getApple() {
		return apple;
	}
	public void setApple(String apple) {
		this.apple = apple;
	}
	 
}
