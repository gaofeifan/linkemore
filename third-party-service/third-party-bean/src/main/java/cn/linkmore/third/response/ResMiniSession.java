package cn.linkmore.third.response;

import java.io.Serializable;

public class ResMiniSession implements Serializable {
	private static final long serialVersionUID = 8180867875988641112L;
	private String session_key;
	private String openid;
	private String unionid;

	public String getSession_key() {
		return session_key;
	}

	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

}