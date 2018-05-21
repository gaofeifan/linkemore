package cn.linkmore.coupon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.coupon.response.ResCoupon;
import cn.linkmore.coupon.service.CouponService;
/**
 * Controller - 停车券
 * @author liwenlong
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/coupons")
public class CouponController {
	@Autowired
	private CouponService couponService;
	
	@RequestMapping(value = "/v2.0/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public List<ResCoupon> enable(@RequestParam(value="userId") Long userId){
		return this.couponService.userEnabledList(userId);
	}
	@RequestMapping(value = "/v2.0/{userId}/{orderId}", method = RequestMethod.GET)
	@ResponseBody
	public List<ResCoupon> order(@RequestParam(value="userId") Long userId,@RequestParam(value="orderId") Long orderId){
		return this.couponService.userOrderEnableList(userId,orderId);
	}
}
