package cn.linkmore.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import cn.linkmore.bean.common.Constants;

public class TokenUtil {
	public static String getKey(HttpServletRequest request) {
		String sessionId = "";
		Object sessionIdAttribute = request.getAttribute(Constants.ACCESS_TOKEN_HEADER_NAME);
		if (sessionIdAttribute != null) {
			sessionId = sessionIdAttribute.toString();
		}
		if (StringUtils.isBlank(sessionId)) {
			sessionId = request.getHeader(Constants.ACCESS_TOKEN_HEADER_NAME);
		}
		if (StringUtils.isBlank(sessionId)) {
			sessionId = request.getParameter("token");
		}
		if (StringUtils.isBlank(sessionId)) {
			sessionId = request.getSession().getId();
		}
		return sessionId;
	}
}	
