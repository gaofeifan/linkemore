package cn.linkmore.order.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.linkmore.account.client.UserClient;
import cn.linkmore.account.client.VehicleMarkClient;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResVechicleMark;
import cn.linkmore.bean.common.Constants.DownLockStatus;
import cn.linkmore.bean.common.Constants.OperateStatus;
import cn.linkmore.bean.common.Constants.OrderFailureReason;
import cn.linkmore.bean.common.Constants.OrderPayType;
import cn.linkmore.bean.common.Constants.OrderStatus;
import cn.linkmore.bean.common.Constants.PushType;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.Constants.StallAssignStatus;
import cn.linkmore.bean.common.Constants.StallStatus;
import cn.linkmore.bean.common.security.Token;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.common.client.BaseDictClient;
import cn.linkmore.common.response.ResOldDict;
import cn.linkmore.order.dao.cluster.OrdersClusterMapper;
import cn.linkmore.order.dao.cluster.StallAssignClusterMapper;
import cn.linkmore.order.dao.master.BookingMasterMapper;
import cn.linkmore.order.dao.master.OrdersDetailMasterMapper;
import cn.linkmore.order.dao.master.OrdersMasterMapper;
import cn.linkmore.order.dao.master.StallAssignMasterMapper;
import cn.linkmore.order.entity.Booking;
import cn.linkmore.order.entity.Orders;
import cn.linkmore.order.entity.OrdersDetail;
import cn.linkmore.order.entity.StallAssign;
import cn.linkmore.order.request.ReqOrderCreate;
import cn.linkmore.order.request.ReqOrderDown;
import cn.linkmore.order.request.ReqOrderSwitch;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.order.service.OrdersService;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.client.StrategyBaseClient;
import cn.linkmore.prefecture.request.ReqStrategy;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.redis.RedisService;
import cn.linkmore.third.client.DockingClient;
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
	private OrdersDetailMasterMapper ordersDetailMasterMapper;
	@Autowired
	private OrdersMasterMapper orderMasterMapper; 
	
	@Autowired
	private BookingMasterMapper bookingMasterMapper;  
	
	@Autowired
	private VehicleMarkClient vehicleMarkClient;
	
	@Autowired
	private StrategyBaseClient strategyBaseClient;
	
	@Autowired
	private StallAssignMasterMapper stallAssignMasterMapper;
	
	@Autowired
	private StallAssignClusterMapper stallAssignClusterMapper;
	
	@Autowired
	private BaseDictClient baseDictClient;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private PushClient pushClient;
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private DockingClient dockingClient;
	
	
	public boolean checkCarFree(String carno) {
		boolean flag = true;
		try {
			Integer status = this.ordersClusterMapper.getLastOrderStatusByCarno(carno);
			if (status != null && status.intValue() == 1) {
				flag = false;
			}
		} catch (Exception e) {

		}
		return flag;
	} 
	
	private String getOrderNumber() {
		Date day = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");
		Long increment = this.redisService.increment(RedisKey.ORDER_SERIAL_NUMBER.key+sdf.format(day), 1);
		Double t = Math.pow(10,5);
		StringBuffer number = new StringBuffer();
		number.append(sdf.format(day));
		number.append(t.intValue()+increment);
		return number.toString();
	}
	private static Set<Long> ORDER_USER_SET = new HashSet<Long>();
	@Async
	@Transactional(rollbackFor = RuntimeException.class)
	public void create(ReqOrderCreate orc) { 
		ResStallEntity stall = null;
		Orders o = null;
		boolean resetRedis = true;
		short failureReason = 0;
		short bookingStatus = 0;
		try {
			synchronized(this) {
				if(ORDER_USER_SET.contains(orc.getUserId())){
					bookingStatus = (short) OperateStatus.FAILURE.status;
					failureReason = (short)OrderFailureReason.UNPAID.value;
					throw new BusinessException(StatusEnum.ORDER_CREATE_FAIL);
				}
				ORDER_USER_SET.add(orc.getUserId());
			} 
			ResUserOrder ruo  = this.ordersClusterMapper.findUserLatest(orc.getUserId());
			if(ruo!=null&&(ruo.getStatus().intValue()==OrderStatus.UNPAID.value||ruo.getStatus().intValue()==OrderStatus.SUSPENDED.value)) {
				bookingStatus = (short) OperateStatus.FAILURE.status;
				failureReason = (short)OrderFailureReason.UNPAID.value;
				throw new BusinessException(StatusEnum.ORDER_CREATE_FAIL);
			} 
			
			ResUser ru = this.userClient.findById(orc.getUserId());
			if (null == orc.getUserId() || null == orc.getPrefectureId() || null == orc.getPlateId()) {
				bookingStatus = (short) OperateStatus.FAILURE.status;
				failureReason = (short)OrderFailureReason.EXCEPTION.value;
				throw new BusinessException(StatusEnum.ORDER_CREATE_FAIL);
			}
			ResVechicleMark  vehicleMark =  vehicleMarkClient.findById(orc.getPlateId());

			if (!this.checkCarFree(vehicleMark.getVehMark())) {
				bookingStatus =(short) OperateStatus.FAILURE.status;
				failureReason = (short)OrderFailureReason.CARNO_BUSY.value;
				throw new BusinessException(StatusEnum.ORDER_REASON_CARNO_BUSY);
			}
			// 指定车位锁
			String key = RedisKey.ORDER_ASSIGN_STALL.key;
			String lockSn = "";
			Set<Object> set = this.redisService.members(RedisKey.ORDER_ASSIGN_STALL.key); 
			String vehMark = vehicleMark.getVehMark();
			boolean assign = false;
			for (Object obj : set) {
				JSONObject json = JSON.parseObject(obj.toString());
				String vm = json.get("plate").toString();
				Long pid = Long.parseLong(json.get("preId").toString());
				if (pid.longValue() == orc.getPrefectureId().longValue() && vehMark.equals(vm)) {
					lockSn = json.get("lockSn").toString();
					Map<String, Object> map = new HashMap<>();
					map.put("lockSn", lockSn);
					map.put("plate", vm);
					map.put("preId", orc.getPrefectureId().toString());
					String val = JSON.toJSON(map).toString(); 
					this.redisService.remove(key, val);
					assign = true;
					log.info("use the admin assign stall:{},plate:{}",lockSn,vehicleMark.getVehMark());
					break;
				}
			}
			log.info("lockSn:{}",lockSn); 
			
			// 以下为预约流程
			if("".equals(lockSn)) {  
				Object sn = redisService.pop(RedisKey.PREFECTURE_FREE_STALL.key + orc.getPrefectureId());
				if(sn!=null) {
					lockSn = sn.toString();
				} 
			}
			
			if (StringUtils.isEmpty(lockSn)) {
				bookingStatus =(short) OperateStatus.FAILURE.status;
				failureReason = (short)OrderFailureReason.STALL_NONE.value;
				throw new BusinessException(StatusEnum.ORDER_REASON_STALL_NONE);
			}
			// 根据lockSn获取车位
			log.info("lock,{}", lockSn);
			
			ResPrefectureDetail pre = prefectureClient.findById(orc.getPrefectureId()); 
			o = new Orders();
			o.setOrderNo(this.getOrderNumber());
			o.setUserType((short)0);
			Date current = new Date();
			o.setPlateNo(vehicleMark.getVehMark());
			o.setUsername(ru.getUsername());
			o.setActualAmount(new BigDecimal(0.0d));
			o.setBeginTime(current);
			o.setCreateTime(current);
			o.setUpdateTime(current);
			// o.setDockId(dockId);
			o.setEndTime(current);
			o.setStrategyId(pre.getStrategyId());
			
			// 支付类型1免费2优惠券3账户
			// 初始化支付类型为账户支付
			o.setPayType(OrderPayType.ACCOUNT.type); 
			o.setPreId(orc.getPrefectureId());
			log.info("order:{}",lockSn);
			stall = this.stallClient.findByLock(lockSn.trim());
			log.info("order :{}",JsonUtil.toJson(stall));
			if (stall == null || stall.getStatus().intValue() != StallStatus.FREE.status) {
				resetRedis = false;
				bookingStatus =(short) OperateStatus.FAILURE.status;
				failureReason = (short)OrderFailureReason.STALL_EXCEPTION.value;
				log.info("{} create order error with {}", ru.getUsername(), JsonUtil.toJson(stall));
				throw new BusinessException(StatusEnum.ORDER_REASON_STALL_EXCEPTION);
			}
			ResUserOrder latest = this.ordersClusterMapper.findStallLatest(stall.getId());
			if (latest != null && latest.getStatus().intValue() == 1) {
				bookingStatus =(short) OperateStatus.FAILURE.status;
				failureReason = (short)OrderFailureReason.STALL_ORDERED.value;
				resetRedis = false;
				log.info("{} create order error latest order {} is unpaid with stall  ", ru.getUsername(),
						JsonUtil.toJson(latest), JsonUtil.toJson(stall));
				throw new BusinessException(StatusEnum.ORDER_REASON_STALL_ORDERED);
			}
			log.info("{} create order with{}", ru.getUsername(), JsonUtil.toJson(stall)); 
			o.setStallId(stall.getId());
			o.setStatus(OrderStatus.UNPAID.value);
			o.setTotalAmount(new BigDecimal(0.0D));
			o.setUserId(orc.getUserId());
			o.setUsername(o.getUsername());

			// 更新车位状态 
			// 订单详情
			OrdersDetail od = new OrdersDetail();
			od.setAccountId(ru.getId());
			od.setBeginTime(current);
			od.setCouponsMoney(new BigDecimal(0.0D));
			od.setCreateTime(current);
			od.setDayFee(new BigDecimal(0.0D));
			od.setDayTime(0);
			od.setNightFee(new BigDecimal(0.0D));
			od.setNightTime(0); 
			Long dictId = pre.getBaseDictId();
			if (null != dictId) {
				ResOldDict dict = this.baseDictClient.findOld(dictId);
				if (null != dict) {
					o.setDockId(dict.getCode());
				}
			}
			od.setOrdNo(o.getOrderNo());
			od.setStallName(stall.getStallName());
			od.setStrategyId(pre.getStrategyId());
			od.setParkName(pre.getName());
			od.setUpdateTime(current);
			o.setStallLocal(pre.getName() + stall.getStallName());
			o.setStallGuidance(pre.getAddress() + stall.getStallName()); 
			this.orderMasterMapper.save(o);
			od.setOrderId(o.getId());
			ordersDetailMasterMapper.save(od);
			this.stallClient.order(stall.getId()); 
			bookingStatus = (short)OperateStatus.SUCCESS.status;
			failureReason = (short)OrderFailureReason.NONE.value;   
			this.userClient.order(ru.getId());
			if(assign){
				log.info("use the admin assign stall:{},orderNo:{}",lockSn,o.getOrderNo());
				StallAssign sa = stallAssignClusterMapper.findByLockSn(lockSn); 
				if(sa!=null&&sa.getStatus().intValue()==StallAssignStatus.FREE.status) {
					sa.setLockSn(lockSn);
					sa.setOrderId(o.getId());
					sa.setOrderNo(o.getOrderNo());
					sa.setOrderTime(o.getCreateTime());
					sa.setStatus((short)StallAssignStatus.ORDER.status);
					stallAssignMasterMapper.orderUpdate(sa);
				} 
			}
			if (StringUtils.isNotBlank(o.getDockId())) { 
				Thread thread = new ProduceBookThread(o);
				thread.start();
			}
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("orderId", o.getId());
			param.put("orderNo", o.getOrderNo());
			param.put("preName", pre.getName());
			param.put("stallName", stall.getStallName());
			param.put("stallId", o.getStallId());
			param.put("startTime", o.getCreateTime());
			param.put("status", o.getStatus()); 
		} catch (BusinessException e) {
			StringBuffer sb = new StringBuffer();
			StackTraceElement[] stackArray = e.getStackTrace();
			for (int i = 0; i < stackArray.length; i++) {
				StackTraceElement element = stackArray[i];
				if (element.toString().indexOf("cn.linkmore") >= 0) {
					sb.append(element.toString() + "\n");
				}
			}
			log.info(sb.toString());
			if (null != stall && resetRedis) { 
				this.redisService.add(RedisKey.PREFECTURE_FREE_STALL.key +stall.getPreId(), stall.getLockSn());
			}
			throw e;
		} catch (RuntimeException e) {
			StringBuffer sb = new StringBuffer();
			StackTraceElement[] stackArray = e.getStackTrace();
			for (int i = 0; i < stackArray.length; i++) {
				StackTraceElement element = stackArray[i];
				if (element.toString().indexOf("cn.linkmore") >= 0) {
					sb.append(element.toString() + "\n");
				}
			}
			log.info(sb.toString());
			if (null != stall && resetRedis) { 
				this.redisService.add(RedisKey.PREFECTURE_FREE_STALL.key +stall.getPreId(), stall.getLockSn());
			}
			throw e;
		} catch (Exception e) {
			StringBuffer sb = new StringBuffer();
			StackTraceElement[] stackArray = e.getStackTrace();
			for (int i = 0; i < stackArray.length; i++) {
				StackTraceElement element = stackArray[i];
				if (element.toString().indexOf("cn.linkmore") >= 0) {
					sb.append(element.toString() + "\n");
				}
			}
			log.info(sb.toString());
			if (null != stall && resetRedis) { 
				this.redisService.add(RedisKey.PREFECTURE_FREE_STALL.key +stall.getPreId(), stall.getLockSn());
			}
			throw new RuntimeException("异常");
		} finally {
			ORDER_USER_SET.remove(orc.getUserId());
			Thread thread = new BookingThread(orc.getPrefectureId(), orc.getUserId(), bookingStatus, failureReason);
			thread.start();  
			String content = "订单预约失败";  
			Boolean status = false;
			if(bookingStatus==OperateStatus.SUCCESS.status) {
				content = "订单预约成功";  
				status = true;
			}  
			push(orc.getUserId().toString(),"车位预约通知",content,PushType.ORDER_CREATE_NOTICE,status);
		} 
	}
	public ResUserOrder findStallLatestOrder(Long stallId) {
		return this.ordersClusterMapper.findStallLatest(stallId); 
	}
	class ProduceBookThread extends Thread {
		private Orders order;

		public ProduceBookThread(Orders order) {
			this.order = order;
		} 
		public void run() {
			try { 
				cn.linkmore.third.request.ReqOrder ro = new cn.linkmore.third.request.ReqOrder();
				ro.setActualAmount(order.getActualAmount());
				ro.setTotalAmount(order.getTotalAmount());
				ro.setBeginTime(order.getBeginTime());
				ro.setDockId(order.getDockId());
				ro.setOrderNo(order.getOrderNo());
				ro.setStatus(order.getStatus());
				ro.setEndTime(order.getEndTime());
				ro.setPreId(order.getPreId());
				ro.setPlateNo(order.getPlateNo());
				dockingClient.order(ro);
			} catch (Exception e) {
				log.info("call park producer error with booking msg");
			}
		}
	}
	class BookingThread extends Thread {
		private Long preId;
		private Long userId;
		private Short status;
		private Short reason;

		public BookingThread(Long preId, Long userId, Short status, Short reason) {
			this.preId = preId;
			this.userId = userId;
			this.status = status;
			this.reason = reason;
		}

		public void run() {
			int count = ordersClusterMapper.userCount(userId);
			Booking booking = new Booking();
			booking.setCreateTime(new Date());
			booking.setDay(new SimpleDateFormat("yyyy-MM-dd").format(booking.getCreateTime()));
			if (count > 0) {
				booking.setFirst((short) 0);
			} else {
				booking.setFirst((short) 1);
			}
			booking.setPreId(preId);
			booking.setReason(reason);
			booking.setStatus(status);
			booking.setUserId(userId);
			Token token =(Token) redisService.get(RedisKey.USER_APP_AUTH_TOKEN.key+userId);
			booking.setSource(token.getClient());
			bookingMasterMapper.save(booking);
		}
	}
	@Override
	public ResUserOrder latest(Long userId) {
		ResUserOrder orders = null;
		try {
			orders = this.ordersClusterMapper.findUserLatest(userId);  
			ReqStrategy rs = new ReqStrategy();
			rs.setBeginTime(orders.getCreateTime().getTime());
			rs.setStrategyId(orders.getStrategyId());
			if(orders.getStatus().intValue()==OrderStatus.SUSPENDED.value) {
				rs.setEndTime(orders.getStatusTime().getTime());
			}else {
				rs.setEndTime(new Date().getTime());
			}
			Map<String,Object> map = strategyBaseClient.fee(rs);
			if(map!=null) {
				Object object = map.get("totalAmount");
				if(object!=null) {
					orders.setTotalAmount(new BigDecimal(object.toString()));
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return orders;
	}
	@Override
	public ResUserOrder detail(Long orderId) {
		ResUserOrder orders = this.ordersClusterMapper.findDetail(orderId); 
		return orders;
	}
	/**
	 * 推送消息
	 * @param uid 
	 * @param title
	 * @param content
	 * @param type
	 * @param res
	 */
	@Async
	private void push(String uid,String title,String content,PushType type,Boolean status) {
		Token token = (Token)this.redisService.get(RedisKey.USER_APP_AUTH_TOKEN.key+uid.toString());
		ReqPush rp = new ReqPush();
		rp.setAlias(uid);
		rp.setTitle(title); 
		rp.setContent(content);
		rp.setClient(token.getClient());
		rp.setType(type);
		rp.setData(status.toString()); 
		this.pushClient.push(rp);
	}
	
	@Override
	@Async
	@Transactional(rollbackFor = RuntimeException.class)
	public void down(ReqOrderDown rod) {
		ResUserOrder order = this.ordersClusterMapper.findDetail(rod.getOrderId());
		Boolean flag = false;    
		Boolean switchStatus = false;
		if(rod.getStallId().intValue()==order.getStallId()&&order.getStatus()==OrderStatus.UNPAID.value) {
			flag = this.stallClient.downlock(order.getStallId());  
			Map<String,Object> param = new HashMap<String,Object>(); 
			param.put("lockDownStatus",flag?OperateStatus.SUCCESS.status:OperateStatus.FAILURE.status);
			param.put("lockDownTime", new Date());
			param.put("orderId", order.getId());
			this.orderMasterMapper.updateLockStatus(param); 
			log.info("stall downing :{}",flag);
			if(!flag) {
				if(this.redisService.exists(RedisKey.ORDER_STALL_DOWN_FAILED.key+order.getId())) {
					switchStatus = true;
				}else {
					this.redisService.set(RedisKey.ORDER_STALL_DOWN_FAILED.key+order.getId(), 1);
				}
				
			}else {
				this.redisService.remove(RedisKey.ORDER_STALL_DOWN_FAILED.key+order.getId());
			}
		} 
		if(switchStatus) {
			this.push(order.getUserId().toString(), "预约切换通知","车位锁降下失败建议切换车位",PushType.ORDER_SWITCH_STATUS_NOTICE, true);
		}else {
			this.push(order.getUserId().toString(), "预约降锁通知",flag? "车位锁降下成功":"车位锁降下失败",PushType.LOCK_DOWN_NOTICE, flag);
		} 
	}

	 

	@Override
	@Async
	@Transactional(rollbackFor = RuntimeException.class)
	public void switchStall(ReqOrderSwitch ros) {
		ResUserOrder order = this.ordersClusterMapper.findDetail(ros.getOrderId());
		Boolean flag = false;
		if(order.getStatus().intValue()==OrderStatus.UNPAID.value) {
			Object sn = redisService.pop(RedisKey.PREFECTURE_FREE_STALL.key + order.getPreId()); 
			log.info("get switch stall sn:{}",sn);
			if(sn!=null) {
				ResStallEntity stall = this.stallClient.findByLock(sn.toString().trim());
				log.info("switch stall:{}",JsonUtil.toJson(stall));
				if(stall.getStatus().intValue()==StallStatus.FREE.status) {
					order.setStallId(stall.getId());
					order.setStallName(stall.getStallName());
					Map<String,Object> param = new HashMap<String,Object>();
					param.put("id", order.getId());
					param.put("stallId", stall.getId());
					param.put("stallName", stall.getStallName());
					param.put("switchTime", new Date());
					param.put("switchStatus", 1);
					this.orderMasterMapper.updateSwitch(param);
					this.stallClient.order(stall.getId()); 
					flag = true;
				} 
			}   
		}
		this.push(order.getUserId().toString(), "车位切换通知",flag? "车位切换成功":"车位切换失败",PushType.ORDER_SWITCH_RESULT_NOTICE, flag);
	}

	@Override
	public List<ResUserOrder> list(Long userId, Long start) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("start", start);
		List<ResUserOrder> list = this.ordersClusterMapper.findUserList(param);
		return list;
	}
}
