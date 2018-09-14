package cn.linkmore.order.client.hystrix;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.order.client.FeignUnusualOrderClient;
import cn.linkmore.order.response.ResUnusualOrder;
@Component
public class FeignUnusualOrderClientHystrix implements FeignUnusualOrderClient {

	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<ResUnusualOrder> findList(Map<String, Object> map) {
		log.info("List<ResUnusualOrder> findList(Map<String, Object> map) Hystrix");
		return null;
	} 
	
	
}
