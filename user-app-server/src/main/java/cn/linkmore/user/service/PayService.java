package cn.linkmore.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.linkmore.user.request.ReqPayConfirm;
import cn.linkmore.user.response.ResOrderDetail;
import cn.linkmore.user.response.ResPayCheckout;
import cn.linkmore.user.response.ResPayConfirm;

public interface PayService {

	/**
	 * 生成账号
	 * @param orderId
	 * @param request
	 * @return
	 */
	ResPayCheckout checkout(Long orderId, HttpServletRequest request);

	/**
	 * 确认付款
	 * @param roc
	 * @param request
	 * @return
	 */
	ResPayConfirm confirm(ReqPayConfirm roc, HttpServletRequest request);

	/**
	 * 核验订单
	 * @param orderId
	 * @param request
	 */
	ResOrderDetail verify(Long orderId, HttpServletRequest request); 

	/**
	 * 微信异步通知[订单]
	 * @param response
	 * @param request
	 */
	void wechatOrderNotice(HttpServletResponse response, HttpServletRequest request);

	/**
	 * 支付宝异步通知[订单]
	 * @param response
	 * @param request
	 */
	void alipayOrderNotice(HttpServletResponse response, HttpServletRequest request);

	/**
	 * 苹果支付异步通知[订单]
	 * @param response
	 * @param request
	 */
	void appleOrderNotice(HttpServletResponse response, HttpServletRequest request);

}
