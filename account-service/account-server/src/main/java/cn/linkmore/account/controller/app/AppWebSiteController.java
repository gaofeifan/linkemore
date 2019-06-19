package cn.linkmore.account.controller.app;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.account.controller.app.request.ReqAuthPW;
import cn.linkmore.account.service.WebSiteUserService;
import cn.linkmore.bean.common.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller - 用户认证
 * @author tony
 * @version 2.0
 *
 */
@Api(tags="WebSite",description="网站")
@RestController
@RequestMapping("/app/web-site") 
@Validated
public class AppWebSiteController {
	@Autowired
	private WebSiteUserService userService;
	
	@ApiOperation(value="账号密码登录",notes="账号密码登录", consumes = "application/json")
	@RequestMapping(value = "/v2.0/login-pw", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> loginPW(@Validated @RequestBody ReqAuthPW pw, HttpServletRequest request){
		ResponseEntity<String> response = null; 
		String mobile =this.userService.loginPW(pw);
		response = ResponseEntity.success(mobile, request);
		return response;
	}
	
	@ApiOperation(value="注册",notes="注册", consumes = "application/json")
	@RequestMapping(value = "/v2.0/register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> register(@Validated @RequestBody ReqAuthPW register, HttpServletRequest request){
		ResponseEntity<Boolean> response = null; 
		Boolean flag =this.userService.register(register);
		response = ResponseEntity.success(flag, request);
		return response;
	}
	
}
