package cn.linkmore.order.controller.staff;

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

import cn.linkmore.order.controller.staff.request.ReqPreDetails;
import cn.linkmore.order.controller.staff.response.ResPreDetails;
import cn.linkmore.order.controller.staff.response.ResPreListAndDetails;
import cn.linkmore.order.controller.staff.response.ResPreReportForms;
import cn.linkmore.order.service.DataStatiscsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/staff/data-statistics")
@Api(tags = "data statistics", description = "数据统计【管理版】")
@Validated
public class StaffDataStatisticsController {

	@Resource
	private DataStatiscsService dataStatiscsService;
	
	@RequestMapping(value = "/find-pre-list", method = RequestMethod.GET)
	@ApiOperation(value = "查询车场数据列表", notes = "查询车场数据列表", consumes = "application/json")
	@ResponseBody
	public ResPreListAndDetails findPreList(HttpServletRequest request,
			@ApiParam("城市id")@RequestParam(value="cityId",required=true) @NotNull(message="城市id不能为null")Long cityId
			){
		return this.dataStatiscsService.findPreList(request,cityId);
	}
	
	@RequestMapping(value = "/find-pre-details", method = RequestMethod.POST)
	@ApiOperation(value = "查询车场数据详情", notes = "查询车场数据详情", consumes = "application/json")
	@ResponseBody
	public ResPreDetails findPreDetails(HttpServletRequest request,@RequestBody ReqPreDetails details){
		return this.dataStatiscsService.findPreDetails(request,details);
	}
	
	@RequestMapping(value = "/find-pre-report-forms", method = RequestMethod.POST)
	@ApiOperation(value = "查询车场数据报表", notes = "查询车场数据报表", consumes = "application/json")
	@ResponseBody
	public ResPreReportForms findPreReportForms(HttpServletRequest request,@RequestBody ReqPreReportForms reportForms){
		return this.dataStatiscsService.findPreReportForms(request,reportForms);
	}
}
