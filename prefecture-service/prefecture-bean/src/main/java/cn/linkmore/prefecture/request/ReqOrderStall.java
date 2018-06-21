package cn.linkmore.prefecture.request;

/**
 * 订单车位
 * @author   GFF
 * @Date     2018年6月21日
 * @Version  v2.0
 */
public class ReqOrderStall {

	private Long stallId;
	
	private Long orderId;
	
	private boolean flag;
	
	private Long userId;

	public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "ReqOrderStall [stallId=" + stallId + ", orderId=" + orderId + ", flag=" + flag + ", userId=" + userId
				+ "]";
	}

	
	
	
}
