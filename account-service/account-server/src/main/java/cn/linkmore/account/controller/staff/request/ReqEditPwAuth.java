package cn.linkmore.account.controller.staff.request;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("修改账户")
public class ReqEditPwAuth {

	@ApiModelProperty(value = "账户", required = true)
	@NotBlank(message="账号不能为空") 
	private String account;
	@ApiModelProperty(value = "密码，必填", required = true)
	@Pattern(regexp="^[0-9a-zA-Z`~!@#$^&*()=|{}':;',\\\\[\\\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]{6,20}$|^(?=.*\\d+)(?!.*?([\\d])\\1{5})[\\d]{6}$", message="密码不能为连续数字/字母") 
	@NotBlank(message="密码不能为空") 
	private String password;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
