package cn.linkmore.account.request;

import java.util.List;

public class ReqUserResetPW {
	private List<Long> ids;
	
	private String passwrod;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public String getPasswrod() {
		return passwrod;
	}

	public void setPasswrod(String passwrod) {
		this.passwrod = passwrod;
	}
	
	
}
