package cn.linkmore.enterprise.response;

import java.util.Date;

public class ResEntRentUser {
    private Long id;

    private Long entId;

    private Long entPreId;

    private Long preId;

    private String mobile;

    private String realname;

    private String plate;

    private Long stallId;

    private Long userId;

    private String entName;
    
    private String preName;
    
    private String stallName;
    
    private Date startTime;
    
    private Date endTime;
    
    /**
     *  0个人 1企业
     */ 
    private Short type;
    //车位状态
    private Integer status;
    
    /**
     * 创建用户Id
     */
    private Long createUserId;
    /**
     * 创建用户名称
     */
    private String createUserName;
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新用户Id
     */
    private Long updateUserId;
    /**
     * 更新用户名称
     */
    private String updateUserName;
    /**
     * 更新时间
     */
    private Date updateTime;
    
    public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
    
    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntId() {
        return entId;
    }

    public void setEntId(Long entId) {
        this.entId = entId;
    }

    public Long getEntPreId() {
        return entPreId;
    }

    public void setEntPreId(Long entPreId) {
        this.entPreId = entPreId;
    }

    public Long getPreId() {
        return preId;
    }

    public void setPreId(Long preId) {
        this.preId = preId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate == null ? null : plate.trim();
    }


    public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}

	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}

	public String getStallName() {
		return stallName;
	}

	public void setStallName(String stallName) {
		this.stallName = stallName;
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

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}
	
	
}