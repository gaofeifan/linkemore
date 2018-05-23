package cn.linkmore.user.controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.linkmore.common.client.AccessDetailClient;
import cn.linkmore.common.request.ReqAccessDetail;
import cn.linkmore.user.service.UserService;
import cn.linkmore.util.JsonUtil;
 	
@Configuration
@Aspect
public class AccessDetailAop {
	
	@Resource
	private AccessDetailClient accessDetailClient;
	@Resource
	private UserService userService;
	
	@Pointcut("execution(* cn.linkmore.user.controller..*.*(..))")
	public void interfaceLog(){}
		
	@AfterReturning(returning = "obj", pointcut = "interfaceLog())")
	public void accessDetailAfter(JoinPoint joinPoint,Object obj){
		Class<? extends Object> clazz = joinPoint.getTarget().getClass();
		String className = clazz.getName();
		RequestMapping mapping = clazz.getAnnotation(RequestMapping.class);
		String[] value = mapping.value();
		StringBuilder sb = new StringBuilder();
		for (String v : value) {
			sb.append(v);
		}
		Map<String,String> result = new HashMap<>();
		Object[] args = joinPoint.getArgs();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		String methodName = method.getName();
		RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
		String[] values = methodMapping.value();
		for (String v : values) {
			sb.append(v);
		}
		String type = "";
		RequestMethod[] methods = methodMapping.method();
		for (int i = 0; i < methods.length; i++) {
			if(methods.length-1 == i) {
				type += methods[i].name();
			}else {
				type += methods[i].name()+",";
			}
		}
		Class<?>[] types = signature.getParameterTypes();
		for (int i = 0; i < types.length; i++) {
			if(args[i] instanceof HttpServletRequest) {
				result.put(types[i].getSimpleName(),"request");
			}else if(args[i] instanceof HttpServletResponse){
				result.put(types[i].getSimpleName(),"response");
			}else {
				String json = JsonUtil.toJson(args[i]);
				result.put(types[i].getSimpleName(),json);
			}
		}
		Map<String, String> responseJson = new HashMap<>();
		String response = responseJson.put(obj.getClass().getSimpleName(), JsonUtil.toJson(obj));
		ReqAccessDetail detail = new ReqAccessDetail();
		detail.setMethod(methodName);
		detail.setMethodType(type);
		detail.setParams(JsonUtil.toJson(result));
		detail.setPath(className);
		detail.setMapping(sb.toString());
		detail.setReturns(response);
//		userService.ca
//		detail.setUserId(userId);
//		detail.setType(type);
	}
	
	
	public void insert( ReqAccessDetail detail) {
		if(detail.getType() == 0) {
			accessDetailClient.appSave(detail);
		}else if(detail.getType() == 1){
			accessDetailClient.appSave(detail);
		}
	}
	
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
}
