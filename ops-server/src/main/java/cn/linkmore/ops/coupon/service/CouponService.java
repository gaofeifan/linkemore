package cn.linkmore.ops.coupon.service;

import java.util.List;

import cn.linkmore.coupon.request.ReqCouponPay;
import cn.linkmore.coupon.response.ResCoupon;

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

}
