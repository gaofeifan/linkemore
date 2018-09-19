package cn.linkmore.enterprise.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.enterprise.controller.staff.request.AssignStallRequestBean;
import cn.linkmore.enterprise.controller.staff.request.OrderOperateRequestBean;
import cn.linkmore.enterprise.controller.staff.request.SraffReqConStall;
import cn.linkmore.enterprise.controller.staff.request.StallOperateRequestBean;
import cn.linkmore.enterprise.dao.cluster.StaffPrefectureClusterMapper;
import cn.linkmore.enterprise.dao.master.StaffPrefectureMasterMapper;
import cn.linkmore.enterprise.entity.StallOperateLog;
import cn.linkmore.enterprise.service.StaffPrefectureService;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.response.ResOrderOperateLog;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.prefecture.client.StallBatteryLogClient;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.client.StallOperateLogClient;
import cn.linkmore.prefecture.request.ReqControlLock;
import cn.linkmore.prefecture.request.ReqStall;
import cn.linkmore.prefecture.request.ReqStallOperateLog;
import cn.linkmore.prefecture.response.ResStallBatteryLog;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.redis.RedisLock;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.TokenUtil;

@Service
public class StaffPrefectureServiceImpl implements StaffPrefectureService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RedisLock redisLock;

	@Autowired
	private RedisService redisService;

	@Autowired
	private StallClient stallClient;

	@Autowired
	private OrderClient orderClient;

	@Autowired
	private StallOperateLogClient stallOperateLogClient;

	@Autowired
	private StallBatteryLogClient stallBatteryLogClient;

	@Autowired
	private StaffPrefectureClusterMapper staffPrefectureClusterMapper;

	@Autowired
	private StaffPrefectureMasterMapper staffPrefectureMasterMapper;

	private static final int TIMEOUT = 30 * 1000;

	/**
	 * 升锁与降锁
	 */
	@Override
	public void control(SraffReqConStall reqOperatStall, HttpServletRequest request) {
		CacheUser user = (CacheUser) this.redisService
				.get(RedisKey.STAFF_STAFF_AUTH_CODE.key + TokenUtil.getKey(request));
		if (user == null) {
			throw new BusinessException(StatusEnum.USER_APP_NO_LOGIN);
		}
		String userid = user.getId().toString();
		long time = System.currentTimeMillis() + TIMEOUT;
		String robkey = RedisKey.MANAGER_STALL.key + reqOperatStall.getStallId();
		if (!redisLock.lock(String.valueOf(robkey), String.valueOf(time))) {
			log.info("no get it,wait a moment");
			throw new BusinessException(StatusEnum.STALL_HIVING_DO);
		}
		String reskey = (reqOperatStall.getState() == 1 ? RedisKey.MANAGER_STALL_DOWN.key
				: RedisKey.MANAGER_STALL_UP.key);
		StringBuilder sb = new StringBuilder(reskey).append(userid);
		redisService.set(String.valueOf(sb), reqOperatStall.getStallId());
		ReqControlLock reqc = new ReqControlLock();
		reqc.setStallId(reqOperatStall.getStallId());
		reqc.setStatus(reqOperatStall.getState());
		reqc.setKey(reskey);
		stallClient.controllock(reqc);
	}

	/**
	 * 释放车位
	 */
	@Override
	public void releaseStall(Long stallId, HttpServletRequest request) {
		CacheUser user = (CacheUser) this.redisService
				.get(RedisKey.STAFF_STAFF_AUTH_CODE.key + TokenUtil.getKey(request));
		if (user == null) {
			throw new BusinessException(StatusEnum.USER_APP_NO_LOGIN);
		}
		// 查询车位是否空闲
		ResStallEntity stall = stallClient.findById(stallId);
		if (stall == null) {
			throw new BusinessException(StatusEnum.STALL_UNKNOW_TYPE);
		}
		// 查询订单有无订单
		ResUserOrder orders = orderClient.findStallLatest(stallId);
		if (orders != null && orders.getStatus().intValue() == orders.ORDERS_STATUS_UNPAY) {
			throw new BusinessException(StatusEnum.STALL_OPERATE_ORDERING);
		}
		// 查询车位状态
		Map<String, Object> map = new HashMap<>();
		map = stallClient.watch(stallId);
		if (map == null || map.isEmpty()) {
			throw new BusinessException(StatusEnum.STALL_LOCK_NO_UP);
		}
		if (!"200".equals(String.valueOf(map.get("code")))) {
			throw new BusinessException(StatusEnum.STALL_LOCK_NO_UP);
		}
		if (String.valueOf(map.get("status")).equals(2)) {
			throw new BusinessException(StatusEnum.STALL_LOCK_OFFLINE);
		}
		// 置为空闲
		stallClient.checkout(stallId);
	}

	/**
	 * 强制释放车位
	 */
	@Override
	public void forceReleaseStall(Long stallId, HttpServletRequest request) {
		CacheUser user = (CacheUser) this.redisService
				.get(RedisKey.STAFF_STAFF_AUTH_CODE.key + TokenUtil.getKey(request));
		if (user == null) {
			throw new BusinessException(StatusEnum.USER_APP_NO_LOGIN);
		}
		// 查询车位是否空闲
		ResStallEntity stall = stallClient.findById(stallId);
		if (stall == null) {
			throw new BusinessException(StatusEnum.STALL_UNKNOW_TYPE);
		}
		// 查询订单有无订单
		ResUserOrder orders = orderClient.findStallLatest(stallId);
		if (orders != null && orders.getStatus().intValue() == orders.ORDERS_STATUS_UNPAY) {
			throw new BusinessException(StatusEnum.STALL_OPERATE_ORDERING);
		}
		// 置为空闲
		stallClient.checkout(stallId);
	}

	/**
	 * 车位下线
	 */
	@Override
	public void offline(StallOperateRequestBean bean, HttpServletRequest request) {
		CacheUser user = (CacheUser) this.redisService
				.get(RedisKey.STAFF_STAFF_AUTH_CODE.key + TokenUtil.getKey(request));
		if (user == null) {
			throw new BusinessException(StatusEnum.USER_APP_NO_LOGIN);
		}
		ResStallEntity stall = stallClient.findById(bean.getStallId());
		if (stall.getStatus().intValue() > stall.STATUS_USED) {
			throw new BusinessException(StatusEnum.STALL_OPERATE_OFFLINED);
		}
		// 查询订单
		ResUserOrder orders = orderClient.findStallLatest(bean.getStallId());
		if (orders != null && orders.getStatus().intValue() == orders.ORDERS_STATUS_UNPAY) {
			throw new BusinessException(StatusEnum.STALL_OPERATE_ORDERING);
		}
		// 更换电池
		if (bean.getRemarkId() == 1) {
			ResStallBatteryLog sbl = new ResStallBatteryLog();
			sbl.setAdminId(user.getId());
			sbl.setAdminName(user.getMobile());
			sbl.setCreateTime(new Date());
			sbl.setTotalNum(Integer.valueOf(0));
			sbl.setVoltage(0d);
			sbl.setStallId(stall.getId());
			stallBatteryLogClient.save(sbl);
		}
		// 更新车位状态
		ReqStall reqStall = new ReqStall();
		reqStall.setId(bean.getStallId());
		reqStall.setStatus(reqStall.STATUS_OUTLINE);
		stallClient.update(reqStall);
		// 插入记录
		ReqStallOperateLog sol = new ReqStallOperateLog();
		sol.setCreateTime(new Date());
		sol.setOperation(StallOperateLog.OPERATION_ONLINE);
		sol.setOperatorId(user.getId());
		sol.setSource(StallOperateLog.SOURCE_APP);
		sol.setStallId(bean.getStallId());
		sol.setRemarkId(bean.getRemarkId());
		sol.setRemark(bean.getRemark());
		sol.setStatus(StallOperateLog.STATUS_TRUE);
		stallOperateLogClient.save(sol);
		// 去除redis
		this.redisService.remove("freelock_key:" + stall.getPreId(), new Object[] { stall.getLockSn() });
	}

	/**
	 * 车位上线
	 * 
	 * @return
	 */
	@Override
	public void online(StallOperateRequestBean bean, HttpServletRequest request) {
		CacheUser user = (CacheUser) this.redisService
				.get(RedisKey.STAFF_STAFF_AUTH_CODE.key + TokenUtil.getKey(request));
		if (user == null) {
			throw new BusinessException(StatusEnum.USER_APP_NO_LOGIN);
		}
		ResStallEntity stall = stallClient.findById(bean.getStallId());
		if (stall.getStatus().intValue() != stall.STATUS_OUTLINE && stall.getStatus() != stall.STATUS_FAULT) {
			throw new BusinessException(StatusEnum.STALL_AlREADY_CONTROL);
		}
		ResUserOrder orders = orderClient.findStallLatest(bean.getStallId());
		if (orders != null && orders.getStatus().intValue() == orders.ORDERS_STATUS_UNPAY) {
			throw new BusinessException(StatusEnum.STALL_OPERATE_ORDERING);
		}
		Map<String, Object> map = new HashMap<>();
		map = stallClient.watch(bean.getStallId());
		if (map == null || map.isEmpty()) {
			throw new BusinessException(StatusEnum.STALL_LOCK_NO_UP);
		}
		if ("200".equals(String.valueOf(map.get("code")))) {
			if (String.valueOf(map.get("status")).equals(2)) {
				throw new BusinessException(StatusEnum.STALL_LOCK_OFFLINE);
			}
			ReqStallOperateLog sol = new ReqStallOperateLog();
			sol.setCreateTime(new Date());
			sol.setOperation(StallOperateLog.OPERATION_ONLINE);
			sol.setOperatorId(user.getId());
			sol.setSource(StallOperateLog.SOURCE_APP);
			sol.setStallId(bean.getStallId());
			sol.setRemarkId(bean.getRemarkId());
			sol.setRemark(bean.getRemark());
			sol.setStatus(StallOperateLog.STATUS_TRUE);
			stallOperateLogClient.save(sol);

			ReqStall reqStall = new ReqStall();
			reqStall.setId(bean.getStallId());
			reqStall.setStatus(reqStall.STATUS_FREE);
			stallClient.update(reqStall);
		} else {
			throw new BusinessException(StatusEnum.STALL_LOCK_NO_UP);
		}
	}

	/**
	 * 挂起订单
	 */
	@Override
	public void suspend(OrderOperateRequestBean oorb, HttpServletRequest request) {
		CacheUser user = (CacheUser) this.redisService
				.get(RedisKey.STAFF_STAFF_AUTH_CODE.key + TokenUtil.getKey(request));
		if (user == null) {
			throw new BusinessException(StatusEnum.USER_APP_NO_LOGIN);
		}
		// 查订单
		ResUserOrder order = orderClient.findOrderById(oorb.getOrderId());
		if (order == null) {
			throw new BusinessException(StatusEnum.ORDER_OPERATE_NULLORDER); // 订单不存在
		}
		if (!order.getStallId().equals(oorb.getStallId())) {
			throw new BusinessException(StatusEnum.ORDER_OPERATE_NULLSTALL); // 车位不匹配
		}
		if (!order.getStallId().equals(oorb.getStallId())) {
			throw new BusinessException(StatusEnum.ORDER_OPERATE_NULLSTALL);
		}
		if (order.getStatus().intValue() != order.ORDERS_STATUS_UNPAY) {
			throw new BusinessException(StatusEnum.ORDER_OPERATE_NOUNPAID); // //非预约中订单
		}
		// 关闭订单
		// 关闭订单
		Map<String, Object> map = new HashMap<>();
		map.put("endTime", new Date());
		map.put("updateTime", new Date());
		map.put("statusTime", new Date());
		map.put("statusHistory", 2);
		map.put("status", 7);
		map.put("id", oorb.getOrderId());
		orderClient.updateClose(map);
		// 更新详情
		map.clear();
		map.put("endTime", new Date());
		map.put("ordNo", order.getOrderNo());
		orderClient.updateDetail(map);
		// 插入订单记录
		ResOrderOperateLog ool = new ResOrderOperateLog();
		ool.setCreateTime(new Date());
		ool.setOrderId(oorb.getOrderId());
		ool.setOperatorId(user.getId());
		ool.setSource(ool.ADMIN);
		ool.setStallId(oorb.getStallId());
		ool.setOperation(ool.ORDER_ClOSE);
		ool.setRemark(oorb.getRemark());
		ool.setRemarkId(oorb.getRemarkId());
		ool.setStatus(ool.SUCCESS);
		orderClient.savel(ool);
		// 查车位
		ResStallEntity stall = stallClient.findById(order.getStallId());
		// 更新车位
		ReqStall reqStall = new ReqStall();
		reqStall.setId(oorb.getStallId());
		reqStall.setStatus(reqStall.STATUS_FREE);
		reqStall.setBindOrderStatus(reqStall.ORDER_ClOSE);
		stallClient.update(reqStall);
		// 更新redis
		this.redisService.remove("freelock_key:" + stall.getPreId(), new Object[] { stall.getLockSn() });
	}

	/**
	 * 关闭订单
	 */
	@Override
	public void close(OrderOperateRequestBean oorb, HttpServletRequest request) {
		CacheUser user = (CacheUser) this.redisService
				.get(RedisKey.STAFF_STAFF_AUTH_CODE.key + TokenUtil.getKey(request));
		if (user == null) {
			throw new BusinessException(StatusEnum.USER_APP_NO_LOGIN);
		}
		// 查订单
		ResUserOrder order = orderClient.findOrderById(oorb.getOrderId());
		if (order == null) {
			throw new BusinessException(StatusEnum.ORDER_OPERATE_NULLORDER); // 订单不存在
		}
		if (!order.getStallId().equals(oorb.getStallId())) {
			throw new BusinessException(StatusEnum.ORDER_OPERATE_NULLSTALL); // 车位不匹配
		}
		if (!order.getStallId().equals(oorb.getStallId())) {
			throw new BusinessException(StatusEnum.ORDER_OPERATE_NULLSTALL);
		}
		if (order.getStatus().intValue() != order.ORDERS_STATUS_UNPAY) {
			throw new BusinessException(StatusEnum.ORDER_OPERATE_NOUNPAID); // //非预约中订单
		}
		// 关闭订单
		Map<String, Object> map = new HashMap<>();
		map.put("endTime", new Date());
		map.put("updateTime", new Date());
		map.put("statusTime", new Date());
		map.put("statusHistory", 2);
		map.put("status", 7);
		map.put("id", oorb.getOrderId());
		orderClient.updateClose(map);
		// 更新详情
		map.clear();
		map.put("endTime", new Date());
		map.put("ordNo", order.getOrderNo());
		orderClient.updateDetail(map);
		// 插入订单记录
		ResOrderOperateLog ool = new ResOrderOperateLog();
		ool.setCreateTime(new Date());
		ool.setOrderId(oorb.getOrderId());
		ool.setOperatorId(user.getId());
		ool.setSource(ool.ADMIN);
		ool.setStallId(oorb.getStallId());
		ool.setOperation(ool.ORDER_ClOSE);
		ool.setRemark(oorb.getRemark());
		ool.setRemarkId(oorb.getRemarkId());
		ool.setStatus(ool.SUCCESS);
		orderClient.savel(ool);
		// 查车位
		ResStallEntity stall = stallClient.findById(order.getStallId());
		// 更新车位
		ReqStall reqStall = new ReqStall();
		reqStall.setId(oorb.getStallId());
		reqStall.setStatus(reqStall.STATUS_FREE);
		reqStall.setBindOrderStatus(reqStall.ORDER_ClOSE);
		stallClient.update(reqStall);
		// 更新redis
		this.redisService.remove("freelock_key:" + stall.getPreId(), new Object[] { stall.getLockSn() });
	}

	/**
	 * 指定车位
	 */
	@Override
	public void assign(AssignStallRequestBean bean, HttpServletRequest request) {

	}
}
