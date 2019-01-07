package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.third.client.hystrix.AppAlipayClientHystrix;
import cn.linkmore.third.client.hystrix.AppLoongPayClientHystrix;
import cn.linkmore.third.request.ReqAppAlipay;
import cn.linkmore.third.request.ReqLongPay;
import cn.linkmore.third.request.ReqLoongPayVerifySign;
import cn.linkmore.third.response.ResLoongPay;
/**
 * @author   GFF
 * @Date     2019年1月3日
 * @Version  v2.0
 */
@FeignClient(value = "third-party-server", path = "/feign/loong-pay", fallback=AppLoongPayClientHystrix.class,configuration = FeignConfiguration.class)
public interface AppLoongPayClient {
	/**
	 * 发起支付宝支付
	 * @param alipay
	 * @return
	 */
	@RequestMapping(value = "/v2.0/order", method = RequestMethod.POST) 
	@ResponseBody
	public ResLoongPay order(@RequestBody ReqLongPay alipay);
	
	
	@RequestMapping(value="/v3.0/verify-sigature",method=RequestMethod.POST)
	@ResponseBody
	public boolean verifySigature(@RequestBody ReqLoongPayVerifySign sign);
}
