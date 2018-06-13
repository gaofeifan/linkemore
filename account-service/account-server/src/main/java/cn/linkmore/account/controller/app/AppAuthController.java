package cn.linkmore.account.controller.app;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.controller.app.request.ReqAuthLogin;
import cn.linkmore.account.controller.app.request.ReqAuthSend;
import cn.linkmore.account.controller.app.response.ResUser;
import cn.linkmore.account.service.UserService;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
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
@RequestMapping("/app/auth") 
@Validated
public class AppAuthController {
	@Autowired
	private UserService userService;
	
	@ApiOperation(value="用户登录",notes="手机号及短信验证码不能为空", consumes = "application/json")
	@RequestMapping(value = "/v2.0/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResUser> login(@Validated @RequestBody ReqAuthLogin rl, HttpServletRequest request) {
		ResponseEntity<ResUser> response = null; 
		try {
			ResUser ru = this.userService.appLogin(rl,request);
			response = ResponseEntity.success(ru, request);
		}catch(BusinessException e){ 
			response = ResponseEntity.fail(e.getStatusEnum(), request); 
		}catch(Exception e){ 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	@ApiOperation(value="用户退出登录",notes="用户退出登录", consumes = "application/json")
	@RequestMapping(value = "/v2.0/logout", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResUser> logout(HttpServletRequest request) {
		ResponseEntity<ResUser> response = null; 
		try {  
			this.userService.logout(request);
			response = ResponseEntity.success(null, request);
		}catch(BusinessException e){ 
			response = ResponseEntity.fail(e.getStatusEnum(), request); 
		}catch(Exception e){ 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	} 
	
	@ApiOperation(value = "微信登录", notes = "微信登录", consumes = "application/json")
	@RequestMapping(value = "/v2.0/wx", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResUser> wxLogin( 
			@RequestParam(value="code")  
			@NotBlank(message="授权码不能为空") 
			@Size(min =32,max=36,message="授权码为无效")
			String code, HttpServletRequest request) {
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
	public ResponseEntity<?> send(@Validated @RequestBody ReqAuthSend rs, HttpServletRequest request){
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
