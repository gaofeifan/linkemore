package cn.linkmore.coupon.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.coupon.request.ReqCouponPay;
import cn.linkmore.coupon.response.ResCoupon;
import cn.linkmore.coupon.response.ResTemplate;

/**
 * Service - 停车券
 * @author liwenlong
 * @version 2.0
 *
 */
public interface CouponService {

	/**
	 * 查询用户下所有可用停车券
	 * @param userId 用户ID
	 * @return
	 */
	List<ResCoupon> userEnabledList(Long userId);

	/**
	 * 查询用户订单可用停车券
	 * @param userId 用户ID
	 * @param orderId 订单ID
	 * @return
	 */
	List<ResCoupon> userOrderEnableList(Long userId, Long orderId);

	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	ResCoupon find(Long id);

	/**
	 * 消费停车券
	 * @param rcp
	 */
	void pay(ReqCouponPay rcp);

	/**
	 * App接口用户可用列表
	 * @param request
	 * @return
	 */
	List<cn.linkmore.coupon.controller.app.response.ResCoupon> usableList(HttpServletRequest request);

	/**
	 * App接口用户支付列表
	 * @param request
	 * @return
	 */
	List<cn.linkmore.coupon.controller.app.response.ResCoupon> paymentList(HttpServletRequest request);
	/**
	 * App接口发送优惠券
	 * @param userId
	 * @return
	 */
	boolean send(Long userId);
	/**
	 * 发送企业品牌优惠券
	 * @param isBrandUser 
	 * @param entId 
	 * @param userId
	 * @return
	 */
	boolean sendBrandCoupon(Boolean isBrandUser, Long entId,Long userId);
	
	/**
	 * 查询当前用户是否已申请企业品牌优惠券
	 * @param entId
	 * @param userId
	 * @return
	 */
	List<ResCoupon> findBrandCouponList(Long entId,Long userId);
	/**
	 * 企业优惠券
	 * @param entId
	 * @return
	 */
	List<ResTemplate> findEntTemplateList(Long entId);
	/**
	 * 实付结账发送优惠券
	 * @param userId
	 * @param type 
	 * @return
	 */
	boolean paySend(Long userId, Integer type);

}
