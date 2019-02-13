package cn.linkmore.user.factory;

import cn.linkmore.bean.common.Constants.RedisKey;

public class StaffUserFactory implements UserFactory {

	private StaffUserFactory() {};
	public static UserFactory getInstance() {
		return LazyStaffUserFactory.userFactory;
	}
	
	@Override
	public String createUserRedisKey(String token, String os) {
		String key ;
		switch (os) {
		case "1":
		case "2":
			key = RedisKey.STAFF_STAFF_AUTH_USER.key;
			break;
		case "0":
		default:
			key = RedisKey.STAFF_STAFF_AUTH_USER.key+MINI;
		}
		return key += token;
	}

	private static class LazyStaffUserFactory{
		private static StaffUserFactory userFactory = new StaffUserFactory();
	}
}
