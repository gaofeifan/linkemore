package cn.linkmore.third.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import cn.linkmore.third.client.PushClient;
import cn.linkmore.third.request.ReqPush;
/**
 * hystrix - 推送
 * @author liwenlong
 * @version 2.0
 *
 */
@Component
public class PushClientHystrix implements PushClient {
	 
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	 
	public void push(@RequestBody ReqPush rp) {
		log.warn("push failure");
	}
 
	  
	public void push(@RequestBody List<ReqPush> rps) {
		log.warn("push failure");
	}
}
