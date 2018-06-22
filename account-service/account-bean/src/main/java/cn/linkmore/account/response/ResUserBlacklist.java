package cn.linkmore.account.response;

import java.util.Date;

public class ResUserBlacklist {
    private Long id;

    private String username;

    private Long maxPreId;

    private Short preIdCount;

    private Long minPreId;

    private Integer totalOrderCount;

    private Integer cashOrderCount;

    private Integer maxCouponAmount;

    private Double totalCouponAmount;

    private Integer couponCount;

    private Date couponValidate;

    private Short limitStatus;

    private Long operatorId;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Long getMaxPreId() {
        return maxPreId;
    }

    public void setMaxPreId(Long maxPreId) {
        this.maxPreId = maxPreId;
    }

    public Short getPreIdCount() {
        return preIdCount;
    }

    public void setPreIdCount(Short preIdCount) {
        this.preIdCount = preIdCount;
    }

    public Long getMinPreId() {
        return minPreId;
    }

    public void setMinPreId(Long minPreId) {
        this.minPreId = minPreId;
    }

    public Integer getTotalOrderCount() {
        return totalOrderCount;
    }

    public void setTotalOrderCount(Integer totalOrderCount) {
        this.totalOrderCount = totalOrderCount;
    }

    public Integer getCashOrderCount() {
        return cashOrderCount;
    }

    public void setCashOrderCount(Integer cashOrderCount) {
        this.cashOrderCount = cashOrderCount;
    }

    public Integer getMaxCouponAmount() {
        return maxCouponAmount;
    }

    public void setMaxCouponAmount(Integer maxCouponAmount) {
        this.maxCouponAmount = maxCouponAmount;
    }

    public Double getTotalCouponAmount() {
        return totalCouponAmount;
    }

    public void setTotalCouponAmount(Double totalCouponAmount) {
        this.totalCouponAmount = totalCouponAmount;
    }

    public Integer getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(Integer couponCount) {
        this.couponCount = couponCount;
    }

    public Date getCouponValidate() {
        return couponValidate;
    }

    public void setCouponValidate(Date couponValidate) {
        this.couponValidate = couponValidate;
    }

    public Short getLimitStatus() {
        return limitStatus;
    }

    public void setLimitStatus(Short limitStatus) {
        this.limitStatus = limitStatus;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}