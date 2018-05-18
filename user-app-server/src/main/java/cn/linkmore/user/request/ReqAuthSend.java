package cn.linkmore.user.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("验证码发送请求")
public class ReqAuthSend {
	@ApiModelProperty(value = "手机号，必填", required = true)
	private String mobile;
	@ApiModelProperty(value = "时间，必填", required = true)
	private Long timestamp;
	@ApiModelProperty(value = "加密字符串，必填", required = true)
	private String token;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	} 
}
