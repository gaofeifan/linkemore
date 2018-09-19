package cn.linkmore.account.controller.staff.response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
@Api("管理版用户信息")
public class ResAdmin {
	@ApiModelProperty(value = "唯一标识")
	private Long id;
	@ApiModelProperty(value = "手机号") 
	private String mobile;
	@ApiModelProperty(value = "令牌")
	private String token;
	@ApiModelProperty(value = "名称")
	private String realname;
	@ApiModelProperty(value = "状态,0禁用，1启用")
	private Short status;
	@ApiModelProperty(value = "角色类型 admin-all 超级管理员 admin-city 城市管理员   admin-pre 车区管理员 ")
	private String type;
	@ApiModelProperty(value = "运营是否可以查看 true 展开  false 不展开")
	private Boolean isOperation = false;
	
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
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getIsOperation() {
		return isOperation;
	}
	public void setIsOperation(Boolean isOperation) {
		this.isOperation = isOperation;
	}
}
