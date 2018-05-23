package cn.linkmore.user.request;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
 
@ApiModel("验证码发送请求")
public class ReqAuthSend {
	
	@ApiModelProperty(value = "手机号，必填", required = true)
	@NotBlank(message="手机号不能为空") 
	@Pattern(regexp="/^(((13[0-9]{1})|(18[0-9]{1})|(17[0-9]{1})|(15[0-9]{1})|(14[0-9]{1}))+\\d{8})$/", message="无效手机号") 
	private String mobile;
	
	@ApiModelProperty(value = "时间戳，必填", required = true)
	@Range(min=1526992926, max=1607702400,message="时间戳不匹配")
	private Long timestamp;
	
	@ApiModelProperty(value = "加密字符串，必填", required = true)
	@Size(min=32, max=32,message="token长度为32位有效字符串") 
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
