package cn.linkmore.user.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.request.ReqOrderCreate;
import cn.linkmore.order.request.ReqOrderDown;
import cn.linkmore.order.request.ReqOrderSwitch;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.redis.RedisService;
import cn.linkmore.user.common.UserCache;
import cn.linkmore.user.request.ReqBooking;
import cn.linkmore.user.request.ReqOrderStall;
import cn.linkmore.user.request.ReqSwitch;
import cn.linkmore.user.response.ResOrder;
import cn.linkmore.user.response.ResUser;
import cn.linkmore.user.service.OrderService;
/**
 * Service实现 - 预约
 * @author liwenlong
 * @version 2.0
 *
 */
@Service
public class OrderServiceImpl implements OrderService { 
	
	@Autowired
	private OrderClient orderClient;
	
	@Autowired
	private RedisService redisService;
	 

	@Override
	public void create(ReqBooking rb, HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key);
		ReqOrderCreate roc = new ReqOrderCreate();
		roc.setPrefectureId(rb.getPrefectureId());
		roc.setPlateId(rb.getPlateId());
		roc.setUserId(ru.getId());
		this.orderClient.create(roc); 
	}

	@Override
	public ResOrder current(HttpServletRequest request) { 
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		ResUserOrder ruo =  this.orderClient.last(ru.getId()); 
		ResOrder order = null;
		if(ruo!=null) {
			order = new ResOrder();
			order.copy(ruo);
		}
		
		return order;
	}

	@Override
	public void down(ReqOrderStall ros, HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key);  
		ReqOrderDown rod = new ReqOrderDown();
		rod.setOrderId(ros.getOrderId());
		rod.setStallId(ros.getStallId());
		rod.setUserId(ru.getId());
		this.orderClient.down(rod);
		
	}

	@Override
	public void switchStall(ReqSwitch rs, HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		ReqOrderSwitch ros = new ReqOrderSwitch();
		ros.setCauseId(rs.getCauseId());
		ros.setOrderId(rs.getOrderId());
		ros.setRemark(rs.getRemark());
		ros.setUserId(ru.getId());
		this.orderClient.switchStall(ros);
	}

}
