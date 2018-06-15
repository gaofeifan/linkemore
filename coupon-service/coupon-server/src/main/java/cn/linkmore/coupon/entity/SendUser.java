package cn.linkmore.coupon.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SendUser {
    private Long id;

    private Long templateId;

    private Long recordId;

    private Long userId;

    private Date createTime;

    private Integer rollbackFlag;
    
  //列表显示字段
    private String username;//显示用户名称
    
    private String tempName;//显示套餐名称
    
    private String creatorName;//创建人
    
    private BigDecimal unitAmount;//停车券金额
    
    private String usage;//使用情况
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public BigDecimal getUnitAmount() {
		return unitAmount;
	}

	public void setUnitAmount(BigDecimal unitAmount) {
		this.unitAmount = unitAmount;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

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