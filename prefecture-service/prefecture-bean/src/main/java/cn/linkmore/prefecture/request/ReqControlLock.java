package cn.linkmore.prefecture.request;



public class ReqControlLock {
    
	private Long stallId;
	
	private String lockSn;
	
	private Integer  status;
	
	private String key;
	
	private String robkey;

	private Short type;
	

	public String getLockSn() {
		return lockSn;
	}

	public void setLockSn(String lockSn) {
		this.lockSn = lockSn;
	}

	public String getRobkey() {
		return robkey;
	}

	public void setRobkey(String robkey) {
		this.robkey = robkey;
	}

	public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}
	
	
	
}
