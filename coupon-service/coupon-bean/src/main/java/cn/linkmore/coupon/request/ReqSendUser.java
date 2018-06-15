package cn.linkmore.coupon.request;

import java.util.Date;

public class ReqSendUser {
    private Long id;

    private Long templateId;

    private Long recordId;

    private Long userId;

    private Date createTime;

    private Integer rollbackFlag;

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

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getRollbackFlag() {
        return rollbackFlag;
    }

    public void setRollbackFlag(Integer rollbackFlag) {
        this.rollbackFlag = rollbackFlag;
    }
}