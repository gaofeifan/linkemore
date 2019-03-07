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
	private static final String saveGroup = "/api/v1/group/save-group";
	private static final String removeGroupCode = "/api/v1/group/remove-group-code";
	private static final String bindGroup = "/api/v1/gateway/config/bindGroup";
	private static final String unbindGroup = "/api/v1/gateway/config/unbindGroup";
	private static final String loadAllLocks = "/api/v1/gateway/option/load-all-locks";
	private static final String serialNumber = "/api/v1/gateway/find-gateway-serialNumber";
	private static final String gatewayGroup  = "/api/v1/gateway/find-gateway-group";
	private static final String unbindLockList = "/api/v1/lock/unbind-lock-list";
	private static final String bindLockList = "/api/v1/lock/bind-lock-list";
	private static final String bindLock = "/api/v1/lock/config/bind-lock";
	private static final String unbindLock = "/api/v1/lock/config/unbind-lock-relave";
	private static final String removeLock = "/api/v1/lock/remove-lock";
	private static final String lockGatewayList = "/api/v1/lock/lock-gateway-list";
	private static final String lockGateway = "/api/v1/lock/locks-gateway";
	private static final String restart = "/api/v1/gateway/option/restart";
	private static final String batchBindGateway = "/api/v1/lock/config/batch-bind-gateway";
	private static final String confirm = "/api/v1//gateway/config/confirm";
	
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
	public static String getSaveGroup() {
		return saveGroup;
	}
	
	public static String getRemoveGroupCode() {
		return removeGroupCode;
	}
	
	public static String getBindGroup() {
		return bindGroup;
	}
	
	public static String unBindGroup() {
		return unbindGroup;
	}
	public static String loadAllLocks() {
		return loadAllLocks;
	}
	public static String getSerialNumber() {
		return serialNumber;
	}
	public static String getGatewayGroup() {
		return gatewayGroup;
	}
	
	public static String getUnbindLockList(){
		return unbindLockList;
	}
	
	public static String getBindLockList() {
		return bindLockList;
	}
	public static String getBindLock() {
		return bindLock;
	}
	public static String getUnBindLock() {
		return unbindLock;
	}
	public static String getRemoveLock() {
		return removeLock;
	}
	public static String getLockGatewayList() {
		return lockGatewayList;
	}
	public static String getLockGateway() {
		return lockGateway;
	}
	public static String getRestart() {
		return restart;
	}
	public static String getBatchBindGateway() {
		return batchBindGateway;
	}
	public static String getConfirm() {
		return confirm;
	}
}
