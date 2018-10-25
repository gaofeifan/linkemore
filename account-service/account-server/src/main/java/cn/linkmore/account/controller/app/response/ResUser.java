package cn.linkmore.account.controller.app.response;

import java.io.Serializable;
import java.util.List;

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
	private String realname;
	@ApiModelProperty(value = "性别")
	private Integer sex;
	@ApiModelProperty(value = "极光别名")
	private String alias;
	@ApiModelProperty(value = "用户类型")
	private Short type;
	@ApiModelProperty(value = "极光标签")
	private List<String> tags; 
	
	@ApiModelProperty(value = "是否新用户")
	private Short newUserFlag = 0;
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
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	public Short getNewUserFlag() {
		return newUserFlag;
	}
	public void setNewUserFlag(Short newUserFlag) {
		this.newUserFlag = newUserFlag;
	} 
	
}
