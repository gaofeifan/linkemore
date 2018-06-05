package cn.linkmore.prefecture.response;
/**
 * 响应 - 车区订单
 * @author jiaohanbin
 * @version 2.0
 */
public class ResPreOrderAmount {
	/**
	 * 专区ID
	 */
	private Long prefectureId;
	/**
	 * 订单数量
	 */
	private Long orderCount;
	public Long getPrefectureId() {
		return prefectureId;
	}
	public void setPrefectureId(Long prefectureId) {
		this.prefectureId = prefectureId;
	}
	public Long getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
	}
	
}
