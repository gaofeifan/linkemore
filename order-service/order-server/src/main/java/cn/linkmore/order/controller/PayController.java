package cn.linkmore.order.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.order.request.ReqOrderConfirm;
import cn.linkmore.order.response.ResOrderCheckout;
import cn.linkmore.order.response.ResOrderConfirm;
import cn.linkmore.order.service.PayService;
import cn.linkmore.util.JsonUtil;

@RestController
@RequestMapping("/pay")
public class PayController {
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PayService payService;
	
	 
	@RequestMapping(value = "/v2.0/checkout", method = RequestMethod.GET)
	@ResponseBody
	public  ResOrderCheckout checkout(@RequestParam("orderId") Long orderId,@RequestParam("userId") Long userId) {
		log.info("pay checkout....{},{}",orderId,userId);
		return this.payService.checkout(orderId,userId); 
	}
	 
	@RequestMapping(value = "/v2.0/confirm", method = RequestMethod.POST)
	@ResponseBody
	public ResOrderConfirm confirm(@RequestBody ReqOrderConfirm roc) {
		log.info("pay confirm....");
		try {
			ResOrderConfirm confirm = this.payService.confirm(roc);
			log.info("confirm:{}",JsonUtil.toJson(confirm));
			return confirm;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	} 
	 
	@RequestMapping(value = "/v2.0/verify", method = RequestMethod.GET)
	@ResponseBody
	public Boolean verify(@RequestParam("orderId") Long orderId,@RequestParam("userId") Long userId) {
		log.info("pay verify....");
		return this.payService.verify(orderId,userId);
	}
	
	@RequestMapping(value = "/v2.0/callback", method = RequestMethod.POST)
	@ResponseBody
	public Boolean callback(@RequestParam("json") String json,@RequestParam("source") Integer source) {
		log.info("pay callback :{},source:{}",json,source);
		try {
			return this.payService.callback(json,source);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
}
