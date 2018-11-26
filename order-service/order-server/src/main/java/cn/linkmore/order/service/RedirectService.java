package cn.linkmore.order.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface RedirectService {

	String	distributed(HttpServletRequest request);
	
	String	auth(Map<String, String> params);
	
}
