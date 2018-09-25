package cn.linkmore.third.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.third.client.SendClient;
import cn.linkmore.third.request.ReqPush;

@Component
public class SendClientHystrix implements  SendClient{

	private  final Logger log = LoggerFactory.getLogger(this.getClass());


	public void send(ReqPush rp) {
		log.warn("push failure");
	}


	@Override
	public void give(ReqPush rp) {
		log.warn("give failure");
	}
	
	
	
}
