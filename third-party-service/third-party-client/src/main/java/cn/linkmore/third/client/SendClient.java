package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.third.client.hystrix.SendClientHystrix;
import cn.linkmore.third.request.ReqPush;

/**
 * Client - 推送
 * @author cl
 * @version 2.0
 * 
 */
@FeignClient(value = "third-party-server", path = "/feign/send", fallback=SendClientHystrix.class,configuration = FeignConfiguration.class)
public interface SendClient {

	/**
	 * Client - 推送
	 * 物业版
	 * @param rps
	 */ 
	@RequestMapping(value = "/v2.0/send", method = RequestMethod.POST) 
	void send(@RequestBody ReqPush rp) ;
}
