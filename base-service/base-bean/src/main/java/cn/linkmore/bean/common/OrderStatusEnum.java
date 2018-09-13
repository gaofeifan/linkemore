package cn.linkmore.bean.common;

public enum OrderStatusEnum {
	// 绑定订单状态
	ORDER_STATUS_NORMAL("正常", 0, "order_status_normal"), ORDER_STATUS_HANGUP("挂起", 1,
			"order_status_hangup"), ORDER_STATUS_CLOSE("挂起", 2, "order_status_close");

	private String msg;

	private int code;

	private String value;

	private OrderStatusEnum(String msg, int code, String value) {
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

	public static OrderStatusEnum valueOf(int code) {
		OrderStatusEnum[] stall = OrderStatusEnum.values();
		for (OrderStatusEnum sta : stall) {
			if (code == sta.getCode()) {
				return sta;
			}
		}
		return null;
	}

}
