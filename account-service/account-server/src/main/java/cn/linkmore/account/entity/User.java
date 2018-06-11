package cn.linkmore.account.entity;

import java.util.Date;

import cn.linkmore.annotation.GColumn;
import cn.linkmore.annotation.GTable;

/**
 * 用户
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@GTable(vlaue="t_user")
public class User {
    private Long id;

    private String username;

    private String password;

    private String nickname;

    private Short sex;

    private String status;

    private String userType;

    private String enterpriseName;

    private String entAdminName;

    private String industry;

    private Long parentId;

    private String mobile;

    private String province;

    private String city;

    private String address;

    private String email;

    private String wechat;

    private String qq;

    private String icon;

    private Date lastLoginTime;

    private Date createTime;

    private Date updateTime;

    private Long hobbyBrandId;

    private Short isAppRegister;

    private Short isWechatBind;

    private Date appRegisterTime;

    private Date wechatBindTime;

    private Short type;

    private Integer loginCount;

    private Integer orderCount;
    
    private Integer completeOrderCount;
    
    private String accountName;

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

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
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

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
    
    
}