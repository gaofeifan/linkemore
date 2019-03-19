package cn.linkmore.account.request;

import java.util.List;

public class ReqUserResetPW {
	private List<Long> ids;
	
	private String password;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

 	
	
}
