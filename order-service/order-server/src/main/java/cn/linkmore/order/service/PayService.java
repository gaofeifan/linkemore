package cn.linkmore.order.service;

import cn.linkmore.order.request.ReqOrderConfirm;
import cn.linkmore.order.response.ResOrderCheckout;
import cn.linkmore.order.response.ResOrderConfirm;

/**
 * Service接口 - 支付
 * @author liwenlong
 * @version 2.0
 */
public interface PayService {

	/**
	 * 生成账单
	 * @param orderId
	 * @param userId
	 * @return
	 */
	ResOrderCheckout checkout(Long orderId, Long userId);

	/**
	 * 确认支付[生成第三支付订单]
	 * @param roc
	 * @return
	 */
	ResOrderConfirm confirm(ReqOrderConfirm roc);

	/**
	 * 核验支付结果
	 * @param orderId
	 * @param userId
	 * @return
	 */
	Boolean verify(Long orderId, Long userId);

	/**
	 * 支付回调
	 * @param json
	 * @param source
	 * @return
	 */
	Boolean callback(String json, Integer source);

}
