package cn.linkmore.user.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("支付[微信支付]")
public class ResPayWeixin {
	@ApiModelProperty(value = "应用ID")
	private String appid;
	@ApiModelProperty(value = "商户号")
	private String partnerid;
	@ApiModelProperty(value = "会话ID")
	private String prepayid; 
	@ApiModelProperty(value = "随机串")
	private String noncestr;
	@ApiModelProperty(value = "时间戳")
	private String timestamp; 
	@ApiModelProperty(value = "签名")
	private String sign;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getPartnerid() {
		return partnerid;
	}
	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}
	public String getPrepayid() {
		return prepayid;
	}
	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}
	public String getNoncestr() {
		return noncestr;
	}
	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
