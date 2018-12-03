package cn.linkmore.prefecture.request;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReqAbuttingPeriod implements Serializable {
	
	private Integer pageNo;
	
	private Long id;

    private String periodName;

    private Long chargeId;

    private String beginTime;

    private String endTime;

    private BigDecimal chargeFee;

    private Integer chargeUnit;

    private Integer chargeHourFree;

    private Integer criticalUnit;

    private BigDecimal limitFee;


    private static final long serialVersionUID = 1L;


    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName == null ? null : periodName.trim();
    }

    public Long getChargeId() {
        return chargeId;
    }

    public void setChargeId(Long chargeId) {
        this.chargeId = chargeId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime == null ? null : beginTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public BigDecimal getChargeFee() {
        return chargeFee;
    }

    public void setChargeFee(BigDecimal chargeFee) {
        this.chargeFee = chargeFee;
    }

    public Integer getChargeUnit() {
        return chargeUnit;
    }

    public void setChargeUnit(Integer chargeUnit) {
        this.chargeUnit = chargeUnit;
    }

    public Integer getChargeHourFree() {
        return chargeHourFree;
    }

    public void setChargeHourFree(Integer chargeHourFree) {
        this.chargeHourFree = chargeHourFree;
    }

    public Integer getCriticalUnit() {
        return criticalUnit;
    }

    public void setCriticalUnit(Integer criticalUnit) {
        this.criticalUnit = criticalUnit;
    }

    public BigDecimal getLimitFee() {
        return limitFee;
    }

    public void setLimitFee(BigDecimal limitFee) {
        this.limitFee = limitFee;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

}