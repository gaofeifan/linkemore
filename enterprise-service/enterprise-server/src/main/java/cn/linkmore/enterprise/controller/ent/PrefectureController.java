package cn.linkmore.enterprise.controller.ent;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.enterprise.service.PrefectureService;
import cn.linkmore.order.response.ResChargeList;
import cn.linkmore.order.response.ResIncome;
import cn.linkmore.order.response.ResPreOrderCount;
import cn.linkmore.order.response.ResTrafficFlow;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/prefecture")
@Api("车区运营")
public class PrefectureController {

	@Resource
	private PrefectureService prefectureService;
	
	@RequestMapping
	@ApiOperation(value = "查询每日车场订单收入", notes = "查询每日车场订单收入", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<List<ResPreOrderCount>> findPreList(HttpServletRequest request){
		return ResponseEntity.success(this.prefectureService.findPreList(request), request);
	}
	
	@RequestMapping
	@ApiOperation(value = "查询车场近七日实收入", notes = "查询车场近七日实收入", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<BigDecimal> findPreDayIncome(@RequestParam("preId") Long preId,HttpServletRequest request){
		BigDecimal income = this.prefectureService.findPreDayIncome(preId,request);
		return ResponseEntity.success(income,request);
	}
	
	@RequestMapping
	@ApiOperation(value = "根据条件查询车场实收入", notes = "根据条件查询车场实收入", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<BigDecimal> findProceeds(@ApiParam(value="type",required=true,name="类型 0 7天 1 15天 2 30天") @RequestParam("type") Short type , @RequestParam("preId") Long preId,HttpServletRequest request){
		BigDecimal income = this.prefectureService.findProceeds(type,preId,request);
		return ResponseEntity.success(income,request);
	}
	
	@RequestMapping
	@ApiOperation(value = "查询车场车流量统计", notes = "查询车场车流量统计", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<Integer> findTrafficFlow(@ApiParam(value="type",required=true,name="类型 0 7天 1 15天 2 30天") @RequestParam("type") Short type , @RequestParam("preId") Long preId,HttpServletRequest request){
		Integer income = this.prefectureService.findTrafficFlow(type,preId,request);
		return ResponseEntity.success(income,request);
	}
	
	@RequestMapping
	@ApiOperation(value = "查询车场实时收费明细", notes = "查询车场实时收费明细", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<List<ResChargeList>> findChargeDetail(@ApiParam(value="type",required=true,name="类型 0 7天 1 15天 2 30天") @RequestParam("type") Short type , @RequestParam("preId") Long preId,HttpServletRequest request){
		List<ResChargeList> list = this.prefectureService.findChargeDetail(type,preId,request);
		return ResponseEntity.success(list, request);
	}
	
	@RequestMapping
	@ApiOperation(value = "查询车场每日车流量列表", notes = "查询车场每日车流量列表", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<List<ResTrafficFlow>> findTrafficFlowList(@ApiParam(value="type",required=true,name="类型 0 7天 1 15天 2 30天") @RequestParam("type") Short type , @RequestParam("preId") Long preId,HttpServletRequest request){
		List<ResTrafficFlow> list = this.prefectureService.findTrafficFlowList(type,preId,request);
		return ResponseEntity.success(list, request);
	}

	@RequestMapping
	@ApiOperation(value = "查询车场每日收入列表", notes = "查询车场每日收入列表", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<List<ResIncome>> findIncomeList(@ApiParam(value="type",required=true,name="类型 0 7天 1 15天 2 30天") @RequestParam("type") Short type , @RequestParam("preId") Long preId,HttpServletRequest request){
		List<ResIncome> list = this.prefectureService.findIncomeList(type,preId,request);
		return ResponseEntity.success(list, request);
	}
	
}
