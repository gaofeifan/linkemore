package cn.linkmore.order.controller.app.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel("支付[微信小程序 微信支付]")
public class ResPayWeixinMini {

	@ApiModelProperty(value = "appId")
	private String id;

	@ApiModelProperty(value = "nonceStr")
	private String nonce;

	@ApiModelProperty(value = "package")
	private String pack;

	@ApiModelProperty(value = "paySign")
	private String sign;

	@ApiModelProperty(value = "signType")
	private String type;

	@ApiModelProperty(value = "timeStamp")
	private String stamp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStamp() {
		return stamp;
	}

	public void setStamp(String stamp) {
		this.stamp = stamp;
	}

}
