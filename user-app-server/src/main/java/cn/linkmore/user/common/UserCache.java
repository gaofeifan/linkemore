package cn.linkmore.user.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
/**
 * 缓存用户
 * @author liwenlong
 * @version 2.0
 *
 */
public class UserCache {
	public static String getCacheKey(HttpServletRequest request) {
		String sessionId = "";
		Object sessionIdAttribute = request.getAttribute(Constant.ACCESS_TOKEN_HEADER_NAME);
		if (sessionIdAttribute != null) {
			sessionId = sessionIdAttribute.toString();
		}
		if (StringUtils.isBlank(sessionId)) {
			sessionId = request.getHeader(Constant.ACCESS_TOKEN_HEADER_NAME);
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
