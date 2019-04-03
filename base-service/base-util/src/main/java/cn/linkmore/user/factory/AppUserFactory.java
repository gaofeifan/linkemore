package cn.linkmore.user.factory;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.util.TokenUtil;

public class AppUserFactory implements UserFactory {

	private static AppUserFactory userFactory = null;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public static UserFactory getInstance() {
		if(userFactory == null) {
			synchronized (AppUserFactory.class) {
				if(userFactory == null) {
					userFactory = new AppUserFactory();
				}
			}
		}
		return userFactory;
	}
	
	@Override
	public String createTokenRedisKey(String token, String os) {
		String key = "";
		os = os == null ? "0" : os;
		switch (os) {
		case "1":
		case "2":
			key = RedisKey.USER_APP_AUTH_USER.key;
			break;
		case "0":
		case "4":
		default:
			key = RedisKey.USER_APP_AUTH_USER.key+MINI;
			break;
		}
		log.info("createTokenRedisKey os = {}, key = {}, token={}", os, key, token);
		return key+token;
	}
	
	
	@Override
	public String createUserIdRedisKey(Long userId, String os) {
		String key = "";
		os = os == null ? "0" : os;
		switch (os) {
		case "1":
		case "2":
			key = RedisKey.USER_APP_AUTH_TOKEN.key;
			break;
		case "0":
		case "4":
		default:
			key = RedisKey.USER_APP_AUTH_TOKEN.key+MINI;
			break;
		}
		log.info("createUserIdRedisKey os = {}, key = {}, userId={}", os, key, userId);
		return key+userId;
	}

	@Override
	public String createOpenIdRedisKey(String openId, String os) {
		String key = "";
		os = os == null ? "0" : os;
		switch (os) {
		case "1":
		case "2":
			key = RedisKey.USER_WXAPP_AUTH_TOKEN.key;
			break;
		case "0":
		case "4":
		default:
			key = RedisKey.USER_WXAPP_AUTH_TOKEN.key+MINI;
			break;
		}
		return key+openId;
	}

	
	
	@Override
	public String createTokenRedisKey(HttpServletRequest request) {
		return createTokenRedisKey(TokenUtil.getKey(request), request.getHeader("os"));
	}


	private AppUserFactory() {};
}
