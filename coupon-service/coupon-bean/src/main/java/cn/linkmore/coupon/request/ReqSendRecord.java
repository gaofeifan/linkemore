package cn.linkmore.coupon.request;

import java.util.Date;

public class ReqSendRecord {
    private Long id;

    private Integer type;

    private Long templateId;

    private Long conditionId;

    private Integer creatorId;

    private String creatorName;

    private Date taskTime;

    private Date createTime;

    private Integer status;

    private Date sendTime;
    //定时时间
    private String taskTimeStr;
    //发送人手机id串
    private String phoneJson;
    
    public String getTaskTimeStr() {
		return taskTimeStr;
	}

	public void setTaskTimeStr(String taskTimeStr) {
		this.taskTimeStr = taskTimeStr;
	}

	public String getPhoneJson() {
		return phoneJson;
	}

	public void setPhoneJson(String phoneJson) {
		this.phoneJson = phoneJson;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName == null ? null : creatorName.trim();
    }

    public Date getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(Date taskTime) {
        this.taskTime = taskTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}