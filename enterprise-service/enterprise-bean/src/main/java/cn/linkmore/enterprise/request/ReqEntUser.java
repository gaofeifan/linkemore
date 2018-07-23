package cn.linkmore.enterprise.request;

/**
 * 请求bean
 * 
 * @author jiaohanbin
 * @Version v2.0
 */
public class ReqEntUser {

	private Long entId;

	private Long userId;

	public ReqEntUser(Long entId, Long userId) {
		this.entId = entId;
		this.userId = userId;
	}

	public Long getEntId() {
		return entId;
	}

	public void setEntId(Long entId) {
		this.entId = entId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
