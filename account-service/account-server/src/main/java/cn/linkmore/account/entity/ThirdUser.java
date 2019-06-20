package cn.linkmore.account.entity;

public class ThirdUser {
    private Long id;
    
    //第三方用户id
    private Long thirdUserId;
    //唯一标识
    private String accountName;

    private String phone;

    private String nickname;

    private Long userId;

    private String appId;
    
    public Long getThirdUserId() {
		return thirdUserId;
	}

	public void setThirdUserId(Long thirdUserId) {
		this.thirdUserId = thirdUserId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }
}