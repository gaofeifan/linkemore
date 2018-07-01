package cn.linkmore.order.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.linkmore.order.controller.app.request.ReqPayConfirm;
import cn.linkmore.order.controller.app.response.ResOrderDetail;
import cn.linkmore.order.controller.app.response.ResPayCheckout;
import cn.linkmore.order.controller.app.response.ResPayConfirm;

/**
 * Service接口 - 支付
 * @author liwenlong
 * @version 2.0
 */
public interface PayService {

	/**
	 * 生成账单
	 * @param orderId
	 * @param request
	 * @return
	 */
	ResPayCheckout checkout(Long orderId, HttpServletRequest request);

	/**
	 * 确认支付[生成第三支付订单]
	 * @param roc
	 * @return request
	 */
	ResPayConfirm confirm(ReqPayConfirm roc, HttpServletRequest request);

	/**
	 * 核验支付结果
	 * @param orderId
	 * @param request
	 * @return
	 */
	ResOrderDetail verify(Long orderId, HttpServletRequest request); 
	/**
	 * 微信预约结账回调
	 * @param response
	 * @param request
	 */
	void wechatOrderNotice(HttpServletResponse response, HttpServletRequest request);

	/**
	 * 支付宝预约结账回调
	 * @param response
	 * @param request
	 */
	void alipayOrderNotice(HttpServletResponse response, HttpServletRequest request);

	/**
	 * Apple Pay预约结账回调
	 * @param response
	 * @param request
	 */
	void appleOrderNotice(HttpServletResponse response, HttpServletRequest request);

	/**
	 * 微信小程序支付异步回调
	 * @param response
	 * @param request
	 */
	void wechatMiniOrderNotice(HttpServletResponse response, HttpServletRequest request);

}
