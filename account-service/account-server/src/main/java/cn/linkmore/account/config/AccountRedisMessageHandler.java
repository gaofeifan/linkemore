package cn.linkmore.account.config;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.XmlWebApplicationContext;

import cn.linkmore.redis.RedisMessageHandler;



/**
 * Redis消息处理类
 * @author liwenlong
 * @version 2.0
 *
 */

@Component
public class AccountRedisMessageHandler implements RedisMessageHandler {

	@Override
	public void handle(String message) {
	}

}
