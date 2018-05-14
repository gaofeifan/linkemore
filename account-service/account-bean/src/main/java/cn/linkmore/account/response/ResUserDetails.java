package cn.linkmore.account.response;

import java.io.Serializable;

import cn.linkmore.annotation.GColumn;
import cn.linkmore.annotation.GTable;

@GTable(vlaue="t_user_vehicle")
public class ResUserDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@GColumn
	private Long id;
	
	@GColumn
	private Integer sex;
	
	@GColumn
	private String brandModel;
	
	@GColumn
	private String nickname;
	
	@GColumn
	private String mobile;
	
	@GColumn
	private String wechatId;
	
	@GColumn
	private String wechatName;
	
	@GColumn
	private String wechatUrl;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

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
	
}
