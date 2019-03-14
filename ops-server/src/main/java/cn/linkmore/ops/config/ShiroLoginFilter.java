package cn.linkmore.ops.config;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

public class ShiroLoginFilter extends FormAuthenticationFilter {

	 @Override
	    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		 /*
		 HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		 httpServletResponse.setCharacterEncoding("UTF-8");
         httpServletResponse.setContentType("application/json");
         response.getWriter().write("{'message':'没权限'}");
         */
		 WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
         return false;
	 }
}
