package cn.linkmore.third.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.third.client.WechatClient;

@Component
public class WechatClientHystrix implements WechatClient {
	
	private  Logger log = LoggerFactory.getLogger(getClass());
	@Override
	public String getTicket(String actionName, Long sceneId) {
		log.info("wechat client hystrix");
		return null;
	}

}
