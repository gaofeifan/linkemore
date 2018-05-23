package cn.linkmore.user.request;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
 
@ApiModel("手机绑定请求")
public class ReqMobileBind {
	@NotBlank(message="手机号不能为空") 
	@Pattern(regexp="/^(((13[0-9]{1})|(18[0-9]{1})|(17[0-9]{1})|(15[0-9]{1})|(14[0-9]{1}))+\\d{8})$/", message="无效手机号") 
	@ApiModelProperty(value = "手机号", required = true)
	private String mobile;
	 
	@Range(min=1000, max=9999,message="验证码是4位有效数字")
	@NotBlank(message="验证码不能为空") 
	@ApiModelProperty(value = "短信验证码", required = true)
	private String code;
	 
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
	
}
