package cn.linkmore.cloud.zuul.filter;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.util.JsonUtil;
/**
 * OpenAPI过滤校验
 * @author llh
 *
 */
public class OpenApiFilter extends ZuulFilter {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String METHOD_GET = "GET";
    private final String METHOD_POST = "POST";
	private final String CONTENTTYPE = "text/json;charset=UTF-8";
	private final HashMap<String, String > SECRET_MAP = new HashMap<String, String>(){
		private static final long serialVersionUID = -2264544236455598813L;
		{
			put("linkmoretest","123456");	//内部测试
			put("139a997d14be400f89bb5eef2670998b","d7fdb7dccb9d4bf8b2b89763dab9e787"); 
		}
	};
	
	//@Autowired
	//private RedisService redisService;

	@Override
	public String filterType() {
		return "pre"; // 可以在请求被路由之前调用
	}

	@Override
	public int filterOrder() {
		return 1; // filter执行顺序，通过数字指定 ,优先级为0，数字越大，优先级越低
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		return ctx.get("isSuccess")!=null? (boolean) ctx.get("isSuccess"):true;
		//return true;// 是否执行该过滤器，此处为true，说明需要过滤
	}

	private String getStr(Object o) {
		return o!=null?o.toString():null;
	}
	
	@Override
	public Object run(){
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
/*
        String appid = getStr(request.getAttribute("X-Access-OpenAPI-APPID"));
        String sign = getStr(request.getAttribute("X-Access-OpenAPI-SIGN"));
        String timestamp = getStr(request.getAttribute("X-Access-OpenAPI-TIMESTAMP"));
        log.info("appid:{},timestamp:{},sign:{}",appid,timestamp,sign);
        */
        response.setCharacterEncoding("utf-8");
        response.setContentType(CONTENTTYPE);
        ctx.setResponse(response);
        String method = request.getMethod();
        request.getParameterMap();
        String url = request.getRequestURL().toString();
        log.info("url:{}",url);
        Map<String,Object> params;
        switch (method) {
            case METHOD_GET:
                params = getRequestGetStr(request);
                break;
            case METHOD_POST:
                params = getRequestPostStr(request);
                break;
            default:
                params = null;
        }
        //参数是否为空
        if (params == null) {
        	filterOut(StatusEnum.VALID_EXCEPTION,ctx);         
            return null;
        }
        /*
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println(uuid);
        */
        System.out.println(System.currentTimeMillis());
        //验证appid
        String appid =getStr(params.get("appId"));
        if(org.apache.commons.lang3.StringUtils.isEmpty(appid) ) {
        	filterOut(StatusEnum.OPENAPI_APPID_NULL,ctx);
        	return null;
        }
        if(org.apache.commons.lang3.StringUtils.isEmpty(SECRET_MAP.get(appid)) ) {
        	filterOut(StatusEnum.OPENAPI_APPID_ERROR,ctx);
        	return null;
        }
        
        //验证签名
        String sign = getStr(params.get("sign"));
        if(org.apache.commons.lang3.StringUtils.isEmpty(sign) ) {
        	filterOut(StatusEnum.OPENAPI_SIGN_NULL,ctx);
        	return null;
        }
        if(!checkSign(params)) {
        	filterOut(StatusEnum.OPENAPI_SIGN_ERROR,ctx);
        	return null;
        }

        //验证时间戳
        if(org.apache.commons.lang3.StringUtils.isEmpty(getStr(params.get("timeStamp"))) ) {
        	filterOut(StatusEnum.OPENAPI_TIMESTAMP_ERROR,ctx);
        	return null;
        }
        long timestamp=0;
		try {
			timestamp = Long.parseLong( getStr(params.get("timeStamp")));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			filterOut(StatusEnum.OPENAPI_TIMESTAMP_ERROR,ctx);
        	return null;
		}

        if(System.currentTimeMillis()-timestamp>50000) {
        	filterOut(StatusEnum.OPENAPI_TIMESTAMP_TIMEOUT,ctx);
        	return null;
        }
        return null;
	}
	
	/**
	 * 过滤该请求，不对其进行路由
	 * @param eenum
	 * @param ctx
	 */
	private void filterOut(StatusEnum eenum ,RequestContext ctx) {
		 ctx.setSendZuulResponse(false);
         ctx.setResponseStatusCode(200);
         ctx.setResponseBody(JsonUtil.toJson(ResponseEntity.fail(eenum, ctx.getRequest())));
         ctx.set("isSuccess", false);
	}
	
	/**
	 * 签名验证
	 * @param params
	 * @return
	 */
	private boolean checkSign( Map<String,Object> params) {
		Map<String, Object> sortMap=sortMapByKey(params);
		String appid =getStr(sortMap.get("appId"));
	    String sign = getStr(sortMap.get("sign"));
	    String timestamp = getStr(sortMap.get("timeStamp"));
	    log.info("参数排序前:{}",params.toString());
	    log.info("参数排序后:{}",sortMap.toString());
	    sortMap.remove("sign");
	    String paramStr=paramsToStr(sortMap);
	    //System.out.println(paramStr);
	    //判断 md5(paramStr+key) == sign ?
	    if(org.apache.commons.lang3.StringUtils.equalsIgnoreCase( MD5(paramStr + SECRET_MAP.get(appid) ), sign)) {
	    	return true;
	    }else {
	    	return false;
	    }
	}

	private String MD5(String s) {
	    try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] bytes = md.digest(s.getBytes("utf-8"));
	        //将加密后的数据转换为16进制数字
	        String md5code = new BigInteger(1, bytes).toString(16);// 16进制数字
	        // 如果生成数字未满32位，需要前面补0
	        for (int i = 0; i < 32 - md5code.length(); i++) {
	            md5code = "0" + md5code;
	        }
	        return md5code;
	    }
	    catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

	private String paramsToStr(Map<String, Object> map) {
		StringBuilder str = new StringBuilder();
		for (Map.Entry<String, Object> entry : map.entrySet()) { 
		  str.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		  //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
		}
		
		return str.substring(0, str.length()-1).toString();
	}
	
	
	/**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public  Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Object> sortMap = new TreeMap<String, Object>();
        sortMap.putAll(map);
        return sortMap;
    }
    /**
     * 描述:获取 post 请求的 byte[] 数组
     * <pre>
     * 举例：
     * </pre>
     * @param request
     * @return
     * @throws IOException
     */
    public  byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
        int contentLength = request.getContentLength();
        if(contentLength<0){
            return null;
        }
        byte[] buffer = new byte[contentLength];
        for (int i = 0; i < contentLength;) {
            int readLen = request.getInputStream().read(buffer, i, contentLength - i);
            if (readLen == -1) {
                break;
          }
            i += readLen;
        }
        return buffer;
    }

	 /**
     * 描述:获取 post 请求内容
     * <pre>
     * 举例：
     * </pre>
     * @param request
     * @return
     * @throws IOException
     */
    public  Map<String, Object> getRequestPostStr(HttpServletRequest request) {
        try {
            byte[] buffer = getRequestPostBytes(request);
            String charEncoding = request.getCharacterEncoding();
            if (charEncoding == null) {
                charEncoding = "UTF-8";
            }
            String value = new String(buffer, charEncoding);
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            return  JSONObject.parseObject(value);
        } catch (IOException e) {
            return null;
        }
    }
    
	 /**
     * 描述:获取 get 请求内容
     * <pre>
     * 举例：
     * </pre>
     * @param request
     * @return
     * @throws IOException
     */
    public  Map<String, Object> getRequestGetStr(HttpServletRequest request) {
        String params = request.getQueryString();
        return transferMap(params);
    }
    /**
     * 将get参数转换成map
     * */
    public Map transferMap(String param) {
        if (StringUtils.isEmpty(param)) {
            return null;
        }
        String regex = "&";
        String[] params = param.split(regex);
        Map<String,Object> paramsMap = new HashMap<>(params.length);
        String[] data;
        String dataRegex = "=";
        for (String paramValue : params) {
            data = paramValue.split(dataRegex);
            if (data.length == 2) {
                paramsMap.put(data[0], data[1]);
            }
        }
        return paramsMap;
    }

	
}