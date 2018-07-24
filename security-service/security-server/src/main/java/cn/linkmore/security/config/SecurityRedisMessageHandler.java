package cn.linkmore.security.config;

import org.springframework.stereotype.Component;

import cn.linkmore.redis.RedisMessageHandler;


/**
 * Redis消息处理类
 * @author liwenlong
 * @version 2.0
 *
 */

@Component
public class SecurityRedisMessageHandler implements RedisMessageHandler {

	@Override
	public void handle(String message) {
		// TODO Auto-generated method stub

	}

}
