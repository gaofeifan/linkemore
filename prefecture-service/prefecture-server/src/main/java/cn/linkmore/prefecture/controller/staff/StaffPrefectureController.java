package cn.linkmore.prefecture.controller.staff;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.order.response.ResIncome;
import cn.linkmore.order.response.ResTrafficFlow;
import cn.linkmore.prefecture.controller.staff.request.ReqPreType;
import cn.linkmore.prefecture.controller.staff.request.ReqPreTypePage;
import cn.linkmore.prefecture.controller.staff.response.ResAmountReport;
import cn.linkmore.prefecture.controller.staff.response.ResCarReport;
import cn.linkmore.prefecture.controller.staff.response.ResStaffPreListCount;
import cn.linkmore.prefecture.service.StaffPrefectureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author   GFF
 * @Date     2018年9月18日
 * @Version  v2.0
 */
@Api(tags="Staff-prefecture",description="【管理版】  运营")
@RestController
@RequestMapping("/staff/prefecture")
public class StaffPrefectureController {

	@Resource
	private StaffPrefectureService staffPrefectureService; 
	
	@RequestMapping(value="/pre-list",method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "车区列表", notes = "根城市查询车区列表", consumes = "application/json")
	public ResponseEntity<List<ResStaffPreListCount>> findPreList(HttpServletRequest request, @ApiParam("城市id") @NotNull(message="城市id不能为空") @RequestParam("cityId") Long cityId) {
		List<ResStaffPreListCount> list = this.staffPrefectureService.findPreList(request,cityId);
		return ResponseEntity.success(list, request);
	}
	
	@RequestMapping(value="/day-income",method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "查询今日收入", notes = "查询今日收入", consumes = "application/json")
	public ResponseEntity<BigDecimal> findDayIncome(HttpServletRequest request, @ApiParam("车区id") @NotNull(message="车区id不能为空") @RequestParam("preId") Long preId) {
		BigDecimal deci = this.staffPrefectureService.findDayIncome(request,preId);
		return ResponseEntity.success(deci, request);
	}
	
	@RequestMapping(value="/amount-report-count",method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "根据类型查询收入统计【报表】", notes = "根据类型查询收入总数【报表】", consumes = "application/json")
	public ResponseEntity<BigDecimal> findAmountReportCount(HttpServletRequest request,@RequestBody ReqPreType type) {
		BigDecimal deci = this.staffPrefectureService.findAmountReportCount(request,type);
		return ResponseEntity.success(deci, request);
	}
	@RequestMapping(value="/amount-report-list",method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "根据类型查询收入列表【报表】", notes = "根据类型查询收入列表【报表】", consumes = "application/json")
	public ResponseEntity<ResAmountReport> findAmountReportList(HttpServletRequest request,@RequestBody ReqPreType type) {
		ResAmountReport list = this.staffPrefectureService.findAmountReportList(request,type);
		return ResponseEntity.success(list, request);
	}
	
	@RequestMapping(value="/car-report-count",method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "根据类型查询车流量统计【报表】", notes = "根据类型查询车流量统计【报表】", consumes = "application/json")
	public ResponseEntity<Integer> findCarReportCount(HttpServletRequest request, @RequestBody ReqPreType type) {
		Integer deci = this.staffPrefectureService.findCarReportCount(request,type);
		return ResponseEntity.success(deci, request);
	}
	@RequestMapping(value="/car-report-list",method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "根据类型查询车流量列表【报表】", notes = "根据类型查询车流量统计【报表】", consumes = "application/json")
	public ResponseEntity<ResCarReport> findCarReportList(HttpServletRequest request, @RequestBody ReqPreType type) {
		ResCarReport list = this.staffPrefectureService.findCarReportList(request,type);
		return ResponseEntity.success(list, request);
	}
	
	@RequestMapping(value = "/car-month-list", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "查询月数据车流量列表", notes = "查询月数据车流量列表", consumes = "application/json")
	public List<ResTrafficFlow> findCarMonthList(HttpServletRequest request,@RequestBody ReqPreTypePage page){
		return this.staffPrefectureService.findCarMonthList(request,page);
	}

	@RequestMapping(value = "/amount-month-list", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "查询月收入列表", notes = "查询月收入列表", consumes = "application/json")
	public List<ResIncome> findAmountMonthList(HttpServletRequest request,@RequestBody ReqPreTypePage page){
		return this.staffPrefectureService.findAmountMonthList(request,page);
	}
	
}
