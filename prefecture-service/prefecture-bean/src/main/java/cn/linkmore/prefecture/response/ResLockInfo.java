package cn.linkmore.prefecture.response;

public class ResLockInfo {
	
	/**
	 *  车位锁编号
	 */ 
	private String lockCode;
	
	/**
	 *  型号
	 */ 
	private String model;
	
	/**
	 *  版本
	 */ 
	private String version;
	
	/**
	 *  电池电量
	 */ 
	private int electricity;
	
	/**
	 *  锁状态    0 降下，1 竖起， 其他值表示未知
	 */ 
	private int lockState;
	
	/**
	 * 车位状态   0 上方无车 1 上方有车 ，其他值 表示未知
	 */ 
	private int parkingState;
	
	/**
	 *  超声波设备状态   0 异常 1正常 其他值表示未知
	 */ 
	private int inductionState;
	
	/**
	 *  通讯网关数
	 */ 
	private int gatewaySum;
	
	/**
	 *  升降次数
	 */ 
	private int optionCount;
	
	/**
	 *  升降成功次数
	 */ 
	private int optionSuccessCount;

	public String getLockCode() {
		return lockCode;
	}

	public void setLockCode(String lockCode) {
		this.lockCode = lockCode;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getElectricity() {
		return electricity;
	}

	public void setElectricity(int electricity) {
		this.electricity = electricity;
	}

	public int getLockState() {
		return lockState;
	}

	public void setLockState(int lockState) {
		this.lockState = lockState;
	}

	public int getParkingState() {
		return parkingState;
	}

	public void setParkingState(int parkingState) {
		this.parkingState = parkingState;
	}

	public int getInductionState() {
		return inductionState;
	}

	public void setInductionState(int inductionState) {
		this.inductionState = inductionState;
	}

	public int getGatewaySum() {
		return gatewaySum;
	}

	public void setGatewaySum(int gatewaySum) {
		this.gatewaySum = gatewaySum;
	}

	public int getOptionCount() {
		return optionCount;
	}

	public void setOptionCount(int optionCount) {
		this.optionCount = optionCount;
	}

	public int getOptionSuccessCount() {
		return optionSuccessCount;
	}

	public void setOptionSuccessCount(int optionSuccessCount) {
		this.optionSuccessCount = optionSuccessCount;
	}
}
