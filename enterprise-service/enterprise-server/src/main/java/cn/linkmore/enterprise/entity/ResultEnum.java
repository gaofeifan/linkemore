package cn.linkmore.enterprise.entity;

public enum ResultEnum {
	SUCCESS("操作成功",1),FAIL("操作失败",0);
	
	private String msg;
	
	private int code;
	
	private ResultEnum(String msg,int code){
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	

}
