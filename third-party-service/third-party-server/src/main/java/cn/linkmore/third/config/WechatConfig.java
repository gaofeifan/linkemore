package cn.linkmore.third.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Config - 微信服务号配置
 * 
 * @author liwenlong
 * @version 2.0
 *
 */
@ConfigurationProperties(prefix = "wechat")
@Component
public class WechatConfig { 
	private String appId;
	private String appSecret;
	private String token;
	private String serviceUrl;
	private String noncestr;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public String getNoncestr() {
		return noncestr;
	}
	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	} 
}
