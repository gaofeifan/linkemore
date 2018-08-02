package cn.linkmore.enterprise.controller.ent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.enterprise.controller.ent.response.ResCharge;
import cn.linkmore.enterprise.controller.ent.response.ResChargeDetail;
import cn.linkmore.enterprise.controller.ent.response.ResDayIncome;
import cn.linkmore.enterprise.controller.ent.response.ResDayTrafficFlow;
import cn.linkmore.enterprise.controller.ent.response.ResIncomeList;
import cn.linkmore.enterprise.controller.ent.response.ResPreOrderCount;
import cn.linkmore.enterprise.service.PrefectureService;
import cn.linkmore.order.response.ResChargeList;
import cn.linkmore.order.response.ResIncome;
import cn.linkmore.order.response.ResTrafficFlow;
import cn.linkmore.util.ObjectUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/ent/prefecture")
@Api(tags = "prefecture",description="车区运营", produces = "application/json")
public class PrefectureController {

	@Resource
	private PrefectureService prefectureService;
	
	@RequestMapping(value="/pre-order-count" ,method=RequestMethod.GET)
	@ApiOperation(value = "查询每日车场订单收入", notes = "查询每日车场订单收入", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<List<ResPreOrderCount>> findPreList(HttpServletRequest request){
		return ResponseEntity.success(this.prefectureService.findPreList(request), request);
	}
	
	@RequestMapping(value="/pre-income" ,method=RequestMethod.GET)
	@ApiOperation(value = "查询车场近七日实收入", notes = "查询车场近七日实收入", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<BigDecimal> findPreDayIncome(@RequestParam("preId") Long preId,HttpServletRequest request){
		BigDecimal income = this.prefectureService.findPreDayIncome(preId,request);
		return ResponseEntity.success(income,request);
	}
	
	@RequestMapping(value="/proceeds" ,method=RequestMethod.GET)
	@ApiOperation(value = "根据条件查询车场实收入[7-15-30]天", notes = "根据条件查询车场实收入[7-15-30]天", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<ResIncomeList> findProceeds(@ApiParam(value="类型 0 7天 1 15天 2 30天",required=true,name="type") @RequestParam("type") Short type , @RequestParam("preId") Long preId,HttpServletRequest request){
		ResIncomeList list = this.prefectureService.findProceeds(type,preId,request);
		return ResponseEntity.success(list,request);
	}
	
	@RequestMapping(value="/traffic-flow" ,method=RequestMethod.GET)
	@ApiOperation(value = "查询车场车流量统计[7-15-30]天", notes = "查询车场车流量统计[7-15-30]天", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<cn.linkmore.enterprise.controller.ent.response.ResTrafficFlow> findTrafficFlow(@ApiParam(value="类型 0 7天 1 15天 2 30天",required=true,name="type") @RequestParam("type") Short type , @RequestParam("preId") Long preId,HttpServletRequest request){
		cn.linkmore.enterprise.controller.ent.response.ResTrafficFlow flow = this.prefectureService.findTrafficFlow(type,preId,request);
		return ResponseEntity.success(flow,request);
	}
	
	@RequestMapping(value="/charge-detail" ,method=RequestMethod.GET)
	@ApiOperation(value = "查询车场实时收费明细", notes = "查询车场实时收费明细", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<List<cn.linkmore.enterprise.controller.ent.response.ResChargeList>> findChargeDetail(@ApiParam(value="类型 0 7天 1 15天 2 30天",required=true,name="type") @RequestParam("type") Short type , @RequestParam("preId") Long preId,HttpServletRequest request){
		List<cn.linkmore.enterprise.controller.ent.response.ResChargeList> list = this.prefectureService.findChargeDetail(type,preId,request);
		return ResponseEntity.success(list, request);
	}
	
	@RequestMapping(value="/traffic-flow-list" ,method=RequestMethod.GET)
	@ApiOperation(value = "查询车场每日车流量列表", notes = "查询车场每日车流量列表", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<List<ResDayTrafficFlow>> findTrafficFlowList(@ApiParam(value="类型 0 7天 1 15天 2 30天",required=true,name="type") @RequestParam("type") Short type , @RequestParam("preId") Long preId,HttpServletRequest request){
		List<ResDayTrafficFlow> list = this.prefectureService.findTrafficFlowList(type,preId,request);
		return ResponseEntity.success(list, request);
	}

	@RequestMapping(value="/income-list" ,method=RequestMethod.GET)
	@ApiOperation(value = "查询车场每日收入列表", notes = "查询车场每日收入列表", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<List<ResDayIncome>> findIncomeList(@ApiParam(value="类型 0 7天 1 15天 2 30天",required=true,name="type") @RequestParam("type") Short type , @RequestParam("preId") Long preId,HttpServletRequest request){
		List<ResDayIncome> list = this.prefectureService.findIncomeList(type,preId,request);
		return ResponseEntity.success(list, request);
	}
	
}
