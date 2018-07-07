package cn.linkmore.monitor.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.common.Constants;
import cn.linkmore.monitor.config.WechatConfig;
import cn.linkmore.monitor.entity.core.HttpMethod;
import cn.linkmore.monitor.entity.core.TemplateMsg;
import cn.linkmore.monitor.entity.core.WeChat;
import cn.linkmore.monitor.entity.vo.Token;
import cn.linkmore.monitor.entity.vo.WechatUserList;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.JsonUtil;
import net.sf.json.JSONObject;

/**
 * @author   GFF
 * @Date     2018年7月4日
 * @Version  v2.0
 */
@Service
public class WechatService {

	@Resource
	private RedisService redisService;
	@Resource
	private WechatConfig wechatConfig;
	
	private  Logger log = LoggerFactory.getLogger(getClass());
	
	public static final String TEMPLATE_ID = "O6svjWCImwFTkExpIYKxpGIKUe08OShwQGUT479VUXY";
	
	public Token getToken() {
		Token token = null;
		String json = (String) redisService.get(Constants.RedisKey.WECHAT_TOKEN_KEY_MONITOR.key); 
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
			token = this.resetToken(wechatConfig.getAppId(),wechatConfig.getAppSecret(),Constants.RedisKey.WECHAT_TOKEN_KEY_MONITOR.key);
		}
		return token;
	}
	
	private Token resetToken(String appid, String appsecret, String key) {
		Token token = WeChat.getToken(appid, appsecret);
		log.info("获取access_token成功，有效时长{}秒 token:{}", token.getExpiresIn(),token.getAccessToken());  
		redisService.set(key, JsonUtil.toJson(token), Long.valueOf(Constants.RedisKey.WECHAT_TOKEN_EXPIRE.key));
		return token;
	}
	
	public void pushTemplateMsg(Object data) {
		TemplateMsg msg = new TemplateMsg();
		msg.setData(data);
		msg.setTemplateId(TEMPLATE_ID);
		Token token = this.getToken();
		WechatUserList userList = this.getUserList(token.getAccessToken());
		msg.setOpenId(userList.getOpenIds());
		this.pushTemplateMsg(msg,token.getAccessToken());
	}
	
	private void pushTemplateMsg(TemplateMsg msg,String token) {
		String url = WeChat.getTemplateMsg(token);
		Map<String, Object> map = new HashMap<>();
		map.put("template_id", msg.getTemplateId());
		map.put("topcolor", msg.getTopcolor());
		map.put("data", msg.getData());
		for (String openId : msg.getOpenId()) {
			map.put("touser", openId);
			String obj = JsonUtil.toJson(map);
			JSONObject json = WeChat.httpsRequest(url, HttpMethod.POST, obj);
			Boolean flag = false; 
			if (null != json &&json.get("errcode")!=null&& "0".equals(json.get("errcode").toString()) ){
				flag = true;
			}else if(null != json){
				flag = false;
			}
			log.info("return msg:"+json.toString()); 
			log.info("success:"+flag); 
		}
	}
	
	public WechatUserList getUserList(String token) {
		WechatUserList userList = null;
		String json = (String) redisService.get(Constants.RedisKey.WECHAT_GET_USER_LIST_MONITOR.key); 
		if(StringUtils.isNotBlank(json)){
			try{
				userList = JsonUtil.toObject(json, WechatUserList.class);
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
		if(userList == null) {
			String url = WeChat.getUserListUrl(token);
			JSONObject obj = WeChat.httpsRequest(url, HttpMethod.GET, null);
			Object total = obj.get("total");
			Object count = obj.get("count");
			Object data = obj.get("data");
			JSONObject openids = JSONObject.fromObject(data);
			List<String> ids = (List<String>) openids.get("openid");
			userList = new WechatUserList();
			userList.setCount(Integer.parseInt(count.toString()));
			userList.setTotal(Integer.parseInt(total.toString()));
			userList.setOpenIds(ids);
			redisService.set(Constants.RedisKey.WECHAT_GET_USER_LIST_MONITOR.key,JsonUtil.toJson(userList), Long.valueOf(Constants.RedisKey.WECHAT_GET_USER_LIST_EXPIRE.key));
		}
		return userList;
	}
	
	
	
}
