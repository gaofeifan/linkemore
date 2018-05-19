package cn.linkmore.user.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.common.Constants;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.redis.RedisService;
import cn.linkmore.user.common.UserCache;
import cn.linkmore.user.response.ResCoupon;
import cn.linkmore.user.service.CouponService;
/**
 * Service实现 - 停车券
 * @author liwenlong
 * @version 2.0
 *
 */
@Service
public class CouponServiceImpl implements CouponService {
	
	@Autowired
	private RedisService redisService;

	@Override
	public List<ResCoupon> paymentList(HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		Object cache = redisService.get(Constants.RedisKey.USER_APP_AUTH_USER.key+key);
		return null;
	}

	@Override
	public List<ResCoupon> usableList(HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		Object cache = redisService.get(RedisKey.USER_APP_AUTH_USER.key+key);
		
		return null;
	}
	
	
	
	
}
