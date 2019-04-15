package cn.linkmore.account.controller.staff.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("账号密码登录")
public class ReqLoginPw {

	@ApiModelProperty(value="账号名称")
	private String accountName;
	
	@ApiModelProperty(value="密码")
	private String passwrod;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPasswrod() {
		return passwrod;
	}

	public void setPasswrod(String passwrod) {
		this.passwrod = passwrod;
	}
}
