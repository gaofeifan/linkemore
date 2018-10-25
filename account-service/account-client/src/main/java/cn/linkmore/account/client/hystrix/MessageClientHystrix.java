package cn.linkmore.account.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.account.client.MessageClient;
import cn.linkmore.account.request.ReqMessage;
/**
 *验证码熔断
 * @author   GFF
 * @Date     2018年10月24日
 * @Version  v2.0
 */
@Component
public class MessageClientHystrix implements MessageClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void save(ReqMessage message) {
		log.info("public void save(ReqMessage message )Hystrix");
	}
	
}
