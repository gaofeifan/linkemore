package cn.linkmore.prefecture.response;

public class ResGatewayDetails {

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
	 *  
	 */ 
	private int appointState;
	/**
	 *  网关版本号
	 */ 
	private String gatewayVersion;
	/**
	 *  网络类型
	 */ 
	private int networkType;
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
	 *  网关在线状态(0离线1在线)
	 */ 
	private int gatewayState;
	/**
	 *  
	 */ 
	private String simType;
	/**
	 *  服务名称
	 */ 
	private String agentName;
	
	/**
	 *  网关信号
	 */ 
	private String signal;
		
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

	public int getAppointState() {
		return appointState;
	}

	public void setAppointState(int appointState) {
		this.appointState = appointState;
	}

	public String getGatewayVersion() {
		return gatewayVersion;
	}

	public void setGatewayVersion(String gatewayVersion) {
		this.gatewayVersion = gatewayVersion;
	}

	public int getNetworkType() {
		return networkType;
	}

	public void setNetworkType(int networkType) {
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

	public int getGatewayState() {
		return gatewayState;
	}

	public void setGatewayState(int gatewayState) {
		this.gatewayState = gatewayState;
	}

	public String getSimType() {
		return simType;
	}

	public void setSimType(String simType) {
		this.simType = simType;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getSignal() {
		return signal;
	}

	public void setSignal(String signal) {
		this.signal = signal;
	}
	
}
