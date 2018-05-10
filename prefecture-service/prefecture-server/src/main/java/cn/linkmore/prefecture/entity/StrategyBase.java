package cn.linkmore.prefecture.entity;

import java.math.BigDecimal;
import java.util.Date;

public class StrategyBase {
    private Long id;

    private Integer type;

    private String name;

    private String beginTime;

    private Integer freeMins;

    private String endTime;

    private BigDecimal firstHour;

    private BigDecimal basePrice;

    private BigDecimal nightPrice;

    private Integer timelyLong;

    private Integer nightTimelyLong;

    private String timelyUnit;

    private Integer topDaily;

    private Integer topDay;

    private Integer topNight;

    private String creator;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private Integer isPrepaidPay;

    private String flexDetail;

    private Integer isFlexed;

    private Integer firstHourLong;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime == null ? null : beginTime.trim();
    }

    public Integer getFreeMins() {
        return freeMins;
    }

    public void setFreeMins(Integer freeMins) {
        this.freeMins = freeMins;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public BigDecimal getFirstHour() {
        return firstHour;
    }

    public void setFirstHour(BigDecimal firstHour) {
        this.firstHour = firstHour;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getNightPrice() {
        return nightPrice;
    }

    public void setNightPrice(BigDecimal nightPrice) {
        this.nightPrice = nightPrice;
    }

    public Integer getTimelyLong() {
        return timelyLong;
    }

    public void setTimelyLong(Integer timelyLong) {
        this.timelyLong = timelyLong;
    }

    public Integer getNightTimelyLong() {
        return nightTimelyLong;
    }

    public void setNightTimelyLong(Integer nightTimelyLong) {
        this.nightTimelyLong = nightTimelyLong;
    }

    public String getTimelyUnit() {
        return timelyUnit;
    }

    public void setTimelyUnit(String timelyUnit) {
        this.timelyUnit = timelyUnit == null ? null : timelyUnit.trim();
    }

    public Integer getTopDaily() {
        return topDaily;
    }

    public void setTopDaily(Integer topDaily) {
        this.topDaily = topDaily;
    }

    public Integer getTopDay() {
        return topDay;
    }

    public void setTopDay(Integer topDay) {
        this.topDay = topDay;
    }

    public Integer getTopNight() {
        return topNight;
    }

    public void setTopNight(Integer topNight) {
        this.topNight = topNight;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsPrepaidPay() {
        return isPrepaidPay;
    }

    public void setIsPrepaidPay(Integer isPrepaidPay) {
        this.isPrepaidPay = isPrepaidPay;
    }

    public String getFlexDetail() {
        return flexDetail;
    }

    public void setFlexDetail(String flexDetail) {
        this.flexDetail = flexDetail == null ? null : flexDetail.trim();
    }

    public Integer getIsFlexed() {
        return isFlexed;
    }

    public void setIsFlexed(Integer isFlexed) {
        this.isFlexed = isFlexed;
    }

    public Integer getFirstHourLong() {
        return firstHourLong;
    }

    public void setFirstHourLong(Integer firstHourLong) {
        this.firstHourLong = firstHourLong;
    }
}