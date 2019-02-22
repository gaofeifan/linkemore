package cn.linkmore.user.factory;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.util.TokenUtil;

/**
 * 物业版用户工厂
 * @author   GFF
 * @Date     2019年2月13日
 * @Version  v2.0
 */
public class EntUserFactory implements UserFactory {

	public static UserFactory getInstance() {
		return userFactory;
	}
	
	@Override
	public String createTokenRedisKey(String token, String os) {
		String key = null;
		os = os == null ? "0" : os;
		switch (os) {
		case "1":
		case "2":
//			break;
		case "0":
		default:
			key = RedisKey.STAFF_ENT_AUTH_USER.key;
//			key = RedisKey.STAFF_ENT_AUTH_USER.key + MINI;
			break;
		}
		return key + token;
	}

	
	@Override
	public String createUserIdRedisKey(Long userId, String os) {
		String key = null;
		os = os == null ? "0" : os;
		switch (os) {
		case "1":
		case "2":
//			break;
		case "0":
		default:
			key = RedisKey.STAFF_ENT_AUTH_TOKEN.key;
//			key = RedisKey.STAFF_ENT_AUTH_TOKEN.key + MINI;
			break;
		}
		return key + userId;
	}

	@Override
	public String createOpenIdRedisKey(String openId, String os) {
		String key = null;
		os = os == null ? "0" : os;
		switch (os) {
		case "1":
		case "2":
//			break;
		case "0":
		default:
			key = RedisKey.STAFF_WXAPP_AUTH_TOKEN.key;
//			key = RedisKey.STAFF_WXAPP_AUTH_TOKEN.key + MINI;
			break;
		}
		return key + openId;
	}


	@Override
	public String createTokenRedisKey(HttpServletRequest request) {
		return createTokenRedisKey(TokenUtil.getKey(request), request.getHeader("os"));
	}




	private static EntUserFactory userFactory = null;
	static{
		userFactory = new EntUserFactory();
	}
	private EntUserFactory() {};
}
