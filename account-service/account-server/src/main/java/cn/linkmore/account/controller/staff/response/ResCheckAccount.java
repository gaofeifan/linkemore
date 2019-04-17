package cn.linkmore.account.controller.staff.response;

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("校验手机号")
public class ResCheckAccount {

	@ApiModelProperty(value="手机号")
	private String mobile;
	
	@ApiModelProperty(value="账号名称")
	private String accountName;
	
	@ApiModelProperty(value="绑定状态 0 未绑定 1已绑定")
	private short bindStatus = 0;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		if(StringUtils.isNotBlank(mobile)) {
			bindStatus = 1;
		}
		this.mobile = mobile;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
}
