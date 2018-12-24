package cn.linkmore.order.controller.h5.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("jsapi发起参数")
public class ResPayParm {
	
	@ApiModelProperty(value = "timeStamp时间戳")
	private String timeStamp;
	@ApiModelProperty(value = "package包id")
	private String pack;
	@ApiModelProperty(value = "nonceStr随机串 ")
	private String nonceStr;
	@ApiModelProperty(value = "paySign微信签名")
	private String paySign;
	@ApiModelProperty(value = "appId公众号名称")
	private String appId;
	
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getPaySign() {
		return paySign;
	}
	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	

}
