package cn.linkmore.account.controller.app;

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

import cn.linkmore.account.controller.app.request.ReqMobileBind;
import cn.linkmore.account.controller.app.response.ResUser;
import cn.linkmore.account.service.UserService;
import cn.linkmore.bean.common.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="Mini",description="小程序")
@RestController
@RequestMapping("/app/mini") 
@Validated
public class AppMiniController {
	@Resource
	private UserService userService;
	
	@ApiOperation(value = "登录", notes = "微信小程序登录", consumes = "application/json")
	@RequestMapping(value = "/v2.0/login", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResUser> mini( 
			@RequestParam(value="code")  
			@NotBlank(message="授权码不能为空") 
			@Size(min =32,max=36,message="授权码为无效")
			String code, HttpServletRequest request) {
		ResponseEntity<ResUser> response = null;
		ResUser urb =null;
		try {
			urb = this.userService.mini(code, request);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		response = ResponseEntity.success( urb, request);
		return response;
	} 
	
	@ApiOperation(value="绑定授权手机号",notes="手机号不能为空,短信验证码不能为空", consumes = "application/json")
	@RequestMapping(value = "/v2.0/bind", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResUser> bindMobile(String mobile, HttpServletRequest request){ 
		ResUser user = this.userService.bindWechatMobile(mobile,request);
		return ResponseEntity.success(user, request);
	}
	
	@ApiOperation(value="绑定普通手机号",notes="手机号不能为空,短信验证码不能为空", consumes = "application/json")
	@RequestMapping(value = "/v2.0/bind", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<ResUser> bind(@RequestBody @Validated ReqMobileBind rmb, HttpServletRequest request){
		ResUser user = this.userService.bindNormalMobile(rmb,request);
		return ResponseEntity.success(user, request);
	}
	
	@ApiOperation(value="发短信验证码",notes="手机号不能为空", consumes = "application/json")
	@RequestMapping(value = "/v2.0/sms", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> sms(@NotBlank(message="手机号不能为空") 
	@Pattern(regexp="^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\\d{8})$", message="无效手机号") 
	@RequestParam(value="mobile" ,required=true)  String mobile,HttpServletRequest request){
		ResponseEntity<?> response = null;  
		this.userService.send(mobile,request);
		response = ResponseEntity.success(null, request);
		return response; 
	}
	
	
}
