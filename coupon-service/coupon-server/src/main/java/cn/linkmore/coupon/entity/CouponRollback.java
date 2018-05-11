package cn.linkmore.coupon.entity;

import java.util.Date;

public class CouponRollback {
    private Long id;

    private Long templateId;

    private Date rollbackDate;

    private String enterpriseDealNumber;

    private Long usedDealPayAmount;

    private Long userDealGiftAmount;

    private Long contractAmount;

    private Long givenAmount;

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

    public Date getRollbackDate() {
        return rollbackDate;
    }

    public void setRollbackDate(Date rollbackDate) {
        this.rollbackDate = rollbackDate;
    }

    public String getEnterpriseDealNumber() {
        return enterpriseDealNumber;
    }

    public void setEnterpriseDealNumber(String enterpriseDealNumber) {
        this.enterpriseDealNumber = enterpriseDealNumber == null ? null : enterpriseDealNumber.trim();
    }

    public Long getUsedDealPayAmount() {
        return usedDealPayAmount;
    }

    public void setUsedDealPayAmount(Long usedDealPayAmount) {
        this.usedDealPayAmount = usedDealPayAmount;
    }

    public Long getUserDealGiftAmount() {
        return userDealGiftAmount;
    }

    public void setUserDealGiftAmount(Long userDealGiftAmount) {
        this.userDealGiftAmount = userDealGiftAmount;
    }

    public Long getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Long contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Long getGivenAmount() {
        return givenAmount;
    }

    public void setGivenAmount(Long givenAmount) {
        this.givenAmount = givenAmount;
    }
}