package cn.linkmore.order.entity;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 订单详情
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class OrdersDetail {
    private Long id;

    private String ordNo;

    private String ordDetailNo;

    private String ordName;

    private String parkName;

    private String stallName;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private Long tradeId;

    private Long strategyId;

    private Long accountId;

    private Long couponsId;

    private BigDecimal couponsMoney;

    private Integer dayTime;

    private BigDecimal dayFee;

    private Integer nightTime;

    private BigDecimal nightFee;

    private Date beginTime;

    private Date endTime;

    private String description;

    private Date createTime;

    private Date updateTime;
 

    private Long orderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrdNo() {
        return ordNo;
    }

    public void setOrdNo(String ordNo) {
        this.ordNo = ordNo == null ? null : ordNo.trim();
    }

    public String getOrdDetailNo() {
        return ordDetailNo;
    }

    public void setOrdDetailNo(String ordDetailNo) {
        this.ordDetailNo = ordDetailNo == null ? null : ordDetailNo.trim();
    }

    public String getOrdName() {
        return ordName;
    }

    public void setOrdName(String ordName) {
        this.ordName = ordName == null ? null : ordName.trim();
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName == null ? null : parkName.trim();
    }

    public String getStallName() {
        return stallName;
    }

    public void setStallName(String stallName) {
        this.stallName = stallName == null ? null : stallName.trim();
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getCouponsId() {
        return couponsId;
    }

    public void setCouponsId(Long couponsId) {
        this.couponsId = couponsId;
    }

    public BigDecimal getCouponsMoney() {
        return couponsMoney;
    }

    public void setCouponsMoney(BigDecimal couponsMoney) {
        this.couponsMoney = couponsMoney;
    }

    public Integer getDayTime() {
        return dayTime;
    }

    public void setDayTime(Integer dayTime) {
        this.dayTime = dayTime;
    }

    public BigDecimal getDayFee() {
        return dayFee;
    }

    public void setDayFee(BigDecimal dayFee) {
        this.dayFee = dayFee;
    }

    public Integer getNightTime() {
        return nightTime;
    }

    public void setNightTime(Integer nightTime) {
        this.nightTime = nightTime;
    }

    public BigDecimal getNightFee() {
        return nightFee;
    }

    public void setNightFee(BigDecimal nightFee) {
        this.nightFee = nightFee;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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
 

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}