package cn.linkmore.bean.common;

public enum StallEnum {
	//车位使用状态
	STATUS_FREE("空闲",1,"free"),
	STATUS_USING("使用中",2,"using"),
	STATUS_PREP_OFFLINE("预下线",3,"prep_offline"),
	STATUS_OFFLINE("下线",4,"offline")
	;
	
	private String msg;
	
	private int code;
	
	private String value;
	
	private StallEnum(String msg,int code,String value){
		this.msg = msg;
		this.code = code;
		this.value = value;
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
	
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static StallEnum valueOf(int code){
		StallEnum[] stall =	StallEnum.values();
		for (StallEnum sta:stall) {
			if(code == sta.getCode()){
				return sta;
			}
		}
		return null;
	}
	
	
	
	
	

}
