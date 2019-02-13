package cn.linkmore.user.factory;

import cn.linkmore.bean.common.Constants.RedisKey;

public class EntUserFactory implements UserFactory {

	public static UserFactory getInstance() {
		return userFactory;
	}
	
	@Override
	public String createUserRedisKey(String token, String os) {
		String key = null;
		switch (os) {
		case "1":
		case "2":
			key = RedisKey.STAFF_ENT_AUTH_USER.key;
			break;
		case "0":
		default:
			key = RedisKey.STAFF_ENT_AUTH_USER.key + MINI;
			break;
		}
		return key += token;
	}

	private static EntUserFactory userFactory = null;
	static{
		userFactory = new EntUserFactory();
	}
	private EntUserFactory() {};
}
