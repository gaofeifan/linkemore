package cn.linkmore.coupon.response;

import java.util.Date;

public class ResTemplateCondition {
    private Long id;

    private String name;

    private Long templateId;

    private Integer availableTime;

    private Integer availablePrefecture;

    private Integer status;

    private Date createTime;

    private Integer isDefault;
    
    //非实例化字段
    private String preIdJson;//车场ID
    private String useTimeJson;//使用时段json
    
    private String preName;
    private String useTime;

    public String getPreIdJson() {
		return preIdJson;
	}

	public void setPreIdJson(String preIdJson) {
		this.preIdJson = preIdJson;
	}

	public String getUseTimeJson() {
		return useTimeJson;
	}

	public void setUseTimeJson(String useTimeJson) {
		this.useTimeJson = useTimeJson;
	}

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}

	public String getUseTime() {
		return useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}

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