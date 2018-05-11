package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.third.client.hystrix.SmsClientHystrix;
import cn.linkmore.third.request.ReqSms;

/**
 * Client - 短信服务
 * @author liwenlong
 *
 */
@FeignClient(value = "third-party-server", path = "/third/sms", fallback=SmsClientHystrix.class,configuration = FeignConfiguration.class)
public interface SmsClient {
	
	/**
	 * 发短信
	 * @param mobile 手机号
	 * @param st SmsTemplate参数
	 * @param param 短信内部参数(如无参数请传null)
	 * @return  true 成功，false 失败
	 */
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Boolean send(@RequestBody ReqSms req);
}
