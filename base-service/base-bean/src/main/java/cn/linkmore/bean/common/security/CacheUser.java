package cn.linkmore.bean.common.security;

import java.io.Serializable;

/**
 * 统一鉴权用户
 * @author liwenlong
 * @version 2.0
 *
 */
public class CacheUser implements Serializable {  
	/**
	 *  
	 */ 
	private static final long serialVersionUID = 1L;
	private Long id; 
	private String mobile; 
	private String token; 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	} 
}
