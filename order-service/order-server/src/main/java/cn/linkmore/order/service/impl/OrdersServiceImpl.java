package cn.linkmore.order.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cn.linkmore.account.client.VehicleMarkClient;
import cn.linkmore.bean.common.Constants.OperateStatus;
import cn.linkmore.bean.common.Constants.OrderStatus;
import cn.linkmore.bean.common.Constants.PushType;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.common.security.Token;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.order.dao.cluster.OrdersClusterMapper;
import cn.linkmore.order.dao.master.OrdersMasterMapper;
import cn.linkmore.order.request.ReqOrderCreate;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.order.service.OrdersService;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.redis.RedisService;
import cn.linkmore.third.client.PushClient;
import cn.linkmore.third.request.ReqPush;
import cn.linkmore.util.JsonUtil;
/**
 * Service实现 -订单
 * @author liwenlong
 * @version 2.0 
 */
@Service
public class OrdersServiceImpl implements OrdersService { 
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	 
	@Autowired
	private PrefectureClient prefectureClient;
	
	@Autowired
	private StallClient stallClient;
	
	@Autowired
	private OrdersClusterMapper ordersClusterMapper;
	@Autowired
	private OrdersMasterMapper orderMasterMapper;  
	@Autowired
	private VehicleMarkClient vehicleMarkClient;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private PushClient pushClient;
	
	@Async
	public void create(ReqOrderCreate orc) { 
		
	}
	@Override
	public ResUserOrder latest(Long userId) {
		ResUserOrder orders = this.ordersClusterMapper.findUserLatest(userId); 
		return orders;
	}
	@Override
	public ResUserOrder detail(Long orderId) {
		ResUserOrder orders = this.ordersClusterMapper.findDetail(orderId); 
		return orders;
	}
	@Async
	private void push(String uid,String title,String content,PushType type,ResponseEntity<?> res) {
		Token token = (Token)this.redisService.get(RedisKey.USER_APP_AUTH_TOKEN+uid.toString());
		ReqPush rp = new ReqPush();
		rp.setAlias(uid);
		rp.setTitle(title); 
		rp.setContent(content);
		rp.setClient(token.getClient());
		rp.setType(type);
		rp.setData(JsonUtil.toJson(res)); 
		this.pushClient.push(rp);
	}
	
	@Override
	@Async
	public void down(Long id) {
		ResUserOrder order = this.ordersClusterMapper.findDetail(id);
		Boolean flag = false; 
		ResponseEntity<?> response = null;
		StatusEnum se = null;
		if(order.getStatus()==OrderStatus.UNPAID.value) {
			flag = this.stallClient.downlock(order.getStallId()); 
		} else {
			se = StatusEnum.ORDER_LOCKDOWN_UNPAY;
		}
		Map<String,Object> param = new HashMap<String,Object>();
		if(flag) {
			response =  ResponseEntity.success(null, null); 
		}else {
			if(se!=null) {
				response = ResponseEntity.fail(se, null);
			}else {
				response = ResponseEntity.fail(StatusEnum.ORDER_LOCKDOWN_FAIL, null);
			} 
		} 
		param.put("lockDownStatus",flag?OperateStatus.SUCCESS.status:OperateStatus.FAILURE.status);
		param.put("lockDownTime", new Date());
		param.put("orderId", order.getId());
		this.orderMasterMapper.updateLockStatus(param);
		this.push(order.getUserId().toString(), "预约降锁通知",flag? "车位锁降下成功":"车位锁降下失败",PushType.LOCK_DOWN_NOTICE, response);
	}
}
