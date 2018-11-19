package cn.linkmore.order.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.linkmore.order.service.RedirectService;

@Service
public class RedirectServiceImpl implements RedirectService{

	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	/**
	 *识别客户端并获取用户信息
	 */
	@Override
	public String distributed(HttpServletRequest request) {
		String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase(); 
		String paytype = null;
		if (ua.indexOf("micromessenger") > 0) {       // 是微信浏览器
			paytype="WX";
			log.info("paytype->" + paytype);
		} else if(ua.indexOf("alipayclient") > 0){        //支付宝浏览器
			paytype="ZFB";
			log.info("paytype->" + paytype);
		}else{      
			log.info("paytype->" + paytype);        //其他浏览器
		}
		//跳转微信服务器
		String appid = "wxd167b1783e872df6";
		String redirect_uri = "http://test.linkmoreparking.cn/api/order/h5/a";
		String url2 = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+redirect_uri+"&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";

		return url2;
	}
	

	@Override
	public String auth(Map<String, String> params) {
	    String code = params.get("code");
		//跳转网页
		String AppSecret ="637c41ce97f055b9afd52a4344322285";
		String url = "redirect:http://pay.linkmoreparking.cn/?openid=111111&type="+1111;
		return code;
	}

	
}
