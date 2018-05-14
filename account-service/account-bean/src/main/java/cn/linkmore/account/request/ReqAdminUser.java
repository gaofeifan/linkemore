package cn.linkmore.account.request;

import cn.linkmore.annotation.GColumn;
import cn.linkmore.annotation.GTable;

@GTable(vlaue="t_admin_user")
public class ReqAdminUser {

	@GColumn
	private String nickname;

	@GColumn
	private Integer sex;

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
	
	
}
