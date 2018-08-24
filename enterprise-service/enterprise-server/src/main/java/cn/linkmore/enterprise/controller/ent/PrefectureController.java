package cn.linkmore.enterprise.controller.ent;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.enterprise.controller.ent.request.ReqPreType;
import cn.linkmore.enterprise.controller.ent.request.ReqPreTypePage;
import cn.linkmore.enterprise.controller.ent.response.ResChargeDetail;
import cn.linkmore.enterprise.controller.ent.response.ResDayIncome;
import cn.linkmore.enterprise.controller.ent.response.ResDayTrafficFlow;
import cn.linkmore.enterprise.controller.ent.response.ResIncomeList;
import cn.linkmore.enterprise.controller.ent.response.ResPreOrderCount;
import cn.linkmore.enterprise.service.PrefectureService;
import cn.linkmore.prefecture.response.ResStallBatteryLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/ent/prefecture")
@Api(tags = "prefecture",description="车区运营【物业版】", produces = "application/json")
@Validated
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
	@ApiOperation(value = "查询车场当日收入", notes = "查询车场近七日实收入", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<BigDecimal> findPreDayIncome(
			@RequestParam("preId") @ApiParam("车区id") @NotNull(message="车区不能为空")Long preId,
			HttpServletRequest request){
		BigDecimal income = this.prefectureService.findPreDayIncome(preId,request);
		return ResponseEntity.success(income,request);
	}
	
	@RequestMapping(value="/proceeds-amount" ,method=RequestMethod.POST)
	@ApiOperation(value = "根据条件查询车场实收入金额[7-15-30]天", notes = "根据条件查询车场实收入[7-15-30]天", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<BigDecimal> findProceedsAmount(@Validated @RequestBody ReqPreType preType ,HttpServletRequest request){
		BigDecimal income = this.prefectureService.findProceedsAmount(preType.getType().shortValue(),preType.getPreId(),request);
		return ResponseEntity.success(income,request);
	}
	
	@RequestMapping(value="/proceeds" ,method=RequestMethod.POST)
	@ApiOperation(value = "根据条件查询车场实收入明细列表[7-15-30]天", notes = "根据条件查询车场实收入[7-15-30]天", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<ResIncomeList> findProceeds(@Validated @RequestBody ReqPreType preType ,HttpServletRequest request){
		ResIncomeList list = this.prefectureService.findProceeds(preType.getType().shortValue(),preType.getPreId(),request);
		return ResponseEntity.success(list,request);
	}
	
	@RequestMapping(value="/traffic-flow" ,method=RequestMethod.POST)
	@ApiOperation(value = "查询车场车流量统计[7-15-30]天明细列表", notes = "查询车场车流量统计[7-15-30]天", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<cn.linkmore.enterprise.controller.ent.response.ResTrafficFlow> findTrafficFlow(@Validated @RequestBody ReqPreType preType ,HttpServletRequest request){
		cn.linkmore.enterprise.controller.ent.response.ResTrafficFlow flow = this.prefectureService.findTrafficFlow(preType.getType().shortValue(),preType.getPreId(),request);
		return ResponseEntity.success(flow,request);
	}
	@RequestMapping(value="/traffic-flow-count" ,method=RequestMethod.POST)
	@ApiOperation(value = "查询车场车流量统计[7-15-30]天总流量", notes = "查询车场车流量统计[7-15-30]天", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<Integer> findTrafficFlowCount(@Validated @RequestBody ReqPreType preType ,HttpServletRequest request){
		Integer count = this.prefectureService.findTrafficFlowCount(preType.getType().shortValue(),preType.getPreId(),request);
		return ResponseEntity.success(count,request);
	}
	
	@RequestMapping(value="/charge-detail" ,method=RequestMethod.GET)
	@ApiOperation(value = "查询车场实时收费明细", notes = "查询车场实时收费明细", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<List<ResChargeDetail>> findChargeDetail( @RequestParam("preId") @NotNull(message="车区id不能为空") @ApiParam(value="车区id",required=true,name="preId") Long preId,
									@RequestParam("pageNo") Integer pageNo,
							HttpServletRequest request){
		List<ResChargeDetail> list = this.prefectureService.findChargeDetail(pageNo,preId,request);
		return ResponseEntity.success(list, request);
	}
	
	/*@RequestMapping(value="/charge-detail-new" ,method=RequestMethod.GET)
	@ApiOperation(value = "查询车场实时收费明细-新", notes = "查询车场实时收费明细-新", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<List<cn.linkmore.enterprise.controller.ent.response.ResCharge>> findChargeDetailNew(@ApiParam(value="类型 0 7天 1 15天 2 30天",required=true,name="type") @NotNull(message="类型不能为空") @RequestParam("type") Short type , @ApiParam(value="车区id",required=true,name="preId") @NotNull(message="车区不能为空") @RequestParam("preId") Long preId,HttpServletRequest request){
		List<ResCharge> list = this.prefectureService.findChargeDetailNew(type,preId,request);
		return ResponseEntity.success(list, request);
	}*/
	
	@RequestMapping(value="/traffic-flow-list" ,method=RequestMethod.POST)
	@ApiOperation(value = "查询车场每日车流量列表", notes = "查询车场每日车流量列表", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<ResDayTrafficFlow> findTrafficFlowList(@Validated @RequestBody ReqPreTypePage page,
																	   HttpServletRequest request){
		ResDayTrafficFlow list = this.prefectureService.findTrafficFlowList(page.getPageNo(),page.getType().shortValue(),page.getPreId(),null,request);
		return ResponseEntity.success(list, request);
	}
	
	@RequestMapping(value="/income-list" ,method=RequestMethod.POST)
	@ApiOperation(value = "查询车场每日收入列表", notes = "查询车场每日收入列表", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<ResDayIncome> findIncomeList(@RequestBody @Validated ReqPreTypePage page,
															HttpServletRequest request){
		ResDayIncome list = this.prefectureService.findIncomeList(page.getPageNo(),page.getType().shortValue(),page.getPreId(),null,request);
		return ResponseEntity.success(list, request);
	}
	
	@RequestMapping(value="/battery-log" ,method=RequestMethod.POST)
	@ApiOperation(value = "查询电池记录", notes = "查询电池记录", consumes = "application/json")
	@ResponseBody
	public ResponseEntity< List<ResStallBatteryLog>> battery(@RequestParam("stallId") @ApiParam("车位id") @NotNull(message="车位不能为null") Long stallId,HttpServletRequest request){
		List<ResStallBatteryLog> res = this.prefectureService.StallBatteryLog(stallId);
		return ResponseEntity.success(res, request);
	}
	
}
