package cn.linkmore.enterprise.controller.staff;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.enterprise.controller.staff.request.SraffReqConStall;
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
	
	
	
}
