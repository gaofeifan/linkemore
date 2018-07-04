package cn.linkmore.third.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import cn.linkmore.third.client.WechatMiniClient;
import cn.linkmore.third.request.ReqWechatMiniOrder;
import cn.linkmore.third.response.ResMiniSession;
import cn.linkmore.third.response.ResWechatMiniOrder;
@Component
public class WechatMiniClientHystrix implements WechatMiniClient {
	 
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	public ResMiniSession getSession(@PathVariable("code") String code) {
		log.info("wechat mini program get session hystrix");
		return null;
	}
	 
	public ResWechatMiniOrder order(@RequestBody ReqWechatMiniOrder wechat) { 
		log.info("wechat mini program  order hystrix");
		return null;
	}
	 
	public Boolean verify(@RequestParam("json")String json) {
		log.info("wechat mini program  verify order hystrix");
		return false;
	}
}
