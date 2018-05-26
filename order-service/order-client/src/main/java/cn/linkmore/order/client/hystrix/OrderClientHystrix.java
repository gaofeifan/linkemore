package cn.linkmore.order.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.request.ReqOrderCreate;
import cn.linkmore.order.request.ReqOrderDown;
import cn.linkmore.order.request.ReqOrderSwitch;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.util.JsonUtil;

@Component
public class OrderClientHystrix implements OrderClient {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	 
	public void create(@RequestBody ReqOrderCreate roc){
		log.info("order create :{}",JsonUtil.toJson(roc)); 
	} 
	 
	public void switchStall(@RequestBody ReqOrderSwitch ros){
		log.info("order create :{}",JsonUtil.toJson(ros)); 
	} 
	
	 
	public ResUserOrder last(@RequestParam("userId") Long userId){
		log.info("latest order :{}",userId);
		return null;
	}
	
	 
	public ResUserOrder detail(@PathVariable("id") Long id){
		log.info("detail order :{}",id);
		return null;
	}
	 
	public void down(@RequestBody ReqOrderDown rod){
		log.info("down stall :{}",JsonUtil.toJson(rod)); 
	} 
}
