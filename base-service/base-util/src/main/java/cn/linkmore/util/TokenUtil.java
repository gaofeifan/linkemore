package cn.linkmore.util;

import java.util.UUID;

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
			sessionId = request.getSession().getId().replaceAll("-", "");
		}
		return sessionId;
	}
	
	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public static String getUUID(HttpServletRequest request) {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * @Description  获取key
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public static String createKey(Short type,HttpServletRequest request) {
		if(type == null || type !=  1) {
			return request.getSession().getId().replaceAll("-", "");
		}
		return getUUID(request);
	}
	
}	
