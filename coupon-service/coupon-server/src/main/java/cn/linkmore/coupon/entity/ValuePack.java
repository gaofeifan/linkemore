package cn.linkmore.coupon.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ValuePack {
    private Long id;

    private Long userId;

    private String packName;

    private BigDecimal availableAmount;

    private BigDecimal totalAmount;

    private Short isDisable;

    private Short status;

    private Short validity;

    private Date validityDate;

    private Long operaterId;

    private Date createTime;

    private Date updateTime;

    private Integer valuePackType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName == null ? null : packName.trim();
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Short getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(Short isDisable) {
        this.isDisable = isDisable;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getValidity() {
        return validity;
    }

    public void setValidity(Short validity) {
        this.validity = validity;
    }

    public Date getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(Date validityDate) {
        this.validityDate = validityDate;
    }

    public Long getOperaterId() {
        return operaterId;
    }

    public void setOperaterId(Long operaterId) {
        this.operaterId = operaterId;
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

    public Integer getValuePackType() {
        return valuePackType;
    }

    public void setValuePackType(Integer valuePackType) {
        this.valuePackType = valuePackType;
    }
}