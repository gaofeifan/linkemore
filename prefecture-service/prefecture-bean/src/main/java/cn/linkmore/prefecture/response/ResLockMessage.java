package cn.linkmore.prefecture.response;


/**
 * 锁服务返回的状态数据
 * @author   GFF
 * @Date     2018年11月15日
 * @Version  v2.0
 */
public class ResLockMessage {
	
	/**
	 *  状态码
	 */ 
	private int code;
	
	/**
	 *  数据
	 */ 
	private Object data;
	
	/**
	 *  异常信息
	 */ 
	private String message;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
