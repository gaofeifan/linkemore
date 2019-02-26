package cn.linkmore.order.core.pay;

public enum PayType {

//	ACCOUNT(0,new AliPay()),
	ALIPAY( 1,new AliPay()),
	WECHAT ( 2,new WechatPay()), 
	APPLE (3,new ApplePay()),
	WECHAT_MINI (4,new WechatMiniPay()),
	UNION(5,new UnionPay()),
	HUAWEI(6,new HuaWeiPay()),
	XIAOMI(7,new XiaoMiPay()),
	CCB(8,new CCBPay());
	
	private int index;
	private PaymentStrategy payment;
	
	private PayType(PaymentStrategy payment) {
		this.payment = payment;
	}
	private PayType (int index , PaymentStrategy payment) {
		this.index = index;
		this.payment = payment;
	}
	public PaymentStrategy get() {
		return payment;
	}

	public PaymentStrategy get(int index) {
		for (PayType v : values()) {
			if(v.index == index) {
				return v.payment;
			}
		}
		return null;
	}
	
	
}
