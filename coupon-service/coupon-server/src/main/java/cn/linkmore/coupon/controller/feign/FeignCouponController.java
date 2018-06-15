package cn.linkmore.coupon.controller.feign;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.coupon.request.ReqCouponPay;
import cn.linkmore.coupon.response.ResCoupon;
import cn.linkmore.coupon.service.CouponService;
/**
 * Controller - 停车券
 * @author liwenlong
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/feign/coupons")
public class FeignCouponController {
	@Autowired
	private CouponService couponService;
	
	@RequestMapping(value = "/v2.0/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResCoupon get(@PathVariable("id")  Long id){
		return this.couponService.find(id);
	}
	
	@RequestMapping(value = "/v2.0/pay", method = RequestMethod.POST) 
	public void pay(@RequestBody ReqCouponPay rcp){
		this.couponService.pay(rcp);
	}
	
	@RequestMapping(value = "/v2.0/enable", method = RequestMethod.GET)
	@ResponseBody
	public List<ResCoupon> enable(@RequestParam(value="userId") Long userId){
		return this.couponService.userEnabledList(userId);
	}
	@RequestMapping(value = "/v2.0/order", method = RequestMethod.GET)
	@ResponseBody
	public List<ResCoupon> order(@RequestParam(value="userId") Long userId,@RequestParam(value="orderId") Long orderId){
		return this.couponService.userOrderEnableList(userId,orderId);
	}
}