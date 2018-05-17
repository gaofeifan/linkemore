package cn.linkmore.third.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.third.client.hystrix.PushClientHystrix;
import cn.linkmore.third.request.ReqPush;

/**
 * Client - 推送
 * @author liwenlong
 * @version 2.0
 * 
 */
@FeignClient(value = "third-party-server", path = "/third/push", fallback=PushClientHystrix.class,configuration = FeignConfiguration.class)
public interface PushClient {
	/**
	 * 推送消息
	 * 个推
	 * @param rp
	 */ 
	@RequestMapping(value = "/v2.0", method = RequestMethod.POST) 
	void push(@RequestBody ReqPush rp);
 
	/**
	 * 推送消息
	 * 群推
	 * @param rps
	 */ 
	@RequestMapping(value = "/v2.0", method = RequestMethod.PUT) 
	void push(@RequestBody List<ReqPush> rps) ;
}
