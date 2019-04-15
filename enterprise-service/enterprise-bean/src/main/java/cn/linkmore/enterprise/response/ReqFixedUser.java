package cn.linkmore.enterprise.response;

import java.util.Date;

public class ReqFixedUser {

	   private Long fixedId;
	  
	   private Long userId;

	   private String username;
	   
	   private String plates;
	   
	   private Long stalls;
	   
	   private Date lastTime;
	   
	   private Date endTime;
	   
	   private Date createTime;
	   
	   private Long pick;

	/**
	 * @return the fixedId
	 */
	public Long getFixedId() {
		return fixedId;
	}

	/**
	 * @param fixedId the fixedId to set
	 */
	public void setFixedId(Long fixedId) {
		this.fixedId = fixedId;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the plates
	 */
	public String getPlates() {
		return plates;
	}

	/**
	 * @param plates the plates to set
	 */
	public void setPlates(String plates) {
		this.plates = plates;
	}

	/**
	 * @return the stalls
	 */
	public Long getStalls() {
		return stalls;
	}

	/**
	 * @param stalls the stalls to set
	 */
	public void setStalls(Long stalls) {
		this.stalls = stalls;
	}

	/**
	 * @return the lastTime
	 */
	public Date getLastTime() {
		return lastTime;
	}

	/**
	 * @param lastTime the lastTime to set
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	 * @return the pick
	 */
	public Long getPick() {
		return pick;
	}

	/**
	 * @param pick the pick to set
	 */
	public void setPick(Long pick) {
		this.pick = pick;
	}

	
	   
	   
}
