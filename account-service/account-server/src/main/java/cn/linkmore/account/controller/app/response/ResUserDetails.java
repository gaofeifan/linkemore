package cn.linkmore.account.controller.app.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户详情")
public class ResUserDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private Long id;
	@ApiModelProperty(value = "性别 0未知，1男，2女")
	private Integer sex;
	
	@ApiModelProperty(value = "车辆品牌-型号")
	private String brandModel;
	
	@ApiModelProperty(value = "用户昵称")
	private String nickname;
	
	@ApiModelProperty(value = "手机号")
	private String mobile;
	
	@ApiModelProperty(value = "微信openId")
	private String wechatId;
	
	@ApiModelProperty(value = "微信昵称")
	private String wechatName;
	
	@ApiModelProperty(value = "微信头像")
	private String wechatUrl;

	@ApiModelProperty(value = "账户名称")
	
	private String realname;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getBrandModel() {
		return brandModel;
	}

	public void setBrandModel(String brandModel) {
		this.brandModel = brandModel;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getWechatName() {
		return wechatName;
	}

	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}

	public String getWechatUrl() {
		return wechatUrl;
	}

	public void setWechatUrl(String wechatUrl) {
		this.wechatUrl = wechatUrl;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	
}
