package cn.linkmore.account.controller.app;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.controller.app.request.ReqAuthCode;
import cn.linkmore.account.controller.app.request.ReqAuthEditPW;
import cn.linkmore.account.controller.app.request.ReqAuthLogin;
import cn.linkmore.account.controller.app.request.ReqAuthPW;
import cn.linkmore.account.controller.app.request.ReqAuthRegister;
import cn.linkmore.account.controller.app.request.ReqAuthSend;
import cn.linkmore.account.controller.app.request.ReqEditPWAuth;
import cn.linkmore.account.controller.app.response.ResUser;
import cn.linkmore.account.service.UserService;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.util.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
		ResUser ru = this.userService.appLogin(rl,request);
		response = ResponseEntity.success(ru, request);
		return response;
	}
	@ApiOperation(value="用户退出登录",notes="用户退出登录", consumes = "application/json")
	@RequestMapping(value = "/v2.0/logout", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResUser> logout(HttpServletRequest request) {
		ResponseEntity<ResUser> response = null; 
		this.userService.logout(request);
		response = ResponseEntity.success(null, request);
		return response;
	} 
	
	@ApiOperation(value = "微信登录", notes = "微信登录", consumes = "application/json")
	@RequestMapping(value = "/v2.0/wechat", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResUser> login( 
			@RequestParam(value="code")  
			@NotBlank(message="授权码不能为空") 
			@Size(min =32,max=36,message="授权码为无效")
			String code, HttpServletRequest request) {
		ResponseEntity<ResUser> response = null;
		ResUser urb = this.userService.login(code, request);
		response = ResponseEntity.success( urb, request);
		return response;
	}  
	
	@ApiOperation(value="发短信验证码",notes="手机号不能为空,需要加密", consumes = "application/json")
	@RequestMapping(value = "/v2.0/send", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> send(@Validated @RequestBody ReqAuthSend rs, HttpServletRequest request){
		ResponseEntity<?> response = null; 
		this.userService.send(rs);
		response = ResponseEntity.success(null, request);
		return response;
	}
	
	
	@ApiOperation(value="账号密码登录",notes="账号密码登录", consumes = "application/json")
	@RequestMapping(value = "/v2.0/login-pw", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResUser> loginPW(@Validated @RequestBody ReqAuthPW pw, HttpServletRequest request){
		ResponseEntity<ResUser> response = null; 
		ResUser ru =this.userService.loginPW(pw,request);
		response = ResponseEntity.success(ru, request);
		return response;
	}
	
	@ApiOperation(value="注册",notes="注册", consumes = "application/json")
	@RequestMapping(value = "/v2.0/register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResUser> register(@Validated @RequestBody ReqAuthRegister register, HttpServletRequest request){
		ResponseEntity<ResUser> response = null; 
		ResUser ru =this.userService.register(register,request);
		response = ResponseEntity.success(ru, request);
		return response;
	}
	
	@ApiOperation(value="认证验证码",notes="认证验证码", consumes = "application/json")
	@RequestMapping(value = "/v2.0/auth-code", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> authCode(HttpServletRequest request,@Validated @RequestBody ReqAuthCode authCode){
		ResponseEntity<Boolean> response = null; 
		Boolean boolean1 = this.userService.authCode(authCode);
		response = ResponseEntity.success(boolean1, request);
		return response;
	}
	@ApiOperation(value="认证是否是新用户",notes="认证是否是新用户")
	@RequestMapping(value = "/v2.0/auth-is-new", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Boolean> authIsNew(HttpServletRequest request,
			@ApiParam(value = "手机号，必填", required = true)
	@Pattern(regexp="^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\\d{8})$", message="无效手机号") 
	@RequestParam(value="mobile" ,required=true)  String mobile){
		ResponseEntity<Boolean> response = null; 
		Boolean boolean1 = this.userService.authIsNew(mobile);
		response = ResponseEntity.success(boolean1, request);
		return response;
	}
	
	@ApiOperation(value="修改密码-原密码认证",notes="修改密码-原密码认证", consumes = "application/json")
	@RequestMapping(value = "/v2.0/edit-pw-auth", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> editPWAuth(HttpServletRequest request,@Validated @RequestBody ReqEditPWAuth pwAuth){
		ResponseEntity<String> response = null; 
		String token = this.userService.editPWAuth(pwAuth);
		response = ResponseEntity.success(token, request);
		return response;
	}
	@ApiOperation(value="修改密码",notes="修改密码", consumes = "application/json")
	@RequestMapping(value = "/v2.0/edit-pw", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> editPW(HttpServletRequest request,@Validated @RequestBody ReqAuthEditPW pw){
		ResponseEntity<Boolean> response = null; 
		Boolean boolean1 = this.userService.editPW(pw,request);
		response = ResponseEntity.success(boolean1, request);
		return response;
	}
	
	@ApiOperation(value="修改密码-发送短信验证码",notes="修改密码-发送短信验证码", consumes = "application/json")
	@RequestMapping(value = "/v2.0/send-pw", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> sendPW(@Validated @RequestBody ReqAuthSend rs, HttpServletRequest request){
		ResponseEntity<String> response = null; 
		String token = this.userService.sendPW(rs,request);
		response = ResponseEntity.success(token, request);
		return response;
	}
}
