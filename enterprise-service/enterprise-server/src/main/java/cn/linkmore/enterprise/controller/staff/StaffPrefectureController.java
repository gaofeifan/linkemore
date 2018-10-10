package cn.linkmore.enterprise.controller.staff;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.enterprise.controller.staff.request.OrderOperateRequestBean;
import cn.linkmore.enterprise.controller.staff.request.SraffReqConStall;
import cn.linkmore.enterprise.controller.staff.request.StallOnLineRequest;
import cn.linkmore.enterprise.controller.staff.request.StallOperateRequestBean;
import cn.linkmore.enterprise.service.StaffPrefectureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller -车区【管理版】
 * 
 * @author
 * @version 2.0
 *
 */
@Api(tags = "ManagePrefecture", description = "车区【管理版】")
@RestController
@RequestMapping("/staff/pre")
public class StaffPrefectureController {

	@Autowired
	private StaffPrefectureService staffPrefectureService;
	
	

	@ApiOperation(value = "管理员操作车位锁", notes = "管理员操作车位锁", consumes = "application/json")
	@RequestMapping(value = "/v2.0/control", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> controlLock(@Validated @RequestBody SraffReqConStall reqConStall,
			HttpServletRequest request) {
		ResponseEntity<Boolean> response = null;
		try {
			staffPrefectureService.control(reqConStall, request);
			response = ResponseEntity.success(true, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

	@ApiOperation(value = "释放车位接口", notes = "释放车位接口", consumes = "application/json")
	@RequestMapping(value = "/release_stall/{stallId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> releaseStall(@PathVariable("stallId") Long stallId, HttpServletRequest request) {
		ResponseEntity<Boolean> response = null;
		try {
			this.staffPrefectureService.releaseStall(stallId, request);
			response = ResponseEntity.success(true, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

	@ApiOperation(value = "强制释放车位接口", notes = "强制释放车位接口", consumes = "application/json")
	@RequestMapping(value = "/force_release_stall/{stallId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> forceReleaseStall(@PathVariable("stallId") Long stallId, HttpServletRequest request) {
		ResponseEntity<Boolean> response = null;
		try {
			this.staffPrefectureService.forceReleaseStall(stallId, request);
			response = ResponseEntity.success(true, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

	@ApiOperation(value = "关闭订单", notes = "关闭订单", consumes = "application/json")
	@RequestMapping(value = "/close", method = RequestMethod.POST)
	public ResponseEntity<?> close(@Valid @RequestBody OrderOperateRequestBean oorb, HttpServletRequest request) {
		ResponseEntity<Boolean> response = null;
		try {
			this.staffPrefectureService.close(oorb, request);
			response = ResponseEntity.success(true, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

	@ApiOperation(value = "挂起订单", notes = "挂起订单", consumes = "application/json")
	@RequestMapping(value = "/suspend", method = RequestMethod.POST)
	public ResponseEntity<?> suspend(@Valid @RequestBody OrderOperateRequestBean oorb, HttpServletRequest request) {
		ResponseEntity<Boolean> response = null;
		try {
			this.staffPrefectureService.suspend(oorb, request);
			response = ResponseEntity.success(true, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

	@ApiOperation(value = "下线", notes = "车位下线操作")
	@RequestMapping(value = "/offline", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> offline(HttpServletRequest request, @Valid @RequestBody StallOperateRequestBean bean) {
		ResponseEntity<Boolean> response = null;
		try {
			this.staffPrefectureService.offline(bean, request);
			response = ResponseEntity.success(true, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

	@ApiOperation(value = "上线", notes = "车位上线操作")
	@RequestMapping(value = "/online", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> online(HttpServletRequest request, @Valid @RequestBody StallOnLineRequest bean) {
		ResponseEntity<Boolean> response = null;
		try {
			this.staffPrefectureService.online(bean, request);
			response = ResponseEntity.success(true, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
		
}
