package cn.linkmore.prefecture.response;

/**
 * 查询锁关联的网关
 * @author   GFF
 * @Date     2019年2月20日
 * @Version  v2.0
 */
public class ResLockGatewayList {

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
	 *  网关序列号
	 */ 
	private String serialNumber;
	
	/**
	 *  连接状态（0未连接 1连接）
	 */ 
	private String connectState;
	
	/**
	 *  是否在线（0 离线 1 在线）
	 */ 
	private String onLine;
	
	/**
	 *  绑定状态（0未绑定1已绑定）
	 */ 
	private String bindFlag;
	
	/**
	 *  锁名称
	 */ 
	private String name;

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

	public String getBindFlag() {
		return bindFlag;
	}

	public void setBindFlag(String bindFlag) {
		this.bindFlag = bindFlag;
	}
	
	
}
