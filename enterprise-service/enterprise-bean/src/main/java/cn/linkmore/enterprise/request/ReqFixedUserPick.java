package cn.linkmore.enterprise.request;

public class ReqFixedUserPick {
	
	private Integer fixedId;

    private Integer userId;
    
    private Integer state;

    private Integer createUserId;

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
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
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
