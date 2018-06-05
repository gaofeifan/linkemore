package cn.linkmore.coupon.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.coupon.client.hystrix.CouponClientHystrix;
import cn.linkmore.coupon.request.ReqCouponPay;
import cn.linkmore.coupon.response.ResCoupon;
import cn.linkmore.feign.FeignConfiguration;
/**
 * Client  - 停车券
 * @author liwenlong
 * @version 2.0
 *
 */
@FeignClient(value = "coupon-server", path = "/coupons", fallback=CouponClientHystrix.class,configuration = FeignConfiguration.class)
public interface CouponClient {
	/**
	 * 消费停车券
	 * @param rcp
	 */
	@RequestMapping(value = "/v2.0/pay", method = RequestMethod.POST) 
	public void pay(@RequestBody ReqCouponPay rcp);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/v2.0/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResCoupon get(@PathVariable("id")  Long id);
	/**
	 * 用户可用停车券列表
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/v2.0/enable", method = RequestMethod.GET)
	@ResponseBody
	public List<ResCoupon> enable(@RequestParam(value="userId") Long userId);
	
	/**
	 * 用户可支付订单停车券列表
	 * @param userId
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/v2.0/order", method = RequestMethod.GET)
	@ResponseBody
	public List<ResCoupon> order(@RequestParam(value="userId") Long userId,@RequestParam(value="orderId") Long orderId);
}
