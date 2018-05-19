package cn.linkmore.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cn.linkmore.order.dao.cluster.OrdersClusterMapper;
import cn.linkmore.order.dao.master.OrdersMasterMapper;
import cn.linkmore.order.request.ReqOrderCreate;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.order.service.OrdersService;
import cn.linkmore.prefecture.client.PrefectureClient;
/**
 * Service实现 -订单
 * @author liwenlong
 * @version 2.0 
 */
@Service
public class OrdersServiceImpl implements OrdersService { 
	@Autowired
	private PrefectureClient prefectureClient;
	@Autowired
	private OrdersClusterMapper ordersClusterMapper;
	@Autowired
	private OrdersMasterMapper orderMasterMapper;  
	
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
}
