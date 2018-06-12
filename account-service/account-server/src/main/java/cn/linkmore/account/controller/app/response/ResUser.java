package cn.linkmore.account.controller.app.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户信息")
public class ResUser implements Serializable {
	/**
	 * serial id
	 */
	private static final long serialVersionUID = 6671933843096649424L;
	@ApiModelProperty(value = "唯一标识")
	private Long id;
	@ApiModelProperty(value = "手机号") 
	private String mobile;
	@ApiModelProperty(value = "令牌")
	private String token;
	@ApiModelProperty(value = "账号名称")
	private String accountName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	 
}
