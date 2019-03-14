package cn.linkmore.account.controller.app.request;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("修改密码")
public class ReqAuthEditPW {
	@ApiModelProperty(value = "token发送短信成功后生成的", required = true)
	@NotBlank(message="token不能为空") 
	@Length(min=32,max=32,message="token长度应为32位")
	private String token;
	@ApiModelProperty(value = "手机号，必填", required = true)
	@NotBlank(message="手机号不能为空") 
	@Pattern(regexp="^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\\d{8})$", message="无效手机号") 
	private String mobile;
	@ApiModelProperty(value = "密码，必填", required = true)
	@Pattern(regexp="^[0-9a-zA-Z]{6,20}$|^(?=.*\\d+)(?!.*?([\\d])\\1{5})[\\d]{6}$", message="密码不能为连续数字/字母") 
	@NotBlank(message="密码不能为空") 
	private String passwrod;
	@Pattern(regexp="^[0-9a-zA-Z]{6,20}$|^(?=.*\\d+)(?!.*?([\\d])\\1{5})[\\d]{6}$", message="密码不能为连续数字/字母") 
	@ApiModelProperty(value = "确认密码，必填", required = true)
	@NotBlank(message="确认密码不能为空") 
	private String repasswrod;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPasswrod() {
		return passwrod;
	}
	public void setPasswrod(String passwrod) {
		this.passwrod = passwrod;
	}
	public String getRepasswrod() {
		return repasswrod;
	}
	public void setRepasswrod(String repasswrod) {
		this.repasswrod = repasswrod;
	}
	
}
