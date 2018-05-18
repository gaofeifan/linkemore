package cn.linkmore.user.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.user.response.ResCoupon;

/**
 * Service接口 - 停车券
 * @author liwenlong
 * @version 2.0
 *
 */
public interface CouponService {

	/**
	 * 支付可用停车券
	 * @param request
	 * @return
	 */
	List<ResCoupon> paymentList(HttpServletRequest request);

	/**
	 * 用户可用停车券
	 * @param request
	 * @return
	 */
	List<ResCoupon> usableList(HttpServletRequest request);

}
