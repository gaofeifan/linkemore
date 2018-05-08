package cn.linkmore.common.request;

/**
 * Request - Redis对象
 * @author liwenlong
 * @version 2.0
 *
 */
public class ReqRedisObject {
	private String key;
	private Object value;
	private Long expireTime;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public Long getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	} 
}
