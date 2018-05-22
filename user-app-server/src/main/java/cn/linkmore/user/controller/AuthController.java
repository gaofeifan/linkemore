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
import cn.linkmore.user.request.ReqAuthLogin;
import cn.linkmore.user.request.ReqAuthSend;
import cn.linkmore.user.response.ResUser;
import cn.linkmore.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller - 用户认证
 * @author liwenlong
 * @version 2.0
 *
 */
@Api(tags="Auth",description="授权")
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserService userService;
	
	@ApiOperation(value="用户登录",notes="手机号及短信验证码不能为空", consumes = "application/json")
	@RequestMapping(value = "/v2.0/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResUser> login(@RequestBody ReqAuthLogin rl, HttpServletRequest request) {
		ResponseEntity<ResUser> response = null; 
		try {
			System.out.println(request.getSession().getId());
			ResUser ru = this.userService.login(rl,request);
			response = ResponseEntity.success(ru, request);
		}catch(BusinessException e){
			e.printStackTrace();
			response = ResponseEntity.fail(e.getStatusEnum(), request); 
		}catch(Exception e){
			e.printStackTrace();
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	@ApiOperation(value = "微信登录", notes = "微信登录", consumes = "application/json")
	@RequestMapping(value = "/v2.0/wx", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResUser> wxLogin(@RequestParam(value="code",required=true) String code, HttpServletRequest request) {
		ResponseEntity<ResUser> response = null;
		try { 
			ResUser urb = this.userService.login(code, request);
			response = ResponseEntity.success( urb, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	} 
	
	@ApiOperation(value="发短信验证码",notes="手机号不能为空,需要加密", consumes = "application/json")
	@RequestMapping(value = "/v2.0/send", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> send(@RequestBody ReqAuthSend rs, HttpServletRequest request){
		ResponseEntity<?> response = null; 
		try {
			this.userService.send(rs);
			response = ResponseEntity.success(null, request);
		}catch(BusinessException e){
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		}catch(Exception e){
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
}
