package cn.linkmore.order.controller;

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
	 
	@RequestMapping(value = "/v2.0", method = RequestMethod.POST)
	@ResponseBody
	public void create(@RequestBody ReqOrderCreate roc){
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
}
