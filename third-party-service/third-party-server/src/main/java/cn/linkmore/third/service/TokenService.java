package cn.linkmore.third.service;

import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.common.Constants;
import cn.linkmore.redis.RedisService;
import cn.linkmore.third.config.WechatConfig;
import cn.linkmore.third.response.ResToken;
import cn.linkmore.third.wechat.core.WeChat;
import cn.linkmore.third.wechat.vo.Token;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.ObjectUtils;

@Service
public class TokenService {
	
	private  Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource
	private WechatConfig wechatConfig;
	
	@Resource
	private RedisService redisService;

	public Token getToken() {
		Token token = null;
		String json = (String) redisService.get(Constants.RedisKey.WECHAT_TOKEN_KEY.key); 
		if(StringUtils.isNotBlank(json)){
			try{
				token = JsonUtil.toObject(json, Token.class);
			}catch(Exception e){ 
				StringBuffer sb = new StringBuffer();
				StackTraceElement[] stackArray = e.getStackTrace();  
		        for (int i = 0; i < stackArray.length; i++) {  
		            StackTraceElement element = stackArray[i];  
		            sb.append(element.toString() + "\n");  
		        }   
				log.info(sb.toString());
			} 
		}
		if(null == token) {
			token = this.resetToken();
		}
		return token;
	}  
	
	public Token resetToken(){
		Token token = WeChat.getToken(wechatConfig.getAppId(), wechatConfig.getAppSecret()); 
		log.info("获取access_token成功，有效时长{}秒 token:{}", token.getExpiresIn(),token.getAccessToken());  
		redisService.set(Constants.RedisKey.WECHAT_TOKEN_KEY.key, JsonUtil.toJson(token), Long.valueOf(Constants.RedisKey.WECHAT_TOKEN_EXPIRE.key));
		return token;
	}

	public ResToken resetToken(String appid, String appsecret, String key) {
		Token token = WeChat.getToken(appid, appsecret);
		ResToken object = ObjectUtils.copyObject(token,new ResToken());
		log.info("获取access_token成功，有效时长{}秒 token:{}", token.getExpiresIn(),token.getAccessToken());  
		redisService.set(key, JsonUtil.toJson(object), Long.valueOf(Constants.RedisKey.WECHAT_TOKEN_EXPIRE.key));
		return object;
	}
}
