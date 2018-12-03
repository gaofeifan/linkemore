/**
 * 
 */
package cn.linkmore.prefecture.request;

import java.math.BigDecimal;

/**
 * @author zhengpengfei
 *
 */
public class ReqPeriodCharge {
	
	private Long id;
	
	private Integer pageNo;
	
	private String parkCode;

    private String parkName;

    private Integer containsFreeTime;

    private Integer freeTime;

    private Integer limitType;

    private BigDecimal limitPrice;

    private Integer limitBeginTime;

    private Integer limitTime;

    private Integer limitUnit;

    private Integer firstHourPeriod;

    private String remark;

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParkCode() {
		return parkCode;
	}

	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public Integer getContainsFreeTime() {
		return containsFreeTime;
	}

	public void setContainsFreeTime(Integer containsFreeTime) {
		this.containsFreeTime = containsFreeTime;
	}

	public Integer getFreeTime() {
		return freeTime;
	}

	public void setFreeTime(Integer freeTime) {
		this.freeTime = freeTime;
	}

	public Integer getLimitType() {
		return limitType;
	}

	public void setLimitType(Integer limitType) {
		this.limitType = limitType;
	}

	public BigDecimal getLimitPrice() {
		return limitPrice;
	}

	public void setLimitPrice(BigDecimal limitPrice) {
		this.limitPrice = limitPrice;
	}

	public Integer getLimitBeginTime() {
		return limitBeginTime;
	}

	public void setLimitBeginTime(Integer limitBeginTime) {
		this.limitBeginTime = limitBeginTime;
	}

	public Integer getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(Integer limitTime) {
		this.limitTime = limitTime;
	}

	public Integer getLimitUnit() {
		return limitUnit;
	}

	public void setLimitUnit(Integer limitUnit) {
		this.limitUnit = limitUnit;
	}

	public Integer getFirstHourPeriod() {
		return firstHourPeriod;
	}

	public void setFirstHourPeriod(Integer firstHourPeriod) {
		this.firstHourPeriod = firstHourPeriod;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
