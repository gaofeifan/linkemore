package cn.linkmore.account.controller.open.requset;

public class ReqOpenUser {
	
	//唯一标识
	String accountName;	
	//手机号
	String phone;
	//昵称
	String nickName;
	//性别(1 男 ,2 女)
	Integer sex;
	//头像
	String icon;
	
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}

	
}
