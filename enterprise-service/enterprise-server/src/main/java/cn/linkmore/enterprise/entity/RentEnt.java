package cn.linkmore.enterprise.entity;

import java.util.Date;

public class RentEnt {
	/**
	 * 主键
	 */
    private Long id;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 开始日期
     */
    private Date startTime;
    /**
     * 结束日期
     */
    private Date endTime;
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
    
    /**
	 * 状态 0默认１关闭 ２开启 ３过期
	 */
	private Integer status;
	/**
	 * 车区id
	 */
	private Long preId;
	/**
	 * 车区名称
	 */
	private String preName;
	

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
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
		this.preName = preName;
	}
	
}