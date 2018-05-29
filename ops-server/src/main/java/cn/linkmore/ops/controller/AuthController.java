package cn.linkmore.ops.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.ops.request.ReqPerson;
import cn.linkmore.ops.response.ResPerson;
import cn.linkmore.ops.service.PersonService;
/*import cn.linkmore.security.entity.Person;
import cn.linkmore.security.request.ReqPerson;
import cn.linkmore.security.service.PersonService;*/
import cn.linkmore.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller - 权限模块 - 认证
 * 
 * @author liwenlong
 * @version 1.0
 *
 */
@Api(tags = "Auth", description = "授权")
@RestController
@RequestMapping("/admin/auth")
public class AuthController {

	@Autowired
	private PersonService personService;

	@ApiOperation(value = "登录", notes = "登录", consumes = "application/json")
	@RequestMapping(value = "/login")
	@ResponseBody
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson) subject.getSession().getAttribute("person");
		Map<String, Object> map = new HashMap<String, Object>();
		if (person != null) {
			map.put("login", true);
			map.put("token", "hello kitty");
			response.setStatus(200);
			map.put("map", person);
		} else {
			if ("POST".equals(request.getMethod().toUpperCase())) {
				String message = null;
				String loginFailure = (String) request
						.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
				if (loginFailure != null) {
					if (loginFailure.equals("org.apache.shiro.authc.pam.UnsupportedTokenException")) {
						message = "验证码校验失败";
					} else if (loginFailure.equals("org.apache.shiro.authc.UnknownAccountException")) {
						message = "账号不存在";
					} else if (loginFailure.equals("org.apache.shiro.authc.DisabledAccountException")) {
						message = "账号已禁用，请与管理员取得联系";
					} else if (loginFailure.equals("org.apache.shiro.authc.LockedAccountException")) {
						message = "账号已锁定，请与管理员取得联系";
					} else if (loginFailure.equals("org.apache.shiro.authc.IncorrectCredentialsException")) {
						message = "账号或者密码错误";
					} else if (loginFailure.equals("org.apache.shiro.authc.AuthenticationException")) {
						message = "校验失败";
					}
					map.put("message", message);
				}
				map.put("login", false);
				response.setStatus(200);
			} else if ("GET".equals(request.getMethod().toUpperCase())) {
				response.setStatus(401);
				map.put("login", false);
				map.put("message", "您还未登录,请先登录");
			}

		}
		OutputStream pw = null;
		try {
			pw = response.getOutputStream();
			pw.write(JsonUtil.toJson(map).getBytes("UTF-8"));
			response.setContentType("text/json; charset=UTF-8");
			pw.flush();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	
	@ApiOperation(value = "成功", notes = "成功", consumes = "application/json")
	@RequestMapping(value = "/success", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> success() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson) subject.getSession().getAttribute("person");
		map.put("login", true);
		map.put("token", "hello kitty");
		map.put("map", person);
		return map;
	}

	@ApiOperation(value = "403", notes = "403", consumes = "application/json")
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	@ResponseBody
	public void unauth(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("path", request.getRequestURI());
		map.put("auth", false);
		map.put("timestamp", new Date().getTime());
		map.put("status", 403);
		OutputStream pw = null;
		response.setStatus(403);
		try {
			pw = response.getOutputStream();
			pw.write(JsonUtil.toJson(map).getBytes("UTF-8"));
			response.setContentType("text/json; charset=UTF-8");
			pw.flush();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	@ApiOperation(value = "退出", notes = "退出", consumes = "application/json")
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> logout() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		Subject subject = SecurityUtils.getSubject();
		subject.getSession().removeAttribute("person");
		subject.logout();
		map.put("logout", true);
		map.put("timestamp", new Date().getTime());
		return map;
	}

	@ApiOperation(value = "更新密码", notes = "更新密码", consumes = "application/json")
	@RequestMapping(value = "/update_password", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> updatePasswrod(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("password") String password) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson) subject.getSession().getAttribute("person");
		try {
			this.personService.updatePassword(person, oldPassword, password);
			map.put("update", true);
		} catch (RuntimeException e) {
			map.put("update", false);
			map.put("message", e.getMessage().toString());
		}
		return map;
	}
}
