package cn.linkmore.order.controller;

import javax.servlet.http.HttpServletRequest;

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

@RestController
@RequestMapping("/pay")
public class PayController {
	@Autowired
	private PayService payService;
	
	 
	@RequestMapping(value = "/v2.0/checkout", method = RequestMethod.GET)
	@ResponseBody
	public  ResOrderCheckout checkout(@RequestParam("orderId") Long orderId,@RequestParam("userId") Long userId, HttpServletRequest request) {
		return this.payService.checkout(orderId,userId); 
	}
	 
	@RequestMapping(value = "/v2.0/confirm", method = RequestMethod.POST)
	@ResponseBody
	public ResOrderConfirm confirm(@RequestBody ReqOrderConfirm roc, HttpServletRequest request) {
		return this.payService.confirm(roc);
	}
	
	 
	@RequestMapping(value = "/v2.0/verify", method = RequestMethod.GET)
	@ResponseBody
	public Boolean verify(@RequestParam("orderId") Long orderId,@RequestParam("userId") Long userId, HttpServletRequest request) {
		return this.payService.verify(orderId,userId);
	}
}
