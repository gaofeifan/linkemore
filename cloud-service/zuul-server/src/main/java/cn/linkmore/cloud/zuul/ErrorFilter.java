package cn.linkmore.cloud.zuul;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 错误处理，可以通过设置ErrorHandler自定义返回错误信息和错误码
 * 
 * @author liwenlong
 * @version 2.0
 *
 */
public class ErrorFilter extends ZuulFilter {
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public static final String KEY_ERROR = "hasError";

	private ResponseHandler errorHandler;

	@Override
	public boolean shouldFilter() {
		return true;
	}
	private static final String CONTENTTYPE = "application/json;charset=UTF-8";
	@Override
	public Object run() {

		try {
			RequestContext context = getCurrentContext(); 
			context.getResponse().setCharacterEncoding(CONTENTTYPE);
			log.error("error", context.getThrowable());
			if (null != errorHandler) {
				context.setResponseStatusCode(errorHandler.getResponseCode());
				String body = errorHandler.getResponseBody(null, context.getThrowable());
				context.setResponseBody(body); 
			} else {
				context.setResponseStatusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				context.setResponseBody(context.getThrowable().getMessage());
			}
			context.remove("throwable");
			context.put(KEY_ERROR, true);
		} catch (Exception e) {
			rethrowRuntimeException(e);
		}
		return null;
	}

	@Override
	public String filterType() {
		return "error";
	}

	@Override
	public int filterOrder() {
		return FilterConstants.SEND_ERROR_FILTER_ORDER - 1;
	}

	public ResponseHandler getErrorHandler() {
		return errorHandler;
	}

	public void setErrorHandler(ResponseHandler errorHandler) {
		this.errorHandler = errorHandler;
	}
}
