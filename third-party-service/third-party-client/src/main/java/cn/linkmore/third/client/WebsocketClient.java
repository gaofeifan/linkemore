package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.third.client.hystrix.WebsocketClientHystrix;

@FeignClient(value = "third-party-server", path = "/feign/ws", fallback=WebsocketClientHystrix.class,configuration = FeignConfiguration.class)
public interface WebsocketClient {
	
	/**
	 * 发送推送[websocket]
	 * @param message json类型
	 * @param token token
	 * @return 成功失败
	 */
	@RequestMapping(value = "/v2.0/send", method = RequestMethod.POST) 
	@ResponseBody
	public Boolean push(String message,String token);
	
}
