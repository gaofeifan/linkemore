package cn.linkmore.enterprise.entity;

public enum OperationEnum {
	UP(1),DOWN(2),FREE(3),FORCE(4);
	
	private int code;
	
	private OperationEnum(int code){
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	
}
