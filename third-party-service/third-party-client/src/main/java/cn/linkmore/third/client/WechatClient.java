package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.third.client.hystrix.WechatClientHystrix;

/**
 * Client - 微信服务号
 * @author liwenlong
 * @version 2.0
 */
@FeignClient(value = "third-party-server", path = "/feign/wechat", fallback=WechatClientHystrix.class,configuration = FeignConfiguration.class)
public interface WechatClient {
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public String getTicket(@RequestParam("actionName") String actionName,@RequestParam("sceneId")Long sceneId);
	
}
