package cn.linkmore.order.core.pay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.linkmore.order.controller.app.response.ResPayConfirm;

public class AccountPay implements PaymentStrategy {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public ResPayConfirm pay(ReqPay pay) {
		return null;
	}

}
