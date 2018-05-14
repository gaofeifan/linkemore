package cn.linkmore.bean.common.security;

import java.io.Serializable;

/**
 * 登录信息
 * @author liwl
 * @version 1.0
 *
 */
public class Token   implements Serializable{
	/**
	 * serial id
	 */
	private static final long serialVersionUID = 9006558116290567282L;
	public final static short OS_ANDROID = 1;
	public final static short OS_IOS = 2;
	private String value;
	private Long timestamp;
	private Short os;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public Short getOs() {
		return os;
	}
	public void setOs(Short os) {
		this.os = os;
	} 
}
