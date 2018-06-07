package cn.linkmore.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.common.Constants.OrderStatus;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.coupon.client.CouponClient;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.redis.RedisService;
import cn.linkmore.user.common.UserCache;
import cn.linkmore.user.response.ResCoupon;
import cn.linkmore.user.response.ResUser;
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
	
	
	@Autowired
	private OrderClient orderClient;
	
	@Autowired
	private CouponClient couponClient;

	@Override
	public List<ResCoupon> paymentList(HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		ResUserOrder order  = this.orderClient.last(ru.getId());
		List<ResCoupon> rcs = null;
		if(order!=null&&(order.getStatus().intValue()==OrderStatus.UNPAID.value||order.getStatus().intValue()==OrderStatus.SUSPENDED.value)) {
			List<cn.linkmore.coupon.response.ResCoupon> list = this.couponClient.order(ru.getId(), order.getId());
			rcs = new ArrayList<ResCoupon>();
			ResCoupon r = null;
			for(cn.linkmore.coupon.response.ResCoupon rc:list) {
				r = new ResCoupon();	
				r.copy(rc);
				rcs.add(r);
			}
		} 
		return rcs;
	}

	@Override
	public List<ResCoupon> usableList(HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		List<cn.linkmore.coupon.response.ResCoupon> list = this.couponClient.enable(ru.getId());
		List<ResCoupon> rcs = new ArrayList<ResCoupon>();
		ResCoupon r = null;
		for(cn.linkmore.coupon.response.ResCoupon rc:list) {
			r = new ResCoupon();	
			r.copy(rc);
			rcs.add(r);
		}
		return rcs;
	}
	
	
	
	
}
