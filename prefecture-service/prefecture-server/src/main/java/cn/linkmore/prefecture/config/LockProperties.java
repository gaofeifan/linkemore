package cn.linkmore.prefecture.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "lock")
public class LockProperties {
	
	private static final String lockInfo = "/api/v1/lock-info";
	private static final String lockSignalHistory = "/api/v1/lock/lock-signal-history";
	private static final String lockOption = "/api/v1/option";
	private static final String lockList = "/api/v1/lock-list";
	private static final String setparkingname = "/api/v1/lock/config/set-parking-name";
	
	private String appId;
	private String appSecret;
	private String linkemoreLockUrl;
	
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
	public String getLockoption() {
		return lockOption;
	}
	public String getLocklist() {
		return lockList;
	}
	public static String getSetparkingname() {
		return setparkingname;
	}
	
	
	
}
