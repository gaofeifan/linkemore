package cn.linkmore.common.request;

public class ReqVersion {
	
	private Long userId;

	private Short client;

	private String model;

	private String osVersion; 

	private String version;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Short getClient() {
		return client;
	}

	public void setClient(Short client) {
		this.client = client;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	} 
	
	
	
}
