package cn.linkmore.enterprise.interceptor;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.TokenUtil;


/**
 * 管理版权限拦截器
 * @author   GFF
 * @Date     2018年7月25日
 * @Version  v2.0
 */
@Component
public class AccessControlInterceptor extends HandlerInterceptorAdapter {
	 @Resource
	 private RedisService redisService;
	 
	 private  Logger log = LoggerFactory.getLogger(getClass());

	 public static final String ENT = "/ent/";
	private static final List<String> noLoginResources = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;

		{
			// swagger相关资源不需要登录
			add("/swagger-ui.html");
			add("/configuration");
			add("/swagger-resources");
			add("/api-docs");
			add("/v2/api-docs");
			add("/ent/auth/login");
			add("/ent/auth/send");
			add("/error");
		}
	};

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		log.info("uri:{}",uri);
		for (String resource : noLoginResources) {
			if (uri.startsWith(resource)) {
				return true;
			}
		}
		if(!uri.contains(ENT)) {
			return true;
		}
		CacheUser user = null;
		try{
			String key = TokenUtil.getKey(request);
			user = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key);
		}catch(BusinessException e){
		}
		if (user == null) {
			response.setStatus(200);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
				pw.write(JsonUtil.toJson(ResponseEntity.fail(StatusEnum.USER_APP_NO_LOGIN, request)));
				pw.flush();
			} finally {
				if (pw != null) {
					pw.close();
				}
			}
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

}
