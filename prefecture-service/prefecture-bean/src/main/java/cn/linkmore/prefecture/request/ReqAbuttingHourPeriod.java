package cn.linkmore.prefecture.request;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReqAbuttingHourPeriod implements Serializable {
    private Long id;

    private Long chargeId;

    private String parkName;

    private BigDecimal firstHourPrice;

    private BigDecimal firstPeriodPrice;

    private Integer firstPeriodTime;

    private Integer firstPeriodUnit;


    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChargeId() {
        return chargeId;
    }

    public void setChargeId(Long chargeId) {
        this.chargeId = chargeId;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName == null ? null : parkName.trim();
    }

    public BigDecimal getFirstHourPrice() {
        return firstHourPrice;
    }

    public void setFirstHourPrice(BigDecimal firstHourPrice) {
        this.firstHourPrice = firstHourPrice;
    }

    public BigDecimal getFirstPeriodPrice() {
        return firstPeriodPrice;
    }

    public void setFirstPeriodPrice(BigDecimal firstPeriodPrice) {
        this.firstPeriodPrice = firstPeriodPrice;
    }

    public Integer getFirstPeriodTime() {
        return firstPeriodTime;
    }

    public void setFirstPeriodTime(Integer firstPeriodTime) {
        this.firstPeriodTime = firstPeriodTime;
    }

    public Integer getFirstPeriodUnit() {
        return firstPeriodUnit;
    }

    public void setFirstPeriodUnit(Integer firstPeriodUnit) {
        this.firstPeriodUnit = firstPeriodUnit;
    }

}