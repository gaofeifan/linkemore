package cn.linkmore.prefecture.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "lock")
public class LockProperties {
	private String linkmoreUrl;
	private String linkmoreNewUrl; 
	private String linkemoreLockUrl;
	private String appId;
	private String appSecret;
	
	
	
	private static final String lockInfo = "/api/v1/lock-info";
	private static final String lockSignalHistory = "/api/v1/lock/lock­signal­history";
	
	public String getLinkmoreUrl() {
		return linkmoreUrl;
	}
	public void setLinkmoreUrl(String linkmoreUrl) {
		this.linkmoreUrl = linkmoreUrl;
	} 
	public String getLinkmoreNewUrl() {
		return linkmoreNewUrl;
	}
	public void setLinkmoreNewUrl(String linkmoreNewUrl) {
		this.linkmoreNewUrl = linkmoreNewUrl;
	}
	public String getLinkemoreLockUrl() {
		return linkemoreLockUrl;
	}
	public void setLinkemoreLockUrl(String linkemoreLockUrl) {
		this.linkemoreLockUrl = linkemoreLockUrl;
	}
	public String getLockInfo() {
		return lockInfo;
	}
	public String getLockSignalHistory() {
		return lockSignalHistory;
	}
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
}
