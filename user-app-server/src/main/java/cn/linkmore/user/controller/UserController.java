package cn.linkmore.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.user.request.ReqMobileBind;
import cn.linkmore.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * Controller - 用户
 * @author liwenlong
 * @version 2.0
 *
 */
@Api(tags="User",description="用户信息")
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService; 
	
	@ApiOperation(value="绑定手机号",notes="手机号不能为空,短信验证码不能为空", consumes = "application/json")
	@RequestMapping(value = "/v2.0/mobile", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> bindMobile(@RequestBody ReqMobileBind rmb, HttpServletRequest request){
		ResponseEntity<?> response = null; 
		try {
			this.userService.bindMobile(rmb,request);
			response = ResponseEntity.success(null, request);
		}catch(BusinessException e){
			e.printStackTrace();
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		}catch(Exception e){
			e.printStackTrace();
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	@ApiOperation(value="发短信验证码",notes="手机号不能为空", consumes = "application/json")
	@RequestMapping(value = "/v2.0/sms", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> sms(@RequestParam(value="mobile" ,required=true) String mobile,HttpServletRequest request){
		ResponseEntity<?> response = null; 
		try {
			this.userService.send(mobile,request);
			response = ResponseEntity.success(null, request);
		}catch(BusinessException e){
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		}catch(Exception e){
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
}
