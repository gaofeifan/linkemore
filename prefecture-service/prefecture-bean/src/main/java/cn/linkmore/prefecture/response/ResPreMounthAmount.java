package cn.linkmore.prefecture.response;
/**
 * 响应-车区月度目标设定
 * @author jiaohanbin
 * @version 2.0
 */
public class ResPreMounthAmount {
	private Long mounthId;
	private Long orderCount;
	private Long userCount;
	public Long getMounthId() {
		return mounthId;
	}
	public void setMounthId(Long mounthId) {
		this.mounthId = mounthId;
	}
	public Long getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
	}
	public Long getUserCount() {
		return userCount;
	}
	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}
	
}
