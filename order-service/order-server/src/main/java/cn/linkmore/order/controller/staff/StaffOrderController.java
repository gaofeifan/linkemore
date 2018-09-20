package cn.linkmore.order.controller.staff;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.order.response.ResChargeDetail;
import cn.linkmore.order.response.ResIncome;
import cn.linkmore.order.response.ResPreOrderCount;
import cn.linkmore.order.response.ResTrafficFlow;
import cn.linkmore.order.service.StaffOrderService;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author   GFF
 * @Date     2018年9月18日
 * @Version  v2.0
 */
@RestController
@RequestMapping("/staff/orders")
@ApiIgnore
public class StaffOrderController {

	@Resource
	private StaffOrderService staffOrderService;
	
	/**
	 * @Description  查询管理版车区统计
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/pre-count", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPreOrderCount> findPreListCount(@RequestBody Map<String, Object> param){
		return this.staffOrderService.findPreListCount(param);
	}
	
	/**
	 * @Description  查询管理版今日收入
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/day-income", method = RequestMethod.POST)
	@ResponseBody
	public BigDecimal findDayIncome(@RequestBody Map<String, Object> map) {
		return this.staffOrderService.findDayIncome(map);
	}
	
	@RequestMapping(value = "/amount-report-count", method = RequestMethod.POST)
	@ResponseBody
	public BigDecimal findAmountReportCount(@RequestBody Map<String, Object> map) {
		return this.staffOrderService.findAmountReportCount(map);
	}

	@RequestMapping(value = "/amount-report-list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findAmountReportList(@RequestBody Map<String, Object> map){
		return this.staffOrderService.findAmountReportList(map);
	}

	@RequestMapping(value = "/car-report-count", method = RequestMethod.POST)
	@ResponseBody
	public Integer findCarReportCount(@RequestBody Map<String, Object> map) {
		return this.staffOrderService.findCarReportCount(map);
	}

	@RequestMapping(value = "/car-report-list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findCarReportList(@RequestBody Map<String, Object> map){
		return this.staffOrderService.findCarReportList(map);
	}
	
	@RequestMapping(value = "/car-month-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResTrafficFlow> findCarMonthList(@RequestBody Map<String, Object> map){
		return this.staffOrderService.findCarMonthList(map);
	}

	@RequestMapping(value = "/amount-month-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResIncome> findAmountMonthList(@RequestBody Map<String, Object> map){
		return this.staffOrderService.findAmountMonthList(map);
	}
	
	@RequestMapping(value = "/amount-detail-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResChargeDetail> findAmountDetail(@RequestBody Map<String, Object> param){
		return this.staffOrderService.findAmountDetail(param);
	}
}
