package cn.linkmore.enterprise.entity;

import java.util.Date;

public class AuthRecord {
	
    private Long id;
    //车位id
    private Long stallId;
    //车位名称
    private String stallName;
    //手机号
    private String mobile;
    //用户名称
    private String username;
    //开始日期
    private Date startTime;
    //结束日期
    private Date endTime;
    //关系id
    private Long relationId;
    //关系名称
    private String relationName;
    //车区id
    private Long preId;
    //车区名称
    private String preName;
    //授权人id
    private Long authUserId;
    //授权标识0正常授权，1、已取消 2、已过期 3、已失效
    private Short authFlag;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    
    private Long userId;
    /**
     * 授权记录开关，默认开1，小后台可操作关闭
     */
    private Short switchFlag = 1;
    
    //非实例化字段
    //授权时段开始时期-结束日期
    private String authPeriod;
    //授权人
    private String authUserName;
    //最后使用时间
    private String lastUseTime;
    //使用次数
    private Integer useCount;

    public String getAuthPeriod() {
		return authPeriod;
	}

	public void setAuthPeriod(String authPeriod) {
		this.authPeriod = authPeriod;
	}

	public String getAuthUserName() {
		return authUserName;
	}

	public void setAuthUserName(String authUserName) {
		this.authUserName = authUserName;
	}

	public String getLastUseTime() {
		return lastUseTime;
	}

	public void setLastUseTime(String lastUseTime) {
		this.lastUseTime = lastUseTime;
	}

	public Integer getUseCount() {
		return useCount;
	}

	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}

	public Short getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(Short switchFlag) {
		this.switchFlag = switchFlag;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStallId() {
        return stallId;
    }

    public void setStallId(Long stallId) {
        this.stallId = stallId;
    }

    public String getStallName() {
        return stallName;
    }

    public void setStallName(String stallName) {
        this.stallName = stallName == null ? null : stallName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName == null ? null : relationName.trim();
    }

    public Long getPreId() {
        return preId;
    }

    public void setPreId(Long preId) {
        this.preId = preId;
    }

    public String getPreName() {
        return preName;
    }

    public void setPreName(String preName) {
        this.preName = preName == null ? null : preName.trim();
    }

    public Long getAuthUserId() {
        return authUserId;
    }

    public void setAuthUserId(Long authUserId) {
        this.authUserId = authUserId;
    }

    public Short getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(Short authFlag) {
        this.authFlag = authFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
    
    
}