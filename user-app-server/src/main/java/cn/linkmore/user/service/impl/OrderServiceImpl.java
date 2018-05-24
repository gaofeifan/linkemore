package cn.linkmore.user.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.common.Constants.OrderStatus;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.request.ReqOrderCreate;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.redis.RedisService;
import cn.linkmore.user.common.UserCache;
import cn.linkmore.user.request.ReqBooking;
import cn.linkmore.user.request.ReqOrderStall;
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
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER+key);
		ResUserOrder ruo =  this.orderClient.userLatest(ru.getId()); 
		ResOrder order = new ResOrder();
		order.copy(ruo);
		return order;
	}

	@Override
	public void down(ReqOrderStall ros, HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER+key); 
		ResUserOrder ruo = this.orderClient.detail(ros.getOrderId());
		if(ruo!=null) { 
			if(ru.getId().intValue()!=ruo.getUserId().intValue()||ruo.getStallId().intValue()!=ros.getStallId().intValue()) {
				throw new BusinessException(StatusEnum.ORDER_LOCKDOWN_FAIL);
			}
			if(ruo.getStatus()!=OrderStatus.UNPAID.value) {
				throw new BusinessException(StatusEnum.ORDER_LOCKDOWN_UNPAY);
			}
			this.orderClient.down(ruo.getId());
		}else {
			throw new BusinessException(StatusEnum.ORDER_LOCKDOWN_FAIL);
		}
		
	}

}
