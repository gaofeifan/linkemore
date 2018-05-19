package cn.linkmore.bean.common.security;

import java.io.Serializable;

/**
 * 登录令牌信息
 * @author liwl
 * @version 1.0
 *
 */
public class Token implements Serializable{
	/**
	 * serial id
	 */
	private static final long serialVersionUID = 9006558116290567282L; 
	/**
	 * access token
	 */
	private String accessToken; 
	/**
	 * 时间
	 */
	private Long timestamp;
	
	/**
	 * 客户端[0:微信小程序,1:android,2:ios]
	 */
	private Short client; 
	
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	 
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public Short getClient() {
		return client;
	}
	public void setClient(Short client) {
		this.client = client;
	} 
	
	
}
