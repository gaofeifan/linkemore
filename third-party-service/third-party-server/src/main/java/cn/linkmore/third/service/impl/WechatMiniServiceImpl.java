package cn.linkmore.third.service.impl;

import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.third.config.WechatMiniConfig;
import cn.linkmore.third.pay.wxmini.WxMiniPay;
import cn.linkmore.third.request.ReqWechatMiniOrder;
import cn.linkmore.third.response.ResMiniSession;
import cn.linkmore.third.response.ResWechatMiniOrder;
import cn.linkmore.third.service.WechatMiniService;
import cn.linkmore.util.HttpUtil;
import cn.linkmore.util.JsonUtil;

@Service
public class WechatMiniServiceImpl implements WechatMiniService {
	
	private static final String SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session"; 
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WechatMiniConfig WechatMiniConfig; 
	
	@Override
	public ResMiniSession getSession(String code) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("appid", WechatMiniConfig.getAppId());
		param.put("secret", WechatMiniConfig.getAppSecret());
		param.put("js_code", code);
		log.info("param:{}",JsonUtil.toJson(param));
		String json = HttpUtil.sendGet(SESSION_URL, param);
		log.info("result:{}",json);
		ResMiniSession rms = JsonUtil.toObject(json, ResMiniSession.class);
		return rms;
	}

	@Override
	public ResWechatMiniOrder order(ReqWechatMiniOrder wechat) {
		ResWechatMiniOrder order = null;
		try { 
			order = WxMiniPay.wxpay(wechat.getAddress(), wechat.getNumber(), wechat.getAmount().toString(), wechat.getOpenId());
			log.info("order:{}",JsonUtil.toJson(order));
		} catch (Exception e) {
			 e.printStackTrace();
		}  
		return order;
	} 
}
