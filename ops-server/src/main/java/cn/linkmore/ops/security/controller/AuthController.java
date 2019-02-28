package cn.linkmore.ops.security.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.linkmore.ops.security.service.PersonService;
import cn.linkmore.security.request.ReqPerson;
import cn.linkmore.security.response.ResPerson;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.PasswordUtil;
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
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private PersonService personService;

	@ApiOperation(value = "登录", notes = "登录", consumes = "application/json")
	@RequestMapping(value = "/login2")
	@ResponseBody
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("login2");
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson) subject.getSession().getAttribute("person");
 		log.info("session person {}, request method{}",JSON.toJSON(person),request.getMethod());
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
	
	
	//@ApiOperation(value = "登录2", notes = "登录2", consumes = "application/json")
	@RequestMapping(value = "/login")
	@ResponseBody
	//public String login2(HttpServletRequest request, HttpServletResponse response,String username,String password) throws IOException {
	public Map<String,Object> login2(HttpServletRequest request, HttpServletResponse response,String username,String password) throws IOException {
		//System.out.println(username);
		//System.out.printf("login=%s,%s\n",username,password);
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isEmpty(username)) {
			map.put("login", false);
			map.put("message", "账号不能为空");
		}else if(StringUtils.isEmpty(password)) {
			map.put("login", false);
			map.put("message", "密码不能为空");
		}else {
			try{
				Subject subject = SecurityUtils.getSubject();
				// 调用安全认证框架的登录方法
				subject.login(new UsernamePasswordToken(username, password));
				ResPerson person = (ResPerson) subject.getSession().getAttribute("person");
				map.put("login", true);
				map.put("token", subject.getSession().getId());
				map.put("map", person);
			}catch(UnsupportedTokenException ex){
				map.put("login", false);
				map.put("message", "验证码校验失败");
			}catch(UnknownAccountException ex){
				map.put("login", false);
				map.put("message", "账号不存在");
			}catch(DisabledAccountException ex){
				map.put("login", false);
				map.put("message", "账号已禁用，请与管理员取得联系");
			}catch(IncorrectCredentialsException ex){
				map.put("login", false);
				map.put("message", "账号或者密码错误");
			}catch(AuthenticationException ex){
				map.put("login", false);
				map.put("message", "登录失败");
			}catch(Exception ex) {
				map.put("login", false);
				map.put("message", "系统错误,请与管理员取得联系");
			}
		}
		return map;
	}
	
	@ApiOperation(value = "成功", notes = "成功", consumes = "application/json")
	@RequestMapping(value = "/success", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> success() throws IOException {
		System.out.println("成功");
		Map<String, Object> map = new HashMap<String, Object>();
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson) subject.getSession().getAttribute("person");
		map.put("login", true);
		map.put("token", "hello kitty");
		map.put("map", person);
		return map;
	}

	@ApiOperation(value = "403", notes = "403", consumes = "application/json")
	@RequestMapping(value = "/403", method = RequestMethod.POST)
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
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> logout() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		Subject subject = SecurityUtils.getSubject();
		subject.getSession().removeAttribute("person");
		subject.logout();
		ResPerson person = (ResPerson) subject.getSession().getAttribute("person");
		log.info("logout session person {}",JSON.toJSON(person));
		map.put("logout", true);
		map.put("timestamp", new Date().getTime());
		return map;
	}

	@ApiOperation(value = "更新密码", notes = "更新密码", consumes = "application/json")
	@RequestMapping(value = "/update_password", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePasswrod(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("password") String password) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson) subject.getSession().getAttribute("person");
		ResPerson resPerson = this.personService.findByUsername(person.getUsername());
		ReqPerson reqPerson = ObjectUtils.copyObject(resPerson, new ReqPerson());
		try {
			if(StringUtils.isBlank(oldPassword) || StringUtils.isBlank(password)){
				throw new RuntimeException("密码不能为空");
			}
			if(StringUtils.isNotBlank(reqPerson.getPassword())){
				if(PasswordUtil.checkPassword(oldPassword, reqPerson.getPassword())){
					reqPerson.setPassword(password);
					this.personService.update(reqPerson);
					subject.getSession().removeAttribute("person");
					subject.logout();
					map.put("logout", true);
				}else{
					throw new RuntimeException("原始密码错误");
				}
			}
			map.put("update", true);
		} catch (RuntimeException e) {
			map.put("update", false);
			map.put("message", e.getMessage().toString());
		}
		return map;
	}
}
