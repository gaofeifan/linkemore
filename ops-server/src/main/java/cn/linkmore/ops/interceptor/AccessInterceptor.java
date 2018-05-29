package cn.linkmore.ops.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 文件头拦截器.
 * @author liwenlong
 * @version 2.0
 *
 */
@Component
public class AccessInterceptor extends HandlerInterceptorAdapter { 
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception { 
		String uri = request.getRequestURI(); 
		String ip = request.getRemoteAddr(); 
		log.info("url: {}, ip: {} ,origin: {}",uri,ip,request.getHeader("Origin"));
		
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT");
		response.setHeader("Access-Control-Allow-Headers", "X-Access-Auth-Token, Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		return true;
	}
}
