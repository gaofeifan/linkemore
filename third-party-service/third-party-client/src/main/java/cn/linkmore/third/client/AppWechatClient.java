package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.third.client.hystrix.AppWechatClientHystrix;
import cn.linkmore.third.request.ReqAppWechatOrder;
import cn.linkmore.third.response.ResAppWechatOrder;
import cn.linkmore.third.response.ResFans;

/**
 * Client - 微信支付[集成App]
 * @author liwenlong
 * @version 2.0
 */
@FeignClient(value = "third-party-server", path = "/app-wechat", fallback=AppWechatClientHystrix.class,configuration = FeignConfiguration.class)
public interface AppWechatClient {
	/**
	 * 根据code获取粉丝
	 * @param code 授权码
	 * @return
	 */
	@RequestMapping(value = "/v2.0/fans/{code}", method = RequestMethod.GET) 
	@ResponseBody
	ResFans getFans(@PathVariable("code") String code);
	
	@RequestMapping(value = "/v2.0/order", method = RequestMethod.POST) 
	@ResponseBody
	public ResAppWechatOrder order(@RequestBody ReqAppWechatOrder wechat);
	
	@RequestMapping(value = "/v2.0/verify", method = RequestMethod.POST) 
	@ResponseBody
	public Boolean verify(@RequestParam("json")String json);
}
