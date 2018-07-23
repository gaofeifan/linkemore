package cn.linkmore.order.client;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.order.client.hystrix.OrderClientHystrix;
import cn.linkmore.order.request.ReqOrderExcel;
import cn.linkmore.order.response.ResOrderExcel;
import cn.linkmore.order.response.ResOrderPlate;
import cn.linkmore.order.response.ResPreOrderCount;
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
	
	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findPage(@RequestBody ViewPageable pageable);

	@RequestMapping(value = "/export", method = RequestMethod.POST)
	@ResponseBody
	List<ResOrderExcel> exportList(@RequestBody ReqOrderExcel bean); 
	
	@RequestMapping(value = "/by-stall", method = RequestMethod.POST)
	@ResponseBody
	List<ResPreOrderCount> findPreCountByIds(@RequestBody List<Long> ids);
	
	/**
	 * @Description  根据车区查询车牌号
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/plate-by-preid", method = RequestMethod.POST)
	@ResponseBody
	List<ResOrderPlate> findPlateByPreId(@RequestParam("preId")Long preId);
}
