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
import cn.linkmore.order.response.ResChargeList;
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
	
	@RequestMapping(value = "/day-income", method = RequestMethod.POST)
	@ResponseBody
	public BigDecimal findPreDayIncome(@RequestBody List<Long> authStall) {
		return this.ordersService.findPreDayIncome(authStall);
	}
	
	@RequestMapping(value = "/traffic-flow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findTrafficFlow(@RequestBody Map<String,Object> map){
		return this.ordersService.findTrafficFlow(map);
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
	 * @Description  查询收费明细
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/charge-detail", method = RequestMethod.POST)
	@ResponseBody
	public ResChargeList findChargeDetail(@RequestBody Map<String, Object> param){
		return this.ordersService.findChargeDetail(param);
	}
	/**
	 * @Description  查询收费明细
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/charge-detail-new", method = RequestMethod.POST)
	@ResponseBody
	public List<ResCharge> findChargeDetailNew(@RequestBody Map<String, Object> param){
		return this.ordersService.findChargeDetailNew(param);
	}

	/**
	 * @Description  查询车流量列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/traffic-flow-list", method = RequestMethod.POST)
	@ResponseBody
	List<ResTrafficFlow> findTrafficFlowList(@RequestBody Map<String, Object> param){
		return this.ordersService.findTrafficFlowList(param);
	}

	/**
	 * @Description  查询收费列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/income-list", method = RequestMethod.POST)
	@ResponseBody
	List<ResIncome> findIncomeList(@RequestBody Map<String, Object> param){
		return this.ordersService.findIncomeList(param);
	}
	
	
	@RequestMapping(value = "/by-stall", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPreOrderCount> findPreCountByIds(@RequestBody List<Long> ids){
		return this.ordersService.findPreCountByIds(ids);
	}
	
}
