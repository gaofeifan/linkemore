package cn.linkmore.order.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.linkmore.order.controller.h5.request.ReqPayParm;
import cn.linkmore.order.controller.h5.request.ReqSerch;
import cn.linkmore.order.controller.h5.response.ResPayParm;
import cn.linkmore.order.controller.h5.response.ResSearch;
import cn.linkmore.third.request.ReqH5Token;
import cn.linkmore.third.response.ResH5Degree;

public interface RedirectService {

	String	distributed(Map<String, String> params,HttpServletRequest request);
	
	String	auth(Map<String, String> params);
	
	ResH5Degree	 Openid(ReqH5Token reqH5Token);
	
	ResPayParm  wxparm(ReqPayParm reqPayParm);
	
	ResSearch getOrder(ReqSerch reqSerch);
	
	void wxNotify(String payResult,HttpServletResponse response);
	
	void aliNotify(Map<String, Object> params,HttpServletResponse response);
	
	String aliparm(ReqPayParm reqPayParm);
	
}
