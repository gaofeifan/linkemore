package cn.linkmore.order.core.pay;

import cn.linkmore.order.controller.app.response.ResPayConfirm;

public class PayClient {
	private PayType payType;
	
	private ReqPay pay;

	public PayClient(ReqPay pay) {
		this.pay = pay;
	}

	public ResPayConfirm getResPayConfirm(int index) {
		return payType.get(index).pay(pay);
	}
	
	public ResPayConfirm getResPayConfirm(PayType payType) {
		this.payType = payType;
		return payType.get().pay(pay);
	}
	
	
}
