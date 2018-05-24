package cn.linkmore.order.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.order.request.ReqOrderCreate;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.order.service.OrdersService;
import cn.linkmore.util.JsonUtil;
/**
 * Controller - 订单
 * @author liwenlong
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {
	
	@Autowired
	private OrdersService ordersService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	 
	@RequestMapping(value = "/v2.0", method = RequestMethod.POST)
	@ResponseBody
	public void create(@RequestBody ReqOrderCreate roc){
		log.info("order create :{}",JsonUtil.toJson(roc));
		this.ordersService.create(roc);
	}
	
	@RequestMapping(value = "/v2.0/latest/{userId}/", method = RequestMethod.GET)
	@ResponseBody
	public ResUserOrder userLatest(@PathVariable("userId") Long userId){
		return this.ordersService.latest(userId);
	}
	
	@RequestMapping(value = "/v2.0/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResUserOrder detail(@PathVariable("id") Long id){
		return this.ordersService.detail(id);
	}
	@RequestMapping(value = "/v2.0/{id}/down", method = RequestMethod.PUT) 
	public void down(@PathVariable("id") Long id){
		this.ordersService.down(id);
	} 
}
