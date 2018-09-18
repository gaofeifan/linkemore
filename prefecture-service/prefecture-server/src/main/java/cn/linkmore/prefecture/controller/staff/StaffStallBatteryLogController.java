package cn.linkmore.prefecture.controller.staff;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.prefecture.controller.staff.response.ResStaffStallBatteryLog;
import cn.linkmore.prefecture.service.StallBatteryLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 管理版电池电量
 * @author   GFF
 * @Date     2018年9月14日
 * @Version  v2.0
 */
@Api(tags = "Staff-battery-log", description = "电池电量【管理版】")
@Validated
@RestController
@RequestMapping("/staff/stall-battery-log")
public class StaffStallBatteryLogController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StallBatteryLogService stallBatteryLogService;
	
	@RequestMapping(value = "/v2.0/battery-log", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "管理版-电池电量列表", notes = "管理版-电池电量列表", consumes = "application/json")
	public ResponseEntity<List<ResStaffStallBatteryLog>> findBatteryLogList(@ApiParam("车位id") @NotNull(message="车位id不能为空")@RequestParam(value="stallId",required=true)Long stallId,HttpServletRequest request) {
		List<ResStaffStallBatteryLog> findBatteryLogList = this.stallBatteryLogService.findStaffBatteryLogList(stallId,request);
		return ResponseEntity.success(findBatteryLogList, request);
		 
	}
}
