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
import cn.linkmore.coupon.response.ResTemplate;
import cn.linkmore.feign.FeignConfiguration;
/**
 * Client  - 停车券
 * @author liwenlong
 * @version 2.0
 *
 */
@FeignClient(value = "coupon-server", path = "/feign/coupons", fallback=CouponClientHystrix.class,configuration = FeignConfiguration.class)
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
	/**
	 * 降锁失败切换车位失败后关闭订单发送优惠券功能
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/v2.0/send", method = RequestMethod.GET)
	@ResponseBody
	public boolean send(@RequestParam(value="userId") Long userId);
	
	
	/**
	 * 实付结账发送专题优惠券
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/v2.0/pay-send", method = RequestMethod.GET)
	@ResponseBody
	public boolean paySend(@RequestParam(value="userId") Long userId);
	
	/**
	 * 品牌车位发送优惠券功能
	 * @param b 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/v2.0/send_brand_coupon", method = RequestMethod.GET)
	@ResponseBody
	public boolean sendBrandCoupon(@RequestParam(value="isBrandUser") Boolean isBrandUser, @RequestParam(value="entId") Long entId, @RequestParam(value="userId") Long userId);

	/**
	 * 查询当前用户是否已申请品牌优惠券
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/v2.0/find_brand_coupon", method = RequestMethod.POST)
	@ResponseBody
	public List<ResCoupon> findBrandCouponList(@RequestParam(value="entId") Long entId, @RequestParam(value="userId") Long userId);
	
	/**
	 * 根据当前企业id查询配置优惠券套餐信息
	 * @param entId
	 * @return
	 */
	@RequestMapping(value = "/v2.0/ent-template-list", method = RequestMethod.GET)
	@ResponseBody
	public List<ResTemplate> findEntTemplateList(@RequestParam(value="entId") Long entId);
}
