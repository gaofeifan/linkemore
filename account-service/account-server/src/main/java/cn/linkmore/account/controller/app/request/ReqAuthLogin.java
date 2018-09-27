package cn.linkmore.account.controller.app.request;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("登录请求")
public class ReqAuthLogin {
	@ApiModelProperty(value = "手机号，必填", required = true)
	@NotBlank(message="手机号不能为空") 
	@Pattern(regexp="^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\\d{8})$", message="无效手机号") 
	private String mobile;
	@ApiModelProperty(value = "短信验证码", required = true)
	@Range(min=1000, max=9999,message="验证码是4位有效数字")
	@NotBlank(message="验证码不能为空") 
	private String code;
	@ApiModelProperty(value="swagger登录不需要传参 ,其他方式传 1 ",required=false)
	private Short type;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	} 
}
