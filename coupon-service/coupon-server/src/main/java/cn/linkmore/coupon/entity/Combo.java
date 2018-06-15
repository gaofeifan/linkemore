package cn.linkmore.coupon.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Combo {
    private Long id;

    private String name;

    private Short validity;

    private String comboJson;

    private Short totalNum;

    private BigDecimal totalAmount;

    private Short status;

    private Date createTime;

    private Date updateTime;

    private Long operaterId;

    private Integer utilityPackage;

    private Integer comboType;

    private Short validityDay;

    private Short deleteStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Short getValidity() {
        return validity;
    }

    public void setValidity(Short validity) {
        this.validity = validity;
    }

    public String getComboJson() {
        return comboJson;
    }

    public void setComboJson(String comboJson) {
        this.comboJson = comboJson == null ? null : comboJson.trim();
    }

    public Short getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Short totalNum) {
        this.totalNum = totalNum;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
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

    public Long getOperaterId() {
        return operaterId;
    }

    public void setOperaterId(Long operaterId) {
        this.operaterId = operaterId;
    }

    public Integer getUtilityPackage() {
        return utilityPackage;
    }

    public void setUtilityPackage(Integer utilityPackage) {
        this.utilityPackage = utilityPackage;
    }

    public Integer getComboType() {
        return comboType;
    }

    public void setComboType(Integer comboType) {
        this.comboType = comboType;
    }

    public Short getValidityDay() {
        return validityDay;
    }

    public void setValidityDay(Short validityDay) {
        this.validityDay = validityDay;
    }

    public Short getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Short deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}