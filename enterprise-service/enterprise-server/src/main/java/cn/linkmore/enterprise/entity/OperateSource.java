package cn.linkmore.enterprise.entity;

public enum OperateSource {
	
	ADMIN("管理版",2);
	
	private String name;
	
	private int code;
	
	private OperateSource(String name,int code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getCode() {
		return (short) code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	

}
