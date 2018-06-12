package cn.linkmore.order.controller.feign;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.order.service.OrdersService;

@RestController
@RequestMapping("/feign/orders")
@Validated
public class FeignOrderController {
	
	private OrdersService ordersService;
	
	@RequestMapping(value = "/v2.0/last", method = RequestMethod.GET)
	@ResponseBody
	ResUserOrder last(@RequestParam("userId") Long userId) {
		return this.ordersService.latest(userId);
	} 
}
