package cn.linkmore.user.security;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.redis.RedisService;
import cn.linkmore.user.common.UserCache;
import cn.linkmore.user.response.ResUser;
import cn.linkmore.util.JsonUtil;


/**
 * 权限控制拦截器.
 * @author liwenlong
 * @version 2.0
 *
 */
@Component
public class AccessInterceptor extends HandlerInterceptorAdapter { 
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private static final List<String> openResources = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{ 
			add("/swagger-ui.html");
			add("/configuration");
			add("/swagger-resources");
			add("/swagger-resources/configuration/ui"); 
			add("/swagger-ui.html/swagger-resources/configuration/ui");
			add("/swagger-resources/configuration/security");
			add("/api-docs");
			add("/v2/api-docs");
			add("/auth/v2.0/login");
			add("/auth/v2.0/wx");
			add("/auth/v2.0/send"); 
			add("/error");
			add("/prefectures/v2.0/map/list"); 
			add("/prefectures/v2.0/free/list"); 
			add("/prefectures/v2.0/strategy");
			add("/citys/v2.0/list"); 
			add("/callback/v2.0/wechat/order");
			add("/callback/v2.0/alipay/order");
			add("/callback/v2.0/apple/order");
		}
	}; 
	@Autowired
	private RedisService redisService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception { 
		String uri = request.getRequestURI(); 
		log.info("url:{}",uri);
		String ip = request.getRemoteAddr(); 
		if(openResources.contains(uri)) {
			return true;
		}  
		String key = UserCache.getCacheKey(request);  
		log.info("ip:{},token:{}",ip,key);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key);  
		if(ru==null) {
			response.setStatus(200);
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("application/json; charset=utf-8");  
			PrintWriter  pw = null;
			try{ 
				pw = response.getWriter(); 
				ResponseEntity<?> re = ResponseEntity.fail(StatusEnum.USER_APP_NO_LOGIN, request); 
				pw.write(JsonUtil.toJson(re));  
				pw.flush(); 
			}finally{
				if(pw!=null){
					pw.close();
				}
			} 
			return false; 
		}
		return true;
		
	}
}
