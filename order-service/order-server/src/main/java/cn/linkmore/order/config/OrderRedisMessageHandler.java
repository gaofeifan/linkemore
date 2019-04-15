package cn.linkmore.order.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.common.Constants.ClientSource;
import cn.linkmore.bean.common.Constants.PushType;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.common.security.Token;
import cn.linkmore.notice.client.UserSocketClient;
import cn.linkmore.redis.RedisMessageHandler;
import cn.linkmore.redis.RedisNotice;
import cn.linkmore.redis.RedisService;
import cn.linkmore.third.client.PushClient;
import cn.linkmore.third.request.ReqPush;
import cn.linkmore.user.factory.AppUserFactory;
import cn.linkmore.user.factory.UserFactory;
import cn.linkmore.util.JsonUtil;

/**
 * Redis消息处理类
 * @author liwenlong
 * @version 2.0
 *
 */
@Component
public class OrderRedisMessageHandler implements RedisMessageHandler { 
	private UserFactory appUserFactory = AppUserFactory.getInstance();
	@Autowired
	private PushClient pushClient;
	
	@Autowired
	private UserSocketClient userSocketClient;
	
	@Autowired
	private RedisService redisService;
	
	
	private void send(String uid,String title,String content,PushType type,Boolean status) {
		Token token = (Token)this.redisService.get(RedisKey.USER_APP_AUTH_TOKEN.key+uid.toString());
		if(token.getClient().intValue()==ClientSource.WXAPP.source) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("title", title);
			map.put("type", type.id);
			map.put("content", content);
			map.put("status", status);
			CacheUser cu = (CacheUser)this.redisService.get(appUserFactory.createTokenRedisKey(token.getAccessToken(), null));
			userSocketClient.push(JsonUtil.toJson(map),cu.getOpenId());
		}else {
			ReqPush rp = new ReqPush();
			rp.setAlias(uid);
			rp.setTitle(title); 
			rp.setContent(content);
			rp.setClient(token.getClient());
			rp.setType(type);
			rp.setData(status.toString()); 
			this.pushClient.push(rp);
		}
		
	}
	private final static String ORDER_TITLE = "订单操作";
	private final static String CLOSE_CONTENT = "订单已被管理员关闭"; 
	private final static String SUSPEND_CONTENT = "订单已被管理员挂起"; 
	private void closeOrder(Long userId) { 
		send( userId.toString(), ORDER_TITLE, CLOSE_CONTENT, PushType.ORDER_STAFF_CLOSED_NOTICE, true);
	}
	
	private void suspendOrder(Long userId) { 
		send( userId.toString(), ORDER_TITLE, SUSPEND_CONTENT, PushType.ORDER_STAFF_SUSPEND_NOTICE, true);
	}
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void handle(String message) {
		log.info("redis message:{}",message);
		RedisNotice notice = JsonUtil.toObject(message, RedisNotice.class);
		if("ORDER_CLOSED_NOTICE".equals(notice.getType())) {
			this.closeOrder(notice.getBizId());
		}else if("ORDER_SUSPEND_NOTICE".equals(notice.getType())) {
			this.suspendOrder(notice.getBizId());
		} 
	}

}
