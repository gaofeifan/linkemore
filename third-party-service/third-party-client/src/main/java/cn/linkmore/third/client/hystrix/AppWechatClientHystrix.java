package cn.linkmore.third.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import cn.linkmore.third.client.AppWechatClient;
import cn.linkmore.third.request.ReqAppWechatOrder;
import cn.linkmore.third.response.ResAppWechatOrder;
import cn.linkmore.third.response.ResFans;
@Component
public class AppWechatClientHystrix implements AppWechatClient {
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public ResFans getFans(@PathVariable("code") String code) {
		log.warn("get app wechat fans hystrix");
		return null;
	}
	
	 
	public ResAppWechatOrder order(@RequestBody ReqAppWechatOrder wechat) {
		log.warn("get app wechat order hystrix");
		return null;
	}
	
	 
	public Boolean verify(@RequestParam("json")String json) { 
		log.warn("get app wechat verify hystrix");
		return false;
	};
}
