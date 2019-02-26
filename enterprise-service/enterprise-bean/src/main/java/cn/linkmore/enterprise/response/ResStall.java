package cn.linkmore.enterprise.response;

public class ResStall {
	private Long id;
	/**
	 * 车位编号
	 */
	private String stallName;
	/**
	 * 锁编号
	 */
	private String lockSN;

	/**
	 * 锁电量
	 */
	private int electricity;
	/**
	 * 锁状态( 0 降下，1 竖起)
	 */
	private Integer lockState;

	/**
	 * 车位状态(1，空闲；2，使用中；4，下线; 5，故障)
	 */
	private Integer stallState;

	/**
	 * 固定车位状态(1启用 ,0禁用)
	 */
	private Integer fixedState;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStallName() {
		return stallName;
	}

	public void setStallName(String stallName) {
		this.stallName = stallName;
	}

	public String getLockSN() {
		return lockSN;
	}

	public void setLockSN(String lockSN) {
		this.lockSN = lockSN;
	}

	public int getElectricity() {
		return electricity;
	}

	public void setElectricity(int electricity) {
		this.electricity = electricity;
	}

	public Integer getLockState() {
		return lockState;
	}

	public void setLockState(Integer lockState) {
		this.lockState = lockState;
	}

	public Integer getStallState() {
		return stallState;
	}

	public void setStallState(Integer stallState) {
		this.stallState = stallState;
	}

	public Integer getFixedState() {
		return fixedState;
	}

	public void setFixedState(Integer fixedState) {
		this.fixedState = fixedState;
	}

}
