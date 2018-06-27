package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.third.client.hystrix.MiniProgramClientHystrix;
import cn.linkmore.third.response.ResMiniSession;
/**
 * Client - 微信小程序
 * @author liwenlong
 * @version 2.0
 */
@FeignClient(value = "third-party-server", path = "/feign/mini-program", fallback=MiniProgramClientHystrix.class,configuration = FeignConfiguration.class)
public interface MiniProgramClient {
	
	/**
	 * 根据code取用户session
	 * @param code 授权码
	 * @return
	 */
	@RequestMapping(value = "/v2.0/session/{code}", method = RequestMethod.GET) 
	@ResponseBody
	public ResMiniSession getSession(@PathVariable("code") String code);

}
