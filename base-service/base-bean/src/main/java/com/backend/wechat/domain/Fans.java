package com.backend.wechat.domain;

import java.io.Serializable;
import java.util.Date;
 
 
public class Fans  implements Serializable{
	/**
	 * serial id
	 */
	private static final long serialVersionUID = -5739923779035665437L;
	/**
	 * openId，每个用户都是唯一的
	 */
	 
	private String id; 
	/**
	 * 订阅时间
	 */
	 
	private String subscribeTime;
	
	
	 
	private Integer subscribeStatus;
	/**
	 * 昵称
	 */
	 
	private String nickname; 
	/**
	 * 性别 0-女；1-男；2-未知
	 */
	 
	private Integer sex;  
	/**
	 * 头像
	 */
	 
	private String headimagurl;  
	
	
	 
	private Date createTime;
	
	
	 
	private Date updateTime;
	
	 
	private String adcode;
	
	 
	private String city;
	 
	 
	private String district;
	
	
	 
	private String unionid;

	/**
	 * user对应的ID
	 */
	 
	private Long uid; 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	} 

	public String getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
 
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
 
	 
	public String getHeadimagurl() {
		return headimagurl;
	}

	public void setHeadimagurl(String headimagurl) {
		this.headimagurl = headimagurl;
	}  

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getSubscribeStatus() {
		return subscribeStatus;
	}

	public void setSubscribeStatus(Integer subscribeStatus) {
		this.subscribeStatus = subscribeStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getAdcode() {
		return adcode;
	}

	public void setAdcode(String adcode) {
		this.adcode = adcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	 
}
