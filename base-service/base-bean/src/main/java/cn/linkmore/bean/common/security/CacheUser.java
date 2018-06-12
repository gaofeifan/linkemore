package cn.linkmore.bean.common.security;
/**
 * 统一鉴权用户
 * @author liwenlong
 * @version 2.0
 *
 */
public class CacheUser {  
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
