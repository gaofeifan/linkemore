package cn.linkmore.enterprise.controller.staff;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.annotation.AopIgnore;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.enterprise.controller.staff.response.MessageSearchResponseBean;
import cn.linkmore.enterprise.service.StaffOperateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller -管理【管理版】
 * @author 
 * @version 2.0
 *
 */
@Api(tags = "ManagerOperate", description = "管理【管理版】")
@RestController
@RequestMapping("/staff/opt")
public class StaffOperateController {
	
   @Autowired
   StaffOperateService staffOperateService;
	
	@ApiOperation(value = "查看用户验证码", notes = "查看用户验证码", consumes = "application/json")
	@RequestMapping(value = "/search/{mobile}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> releaseStall(@PathVariable("mobile") Long mobile, HttpServletRequest request) {
		ResponseEntity<MessageSearchResponseBean> response = null;
		try {
			MessageSearchResponseBean msrb  = staffOperateService.getMessage(mobile);
			response = ResponseEntity.success(msrb, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	
	
	
	
	
}
