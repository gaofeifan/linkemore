package cn.linkmore.order.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.order.client.hystrix.FeignUnusualOrderClientHystrix;
import cn.linkmore.order.response.ResUnusualOrder;

@FeignClient(value = "order-server", path = "/feign/unusual/order", fallback=FeignUnusualOrderClientHystrix.class,configuration = FeignConfiguration.class)
public interface FeignUnusualOrderClient { 
	
	@RequestMapping(value="/list",method = RequestMethod.POST)
	@ResponseBody
	public List<ResUnusualOrder> findList(@RequestBody Map<String,Object> map );
	
}
