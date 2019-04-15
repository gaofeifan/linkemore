package cn.linkmore.account.controller.staff;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.controller.app.request.ReqAuthLogin;
import cn.linkmore.account.controller.app.request.ReqAuthSend;
import cn.linkmore.account.controller.staff.request.ReqLoginPw;
import cn.linkmore.account.controller.staff.response.ResAdmin;
import cn.linkmore.account.service.StaffAdminUserService;
import cn.linkmore.bean.common.ResponseEntity;
import io.swagger.annotations.Api;
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
		ResAdmin ru = this.staffAdminUserService.login(rl,request);
		response = ResponseEntity.success(ru, request);
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
		this.staffAdminUserService.send(rs);
		response = ResponseEntity.success(null, request);
		return response;
	}
}
