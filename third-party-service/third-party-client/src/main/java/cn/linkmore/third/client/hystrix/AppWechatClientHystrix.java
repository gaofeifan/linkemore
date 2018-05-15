package cn.linkmore.third.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import cn.linkmore.third.client.AppWechatClient;
import cn.linkmore.third.response.ResFans;
@Component
public class AppWechatClientHystrix implements AppWechatClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public ResFans getFans(@RequestParam String code) {
		log.warn("get app wechat fans failure");
		return null;
	}
}
