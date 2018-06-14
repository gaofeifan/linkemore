package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.third.client.hystrix.ApplePayClientHystrix;
import cn.linkmore.third.request.ReqApplePay;

/**
 * Client - 苹果支付[App]
 * @author liwenlong
 * @version 2.0
 */
@FeignClient(value = "third-party-server", path = "/feign/apple-pay", fallback=ApplePayClientHystrix.class,configuration = FeignConfiguration.class)
public interface ApplePayClient {
	/**
	 * 统一下单
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/v2.0/order", method = RequestMethod.POST) 
	@ResponseBody
	public String order(@RequestBody ReqApplePay order);
	
	/**
	 * 核验订单
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/v2.0/verify", method = RequestMethod.POST) 
	@ResponseBody
	public Boolean verify(@RequestParam("json")String json) ;
}
