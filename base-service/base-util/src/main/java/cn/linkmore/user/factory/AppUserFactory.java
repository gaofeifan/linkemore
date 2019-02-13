package cn.linkmore.user.factory;

import cn.linkmore.bean.common.Constants.RedisKey;

public class AppUserFactory implements UserFactory {

	private static AppUserFactory userFactory = null;

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
	public String createUserRedisKey(String token, String os) {
		String key = "";
		switch (os) {
		case "1":
		case "2":
			key = RedisKey.USER_APP_AUTH_USER.key;
			break;
		case "0":
		default:
			key = RedisKey.USER_APP_AUTH_USER.key+MINI;
			break;
		}
		return key+token;
	}
	
	private AppUserFactory() {};
}
