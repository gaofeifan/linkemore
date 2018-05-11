package cn.linkmore.coupon.entity;

import java.math.BigDecimal;

public class CouponTemplateItem {
    private Long id;

    private Long templateId;

    private Integer type;

    private BigDecimal faceAmount;

    private Integer quantity;

    private Integer validDay;

    private Integer discount;

    private BigDecimal conditionAmount;

    private Integer source;

    private Integer deleteStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getFaceAmount() {
        return faceAmount;
    }

    public void setFaceAmount(BigDecimal faceAmount) {
        this.faceAmount = faceAmount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getValidDay() {
        return validDay;
    }

    public void setValidDay(Integer validDay) {
        this.validDay = validDay;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public BigDecimal getConditionAmount() {
        return conditionAmount;
    }

    public void setConditionAmount(BigDecimal conditionAmount) {
        this.conditionAmount = conditionAmount;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}