package cn.linkmore.coupon.entity;

import java.util.Date;

public class CouponTemplateCondition {
    private Long id;

    private String name;

    private Long templateId;

    private Integer availableTime;

    private Integer availablePrefecture;

    private Integer status;

    private Date createTime;

    private Integer isDefault;

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

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Integer getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(Integer availableTime) {
        this.availableTime = availableTime;
    }

    public Integer getAvailablePrefecture() {
        return availablePrefecture;
    }

    public void setAvailablePrefecture(Integer availablePrefecture) {
        this.availablePrefecture = availablePrefecture;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }
}