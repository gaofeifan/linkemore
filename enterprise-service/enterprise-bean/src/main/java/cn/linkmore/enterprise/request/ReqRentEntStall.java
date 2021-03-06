package cn.linkmore.enterprise.request;

import java.util.Date;

public class ReqRentEntStall {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 公司Id
	 */
    private Long rentComId;

    private Long stallId;

    private String stallName;
    
    private Long preId;
    
    private String preName;
    
    /**
     * 创建用户Id
     */
    private Long createUserId;
    /**
     * 创建用户名称
     */
    private String createUserName;
    
    /**
     * 创建用户Id
     */
    private Long entId;
    /**
     * 创建用户名称
     */
    private String entName;
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
	 * @return the entId
	 */
	public Long getEntId() {
		return entId;
	}

	/**
	 * @param entId the entId to set
	 */
	public void setEntId(Long entId) {
		this.entId = entId;
	}

	/**
	 * @return the entName
	 */
	public String getEntName() {
		return entName;
	}

	/**
	 * @param entName the entName to set
	 */
	public void setEntName(String entName) {
		this.entName = entName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRentComId() {
		return rentComId;
	}

	public void setRentComId(Long rentComId) {
		this.rentComId = rentComId;
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
}