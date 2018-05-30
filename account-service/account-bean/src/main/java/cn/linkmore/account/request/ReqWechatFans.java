package cn.linkmore.account.request;

import java.util.Date;
public class ReqWechatFans {
	private String id;

	private Integer subscribeStatus;

	private String subscribeTime;

	private String nickname;

	private Integer sex;

	private Long uid;

	private Date createTime;

	private Date updateTime;

	private String headimagurl;

	private String adcode;

	private String city;

	private String district;

	private String unionid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public Integer getSubscribeStatus() {
		return subscribeStatus;
	}

	public void setSubscribeStatus(Integer subscribeStatus) {
		this.subscribeStatus = subscribeStatus;
	}

	public String getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime == null ? null : subscribeTime.trim();
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname == null ? null : nickname.trim();
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
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

	public String getHeadimagurl() {
		return headimagurl;
	}

	public void setHeadimagurl(String headimagurl) {
		this.headimagurl = headimagurl == null ? null : headimagurl.trim();
	}

	public String getAdcode() {
		return adcode;
	}

	public void setAdcode(String adcode) {
		this.adcode = adcode == null ? null : adcode.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district == null ? null : district.trim();
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid == null ? null : unionid.trim();
	}
}
