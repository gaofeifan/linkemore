package cn.linkmore.prefecture.response;

public class ResLocksGateway {
	/**
	 *  锁序列号
	 */ 
	private String lockSerialNumber;
	
	/**
	 *  网关序列号
	 */ 
	private String gatewaySerialNumber;
	
	/**
	 *  信号强度
	 */ 
	private String signal;
	
	
	/**
	 *  连接状态（0未连接 1连接）
	 */ 
	private String connectState;
	
	private String reportTime;
	
	private String bindFlag;

	/**
	 *  是否在线（0 离线 1 在线）
	 */ 
	private String onLine;
	
	/**
	 *  锁名称
	 */ 
	private String name;
	
	/**
	 *  电池电量
	 */ 
	private String electricity;

	public String getLockSerialNumber() {
		return lockSerialNumber;
	}

	public void setLockSerialNumber(String lockSerialNumber) {
		this.lockSerialNumber = lockSerialNumber;
	}

	public String getGatewaySerialNumber() {
		return gatewaySerialNumber;
	}

	public void setGatewaySerialNumber(String gatewaySerialNumber) {
		this.gatewaySerialNumber = gatewaySerialNumber;
	}

	public String getSignal() {
		return signal;
	}

	public void setSignal(String signal) {
		this.signal = signal;
	}

	public String getConnectState() {
		return connectState;
	}

	public void setConnectState(String connectState) {
		this.connectState = connectState;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public String getBindFlag() {
		return bindFlag;
	}

	public void setBindFlag(String bindFlag) {
		this.bindFlag = bindFlag;
	}

	public String getOnLine() {
		return onLine;
	}

	public void setOnLine(String onLine) {
		this.onLine = onLine;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getElectricity() {
		return electricity;
	}

	public void setElectricity(String electricity) {
		this.electricity = electricity;
	}
	
	
}
