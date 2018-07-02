package cn.linkmore.third.service.impl;

import java.io.IOException;
import java.util.Date;

import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.third.config.AppWechatConfig;
import cn.linkmore.third.pay.PayConstants;
import cn.linkmore.third.pay.wxpay.WeixinPay;
import cn.linkmore.third.request.ReqAppWechatOrder;
import cn.linkmore.third.response.ResAppWechatOrder;
import cn.linkmore.third.response.ResFans;
import cn.linkmore.third.service.AppWechatService;
import cn.linkmore.third.wechat.HttpsRequest;
import cn.linkmore.util.JsonUtil;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

@Service
public class AppWechatServiceImpl implements AppWechatService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AppWechatConfig appWechatConfig;
	
	private static final String GET = "GET";
	
	//网页授权OAuth2.0获取token
	private static final String GET_OAUTH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
 
	//网页授权OAuth2.0获取用户信息
	private static final String GET_OAUTH_USERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

	
	//网页授权OAuth2.0获取token
	private  String getOAuthTokenUrl(String code ){
		return String.format(GET_OAUTH_TOKEN, appWechatConfig.getAppId(), appWechatConfig.getAppSecret(), code);
	}
	
	//网页授权OAuth2.0获取用户信息
	public static String getOAuthUserinfoUrl(String token ,String openid){
		return String.format(GET_OAUTH_USERINFO, token, openid);
	}
	
	
	/**
	 * 获取OAuth2.0 UserInfo
	 * @param appId
	 * @param appSecret
	 * @param code
	 * @return
	 */
	@Override
	public ResFans getWechatFans(String code) {
		String tokenUrl = getOAuthTokenUrl(code);
		JSONObject json = HttpsRequest.parse(tokenUrl, GET, null); 
		if (null != json && json.get("errcode") == null) {
			try {
				String openid = json.getString("openid");
				String token = json.getString("access_token");
				String url = getOAuthUserinfoUrl(token, openid);
				json =  HttpsRequest.parse(url,GET,null);
			} catch (JSONException e) {
				json = null;
			}
		} 
		log.info("get wechat fans json:{}",JsonUtil.toJson(json));
		ResFans rf = null;
		if(json!=null) {
			String openid = json.getString("openid"); 
			String nickname = json.getString("nickname");
			String headimgurl = json.getString("headimgurl"); 
			String unionid = json.getString("unionid"); 
			rf = new ResFans();
			rf.setId(openid);
			rf.setHeadurl(headimgurl);
			rf.setNickname(nickname);
			rf.setUnionid(unionid);
			rf.setCreateTime(new Date());
			rf.setStatus((short)1);
			rf.setRegisterStatus((short)0); 
		} 
		return rf;
	}

	@Override
	public ResAppWechatOrder order(ReqAppWechatOrder wechat) throws JDOMException, IOException {
		return WeixinPay.orderPay(wechat.getAddress(), wechat.getNumber(),
				PayConstants.BODY_ORDER, wechat.getAmount()); 
	} 
}

