package cn.linkmore.monitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author   GFF
 * @Date     2018年7月5日
 * @Version  v2.0
 */
@ConfigurationProperties(prefix = "wechat")
@Component
public class WechatConfig { 
	private String appId;
	private String appSecret;
	
	private String token;
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
	
	
}
