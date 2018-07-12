package cn.linkmore.account.response;

import java.util.Date;

import cn.linkmore.annotation.GColumn;

public class ResUser {

	@GColumn
    private Long id;

	@GColumn
    private String username;

	@GColumn
    private String password;

	@GColumn
    private String nickname;

	@GColumn
    private Integer sex;

    private String status;

    @GColumn
    private String userType;

    @GColumn
    private String enterpriseName;

    @GColumn
    private String entAdminName;

    @GColumn
    private String industry;

    @GColumn
    private Long parentId;

    @GColumn
    private String mobile;

    @GColumn
    private String province;

    @GColumn
    private String city;

    @GColumn
    private String address;

    @GColumn
    private String email;

    @GColumn
    private String wechat;

    @GColumn
    private String qq;

    @GColumn
    private String icon;

    @GColumn
    private Date lastLoginTime;

    @GColumn
    private Date createTime;

    @GColumn
    private Date updateTime;

    @GColumn
    private Long hobbyBrandId;

    @GColumn
    private Short isAppRegister;

    @GColumn
    private Short isWechatBind;

    @GColumn
    private Date appRegisterTime;

    @GColumn
    private Date wechatBindTime;

    @GColumn
    private Short type;

    @GColumn
    private Integer loginCount;

    @GColumn
    private Integer orderCount;

    @GColumn
    private Integer completeOrderCount;
    
    private String realname;

    private Short fansStatus;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName == null ? null : enterpriseName.trim();
    }

    public String getEntAdminName() {
        return entAdminName;
    }

    public void setEntAdminName(String entAdminName) {
        this.entAdminName = entAdminName == null ? null : entAdminName.trim();
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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

    public Long getHobbyBrandId() {
        return hobbyBrandId;
    }

    public void setHobbyBrandId(Long hobbyBrandId) {
        this.hobbyBrandId = hobbyBrandId;
    }

    public Short getIsAppRegister() {
        return isAppRegister;
    }

    public void setIsAppRegister(Short isAppRegister) {
        this.isAppRegister = isAppRegister;
    }

    public Short getIsWechatBind() {
        return isWechatBind;
    }

    public void setIsWechatBind(Short isWechatBind) {
        this.isWechatBind = isWechatBind;
    }

    public Date getAppRegisterTime() {
        return appRegisterTime;
    }

    public void setAppRegisterTime(Date appRegisterTime) {
        this.appRegisterTime = appRegisterTime;
    }

    public Date getWechatBindTime() {
        return wechatBindTime;
    }

    public void setWechatBindTime(Date wechatBindTime) {
        this.wechatBindTime = wechatBindTime;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getCompleteOrderCount() {
        return completeOrderCount;
    }

    public void setCompleteOrderCount(Integer completeOrderCount) {
        this.completeOrderCount = completeOrderCount;
    }

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Short getFansStatus() {
		return fansStatus;
	}

	public void setFansStatus(Short fansStatus) {
		this.fansStatus = fansStatus;
	}

    
    
}
