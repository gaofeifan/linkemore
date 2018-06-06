package cn.linkmore.order.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.order.client.hystrix.OrderClientHystrix;
import cn.linkmore.order.request.ReqOrderCreate;
import cn.linkmore.order.request.ReqOrderDown;
import cn.linkmore.order.request.ReqOrderSwitch;
import cn.linkmore.order.response.ResUserOrder;

@FeignClient(value = "order-server", path = "/orders", fallback=OrderClientHystrix.class,configuration = FeignConfiguration.class)
public interface OrderClient {
	
	/**
	 * 预约车位
	 * @param roc
	 */
	@RequestMapping(value = "/v2.0/create", method = RequestMethod.POST) 
	void create(@RequestBody ReqOrderCreate roc);
	
	/**
	 * 要的车位
	 * @param ros
	 */
	@RequestMapping(value = "/v2.0/switch", method = RequestMethod.PUT) 
	void switchStall(@RequestBody ReqOrderSwitch ros);
	
	/**
	 * 最近订单
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/v2.0/last", method = RequestMethod.GET)
	@ResponseBody
	ResUserOrder last(@RequestParam("userId") Long userId);
	
	/**
	 * 订单详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/v2.0/detail", method = RequestMethod.GET)
	@ResponseBody
	ResUserOrder detail(@RequestParam("id") Long id);
	
	/**
	 * 降下地锁
	 * @param rod
	 */
	@RequestMapping(value = "/v2.0/down", method = RequestMethod.PUT) 
	void down(@RequestBody ReqOrderDown rod);
	
	/**
	 * 根据userId查询对应已完成订单[10条一页]
	 * @param userId 用户ID
	 * @param start 起始为0
	 * @return
	 */
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.GET)
	@ResponseBody
	public List<ResUserOrder> list(@RequestParam("userId") Long userId,@RequestParam("start") Long start);
}
