package cn.linkmore.order.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.order.client.hystrix.PayClientHystrix;
import cn.linkmore.order.request.ReqOrderConfirm;
import cn.linkmore.order.response.ResOrderCheckout;
import cn.linkmore.order.response.ResOrderConfirm;

@FeignClient(value = "order-server", path = "/pay", fallback=PayClientHystrix.class,configuration = FeignConfiguration.class)
public interface PayClient {
	
	/**
	 * 生成账单
	 * @param orderId
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/v2.0/checkout", method = RequestMethod.GET)
	@ResponseBody
	public  ResOrderCheckout checkout(@RequestParam("orderId") Long orderId,@RequestParam("userId") Long userId) ;
	 
	/**
	 * 确认付款
	 * @param roc
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/v2.0/confirm", method = RequestMethod.POST)
	@ResponseBody
	public ResOrderConfirm confirm(@RequestBody ReqOrderConfirm roc);
	
	 
	/**
	 * 核验订单
	 * @param orderId
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/v2.0/verify", method = RequestMethod.GET)
	@ResponseBody
	public Boolean verify(@RequestParam("orderId") Long orderId,@RequestParam("userId") Long userId) ;
	
	/**
	 * 异步回调
	 * @param json
	 * @param source
	 * @return
	 */
	@RequestMapping(value = "/v2.0/callback", method = RequestMethod.POST)
	@ResponseBody
	public Boolean callback(@RequestParam("json") String json,@RequestParam("source") Integer source);
}
