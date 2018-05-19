package cn.linkmore.user.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.request.ReqOrderCreate;
import cn.linkmore.redis.RedisService;
import cn.linkmore.user.common.UserCache;
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
	public void create(Long prefectureId, HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER+key);
		ReqOrderCreate roc = new ReqOrderCreate();
		roc.setPrefectureId(prefectureId);
		roc.setUserId(ru.getId());
		this.orderClient.create(roc); 
	}

	@Override
	public ResOrder current(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		return null;
	}

}
