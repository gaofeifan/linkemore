package cn.linkmore.order.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.order.client.hystrix.OrderClientHystrix;
import cn.linkmore.order.request.ReqOrderCreate;
import cn.linkmore.order.response.ResUserOrder;

@FeignClient(value = "order-server", path = "/orders", fallback=OrderClientHystrix.class,configuration = FeignConfiguration.class)
public interface OrderClient {
	
	@RequestMapping(value = "/v2.0", method = RequestMethod.POST)
	@ResponseBody
	public void create(@RequestBody ReqOrderCreate roc);
	
	@RequestMapping(value = "/v2.0/latest/{userId}/", method = RequestMethod.GET)
	@ResponseBody
	public ResUserOrder userLatest(@PathVariable("userId") Long userId);
	
	@RequestMapping(value = "/v2.0/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResUserOrder detail(@PathVariable("id") Long id);
}
