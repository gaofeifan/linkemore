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
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.prefecture.controller.staff.request.ReqAssignStall;
import cn.linkmore.prefecture.controller.staff.request.ReqLockIntall;
import cn.linkmore.prefecture.controller.staff.request.ReqStaffStallList;
import cn.linkmore.prefecture.controller.staff.response.ResSignalHistory;
import cn.linkmore.prefecture.controller.staff.response.ResStaffNewAuth;
import cn.linkmore.prefecture.controller.staff.response.ResStaffPreList;
import cn.linkmore.prefecture.controller.staff.response.ResStaffStallDetail;
import cn.linkmore.prefecture.controller.staff.response.ResStaffStallList;
import cn.linkmore.prefecture.controller.staff.response.ResStaffStallSn;
import cn.linkmore.prefecture.request.ReqStall;
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
	
	@RequestMapping(value="/stall-detail-sn",method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据车位锁编号查询车位编号", notes = "根据车位锁编号查询车位编号", consumes = "application/json")
	public ResponseEntity<ResStaffStallSn> findStaffStallSn(HttpServletRequest request,  @ApiParam("车位锁编号") @NotNull(message="sn") @RequestParam("sn") String sn) {
		ResStaffStallSn detail = this.stallService.findStaffStallSn(request,sn);
		return ResponseEntity.success(detail, request);
	}
	
	@RequestMapping(value="/lock­signal­history",method=RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "查询车位锁在一定时间内的信号强度", notes = "查询车位锁在一定时间内的信号强度", consumes = "application/json")
	public ResponseEntity<ResSignalHistory> lockSignalHistory(HttpServletRequest request,  @ApiParam("车位锁编号") @NotNull(message="sn") @RequestParam("sn") String sn) {
		ResSignalHistory history = this.stallService.lockSignalHistory(request,sn);
		return ResponseEntity.success(history, request);
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
	
	@ApiOperation(value = "复位", notes = "复位", consumes = "application/json")
	@RequestMapping(value = "/reset",method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> reset(@RequestParam("stallId") @ApiParam("车位id") @NotNull(message="车位不能为null") Long stallId,HttpServletRequest request){
		this.stallService.reset(stallId,request);
		return ResponseEntity.success("复位成功", request);
	}
	
	
	@ApiOperation(value = "地锁安装", notes = "地锁安装")
	@RequestMapping(value = "/installLock", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> installLock(HttpServletRequest request, @Validated @RequestBody ReqLockIntall reqLockIntall) {
		ResponseEntity<Boolean> response = null;
		try {
			this.stallService.install(reqLockIntall,request);
			response = ResponseEntity.success(true, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	@ApiOperation(value = "获取最新人员的权限", notes = "获取最新人员的权限")
	@RequestMapping(value = "/find-new-auth", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ResStaffNewAuth>> findNewAuth(HttpServletRequest request,  @ApiParam(value="城市id 非必填默认查询人员所有的权限",required=false) @RequestParam(value = "cityId",required= false) Long cityId) {
		try {
			return ResponseEntity.success(this.stallService.findNewAuth(cityId,request), request);
		} catch (BusinessException e) {
			return ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			return ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
	}
}
