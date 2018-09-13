package cn.linkmore.order.controller.feign;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.order.response.ResUnusualOrder;
import cn.linkmore.order.service.UnusualOrderService;

@RestController
@RequestMapping("/feign/unusual/order")
public class FeignUnusualOrderController {

	@Resource
	private UnusualOrderService unusualOrderService;
	
	@RequestMapping(value="/list",method = RequestMethod.POST)
	@ResponseBody
	public List<ResUnusualOrder> findList(@RequestBody Map<String,Object> map ) {
		return this.unusualOrderService.findList(map);
	}
}
