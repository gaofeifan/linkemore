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
import cn.linkmore.prefecture.controller.staff.request.ReqAssignStall;
import cn.linkmore.prefecture.controller.staff.request.ReqStaffStallList;
import cn.linkmore.prefecture.controller.staff.response.ResStaffPreList;
import cn.linkmore.prefecture.controller.staff.response.ResStaffStallDetail;
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
	
	@RequestMapping(value="/stall-detail",method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "车位详情", notes = "车位详情", consumes = "application/json")
	public ResponseEntity<ResStaffStallDetail> findStaffStallDetails(HttpServletRequest request,  @ApiParam("车位id") @NotNull(message="车位id不能为空") @RequestParam("stallId") Long stallId) {
		ResStaffStallDetail detail = this.stallService.findStaffStallDetails(request,stallId);
		return ResponseEntity.success(detail, request);
	}
	
	@ApiOperation(value = "指定车位操作", notes = "指定车位操作")
	@RequestMapping(value = "/staff-assign", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> staffAssign(HttpServletRequest request, @Validated @RequestBody ReqAssignStall bean) {
		ResponseEntity<String> response = null;
			String plate = this.stallService.staffAssign(bean,request);
			response = ResponseEntity.success(plate, request);
		return response;
	}
	
	@ApiOperation(value = "删除指定车位操作", notes = "删除指定车位操作")
	@RequestMapping(value = "/assign-del", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Void> staffAssignDel(HttpServletRequest request, @Validated @RequestBody ReqAssignStall bean) {
		this.stallService.staffAssignDel(bean);
		return ResponseEntity.success(null, request);
	}

}
