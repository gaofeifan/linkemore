package cn.linkmore.prefecture.response;
/**
 * 响应-车位信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ResStall {
	/**
	 * 车位id
	 */
	private Long id;
	/**
	 * 车位名称
	 */
	private String stallName;
	/**
	 * 车位锁编号
	 */
	private String lockSn;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStallName() {
		return stallName;
	}
	public void setStallName(String stallName) {
		this.stallName = stallName;
	}
	public String getLockSn() {
		return lockSn;
	}
	public void setLockSn(String lockSn) {
		this.lockSn = lockSn;
	}
}
