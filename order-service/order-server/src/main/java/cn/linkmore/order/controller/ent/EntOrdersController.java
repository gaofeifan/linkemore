package cn.linkmore.order.controller.ent;

import java.math.BigDecimal;
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

import cn.linkmore.order.response.ResCharge;
import cn.linkmore.order.response.ResChargeDetail;
import cn.linkmore.order.response.ResChargeList;
import cn.linkmore.order.response.ResEntOrder;
import cn.linkmore.order.response.ResIncome;
import cn.linkmore.order.response.ResOrderPlate;
import cn.linkmore.order.response.ResPreOrderCount;
import cn.linkmore.order.response.ResTrafficFlow;
import cn.linkmore.order.service.OrdersService;

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
	ResTrafficFlow findTrafficFlowList(@RequestBody Map<String, Object> param){
		return this.ordersService.findTrafficFlowList(param);
	}

	/**
	 * @Description  查询收费列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/income-list", method = RequestMethod.POST)
	@ResponseBody
	ResIncome findIncomeList(@RequestBody Map<String, Object> param){
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
	
	
	
}
