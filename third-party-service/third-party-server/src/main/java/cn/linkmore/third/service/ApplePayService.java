package cn.linkmore.third.service;

import cn.linkmore.third.request.ReqApplePay;

public interface ApplePayService {

	/**
	 * 统一下单
	 * @param order
	 * @return
	 */
	String order(ReqApplePay order);

}
