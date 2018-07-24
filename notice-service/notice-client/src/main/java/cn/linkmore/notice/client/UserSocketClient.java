package cn.linkmore.notice.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.notice.client.hystrix.UserSocketClientHystrix;

@FeignClient(value = "notice-server", path = "/feign/user-socket", fallback=UserSocketClientHystrix.class,configuration = FeignConfiguration.class)
public interface UserSocketClient {
	/**
	 * 发送消息通知
	 * @param message 消息JSON
	 * @param token 用户对应的token
	 * @return
	 */
	@RequestMapping(value = "/v2.0/send", method = RequestMethod.POST) 
	@ResponseBody
	public Boolean push(@RequestParam("message")String message,@RequestParam("openid")String openid);
}
