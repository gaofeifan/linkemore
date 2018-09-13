package cn.linkmore.order.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import cn.linkmore.order.dao.cluster.OrdersClusterMapper;
import cn.linkmore.order.dao.cluster.UnusualOrderClusterMapper;
import cn.linkmore.order.dao.master.UnusualOrderMasterMapper;
import cn.linkmore.order.entity.UnusualOrder;
import cn.linkmore.order.response.ResOrderOps;
import cn.linkmore.order.response.ResUnusualOrder;
import cn.linkmore.order.service.UnusualOrderService;

@Service
public class UnusualOrderServiceImpl implements UnusualOrderService {
	@Resource
	private UnusualOrderClusterMapper unusualOrderClusterMapper;
	@Resource
	private UnusualOrderMasterMapper unusualOrderMasterMapper;
	@Resource
	private OrdersClusterMapper ordersClusterMapper;
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public void updateAppointmentTimeoutList() {

		this.unusualOrderMasterMapper.deleteByCategory((short) 0);
		List<ResOrderOps> list = ordersClusterMapper.appointmentTimeoutList();
		List<Long> stallList = new ArrayList<Long>();
		List<UnusualOrder> usualOrderList = new ArrayList<UnusualOrder>();
		for (ResOrderOps order : list) {
			stallList.add(order.getStallId());
			UnusualOrder unusualOrder = new UnusualOrder();
			unusualOrder.setStallId(order.getStallId());
			unusualOrder.setStallName(order.getStallName());
			unusualOrder.setPrefectureId(order.getPreId());
			unusualOrder.setOrderId(order.getId());
			unusualOrder.setOrderNo(order.getOrderNo());
			unusualOrder.setOrderStartTime(order.getBeginTime());
			unusualOrder.setOrderEndTime(order.getEndTime());
			unusualOrder.setOrderStatus(order.getStatus().shortValue());
			unusualOrder.setLockDownStatus(order.getLockDownStatus());
			unusualOrder.setLockDownTime(order.getLockDownTime());
			unusualOrder.setOrderOperateTime(order.getStatusTime());
			unusualOrder.setCreateTime(order.getCreateTime());
			unusualOrder.setOrderMobile(order.getUsername());
			unusualOrder.setCategory((short) 0);// 0 预约30分钟未降锁
			usualOrderList.add(unusualOrder);
		}
		log.info("appoint time out list {}", JSON.toJSON(stallList));
		if (CollectionUtils.isNotEmpty(usualOrderList)) {
			this.unusualOrderMasterMapper.insertBatch(usualOrderList);
		}
	}

	public void updateLockDownTimeoutList() {
		
		this.unusualOrderMasterMapper.deleteByCategory((short) 1);
		List<ResOrderOps> list = ordersClusterMapper.lockDownTimeoutList();
		List<Long> stallList = new ArrayList<Long>();
		List<UnusualOrder> usualOrderList = new ArrayList<UnusualOrder>();
		for (ResOrderOps order : list) {
			stallList.add(order.getStallId());
			UnusualOrder unusualOrder = new UnusualOrder();
			unusualOrder.setStallId(order.getStallId());
			unusualOrder.setStallName(order.getStallName());
			unusualOrder.setPrefectureId(order.getPreId());
			unusualOrder.setOrderId(order.getId());
			unusualOrder.setOrderNo(order.getOrderNo());
			unusualOrder.setOrderStartTime(order.getBeginTime());
			unusualOrder.setOrderEndTime(order.getEndTime());
			unusualOrder.setOrderStatus(order.getStatus().shortValue());
			unusualOrder.setLockDownStatus(order.getLockDownStatus());
			unusualOrder.setLockDownTime(order.getLockDownTime());
			unusualOrder.setOrderOperateTime(order.getStatusTime());
			unusualOrder.setCreateTime(order.getCreateTime());
			unusualOrder.setOrderMobile(order.getUsername());
			unusualOrder.setCategory((short) 1);// 1 降锁超4个小时
			usualOrderList.add(unusualOrder);
		}
		log.info("lockdown time out list {}", JSON.toJSON(stallList));
		if (CollectionUtils.isNotEmpty(usualOrderList)) {
			this.unusualOrderMasterMapper.insertBatch(usualOrderList);
		}
	}

	public void updateUnreleaseCloseOrders() {
		List<UnusualOrder> unusualOrderList = this.unusualOrderClusterMapper.selectByCategory();
		List<Long> stallIds = new ArrayList<Long>();
		for (UnusualOrder order : unusualOrderList) {
			stallIds.add(order.getStallId());
		}
		log.info("appoint and lockdown exception list {}", JSON.toJSON(stallIds));

		this.unusualOrderMasterMapper.deleteByCategory((short) 3);
		List<ResOrderOps> list = ordersClusterMapper.unreleaseCloseOrders();

		List<Long> stallList = new ArrayList<Long>();
		List<Long> conflictStallList = new ArrayList<Long>();
		List<UnusualOrder> usualOrderList = new ArrayList<UnusualOrder>();
		for (ResOrderOps order : list) {
			UnusualOrder unusualOrder = new UnusualOrder();
			unusualOrder.setStallId(order.getStallId());
			unusualOrder.setStallName(order.getStallName());
			unusualOrder.setPrefectureId(order.getPreId());
			unusualOrder.setOrderId(order.getId());
			unusualOrder.setOrderNo(order.getOrderNo());
			unusualOrder.setOrderStartTime(order.getBeginTime());
			unusualOrder.setOrderEndTime(order.getEndTime());
			unusualOrder.setOrderStatus(order.getStatus().shortValue());
			unusualOrder.setLockDownStatus(order.getLockDownStatus());
			unusualOrder.setLockDownTime(order.getLockDownTime());
			unusualOrder.setOrderOperateTime(order.getStatusTime());
			unusualOrder.setCreateTime(order.getCreateTime());
			unusualOrder.setOrderMobile(order.getUsername());
			unusualOrder.setCategory((short) 3);// 3关闭订单未释放车位
			if(!stallIds.contains(order.getStallId())){
				stallList.add(order.getStallId());
				usualOrderList.add(unusualOrder);
			}else{
				conflictStallList.add(order.getStallId());
			}
		}
		log.info("unrelease close order list - conflict {}", JSON.toJSON(conflictStallList));
		log.info("unrelease close order list {}", JSON.toJSON(stallList));
		if (CollectionUtils.isNotEmpty(usualOrderList)) {
			this.unusualOrderMasterMapper.insertBatch(usualOrderList);
		}
	}

	public void updateUnreleaseHangOrders() {
		List<UnusualOrder> unusualOrderList = this.unusualOrderClusterMapper.selectByCategory();
		List<Long> stallIds = new ArrayList<Long>();
		for (UnusualOrder order : unusualOrderList) {
			stallIds.add(order.getStallId());
		}
		log.info("appoint and lockdown exception list {}", JSON.toJSON(stallIds));

		this.unusualOrderMasterMapper.deleteByCategory((short) 4);
		List<ResOrderOps> list = ordersClusterMapper.unreleaseHangOrders();
		List<Long> stallList = new ArrayList<Long>();
		List<Long> conflictStallList = new ArrayList<Long>();
		List<UnusualOrder> usualOrderList = new ArrayList<UnusualOrder>();
		for (ResOrderOps order : list) {
			UnusualOrder unusualOrder = new UnusualOrder();
			unusualOrder.setStallId(order.getStallId());
			unusualOrder.setStallName(order.getStallName());
			unusualOrder.setPrefectureId(order.getPreId());
			unusualOrder.setOrderId(order.getId());
			unusualOrder.setOrderNo(order.getOrderNo());
			unusualOrder.setOrderStartTime(order.getBeginTime());
			unusualOrder.setOrderEndTime(order.getEndTime());
			unusualOrder.setOrderStatus(order.getStatus().shortValue());
			unusualOrder.setLockDownStatus(order.getLockDownStatus());
			unusualOrder.setLockDownTime(order.getLockDownTime());
			unusualOrder.setOrderOperateTime(order.getStatusTime());
			unusualOrder.setCreateTime(order.getCreateTime());
			unusualOrder.setOrderMobile(order.getUsername());
			unusualOrder.setCategory((short) 4);// 4挂起订单未释放车位
			if(!stallIds.contains(order.getStallId())){
				stallList.add(order.getStallId());
				usualOrderList.add(unusualOrder);
			}else{
				conflictStallList.add(order.getStallId());
			}
		}
		log.info("unrelease hang order list - conflict {}", JSON.toJSON(conflictStallList));
		log.info("unrelease hang order list {}", JSON.toJSON(stallList));
		if (CollectionUtils.isNotEmpty(usualOrderList)) {
			this.unusualOrderMasterMapper.insertBatch(usualOrderList);
		}
	}

	@Override
	public void updateUnreleaseCompleteOrders() {

		this.unusualOrderMasterMapper.deleteByCategory((short) 2);
		List<ResOrderOps> list = ordersClusterMapper.unreleaseCompleteOrders();
		List<Long> stallList = new ArrayList<Long>();
		List<UnusualOrder> usualOrderList = new ArrayList<UnusualOrder>();
		for (ResOrderOps order : list) {
			stallList.add(order.getStallId());
			UnusualOrder unusualOrder = new UnusualOrder();
			unusualOrder.setStallId(order.getStallId());
			unusualOrder.setStallName(order.getStallName());
			unusualOrder.setPrefectureId(order.getPreId());
			unusualOrder.setOrderId(order.getId());
			unusualOrder.setOrderNo(order.getOrderNo());
			unusualOrder.setOrderStartTime(order.getBeginTime());
			unusualOrder.setOrderEndTime(order.getEndTime());
			unusualOrder.setOrderStatus(order.getStatus().shortValue());
			unusualOrder.setLockDownStatus(order.getLockDownStatus());
			unusualOrder.setLockDownTime(order.getLockDownTime());
			unusualOrder.setOrderOperateTime(order.getStatusTime());
			unusualOrder.setCreateTime(order.getCreateTime());
			unusualOrder.setOrderMobile(order.getUsername());
			unusualOrder.setCategory((short) 2);// 2完成订单未释放车位
			usualOrderList.add(unusualOrder);
		}

		log.info("unrelease complete order list {}", JSON.toJSON(stallList));
		if (CollectionUtils.isNotEmpty(usualOrderList)) {
			this.unusualOrderMasterMapper.insertBatch(usualOrderList);
		}
	}

	@Override
	public List<ResUnusualOrder> findList(Map<String, Object> map) {
		return this.unusualOrderClusterMapper.findList(map);
	}
	
	
}
