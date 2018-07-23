package cn.linkmore.cloud.zuul;

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
	private static final String API_WS_PATH = "/ws/";
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
			add("/app/prefectures/v2.0/map/list"); 
			add("/app/prefectures/v2.0/free/list"); 
			add("/app/prefectures/v2.0/strategy");
			
			add("/app/brand_pre/v2.0/map/list"); 
			add("/app/brand_pre/v2.0/free/list"); 
			add("/app/brand_pre/v2.0/strategy");
			
			add("/app/brands/v2.0/brand_ad"); 
			add("/app/brands/v2.0/brand_apply"); 
			add("/app/brands/v2.0/brand_pre_ad");
			
			add("/app/citys/v2.0/list"); 
			add("/app/callback/v2.0/wechat-mini/order");
			add("/app/callback/v2.0/wechat/order");
			add("/app/callback/v2.0/alipay/order");
			add("/app/callback/v2.0/apple/order");
			
			 
			add("/ent/mini/v2.0/login");
			add("/ent/auth/v2.0/login");
			add("/ent/auth/v2.0/send");  
			
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
        String upgradeHeader = request.getHeader("Upgrade");
        if (null == upgradeHeader) {
            upgradeHeader = request.getHeader("upgrade");
        }
        if (null != upgradeHeader && "websocket".equalsIgnoreCase(upgradeHeader)) {
        	log.info("websokcet running...");
            ctx.addZuulRequestHeader("connection", "Upgrade");
        }
        String ip = request.getRemoteAddr(); 
        String url = request.getRequestURI();  
        String uri = url.substring(url.indexOf("/",5));
		String key = TokenUtil.getKey(request);
		log.info("ip:{},token:{}",ip,key);
		log.info(uri); 
		CacheUser cu = (CacheUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key);  
		log.info("json:{}",JsonUtil.toJson(cu));
        if (url.contains(SWAGGER_PATH)||(openResources.contains(uri)||cu!=null)||url.contains(API_WS_PATH)) {
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);
            return null;
        }else if (uri.contains(API_OPS_PATH)) {
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);
            return null;
        } else if (uri.contains(API_FEIGN_PATH)) {
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