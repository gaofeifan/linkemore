package cn.linkmore.coupon.service;

import java.util.List;

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
	 * @param userId
	 * @return
	 */
	List<ResCoupon> enabledList(Long userId);

}
