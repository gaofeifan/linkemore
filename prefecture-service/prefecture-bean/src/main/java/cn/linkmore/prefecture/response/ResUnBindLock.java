package cn.linkmore.prefecture.response;

public class ResUnBindLock {

	/**
	 *  锁序列号
	 */ 
	private String lockSerialNumber;
	
	/**
	 *  网关序列号 
	 */ 
	private String gatewaySerialNumbe;
	
	/**
	 *  信号强度
	 */ 
	private String signal;
	
	/**
	 *  网关序列号
	 */ 
	private String serialNumber;
	
	/**
	 *  连接状态（0未连接 1连 接）
	 */ 
	private String connectState;
	
	/**
	 *   绑定标识（0未绑定 1已 绑定）
	 */ 
	private String bindFlag;
	
	/**
	 *   是否在线（0 离线 1 在 线）
	 */ 
	private String onLine;

	public String getLockSerialNumber() {
		return lockSerialNumber;
	}

	public void setLockSerialNumber(String lockSerialNumber) {
		this.lockSerialNumber = lockSerialNumber;
	}

	public String getGatewaySerialNumbe() {
		return gatewaySerialNumbe;
	}

	public void setGatewaySerialNumbe(String gatewaySerialNumbe) {
		this.gatewaySerialNumbe = gatewaySerialNumbe;
	}

	public String getSignal() {
		return signal;
	}

	public void setSignal(String signal) {
		this.signal = signal;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getConnectState() {
		return connectState;
	}

	public void setConnectState(String connectState) {
		this.connectState = connectState;
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
	
	
}
