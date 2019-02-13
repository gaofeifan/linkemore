package cn.linkmore.cloud.zuul.filter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 限流
 * @author alec
 * @date 2018/11/2
 */
@Component
public class RateLimiterFilter extends ZuulFilter{

    private static final RateLimiter RATE_LIMITER = RateLimiter.create(120);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;character=utf-8");
        ctx.setResponse(response);
        /**
         * 就相当于每调用一次tryAcquire()方法，令牌数量减1，当1000个用完后，那么后面进来的用户无法访问上面接口
        当然这里只写类上面一个接口，可以这么写，实际可以在这里要加一层接口判断。*/
        if (!RATE_LIMITER.tryAcquire()) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(200);
            ctx.setResponseBody("{}");
            return null;
        }
        return null;
    }
}
