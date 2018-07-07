package cn.linkmore.monitor.entity.vo;

import java.io.Serializable;

import cn.linkmore.monitor.entity.core.ErrCode;

/**
 * 接口凭证
 * @author liwl
 * @version 1.0
 *
 */
public class Token implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8901475040211577659L;
	// 接口访问凭证
	private String accessToken;
	// 凭证有效期，单位：秒
	private int expiresIn;

	//oauth2.0
	//刷新token
	private String refreshToken;
	private String openid;
	private String scope;

	private Integer errcode;//错误编码
	private String errmsg;//错误消息

	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public Integer getErrcode() {
		return errcode;
	}
	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
		this.errmsg = ErrCode.errMsg(errcode);
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
