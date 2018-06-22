package cn.linkmore.order.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.response.ResUserOrder;

@Component
public class OrderClientHystrix implements OrderClient {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	public ResUserOrder last(@RequestParam("userId") Long userId){
		log.info("latest order :{}",userId);
		return null;
	}

	@Override
	public void downMsgPush(Long orderId, Long stallId) {
		log.info("downMsgPush order :{}",orderId + "," + stallId);
		
	}
	
	
	 
}
