package cn.linkmore.common.entity;

import java.util.Date;

/**
 * app异常日志
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
public class UnusualLog {
    private Long id;

    /**
     *  app版本
     */ 
    private String appVersion;

    /**
     *  os版本
     */ 
    private String osVersion;

    /**
     *  型号
     */ 
    private String model;

    /**
     *  客户端类型
     */ 
    private Integer clientType;

    /**
     *  日志级别
     */ 
    private String level;

    /**
     *  品牌
     */ 
    private String brand;
    
    private Integer system;
    
    private String content;
    
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion == null ? null : appVersion.trim();
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion == null ? null : osVersion.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

	public Integer getSystem() {
		return system;
	}

	public void setSystem(Integer system) {
		this.system = system;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
}