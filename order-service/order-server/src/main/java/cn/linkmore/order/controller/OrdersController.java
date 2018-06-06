package cn.linkmore.order.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.order.request.ReqOrderCreate;
import cn.linkmore.order.request.ReqOrderDown;
import cn.linkmore.order.request.ReqOrderSwitch;
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
	 
	@RequestMapping(value = "/v2.0/create", method = RequestMethod.POST) 
	public void create(@RequestBody ReqOrderCreate roc){
		log.info("order create :{}",JsonUtil.toJson(roc));
		this.ordersService.create(roc);
	} 
	@RequestMapping(value = "/v2.0/switch", method = RequestMethod.PUT) 
	public void switchStall(@RequestBody ReqOrderSwitch ros){
		log.info("order switch :{}",JsonUtil.toJson(ros));
		this.ordersService.switchStall(ros);
	} 
	
	@RequestMapping(value = "/v2.0/last", method = RequestMethod.GET)
	@ResponseBody
	public ResUserOrder last(@RequestParam("userId") Long userId){
		log.info("latest order :{}",userId);
		return this.ordersService.latest(userId);
	}
	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.GET)
	@ResponseBody
	public List<ResUserOrder> list(@RequestParam("userId") Long userId,@RequestParam("start") Long start){
		log.info("latest order :{}",userId);
		return this.ordersService.list(userId,start);
	}
	
	@RequestMapping(value = "/v2.0/detail", method = RequestMethod.GET)
	@ResponseBody
	public ResUserOrder detail(@RequestParam("id") Long id){
		log.info("detail order :{}",id);
		return this.ordersService.detail(id);
	}
	@RequestMapping(value = "/v2.0/down", method = RequestMethod.PUT) 
	public void down(@RequestBody ReqOrderDown rod){
		log.info("down stall :{}",JsonUtil.toJson(rod));
		this.ordersService.down(rod);
	} 
}
