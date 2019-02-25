package cn.linkmore.user.factory;

public class FactoryTest {

	public static void main(String[] args) {
		String os = "0";
		String token = "32位字符串";
		String redisKey = null;
		//调用个人小程序
		redisKey = AppUserFactory.getInstance().createTokenRedisKey(token, os);
		//调用个人客户端
		os= "1";
		redisKey = AppUserFactory.getInstance().createTokenRedisKey(token, os);
		
		
		//调用物业
		redisKey = EntUserFactory.getInstance().createTokenRedisKey(token, os);
		os= "1";
		redisKey = EntUserFactory.getInstance().createTokenRedisKey(token, os);
		
		
		//调用管理
		redisKey = StaffUserFactory.getInstance().createTokenRedisKey(token, os);
		os= "1";
		redisKey = StaffUserFactory.getInstance().createTokenRedisKey(token, os);
		
		
		// 获取用户信息
		// User user = redisService.get(redisKey);
	}
}
