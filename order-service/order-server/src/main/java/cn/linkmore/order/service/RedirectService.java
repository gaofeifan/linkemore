package cn.linkmore.order.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.third.request.ReqH5Token;
import cn.linkmore.third.response.ResH5Degree;

public interface RedirectService {

	String	distributed(Map<String, String> params,HttpServletRequest request);
	
	String	auth(Map<String, String> params);
	
	ResH5Degree	 Openid(ReqH5Token reqH5Token);
	
}
