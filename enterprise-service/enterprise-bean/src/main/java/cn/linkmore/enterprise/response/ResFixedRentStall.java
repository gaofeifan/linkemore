package cn.linkmore.enterprise.response;

import java.util.Date;

public class ResFixedRentStall {

	private Long fixedId;
	private Long stallId;
	private String stallName;
	private String plateNos;
    private Date startTime;
    private Date endTime;
    
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
 
	public Long getFixedId() {
		return fixedId;
	}
	public void setFixedId(Long fixedId) {
		this.fixedId = fixedId;
	}
	public Long getStallId() {
		return stallId;
	}
	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}
	public String getStallName() {
		return stallName;
	}
	public void setStallName(String stallName) {
		this.stallName = stallName;
	}
	public String getPlateNos() {
		return plateNos;
	}
	public void setPlateNos(String plateNos) {
		this.plateNos = plateNos;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
