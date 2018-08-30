package cn.linkmore.order.controller.ent;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.order.controller.app.request.ReqOrderStall;
import cn.linkmore.order.response.ResCharge;
import cn.linkmore.order.response.ResChargeDetail;
import cn.linkmore.order.response.ResChargeList;
import cn.linkmore.order.response.ResEntOrder;
import cn.linkmore.order.response.ResIncome;
import cn.linkmore.order.response.ResOrder;
import cn.linkmore.order.response.ResOrderPlate;
import cn.linkmore.order.response.ResPreOrderCount;
import cn.linkmore.order.response.ResTrafficFlow;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.order.service.OrdersService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/ent/orders")
@Validated
public class EntOrdersController {

	@Autowired
	private OrdersService ordersService;
	
	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/plate-by-preid", method = RequestMethod.POST)
	@ResponseBody
	public List<ResOrderPlate> findPlateByPreId(@RequestParam("preId")Long preId){
		return this.ordersService.findPlateByPreId(preId);
	}
	
	@RequestMapping(value = "/day-income", method = RequestMethod.GET)
	@ResponseBody
	public BigDecimal findPreDayIncome(@RequestParam("preId") Long preId) {
		return this.ordersService.findPreDayIncome(preId);
	}
	
	@RequestMapping(value = "/traffic-flow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findTrafficFlow(@RequestBody Map<String,Object> map){
		return this.ordersService.findTrafficFlow(map);
	}
	
	@RequestMapping(value = "/traffic-flow-count", method = RequestMethod.POST)
	@ResponseBody
	public Integer findTrafficFlowCount(@RequestBody Map<String, Object> param) {
		return this.ordersService.findTrafficFlowCount(param);
	}

	/**
	 * @Description  根据条件查询实收入
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/proceeds", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findProceeds(@RequestBody Map<String,Object> map) {
		return this.ordersService.findProceeds(map);
	}
	
	/**
	 * @Description  根据类型查询总金额
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/proceeds-amount", method = RequestMethod.POST)
	@ResponseBody
	public BigDecimal findProceedsAmount(@RequestBody Map<String, Object> param) {
		return this.ordersService.findProceedsAmount(param);
	}
	
	/**
	 * @Description  查询收费明细
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/charge-detail", method = RequestMethod.POST)
	@ResponseBody
	public List<ResChargeDetail> findChargeDetail(@RequestBody Map<String, Object> param){
		return this.ordersService.findChargeDetail(param);
	}
	/**
	 * @Description  查询收费明细
	 * @Author   GFF 
	 * @Version  v2.0
	 
	@RequestMapping(value = "/charge-detail-new", method = RequestMethod.POST)
	@ResponseBody
	public List<ResCharge> findChargeDetailNew(@RequestBody Map<String, Object> param){
		return this.ordersService.findChargeDetailNew(param);
	}*/

	/**
	 * @Description  查询车流量列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/traffic-flow-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResTrafficFlow> findTrafficFlowList(@RequestBody Map<String, Object> param){
		return this.ordersService.findTrafficFlowList(param);
	}

	/**
	 * @Description  查询收费列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/income-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResIncome> findIncomeList(@RequestBody Map<String, Object> param){
		return this.ordersService.findIncomeList(param);
	}
	
	
	@RequestMapping(value = "/by-stall", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPreOrderCount> findPreCountByIds(@RequestBody List<Long> preId){
		return this.ordersService.findPreCountByIds(preId);
	}

	@RequestMapping(value = "/by-stall-id", method = RequestMethod.GET)
	@ResponseBody
	public ResEntOrder findOrderByStallId(@RequestParam("stallId") Long stallId){
		return this.ordersService.findOrderByStallId(stallId);
	}
	
	@RequestMapping(value = "/stall-latest", method = RequestMethod.GET)
	@ResponseBody
	public ResUserOrder findStallLatest(@RequestParam("stallId") Long stallId) {
		return this.ordersService.findStallLatest(stallId);
	}

	@RequestMapping(value = "/v2.0/down-wy-msg-push", method = RequestMethod.POST)
	@ResponseBody
	public void downWYMsgPush(@RequestParam("orderId")Long orderId, @RequestParam("stallId")Long stallId) {
		this.ordersService.downWYMsgPush(orderId,stallId);
	}
	
	@ApiOperation(value = "降锁回调", notes = "降锁回调校验结果", consumes = "application/json")
	@RequestMapping(value = "/v2.0/down/result", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> downResult(@RequestParam("userId") Long userId) { 
//		Integer count = this.ordersService.downResult(userId);
		ResponseEntity<?> response = null;
		/*if(count==0) {
			response =  ResponseEntity.success(count, request);
		}else if(count==1){
			response =  ResponseEntity.fail(StatusEnum.ORDER_LOCKDOWN_FAIL, request);
		}else if(count>1) {
			response = ResponseEntity.fail(StatusEnum.ORDER_FAIL_SWITCHLOCK, request);
		}*/
		return response;
	}
	
	
}
