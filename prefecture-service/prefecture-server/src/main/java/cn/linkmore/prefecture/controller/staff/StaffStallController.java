package cn.linkmore.prefecture.controller.staff;

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
import cn.linkmore.prefecture.controller.staff.request.ReqStaffStallList;
import cn.linkmore.prefecture.controller.staff.response.ResStaffPreList;
import cn.linkmore.prefecture.controller.staff.response.ResStaffStallList;
import cn.linkmore.prefecture.service.StallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Stall-Staff", description = "车位管理【管理版】")
@RestController
@RequestMapping("/staff/stall")
@Validated
public class StaffStallController {

	@Resource
	private StallService stallService;
	
	@RequestMapping(value="/pre-list",method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "车区列表", notes = "根查询车区列表", consumes = "application/json")
	public ResponseEntity<List<ResStaffPreList>> findPreList(HttpServletRequest request, @ApiParam("城市id") @NotNull(message="城市id不能为空") @RequestParam("cityId") Long cityId) {
		List<ResStaffPreList> list = this.stallService.findPreList(request,cityId);
		return ResponseEntity.success(list, request);
	}
	@RequestMapping(value="/stall-list",method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "车位列表", notes = "根查询车位列表", consumes = "application/json")
	public ResponseEntity<List<ResStaffStallList>> findStallList(HttpServletRequest request, @RequestBody @Validated ReqStaffStallList staffList) {
		List<ResStaffStallList> list = this.stallService.findStallList(request,staffList);
		return ResponseEntity.success(list, request);
	}
}
