package cn.linkmore.order.controller.feign;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.order.request.ReqOrderExcel;
import cn.linkmore.order.response.ResOrderExcel;
import cn.linkmore.order.response.ResOrderOperateLog;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.order.service.OrdersService;

@RestController
@RequestMapping("/feign/orders")
@Validated
public class FeignOrderController {
	
	@Autowired
	private OrdersService ordersService;
	
	@RequestMapping(value = "/v2.0/update-lock-status", method = RequestMethod.POST)
	@ResponseBody
	public void updateLockStatus(@RequestBody Map<String, Object> param) {
		this.ordersService.updateLockStatus(param);
	}
	
	@RequestMapping(value = "/v2.0/last", method = RequestMethod.GET)
	@ResponseBody
	ResUserOrder last(@RequestParam("userId") Long userId) {
		return this.ordersService.latest(userId);
	} 

	@RequestMapping(value = "/v2.0/down-msg-push", method = RequestMethod.POST)
	@ResponseBody
	void downMsgPush(@RequestParam("orderId")Long orderId, @RequestParam("stallId")Long stallId) {
		this.ordersService.downMsgPush(orderId,stallId);
	} 
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){
		return this.ordersService.findPage(pageable);
	} 
	
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	@ResponseBody
	public List<ResOrderExcel> exportList(@RequestBody ReqOrderExcel bean){
		return this.ordersService.exportList(bean);
	}
	
	/**
	 * 当前车牌号最近订单状态 是否存在未结账订单
	 * @param carno
	 * @return
	 */
	@RequestMapping(value = "/v2.0/last-order", method = RequestMethod.GET)
	@ResponseBody
	Integer getPlateLastOrderStatus(@RequestParam("carno") String carno) {
		return this.ordersService.getPlateLastOrderStatus(carno);
	}
	
	@RequestMapping(value = "/stall-latest", method = RequestMethod.GET)
	@ResponseBody
	public ResUserOrder findStallLatest(@RequestParam("stallId") Long stallId) {
		return this.ordersService.findStallLatest(stallId);
	}
	
	@RequestMapping(value = "/findOrderById", method = RequestMethod.GET)
	@ResponseBody
	public ResUserOrder findOrderById(@RequestParam("id") Long id) {
		return this.ordersService.getOrderById(id);
	}
	
	@RequestMapping(value = "/updateClose", method = RequestMethod.POST)
	@ResponseBody
	public void updateClose(@RequestBody Map<String, Object> param ) {
		 this.ordersService.updateClose(param);
	}
	
	@RequestMapping(value = "/update-order-detail", method = RequestMethod.POST)
	@ResponseBody
	public void updateDetail(@RequestBody Map<String, Object> param ) {
		this.ordersService.updateDetail(param);
	}
	
	@RequestMapping(value = "/save-order-log", method = RequestMethod.POST)
	@ResponseBody
	public void savel(@RequestBody  ResOrderOperateLog resOrderOperateLog) {
		this.ordersService.savelog(resOrderOperateLog);
	}
	
}
