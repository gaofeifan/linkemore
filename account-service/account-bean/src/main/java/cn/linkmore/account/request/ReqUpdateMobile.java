package cn.linkmore.account.request;

public class ReqUpdateMobile {

	private String mobile;
	
	private String code;

	private Long userId;
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCode() {
		return code;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
