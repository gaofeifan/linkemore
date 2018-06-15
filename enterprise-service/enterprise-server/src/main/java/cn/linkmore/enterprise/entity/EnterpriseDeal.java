package cn.linkmore.enterprise.entity;

import java.math.BigDecimal;
import java.util.Date;

public class EnterpriseDeal {
    private Long id;

    private Long enterpriseId;

    private String enterpriseName;

    private String serialNumber;

    private Integer dealPayAmount;

    private Integer dealGiftAmount;

    private String remark;

    private Long creatorId;

    private String creatorName;

    private Date createTime;

    private Integer isCreate;

    private BigDecimal usedDealPayAmount;

    private BigDecimal userDealGiftAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName == null ? null : enterpriseName.trim();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    public Integer getDealPayAmount() {
        return dealPayAmount;
    }

    public void setDealPayAmount(Integer dealPayAmount) {
        this.dealPayAmount = dealPayAmount;
    }

    public Integer getDealGiftAmount() {
        return dealGiftAmount;
    }

    public void setDealGiftAmount(Integer dealGiftAmount) {
        this.dealGiftAmount = dealGiftAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName == null ? null : creatorName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsCreate() {
        return isCreate;
    }

    public void setIsCreate(Integer isCreate) {
        this.isCreate = isCreate;
    }

    public BigDecimal getUsedDealPayAmount() {
        return usedDealPayAmount;
    }

    public void setUsedDealPayAmount(BigDecimal usedDealPayAmount) {
        this.usedDealPayAmount = usedDealPayAmount;
    }

    public BigDecimal getUserDealGiftAmount() {
        return userDealGiftAmount;
    }

    public void setUserDealGiftAmount(BigDecimal userDealGiftAmount) {
        this.userDealGiftAmount = userDealGiftAmount;
    }
}