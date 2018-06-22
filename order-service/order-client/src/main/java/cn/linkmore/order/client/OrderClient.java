package cn.linkmore.order.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.order.client.hystrix.OrderClientHystrix;
import cn.linkmore.order.response.ResUserOrder;

@FeignClient(value = "order-server", path = "/feign/orders", fallback=OrderClientHystrix.class,configuration = FeignConfiguration.class)
public interface OrderClient { 
	
	/**
	 * 最近订单
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/v2.0/last", method = RequestMethod.GET)
	@ResponseBody
	ResUserOrder last(@RequestParam("userId") Long userId); 
	
	/**
	 * @Description  消息推送
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/down-msg-push", method = RequestMethod.POST)
	@ResponseBody
	void downMsgPush(@RequestParam("orderId")Long orderId, @RequestParam("stallId")Long stallId);
}
