package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.third.client.hystrix.AppAlipayClientHystrix;
import cn.linkmore.third.request.ReqAppAlipay;
/**
 * Client - 支付宝支付服务[集成App]
 * @author liwenlong
 * @version 2.0
 *
 */
@FeignClient(value = "third-party-server", path = "/feign/alipay", fallback=AppAlipayClientHystrix.class,configuration = FeignConfiguration.class)
public interface AppAlipayClient {
	/**
	 * 发起支付宝支付
	 * @param alipay
	 * @return
	 */
	@RequestMapping(value = "/v2.0/order", method = RequestMethod.POST) 
	@ResponseBody
	public String order(@RequestBody ReqAppAlipay alipay);
	
	/**
	 * 核验支付
	 * @param json 
	 * @param number
	 * @param amount
	 * @return
	 */
	@RequestMapping(value = "/v2.0/verify", method = RequestMethod.POST) 
	@ResponseBody
	public Boolean verify(@RequestParam("json")String json,@RequestParam("number")String number,@RequestParam("amount")String amount) ;
}
