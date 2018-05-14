package cn.linkmore.account.request;

import java.io.Serializable;

public class ReqBind implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 手机号
	 */
	private String mobile;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
