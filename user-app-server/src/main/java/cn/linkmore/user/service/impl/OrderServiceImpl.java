package cn.linkmore.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.common.Constants.OrderStatus;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.request.ReqOrderCreate;
import cn.linkmore.order.request.ReqOrderDown;
import cn.linkmore.order.request.ReqOrderSwitch;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.redis.RedisService;
import cn.linkmore.user.common.UserCache;
import cn.linkmore.user.request.ReqBooking;
import cn.linkmore.user.request.ReqOrderStall;
import cn.linkmore.user.request.ReqSwitch;
import cn.linkmore.user.response.ResOrder;
import cn.linkmore.user.response.ResOrderDetail;
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
	
	@Autowired
	private PrefectureClient prefectureClient;
	
	@Autowired
	private StallClient stallClient;
	 

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
		if(ruo!=null&&(ruo.getStatus()==OrderStatus.UNPAID.value||ruo.getStatus()==OrderStatus.SUSPENDED.value)) {
			order = new ResOrder();
			order.copy(ruo);
			ResPrefectureDetail pre = this.prefectureClient.findById(order.getPrefectureId());
			if(pre!=null) {
				order.setPreLongitude(pre.getLongitude());
				order.setPreLatitude(pre.getLatitude());
				order.setPrefectureAddress(pre.getAddress());
				order.setGuideImage(pre.getRouteGuidance());
				order.setGuideRemark(pre.getRouteDescription());
				order.setPrefectureName(pre.getName());
			}
			ResStallEntity stall = this.stallClient.findById(order.getStallId());
			if(stall!=null) {
				order.setStallName(stall.getStallName());
				order.setBluetooth("E0FE28BF8161|DAB57585F519|E6B0C368D285");
			}
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

	@Override
	public List<ResOrder> list(Long start, HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		List<ResUserOrder> list =this.orderClient.list(ru.getId(), start);
		List<ResOrder> res = new ArrayList<ResOrder>();
		ResOrder ro = null;
		for(ResUserOrder ruo:list) {
			ro = new ResOrder();
			ro.copy(ruo);
			res.add(ro);
		}
		return res;
	}

	@Override
	public ResOrderDetail detail(Long orderId, HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		ResOrderDetail detail = null;
		return detail;
	}

}
