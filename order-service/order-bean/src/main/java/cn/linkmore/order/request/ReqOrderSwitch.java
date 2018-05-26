package cn.linkmore.order.request;

public class ReqOrderSwitch {
	private Long userId;
	private Long orderId;
	private Long causeId;
	private String remark;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getCauseId() {
		return causeId;
	}
	public void setCauseId(Long causeId) {
		this.causeId = causeId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	} 
}
