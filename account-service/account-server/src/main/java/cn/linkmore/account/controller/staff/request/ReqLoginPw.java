package cn.linkmore.account.controller.staff.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("账号密码登录")
public class ReqLoginPw {

	@ApiModelProperty(value="账号名称")
	private String accountName;
	
	@ApiModelProperty(value="密码")
	private String password;
	@ApiModelProperty(value="swagger登录不需要传参 ,其他方式传 1 ",required=false)
	private Short type;
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}
}
