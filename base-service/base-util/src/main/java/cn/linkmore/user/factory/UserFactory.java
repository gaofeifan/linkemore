package cn.linkmore.user.factory;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.bean.common.security.CacheUser;

/**
 * 用户工厂接口
 * @author   GFF
 * @Date     2019年2月13日
 * @Version  v2.0
 */
public interface UserFactory {

	public static final String MINI = "mini:";
	
	/**
	 * @Description  创建token的rediskey
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public String createTokenRedisKey(String token,String os);
	public String createTokenRedisKey(HttpServletRequest request);
	
	/**
	 * @Description  创建userId的rediskey
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public String createUserIdRedisKey(Long userId , String os);
	
	/**
	 * @Description  创建openid的rediskey
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public String createOpenIdRedisKey(String openId,String os);
}
