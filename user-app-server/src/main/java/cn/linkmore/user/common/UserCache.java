package cn.linkmore.user.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import cn.linkmore.bean.common.Constants;

/**
 * 缓存用户
 * 
 * @author liwenlong
 * @version 2.0
 *
 */
public class UserCache {
	public static String getCacheKey(HttpServletRequest request) {
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
