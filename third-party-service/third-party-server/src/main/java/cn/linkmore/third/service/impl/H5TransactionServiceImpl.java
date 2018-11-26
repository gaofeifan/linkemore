package cn.linkmore.third.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.linkmore.third.service.H5TransactionService;

@Service
public class H5TransactionServiceImpl implements H5TransactionService{
	
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
		String url = "redirect:http://pay.linkmoreparking.cn/?openid=111111&type="+paytype;
		return url;
	}


	
	
}
