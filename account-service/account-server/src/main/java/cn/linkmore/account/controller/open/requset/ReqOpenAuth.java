package cn.linkmore.account.controller.open.requset;

import org.hibernate.validator.constraints.NotBlank;
import io.swagger.annotations.ApiModel;

@ApiModel("开放授权请求参数")
public class ReqOpenAuth{
	
	@NotBlank(message="appid不能未空") 
	private String appid;
	
	@NotBlank(message="token不能为空") 
	private String token;

	/**
	 * @return the appid
	 */
	public String getAppid() {
		return appid;
	}

	/**
	 * @param appid the appid to set
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	

}
