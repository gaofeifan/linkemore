package cn.linkmore.enterprise.entity;

import java.util.Date;

public class FixedUserPick {

	private Integer fixedId;

    private Integer userId;
    
    private Date createTime;

    private  Integer createUserId;

	/**
	 * @return the fixedId
	 */
	public Integer getFixedId() {
		return fixedId;
	}

	/**
	 * @param fixedId the fixedId to set
	 */
	public void setFixedId(Integer fixedId) {
		this.fixedId = fixedId;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the createUserId
	 */
	public Integer getCreateUserId() {
		return createUserId;
	}

	/**
	 * @param createUserId the createUserId to set
	 */
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
    
    
	
	
}
