package cn.linkmore.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.user.response.ResCoupon;
import cn.linkmore.user.service.CouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * Controller - 停车券
 * @author liwenlong
 * @version 2.0
 *
 */
@Api(tags="Coupon",description="停车券")
@RestController
@RequestMapping("/coupons")
public class CouponController {
	@Autowired
	private CouponService couponService; 
	
	@ApiOperation(value = "账户下停车券", notes = "停车劵管理", consumes = "application/json")
	@RequestMapping(value = "/v2.0/usable", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ResCoupon>> usableList(HttpServletRequest request) {  
		List<ResCoupon> resultMap = couponService.usableList(request); 
		return ResponseEntity.success(resultMap, request);
	}
	 
	
	@ApiOperation(value = "支付可用停车券", notes = "停车劵管理", consumes = "application/json")
	@RequestMapping(value = "/v2.0/payment", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ResCoupon>>  paymentList(HttpServletRequest request) {  
		List<ResCoupon> resultMap = couponService.paymentList(request);
		return ResponseEntity.success(resultMap, request);
	}
	
	
	
	
}
