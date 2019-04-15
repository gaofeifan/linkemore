package cn.linkmore.prefecture.response;

public class ResGatewayGroup {

	/**
	 *  分组名称
	 */ 
	private String groupName;
	/**
	 *  分组编号
	 */ 
	private String groupCode;
	/**
	 *  网关名称
	 */ 
	private String gatewayName;
	/**
	 *  网关序列号
	 */ 
	private String serialNumber;
	/**
	 *  网关型号
	 */ 
	private String gatewayModel;
	/**
	 *  网关版本号
	 */ 
	private String gatewayVersion;
	
	/**
	 *  网络类型
	 */ 
	private String networkType;
	/**
	 *  网关温度
	 */ 
	private int temperature;
	/**
	 *  网关地址
	 */ 
	private String address;
	/**
	 *  覆盖车位锁数
	 */ 
	private int lockNumber;
	/**
	 *  服务名称
	 */ 
	private String agentName;

	/**
	 * 配置标识（0 未配置 1 已配置）
	 */ 
	private int configurationIdentity;
	
	/**
	 *  网关在线状态0离线1在线
	 */ 
	private int gatewayState;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGatewayName() {
		return gatewayName;
	}

	public void setGatewayName(String gatewayName) {
		this.gatewayName = gatewayName;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getGatewayModel() {
		return gatewayModel;
	}

	public void setGatewayModel(String gatewayModel) {
		this.gatewayModel = gatewayModel;
	}

	public String getGatewayVersion() {
		return gatewayVersion;
	}

	public void setGatewayVersion(String gatewayVersion) {
		this.gatewayVersion = gatewayVersion;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getLockNumber() {
		return lockNumber;
	}

	public void setLockNumber(int lockNumber) {
		this.lockNumber = lockNumber;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public int getConfigurationIdentity() {
		return configurationIdentity;
	}

	public void setConfigurationIdentity(int configurationIdentity) {
		this.configurationIdentity = configurationIdentity;
	}

	public int getGatewayState() {
		return gatewayState;
	}

	public void setGatewayState(int gatewayState) {
		this.gatewayState = gatewayState;
	}
	
	
}
