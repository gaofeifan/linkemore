package cn.linkmore.cloud.zuul.filter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.TokenUtil;
 

public class TokenFilter extends ZuulFilter {

	private  final Logger log = LoggerFactory.getLogger(this.getClass()); 
	private static final String API_APP_PATH="/app/";
	private static final String API_ENT_PATH="/ent/";
	private static final String API_STAFF_PATH="/staff/"; 
	private static final String API_OPS_PATH="/admin/";
	private static final String API_FEIGN_PATH="/feign/"; 
	private static final String SWAGGER_PATH = "/webjars/";
	
	private static final String CONTENTTYPE = "text/json;charset=UTF-8";
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
			 
			add("/app/versions/v2.0/current");
			add("/app/mini/v2.0/login");
			add("/app/auth/v2.0/login"); 
			add("/app/auth/v2.0/wechat");
			add("/app/auth/v2.0/send");  

			
			add("/enterprise/ent/auth/login");
			add("/enterprise/ent/auth/send");
			add("/feign/stall/exc/stall-exc-cause");  
			
			add("/app/prefectures/v2.0/map/list"); 
			add("/app/prefectures/v2.0/free/list"); 
			add("/app/prefectures/v2.0/strategy");
			add("/feign/stall/v2.0/update-brand");
			
			add("/app/brand-pre/v2.0/map/list"); 
			add("/app/brand-pre/v2.0/free/list"); 
			add("/app/brand-pre/v2.0/strategy");
			
			add("/app/brands/v2.0/brand-ad"); 
			add("/app/brands/v2.0/brand-apply"); 
			add("/app/brands/v2.0/brand-pre-ad");
			
			add("/app/citys/v2.0/list"); 
			add("/app/callback/v2.0/wechat-mini/order");
			add("/app/callback/v2.0/wechat/order");
			add("/app/callback/v2.0/alipay/order");
			add("/app/callback/v2.0/apple/order");
			 
			add("/ent/mini/v2.0/login");
			add("/ent/auth/v2.0/login");
			add("/ent/auth/v2.0/send");  
			
			add("/attach/image_upload"); 
		}
	};  
	
	@Autowired
	private RedisService redisService;
	 

    @Override
    public String filterType() {
        return "pre"; // 可以在请求被路由之前调用
    }

    @Override
    public int filterOrder() {
        return 0; // filter执行顺序，通过数字指定 ,优先级为0，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        return true;// 是否执行该过滤器，此处为true，说明需要过滤
    }  
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext(); 
        HttpServletRequest request = ctx.getRequest();  
        String ip = request.getRemoteAddr(); 
        String url = request.getRequestURI();  
        String uri = url.substring(url.indexOf("/",5));
		String key = TokenUtil.getKey(request);
		log.info("ip:{},token:{}",ip,key);
		log.info(uri); 
		CacheUser cu = (CacheUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key);  
		log.info("json:{}",JsonUtil.toJson(cu));
        if (url.contains(SWAGGER_PATH)||(openResources.contains(uri)||cu!=null)) {
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);
            return null;
        }else if (uri.contains(API_OPS_PATH)) {
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);
            return null;
        } else if(uri.contains(API_APP_PATH)){  
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(200);
            ctx.setResponseBody(JsonUtil.toJson(ResponseEntity.fail(StatusEnum.USER_APP_NO_LOGIN, request)));
            ctx.getResponse().setContentType(CONTENTTYPE);
            ctx.set("isSuccess", false);
            return null;
        }else if(uri.contains(API_OPS_PATH)){  
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(200); 
            ctx.setResponseBody(JsonUtil.toJson(ResponseEntity.fail(StatusEnum.USER_APP_NO_LOGIN, request)));
            ctx.getResponse().setContentType(CONTENTTYPE);
            ctx.set("isSuccess", false);
            return null;
        }else if(uri.contains(API_FEIGN_PATH)){  
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(200);
            ctx.setResponseBody(JsonUtil.toJson(ResponseEntity.fail(StatusEnum.USER_APP_NO_LOGIN, request)));
            ctx.getResponse().setContentType(CONTENTTYPE);
            ctx.set("isSuccess", false);
            return null;
        }else if(uri.contains(API_ENT_PATH)) {
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);
        	return null;
        }else{  
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(200);
            ctx.setResponseBody(JsonUtil.toJson(ResponseEntity.fail(StatusEnum.USER_APP_NO_LOGIN, request)));
            ctx.getResponse().setContentType(CONTENTTYPE);
            ctx.set("isSuccess", false);
            return null;
        }
    }   
}