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
import cn.linkmore.enterprise.controller.staff.request.AssignStallRequestBean;
import cn.linkmore.enterprise.controller.staff.request.OrderOperateRequestBean;
import cn.linkmore.enterprise.controller.staff.request.SraffReqConStall;
import cn.linkmore.enterprise.controller.staff.request.StallOperateRequestBean;
import cn.linkmore.enterprise.controller.staff.response.PrefectureResponseBean;
import cn.linkmore.enterprise.service.StaffPrefectureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller -车区【管理版】
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
	public ResponseEntity<Boolean> controlLock(@Validated @RequestBody SraffReqConStall reqConStall,HttpServletRequest request) {
		ResponseEntity<Boolean> response = null;
		 try {
			 staffPrefectureService.control(reqConStall, request);
			 response  =	 ResponseEntity.success(true, request);
		}  catch (BusinessException e) {
			response = ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		 return response;
	}
	
	@ApiOperation(value = "释放车位接口", notes = "释放车位接口", consumes = "application/json")
	@RequestMapping(value = "/release_stall/{stallId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> releaseStall(@PathVariable("stallId") Long stallId, HttpServletRequest request) {
		ResponseEntity<PrefectureResponseBean> response = this.staffPrefectureService.releaseStall(stallId,request);
		return response;
	}
	
	@ApiOperation(value = "强制释放车位接口", notes = "强制释放车位接口", consumes = "application/json")
	@RequestMapping(value = "/force_release_stall/{stallId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<PrefectureResponseBean> forceReleaseStall(@PathVariable("stallId") Long stallId,
			HttpServletRequest request) {
		ResponseEntity<PrefectureResponseBean> response = this.staffPrefectureService.forceReleaseStall(stallId,request);
		return response;
	}
	
	@ApiOperation(value = "关闭订单", notes = "关闭订单", consumes = "application/json")
	@RequestMapping(value = "/close", method = RequestMethod.POST)
	public ResponseEntity<Void> close(@Valid @RequestBody OrderOperateRequestBean oorb,HttpServletRequest request) {
		ResponseEntity<Void> re = null;
		try{
			this.staffPrefectureService.close(oorb,request);
			re = ResponseEntity.success(null, request);
		}catch (BusinessException e) {
			re = null;
		} catch(Exception e){
			e.printStackTrace();
			re = null;
		} 
		return re;
	}
	
	
	@ApiOperation(value = "挂起订单", notes = "挂起订单", consumes = "application/json")
	@RequestMapping(value = "/suspend", method = RequestMethod.POST)
	public ResponseEntity<Void> suspend(@Valid @RequestBody OrderOperateRequestBean oorb,HttpServletRequest request) {
		ResponseEntity<Void> re = null;
		try{
			this.staffPrefectureService.suspend(oorb,request);
			re = ResponseEntity.success(null, request);
		}catch (BusinessException e) { 
			re = null;
		} catch(Exception e){ 
			re = null;
		} 
		return re;
	}
	
	
	@ApiOperation(value = "下线", notes = "车位下线操作")
	@RequestMapping(value = "/offline", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PrefectureResponseBean> offline(HttpServletRequest request,
			@Valid @RequestBody StallOperateRequestBean bean) {
		ResponseEntity<PrefectureResponseBean> response = null;
		PrefectureResponseBean prb = null;
		try {
			prb = this.staffPrefectureService.offline(bean,request);
			response = ResponseEntity.success(prb, request);
		} catch (BusinessException e) {
			response = null;
		}
		return response;
	}
	
	@ApiOperation(value = "上线", notes = "车位上线操作")
	@RequestMapping(value = "/online", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PrefectureResponseBean> online(HttpServletRequest request,
			@Valid @RequestBody StallOperateRequestBean bean) {
		ResponseEntity<PrefectureResponseBean> response = null;
		PrefectureResponseBean prb = null;
		try {
			prb = this.staffPrefectureService.online(bean,request);
			response = ResponseEntity.success(prb, request);
		} catch (BusinessException e) {
			response =null;
		}
		return response;
	}
	
	@ApiOperation(value = "指定车位操作", notes = "指定车位操作")
	@RequestMapping(value = "/assign", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> assign(HttpServletRequest request, @Valid @RequestBody AssignStallRequestBean bean) {
		ResponseEntity<String> response = null;
		try {
			String plate = this.staffPrefectureService.assign(bean,request);
			response = null;
		} catch (BusinessException e) {
			response = null;
		}
		return response;
	}
	
	@ApiOperation(value = "删除指定车位操作", notes = "删除指定车位操作")
	@RequestMapping(value = "/assignDel", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Void> assignDel(HttpServletRequest request, @Valid @RequestBody AssignStallRequestBean bean) {
		ResponseEntity<Void> response = null;
		try {
			this.staffPrefectureService.assignDel(bean,request);
			response = ResponseEntity.success(null, request);
		} catch (BusinessException e) {
			response = null;
		}
		return response;
	}
	
}
