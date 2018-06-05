package cn.linkmore.prefecture.request;

import java.util.Date;

/**
 * 车位锁电池更换-Excel导出
 * @author jiaohanbin
 * @version 2.0
 */
public class ReqStallRechargeExcel {

	private String stallName;
	private String lockSn;
	private Date startTime;
	private Date endTime;
	
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
	public String getStallName() {
		return stallName;
	}
	public void setStallName(String stallName) {
		this.stallName = stallName;
	}
	public String getLockSn() {
		return lockSn;
	}
	public void setLockSn(String lockSn) {
		this.lockSn = lockSn;
	}
	
}
