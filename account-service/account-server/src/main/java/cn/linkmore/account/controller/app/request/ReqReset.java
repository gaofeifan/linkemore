package cn.linkmore.account.controller.app.request;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

@ApiModel("重置密码")
public class ReqReset {
	@NotBlank(message="账号不能为空") 
	@ApiModelProperty(value="account")
	private String account;
	@NotBlank(message="token不能为空") 
	@Size(min=32, max=32,message="token长度为32位有效字符串")  
	@ApiModelProperty(value="token")
	private String token;
	
	@ApiModelProperty(value = "密码，必填", required = true)
	@Pattern(regexp="^[0-9a-zA-Z`~!@#$^&*()=|{}':;',\\\\[\\\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]{6,20}$|^(?=.*\\d+)(?!.*?([\\d])\\1{5})[\\d]{6}$", message="密码不能为连续数字/字母") 
	@NotBlank(message="密码不能为空") 
	private String password;
	@Pattern(regexp="^[0-9a-zA-Z`~!@#$^&*()=|{}':;',\\\\[\\\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]{6,20}$|^(?=.*\\d+)(?!.*?([\\d])\\1{5})[\\d]{6}$", message="密码不能为连续数字/字母") 
	@ApiModelProperty(value = "确认密码，必填", required = true)
	@NotBlank(message="确认密码不能为空") 
	private String repassword;
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
}
