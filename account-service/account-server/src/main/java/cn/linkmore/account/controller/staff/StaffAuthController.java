package cn.linkmore.account.controller.staff;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.controller.app.request.ReqAuthEditPW;
import cn.linkmore.account.controller.app.request.ReqAuthLogin;
import cn.linkmore.account.controller.app.request.ReqAuthSend;
import cn.linkmore.account.controller.app.request.ReqEditPWAuth;
import cn.linkmore.account.controller.app.request.ReqReset;
import cn.linkmore.account.controller.staff.request.ReqEditPw;
import cn.linkmore.account.controller.staff.request.ReqEditPwAuth;
import cn.linkmore.account.controller.staff.request.ReqLoginPw;
import cn.linkmore.account.controller.staff.response.ResAdmin;
import cn.linkmore.account.controller.staff.response.ResCheckAccount;
import cn.linkmore.account.service.StaffAdminUserService;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 用户认证
 * @author   GFF
 * @Date     2018年7月19日
 * @Version  v2.0
 */
@Api(tags="Staff-Auth",description="用户认证【管理版】")
@RestController
@RequestMapping("/staff/auth") 
@Validated
public class StaffAuthController {

	@Resource
	private StaffAdminUserService staffAdminUserService; 
	
	@ApiOperation(value="用户登录",notes="手机号及短信验证码不能为空", consumes = "application/json")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResAdmin> login(@Validated @RequestBody ReqAuthLogin rl, HttpServletRequest request) {
		ResponseEntity<ResAdmin> response = null; 
		ResAdmin ru = this.staffAdminUserService.login(rl,request);
		response = ResponseEntity.success(ru, request);
		return response;
	}
	
	@ApiOperation(value="账户密码登录",notes="账户密码登录", consumes = "application/json")
	@RequestMapping(value = "/login-pw", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResAdmin> loginPw(@Validated @RequestBody ReqLoginPw rl, HttpServletRequest request) {
		ResponseEntity<ResAdmin> response = null; 
		ResAdmin ru;
		try {
			ru = this.staffAdminUserService.login(rl,request);
			response = ResponseEntity.success(ru, request);
		} catch (Exception e) {
			if(e instanceof BusinessException) {
				BusinessException be =(BusinessException)e;
				return ResponseEntity.fail(be.getStatusEnum(), request);
			}else {
				return ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION.code, e.getMessage(), request);
			}
		}
		return response;
	}
	
	@ApiOperation(value="用户退出登录",notes="用户退出登录", consumes = "application/json")
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> logout(HttpServletRequest request) {
		ResponseEntity<?> response = null;
		this.staffAdminUserService.logout(request);
		response = ResponseEntity.success("用户退出成功", request);
		return response;
	}
	
	/*@ApiOperation(value = "微信登录", notes = "微信登录", consumes = "application/json")
	@RequestMapping(value = "/wechat", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> login( 
			@RequestParam(value="code")  
			@NotBlank(message="授权码不能为空") 
			@Size(min =32,max=36,message="授权码为无效")
			String code, HttpServletRequest request) {
		ResponseEntity<?> response = null;
		this.adminUserService.bindLogin(code, request);
		response = ResponseEntity.success( "绑定成功", request);
		return response;
	}  
	*/
	@ApiOperation(value = "绑定微信号", notes = "小程序绑定微信号", consumes = "application/json")
	@RequestMapping(value = "/bind-wechat", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> bindWechat( 
			@RequestParam(value="code")  
			@NotBlank(message="授权码不能为空") 
			@Size(min =32,max=36,message="授权码为无效")
			String code, HttpServletRequest request) {
		ResponseEntity<?> response = null;
		String bind = this.staffAdminUserService.bindWechat(code, request);
		response = ResponseEntity.success( bind, request);
		return response;
	}  
	
	@ApiOperation(value="校验手机号是否存在",notes="校验手机号是否存在", consumes = "application/json")
	@RequestMapping(value = "/check-mobile", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> checkMobile(
			@NotBlank(message="手机号不能为空") 
			@Pattern(regexp="^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\\d{8})$", message="无效手机号") 
			@ApiParam("手机号") @RequestParam("mobile") String mobile
			, HttpServletRequest request){
		ResponseEntity<?> response = null; 
		boolean flag = this.staffAdminUserService.checkMobile(mobile);
		response = ResponseEntity.success(flag, request);
		return response;
	}
	
	@ApiOperation(value="发短信验证码",notes="手机号不能为空,需要加密", consumes = "application/json")
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> send(@Validated @RequestBody ReqAuthSend rs, HttpServletRequest request){
		ResponseEntity<?> response = null; 
		try {
			this.staffAdminUserService.send(rs);
		} catch (Exception e) {
			if(e instanceof BusinessException) {
				BusinessException be =(BusinessException)e;
				return ResponseEntity.fail(be.getStatusEnum(), request);
			}else {
				return ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION.code, e.getMessage(), request);
			}
		}
		response = ResponseEntity.success(null, request);
		return response;
	}
	
	@ApiOperation(value="验证账号是否存在",notes="验证账号是否存在", consumes = "application/json")
	@RequestMapping(value = "/check-account", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResCheckAccount> checkAccount(HttpServletRequest request,@NotBlank(message="账号不能为空") 
	@ApiParam(value="账号",required=true) @RequestParam("account") String account) {
		ResponseEntity<ResCheckAccount> response = null;
		ResCheckAccount a;
		try {
			a = this.staffAdminUserService.checkAccount(account);
		} catch (Exception e) {
			if(e instanceof BusinessException) {
				BusinessException be =(BusinessException)e;
				return ResponseEntity.fail(be.getStatusEnum(), request);
			}else {
				return ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION.code, e.getMessage(), request);
			}
		}
		response = ResponseEntity.success(a, request);
		return response;
	}
	@ApiOperation(value="重置密码-发送短信",notes="重置密码-发送短信", consumes = "application/json")
	@RequestMapping(value = "/send-reset", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> sendReset(HttpServletRequest request,
	@ApiParam(value="账号/手机号(为空即可)",required=false) @RequestParam(value="account",required=false)String account) {
		ResponseEntity<?> response = null;
		String token;
		try {
			token = this.staffAdminUserService.sendReset(request,account);
			response = ResponseEntity.success(token, request);
		} catch (Exception e) {
			if(e instanceof BusinessException) {
				BusinessException be =(BusinessException)e;
				return ResponseEntity.fail(be.getStatusEnum(), request);
			}else {
				return ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION.code, e.getMessage(), request);
			}
		}
		return response;
	}
	@ApiOperation(value="认证验证码",notes="认证验证码", consumes = "application/json")
	@RequestMapping(value = "/auth-code", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> authCode(HttpServletRequest request,
	@ApiParam(value="账号/手机号",required=false) @RequestParam(value="account",required=false)
	String account,
	@ApiParam(value="验证码",required=true)@RequestParam("code")
	@Range(min=1000, max=9999,message="验证码是4位有效数字")
	@NotBlank(message="验证码不能为空") 
	 String code) {
		ResponseEntity<Boolean> response = null;
		boolean token;
		try {
			token = this.staffAdminUserService.authCode(request,account,code);
			response = ResponseEntity.success(token, request);
		} catch (Exception e) {
			if(e instanceof BusinessException) {
				BusinessException be =(BusinessException)e;
				return ResponseEntity.fail(be.getStatusEnum(), request);
			}else {
				return ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION.code, e.getMessage(), request);
			}
		}
		return response;
	}
	
	
	@ApiOperation(value="重置密码",notes="重置密码", consumes = "application/json")
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> reset(HttpServletRequest request,@Validated @RequestBody ReqReset reset) {
		try {
			this.staffAdminUserService.reset(request,reset);
			return ResponseEntity.success(true,request);
		} catch (Exception e) {
			if(e instanceof BusinessException) {
				BusinessException be =(BusinessException)e;
				return ResponseEntity.fail(be.getStatusEnum(), request);
			}else {
				return ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION.code, e.getMessage(), request);
			}
		}
	}
	
	@ApiOperation(value="修改密码-原密码认证",notes="修改密码-原密码认证", consumes = "application/json")
	@RequestMapping(value = "/v2.0/edit-pw-auth", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> editPWAuth(HttpServletRequest request,@Validated @RequestBody ReqEditPwAuth pwAuth){
		ResponseEntity<String> response = null; 
		String token;
		try {
			token = this.staffAdminUserService.editPWAuth(pwAuth);
			response = ResponseEntity.success(token, request);
		} catch (Exception e) {
			if(e instanceof BusinessException) {
				BusinessException be =(BusinessException)e;
				return ResponseEntity.fail(be.getStatusEnum(), request);
			}else {
				return ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION.code, e.getMessage(), request);
			}
		}
		return response;
	}

	private int i = 1;
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public String test() {
		new Thread(()->{try {
			Thread.sleep(5000);
			if(i%2 == 0) {
				throw new BusinessException(StatusEnum.ACCOUNT_PASSWORD_ERROR);
			}
			i++;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}}) .start();
		return "test";
	}
}
