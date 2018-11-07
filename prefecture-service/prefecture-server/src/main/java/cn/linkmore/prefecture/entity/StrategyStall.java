package cn.linkmore.prefecture.entity;

import java.util.Date;

public class StrategyStall {
	/**
	 * 分期类型1 按日期段2按周
	 */
	private Byte datetype;
	/**
	 * 分期类型-按周开始时间
	 */
	private Date startDate;
	/**
	 * 分期类型-按周结束时间
	 */
	private Date stopDate;
	/**
	 * 分期类型-按日期段开始时间
	 */
	private String beginDate;
	/**
	 * 分期类型-按日期段结束时间
	 */
	private String endDate;
	/**
	 * 费用策略code
	 */
	private String parkCode;

	public Byte getDatetype() {
		return datetype;
	}

	public void setDatetype(Byte datetype) {
		this.datetype = datetype;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStopDate() {
		return stopDate;
	}

	public void setStopDate(Date stopDate) {
		this.stopDate = stopDate;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getParkCode() {
		return parkCode;
	}

	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}

}
