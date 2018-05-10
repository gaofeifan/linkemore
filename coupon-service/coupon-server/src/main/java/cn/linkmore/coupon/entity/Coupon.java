package cn.linkmore.coupon.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Coupon {
	/**
	 * 主键
	 */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 优惠包id
     */
    private Long comboId;

    private Long couponGrantId;

    private Long valuePackId;
    /**
     * 减免金额/折扣上限/立减金额
     */
    private BigDecimal faceAmount;

    private Date vpValidTime;
    /**
     * 过期时间
     */
    private Date validTime;
    /**
     * 状态0未使用，1已使用，2已过期
     */
    private Short status;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 企业id
     */
    private Long enterpriseId;
    /**
     * 主题id
     */
    private Long themeId;
    /**
     * 优惠券项id
     */
    private Long itemId;
    /**
     * 发放记录id
     */
    private Long recordId;
    /**
     * 优惠券套餐id
     */
    private Long templateId;
    /**
     * 使用条件id
     */
    private Long conditionId;
    /**
     * 类型0立减，1满减，2折扣
     */
    private Integer type;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 折扣[1,99]
     */
    private Integer discount;
    /**
     * 使用金额[券最终价值]
     */
    private BigDecimal usedAmount;
    /**
     * 发送人id
     */
    private Long sendUserId;
    /**
     * 满足金额
     */
    private BigDecimal conditionAmount;

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

    public Long getComboId() {
        return comboId;
    }

    public void setComboId(Long comboId) {
        this.comboId = comboId;
    }

    public Long getCouponGrantId() {
        return couponGrantId;
    }

    public void setCouponGrantId(Long couponGrantId) {
        this.couponGrantId = couponGrantId;
    }

    public Long getValuePackId() {
        return valuePackId;
    }

    public void setValuePackId(Long valuePackId) {
        this.valuePackId = valuePackId;
    }

    public BigDecimal getFaceAmount() {
        return faceAmount;
    }

    public void setFaceAmount(BigDecimal faceAmount) {
        this.faceAmount = faceAmount;
    }

    public Date getVpValidTime() {
        return vpValidTime;
    }

    public void setVpValidTime(Date vpValidTime) {
        this.vpValidTime = vpValidTime;
    }

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getConditionId() {
        return conditionId;
    }

    public void setConditionId(Long conditionId) {
        this.conditionId = conditionId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public BigDecimal getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(BigDecimal usedAmount) {
        this.usedAmount = usedAmount;
    }

    public Long getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Long sendUserId) {
        this.sendUserId = sendUserId;
    }

    public BigDecimal getConditionAmount() {
        return conditionAmount;
    }

    public void setConditionAmount(BigDecimal conditionAmount) {
        this.conditionAmount = conditionAmount;
    }
}