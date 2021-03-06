package cn.linkmore.order.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.linkmore.account.client.UserClient;
import cn.linkmore.account.client.VehicleMarkClient;
import cn.linkmore.account.response.ResVechicleMark;
import cn.linkmore.bean.common.Constants.ClientSource;
import cn.linkmore.bean.common.Constants.ExpiredTime;
import cn.linkmore.bean.common.Constants.LockStatus;
import cn.linkmore.bean.common.Constants.OperateStatus;
import cn.linkmore.bean.common.Constants.OrderFailureReason;
import cn.linkmore.bean.common.Constants.OrderPayType;
import cn.linkmore.bean.common.Constants.OrderStatus;
import cn.linkmore.bean.common.Constants.OrderStatusHistory;
import cn.linkmore.bean.common.Constants.PushType;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.Constants.StallAssignStatus;
import cn.linkmore.bean.common.Constants.StallStatus;
import cn.linkmore.bean.common.Constants.SwitchResult;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.common.security.Token;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.BaseDictClient;
import cn.linkmore.common.response.ResOldDict;
import cn.linkmore.coupon.client.CouponClient;
import cn.linkmore.coupon.request.ReqCouponPay;
import cn.linkmore.enterprise.request.ReqPreDetails;
import cn.linkmore.enterprise.request.ReqStaffPreOwnerStall;
import cn.linkmore.enterprise.response.ResBrandPre;
import cn.linkmore.enterprise.response.ResBrandStall;
import cn.linkmore.enterprise.response.ResStaffPreOwnerStall;
import cn.linkmore.notice.client.UserSocketClient;
import cn.linkmore.order.config.BaseConfig;
import cn.linkmore.order.controller.app.request.ReqBooking;
import cn.linkmore.order.controller.app.request.ReqBrandBooking;
import cn.linkmore.order.controller.app.request.ReqOrderStall;
import cn.linkmore.order.controller.app.request.ReqStallBooking;
import cn.linkmore.order.controller.app.request.ReqSwitch;
import cn.linkmore.order.controller.app.response.ResCheckedOrder;
import cn.linkmore.order.controller.app.response.ResMonthCount;
import cn.linkmore.order.controller.app.response.ResOrder;
import cn.linkmore.order.controller.app.response.ResOrderDetail;
import cn.linkmore.order.controller.staff.response.ResPreDetails;
import cn.linkmore.order.controller.staff.response.ResPreList;
import cn.linkmore.order.controller.staff.response.ResPreListType;
import cn.linkmore.order.controller.staff.response.ResPreReportForms;
import cn.linkmore.order.dao.cluster.OrdersClusterMapper;
import cn.linkmore.order.dao.cluster.StallAssignClusterMapper;
import cn.linkmore.order.dao.master.BookingMasterMapper;
import cn.linkmore.order.dao.master.OrdersDetailMasterMapper;
import cn.linkmore.order.dao.master.OrdersMasterMapper;
import cn.linkmore.order.dao.master.StallAssignMasterMapper;
import cn.linkmore.order.entity.Booking;
import cn.linkmore.order.entity.Orders;
import cn.linkmore.order.entity.OrdersDetail;
import cn.linkmore.order.entity.ResStaffDataCountVo;
import cn.linkmore.order.entity.StallAssign;
import cn.linkmore.order.request.ReqDataCount;
import cn.linkmore.order.request.ReqOrderExcel;
import cn.linkmore.order.response.ResChargeDetail;
import cn.linkmore.order.response.ResEntOrder;
import cn.linkmore.order.response.ResIncome;
import cn.linkmore.order.response.ResIncomeList;
import cn.linkmore.order.response.ResOrderExcel;
import cn.linkmore.order.response.ResOrderOperateLog;
import cn.linkmore.order.response.ResOrderPlate;
import cn.linkmore.order.response.ResPreDataList;
import cn.linkmore.order.response.ResPreOrderCount;
import cn.linkmore.order.response.ResPreOrderDetails;
import cn.linkmore.order.response.ResTempStallReportForms;
import cn.linkmore.order.response.ResTrafficFlow;
import cn.linkmore.order.response.ResTrafficFlowList;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.order.service.OrdersService;
import cn.linkmore.prefecture.client.EntBrandPreClient;
import cn.linkmore.prefecture.client.EntBrandUserClient;
import cn.linkmore.prefecture.client.FeignLockClient;
import cn.linkmore.prefecture.client.OpsEntUserPlateClient;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.client.StrategyFeeClient;
import cn.linkmore.prefecture.response.ResLockInfo;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.redis.RedisLock;
import cn.linkmore.redis.RedisService;
import cn.linkmore.third.client.DockingClient;
import cn.linkmore.third.client.PushClient;
import cn.linkmore.third.request.ReqPush;
import cn.linkmore.user.factory.AppUserFactory;
import cn.linkmore.user.factory.UserFactory;
import cn.linkmore.util.DateUtils;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.JsonUtil;

/**
 * Service实现 -订单
 * 
 * @author liwenlong
 * @version 2.0
 */
@Service
public class OrdersServiceImpl implements OrdersService {
	private UserFactory appUserFactory = AppUserFactory.getInstance();
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BaseConfig baseConfig;

	@Autowired
	private PrefectureClient prefectureClient;

	@Autowired
	private StallClient stallClient;

	@Autowired
	private OrdersClusterMapper ordersClusterMapper;
	@Autowired
	private OrdersMasterMapper orderMasterMapper;

	@Autowired
	private OrdersDetailMasterMapper ordersDetailMasterMapper;

	@Autowired
	private BookingMasterMapper bookingMasterMapper;

	@Autowired
	private VehicleMarkClient vehicleMarkClient;

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
	private UserSocketClient userSocketClient;

	@Autowired
	private UserClient userClient;

	@Autowired
	private DockingClient dockingClient;

	@Autowired
	private CouponClient couponClient;

	@Autowired
	private EntBrandPreClient entBrandPreClient;

	@Autowired
	private EntBrandUserClient entBrandUserClient;
	
	@Autowired
	private StrategyFeeClient strategyFeeClient;
	
	@Resource
	private OpsEntUserPlateClient userPlateClient;
	
	@Resource
	private FeignLockClient lockClient;
	
	@Autowired
	private RedisLock redisLock;
	

	public boolean checkCarFree(String carno) {
		boolean flag = true;
		try {
			Integer status = this.ordersClusterMapper.getPlateLastOrderStatus(carno);
			if (status != null && status.intValue() == 1) {
				flag = false;
			}
		} catch (Exception e) {

		}
		return flag;
	}

	private final static Long ORDER_NUMBER_AMOUNT = 1000000000L;

	private String getOrderNumber() {
		Date day = new Date();
		Long time = day.getTime() % ORDER_NUMBER_AMOUNT;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Long increment = this.redisService.increment(RedisKey.ORDER_SERIAL_NUMBER.key + sdf.format(day), 1);
		Double t = Math.pow(10, baseConfig.getOrderNumber());
		StringBuffer number = new StringBuffer();
		number.append(sdf.format(day));
		number.append(t.longValue() + increment + time);
		return number.toString();
	}

	private static Set<Long> ORDER_USER_SET = new HashSet<Long>();
	private static Set<Long> STALL_ID_SET = new HashSet<Long>();

	class OrderThread extends Thread {
		private ReqBooking rb;
		private CacheUser cu;

		public OrderThread(ReqBooking rb, CacheUser cu) {
			this.rb = rb;
			this.cu = cu;
		}

		public void run() {
			log.info("order thread starting");
			order(rb.getPrefectureId(), rb.getPlateId(), null, null, rb.getOrderSource(), cu);
		}
	}

	class OrderBrandThread extends Thread {
		private ReqBrandBooking rb;
		private CacheUser cu;

		public OrderBrandThread(ReqBrandBooking rb, CacheUser cu) {
			this.rb = rb;
			this.cu = cu;
		}

		public void run() {
			log.info("brand order thread starting {}", rb.getBrandId());
			order(rb.getPrefectureId(), rb.getPlateId(), rb.getBrandId(), null, "1", cu);
		}
	}

	class StallOrderThread extends Thread {
		private ReqStallBooking rsb;
		private CacheUser cu;

		public StallOrderThread(ReqStallBooking rsb, CacheUser cu) {
			this.rsb = rsb;
			this.cu = cu;
		}

		public void run() {
			log.info("choose stall order thread starting");
			order(rsb.getPrefectureId(), rsb.getPlateId(), null, rsb.getStallId(), rsb.getOrderSource(), cu);
		}
	}
	
	class UplockThread extends Thread {
		private Long stallId;

		public UplockThread(Long stallId) {
			this.stallId = stallId;
		}

		public void run() {
			log.info("uplock thread starting {}", stallId);
			stallClient.uplock(stallId);
		}
	}

	public void create(ReqBooking rb, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		log.info("create order ing rb:{},cu:{}", JsonUtil.toJson(rb), JsonUtil.toJson(cu));
		Thread thread = new OrderThread(rb, cu);
		thread.start();
	}
	
	@Transactional(rollbackFor = RuntimeException.class)
	public Boolean downAppointOrder(Long prefectureId, Long plateId, Long brandId, Long stallId, String orderSource,
			CacheUser cu, boolean downFlag) {
		Boolean status = false;
		ResStallEntity stall = null;
		Orders o = null;
		boolean resetRedis = true;
		short failureReason = 0;
		short bookingStatus = 0;
		log.info("..........down appoint cu:{} booking preId:{},plateId:{},brandId:{},stallId:{},orderSource:{}, downFlag:{}", cu.getMobile(), prefectureId, plateId,
				brandId, stallId, orderSource,downFlag);
		try {
			synchronized (this) {
				if (ORDER_USER_SET.contains(cu.getId())) { // ？？？
					bookingStatus = (short) OperateStatus.FAILURE.status;
					failureReason = (short) OrderFailureReason.UNPAID.value;
					throw new BusinessException(StatusEnum.ORDER_CREATE_FAIL); // 预约失败
				}
				ORDER_USER_SET.add(cu.getId());
				if (stallId != null) {
					if (STALL_ID_SET.contains(stallId)) { // ？？？
						bookingStatus = (short) OperateStatus.FAILURE.status;
						failureReason = (short) OrderFailureReason.UNPAID.value;
						throw new BusinessException(StatusEnum.ORDER_CREATE_FAIL); // 预约失败
					}
					STALL_ID_SET.add(stallId);
					log.info("..........down appoint 1  STALL_ID_SET = {}", JSON.toJSON(STALL_ID_SET));
				}
			}
			ResUserOrder ruo = this.ordersClusterMapper.findUserLatest(cu.getId()); // 找到最新一单
			if (ruo != null && (ruo.getStatus().intValue() == OrderStatus.UNPAID.value
					|| ruo.getStatus().intValue() == OrderStatus.SUSPENDED.value)) {
				bookingStatus = (short) OperateStatus.FAILURE.status;
				failureReason = (short) OrderFailureReason.UNPAID.value;
				throw new BusinessException(StatusEnum.ORDER_CREATE_FAIL); // 有订单
			}
			if (null == cu.getId() || null == prefectureId || null == plateId) {
				bookingStatus = (short) OperateStatus.FAILURE.status;
				failureReason = (short) OrderFailureReason.EXCEPTION.value;
				throw new BusinessException(StatusEnum.ORDER_CREATE_FAIL); // 预约失败请重新预约
			}
			ResVechicleMark vehicleMark = vehicleMarkClient.findById(plateId); // 车牌号管理表

			if (vehicleMark.getUserId().longValue() != cu.getId().longValue()) { // 无空闲车位？？
				bookingStatus = (short) OperateStatus.FAILURE.status;
				failureReason = (short) OrderFailureReason.CARNO_NONE.value;
				throw new BusinessException(StatusEnum.ORDER_REASON_CARNO_NONE);
			}
			if (!this.checkCarFree(vehicleMark.getVehMark())) {
				bookingStatus = (short) OperateStatus.FAILURE.status;
				failureReason = (short) OrderFailureReason.CARNO_BUSY.value;
				throw new BusinessException(StatusEnum.ORDER_REASON_CARNO_BUSY); // 当前车牌号已在预约中，请更换车牌号重新预约
			}

			ResPrefectureDetail pre = prefectureClient.findById(prefectureId);
			log.info("order-pre:{}", JsonUtil.toJson(pre));
			String lockSn = "";
			ResBrandPre brand = null;
			boolean assign = false;
			if (stallId != null) {
				boolean flag = false;
				stall = this.stallClient.findById(stallId);
				if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
					lockSn = stall.getLockSn();
					if(!downFlag) {
						log.info(".................2  order-stall-lockSn,{}", lockSn);
						if(pre != null  && pre.getCategory() == 2) {
							//根据lockCode查询当前车位是否降下并且上方无车
							//若上方无车则升起地锁
							flag = true;
							Thread thread = new UplockThread(stallId);
							thread.start();
						}else {
							Set<Object> lockSnList = this.redisService
									.members(RedisKey.PREFECTURE_FREE_STALL.key + prefectureId); // 集合中所有成员元素
							log.info(".................3  lockSnList = {}",JSON.toJSON(lockSnList));
							if (CollectionUtils.isNotEmpty(lockSnList)) {
								if (lockSnList.contains(lockSn)) {
									flag = true;
									log.info("current lockSn is free, flag = {}", flag);
									log.info("before remove size = {}",this.redisService.size(RedisKey.PREFECTURE_FREE_STALL.key + prefectureId));
									this.redisService.remove(RedisKey.PREFECTURE_FREE_STALL.key + prefectureId, lockSn);
									log.info("after remove size = {}",this.redisService.size(RedisKey.PREFECTURE_FREE_STALL.key + prefectureId));
									this.redisService.set(RedisKey.PREFECTURE_BUSY_STALL.key + lockSn, lockSn,
											ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
								}
							}
						}
						log.info(">>>>>>>4  current lockSn flag = {}", flag);
						if (!flag) {
							resetRedis = false;
							bookingStatus = (short) OperateStatus.FAILURE.status;
							failureReason = (short) OrderFailureReason.STALL_NONE.value;
							throw new BusinessException(StatusEnum.ORDER_REASON_STALL_ORDERED);
						}
					}
				}
			} else {
				String key = RedisKey.ORDER_ASSIGN_STALL.key; // assign_lock
				Set<Object> set = this.redisService.members(RedisKey.ORDER_ASSIGN_STALL.key); // 集合中所有成员元素
				String vehMark = vehicleMark.getVehMark(); // 车牌号
				for (Object obj : set) {
					JSONObject json = JSON.parseObject(obj.toString());
					String vm = json.get("plate").toString(); // 车牌
					Long pid = Long.parseLong(json.get("preId").toString()); // 车区id
					if (pid.longValue() == prefectureId.longValue() && vehMark.equals(vm)) { // 找到车区
						lockSn = json.get("lockSn").toString();
						Map<String, Object> map = new HashMap<>();
						map.put("lockSn", lockSn);
						map.put("plate", vm);
						map.put("preId", prefectureId);
						String val = JSON.toJSON(map).toString();
						log.info("use the admin assign key {}, val {}", key, val);
						this.redisService.set(RedisKey.PREFECTURE_BUSY_STALL.key + lockSn, lockSn,
								ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
						this.redisService.remove(key, val);
						assign = true;
						log.info("use the admin assign stall:{},plate:{}", lockSn, vehicleMark.getVehMark());
						break;
					}
				}
				if (brandId != null) {
					log.info("brand pre lockSn:{}", lockSn);
					brand = entBrandPreClient.findById(brandId);
					log.info("brandId {},brand {}", JSON.toJSON(brand));
					if ("".equals(lockSn)) {
						Object sn = redisService.pop(RedisKey.PREFECTURE_BRAND_FREE_STALL.key + brandId);
						if (sn != null) {
							this.redisService.set(RedisKey.PREFECTURE_BUSY_STALL.key + sn.toString(), sn.toString(),
									ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
							lockSn = sn.toString();
						}
					}

				} else {
					log.info("common pre lockSn:{}", lockSn);
					// 以下为预约流程
					if ("".equals(lockSn)) {
						Object sn = redisService.pop(RedisKey.PREFECTURE_FREE_STALL.key + prefectureId);
						if (sn != null) {
							this.redisService.set(RedisKey.PREFECTURE_BUSY_STALL.key + sn.toString(), sn.toString(),
									ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
							lockSn = sn.toString();
						}
					}
				}
			}

			if (StringUtils.isEmpty(lockSn)) {
				bookingStatus = (short) OperateStatus.FAILURE.status;
				failureReason = (short) OrderFailureReason.STALL_NONE.value;
				throw new BusinessException(StatusEnum.ORDER_REASON_STALL_NONE); // 无空闲车位，请重新预约
			}
			// 根据lockSn获取车位
			log.info("lock,{}", lockSn);
			stall = this.stallClient.findByLock(lockSn.trim());
			log.info("order-stall:{}", JsonUtil.toJson(stall));
			//降锁下单直接过滤
			if (stall == null || stall.getStatus().intValue() != StallStatus.FREE.status) {
				resetRedis = false;
				bookingStatus = (short) OperateStatus.FAILURE.status;
				failureReason = (short) OrderFailureReason.STALL_EXCEPTION.value;
				log.info("{} create order error with {}", cu.getMobile(), JsonUtil.toJson(stall));
				throw new BusinessException(StatusEnum.ORDER_REASON_STALL_EXCEPTION);
			}
			ResUserOrder latest = this.ordersClusterMapper.findStallLatest(stall.getId());
			if (latest != null && latest.getStatus().intValue() == 1) {
				bookingStatus = (short) OperateStatus.FAILURE.status;
				failureReason = (short) OrderFailureReason.STALL_ORDERED.value;
				resetRedis = false;
				log.info("{} create order error latest order {} is unpaid with stall  ", cu.getMobile(),
						JsonUtil.toJson(latest), JsonUtil.toJson(stall));
				throw new BusinessException(StatusEnum.ORDER_REASON_STALL_ORDERED);
			}
			log.info("{} create order with{}", cu.getMobile(), JsonUtil.toJson(stall));
			o = new Orders(); // 插入订单
			o.setOrderNo(this.getOrderNumber());
			o.setUserType((short) 0);
			Date current = new Date();
			o.setPlateNo(vehicleMark.getVehMark());
			o.setUsername(cu.getMobile());
			o.setActualAmount(new BigDecimal(0.0d));
			o.setBeginTime(current);
			o.setCreateTime(current);
			o.setUpdateTime(current);
			o.setEndTime(current);
			o.setStrategyId(pre.getStrategyId());
			// 支付类型1免费2优惠券3账户
			// 初始化支付类型为账户支付
			o.setPayType(OrderPayType.FREE.type);
			o.setPreId(prefectureId);
			o.setStallId(stall.getId());
			o.setPreName(pre.getName());
			o.setStallName(stall.getStallName());
			o.setStatus(OrderStatus.UNPAID.value);
			o.setTotalAmount(new BigDecimal(0.0D));
			o.setUserId(cu.getId());
			o.setUsername(o.getUsername());
			o.setClientType(cu.getClient());
			o.setOrderSource(new Short(orderSource));
			o.setAreaName(stall.getAreaName());
			// 更新车位状态
			// 订单详情
			Long dictId = pre.getBaseDictId();
			if (null != dictId) {
				ResOldDict dict = this.baseDictClient.findOld(dictId);
				if (null != dict) {
					o.setDockId(dict.getCode());
				}
			}
			o.setStallGuidance(pre.getAddress() + stall.getStallName());
			o.setStallType(stall.getType());
			if (brand != null) {
				o.setBrandId(brandId);
				o.setEntId(brand.getEntId());
				o.setStrategyId(brand.getStrategyId());
				o.setPreName(brand.getName());
			}
			o.setStallLocal(o.getPreName() + stall.getStallName());
			o.setLockDownTime(new Date());
			o.setLockDownStatus((short)1);
			this.orderMasterMapper.save(o);

			OrdersDetail od = new OrdersDetail();
			od.setAccountId(cu.getId());
			od.setBeginTime(current);
			od.setCouponsMoney(new BigDecimal(0.0D));
			od.setCreateTime(current);
			od.setDayFee(new BigDecimal(0.0D));
			od.setDayTime(0);
			od.setNightFee(new BigDecimal(0.0D));
			od.setEndTime(new Date());
			od.setNightTime(0);
			od.setOrdNo(o.getOrderNo());
			od.setStallName(stall.getStallName());
			od.setStrategyId(pre.getStrategyId());
			od.setParkName(pre.getName());
			od.setUpdateTime(current);
			od.setOrderId(o.getId());
			this.ordersDetailMasterMapper.save(od);
			this.stallClient.order(stall.getId());
			bookingStatus = (short) OperateStatus.SUCCESS.status;
			failureReason = (short) OrderFailureReason.NONE.value;
			this.userClient.order(cu.getId());
			if (assign) {
				log.info("use the admin assign stall:{},orderNo:{}", lockSn, o.getOrderNo());
				StallAssign sa = stallAssignClusterMapper.findByLockSn(lockSn);
				if (sa != null && sa.getStatus().intValue() == StallAssignStatus.FREE.status) {
					sa.setLockSn(lockSn);
					sa.setOrderId(o.getId());
					sa.setOrderNo(o.getOrderNo());
					sa.setOrderTime(o.getCreateTime());
					sa.setStatus((short) StallAssignStatus.ORDER.status);
					stallAssignMasterMapper.orderUpdate(sa);
				}
			}
			if (StringUtils.isNotBlank(o.getDockId())) {
				Thread thread = new ProduceBookThread(o);
				thread.start();
			}
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
				if (brandId != null) {
					this.redisService.add(RedisKey.PREFECTURE_BRAND_FREE_STALL.key + brandId, stall.getLockSn());
				} else {
					if(!downFlag) {
						this.redisService.add(RedisKey.PREFECTURE_FREE_STALL.key + stall.getPreId(), stall.getLockSn());
					}
				}
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
				if (brandId != null) {
					this.redisService.add(RedisKey.PREFECTURE_BRAND_FREE_STALL.key + brandId, stall.getLockSn());
				} else {
					if(!downFlag) {
						this.redisService.add(RedisKey.PREFECTURE_FREE_STALL.key + stall.getPreId(), stall.getLockSn());
					}
				}
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
				if (brandId != null) {
					this.redisService.add(RedisKey.PREFECTURE_BRAND_FREE_STALL.key + brandId, stall.getLockSn());
				} else {
					if(!downFlag) {
						this.redisService.add(RedisKey.PREFECTURE_FREE_STALL.key + stall.getPreId(), stall.getLockSn());
					}
				}
			}
			throw new RuntimeException("异常");
		} finally {
			ORDER_USER_SET.remove(cu.getId());
			if (stallId != null) {
				log.info(".................5  cu = {}, stallId = {},STALL_ID_SET = {}", cu.getId(), stallId, JSON.toJSON(STALL_ID_SET));
				if (STALL_ID_SET.contains(stallId)) {
					STALL_ID_SET.remove(stallId);
					log.info(".................6  cu = {}, stallId = {},STALL_ID_SET = {}", cu.getId(), stallId,
							JSON.toJSON(STALL_ID_SET));
				}
			}
			Thread thread = new BookingThread(prefectureId, cu.getId(), bookingStatus, failureReason);
			thread.start();
			String content = "订单预约失败";
			if (stallId != null) {
				content = "当前车位已被占用,请选择其他车位";
			}
			
			if (bookingStatus == OperateStatus.SUCCESS.status) {
				content = "订单预约成功";
				status = true;
			}
			//thread = new PushThread(cu.getId().toString(), "车位预约通知", content, PushType.ORDER_CREATE_NOTICE, status);
			//thread.start();
		}
		return status;
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
			Token token = (Token) redisService.get(RedisKey.USER_APP_AUTH_TOKEN.key + userId);
			booking.setSource(token.getClient());
			bookingMasterMapper.save(booking);
		}
	}

	@Override
	public ResUserOrder latest(Long userId) {
		ResUserOrder orders = this.ordersClusterMapper.findUserLatest(userId);
		if (orders == null) {
			return null;
		}
		/*ReqStrategy rs = new ReqStrategy();
		rs.setBeginTime(orders.getCreateTime().getTime());
		rs.setStrategyId(orders.getStrategyId());
		if (orders.getStatus().intValue() == OrderStatus.SUSPENDED.value) {
			rs.setEndTime(orders.getStatusTime().getTime());
		} else {
			rs.setEndTime(new Date().getTime());
		}
		Map<String, Object> map = strategyBaseClient.fee(rs);*/
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("stallId", orders.getId());
		param.put("plateNo", orders.getPlateNo());
		param.put("startTime", sdf.format(orders.getCreateTime()));
		if (orders.getStatus().intValue() == OrderStatus.SUSPENDED.value) {
			param.put("endTime", sdf.format(orders.getStatusTime()));
		} else {
			param.put("endTime", sdf.format(new Date()));
		}
		Map<String, Object> map = this.strategyFeeClient.amount(param);
		if (map != null) {
			Object object = map.get("chargePrice");
			if (object != null) {
				orders.setTotalAmount(new BigDecimal(object.toString()));
			}
		}
		return orders;
	}

	@Override
	public ResOrderDetail detail(Long id, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		ResUserOrder order = this.ordersClusterMapper.findDetail(id);
		if (order.getUserId().intValue() != cu.getId().intValue()) {
			return null;
		}
		ResOrderDetail detail = new ResOrderDetail();
		detail.copy(order);
		return detail;
	}

	class PushThread extends Thread {
		private String uid;
		private String title;
		private String content;
		private PushType type;
		private Boolean status;

		public PushThread(String uid, String title, String content, PushType type, Boolean status) {
			this.uid = uid;
			this.title = title;
			this.content = content;
			this.type = type;
			this.status = status;
		}

		public void run() {
			send(uid, title, content, type, status);
		}
	}

	class PushWYThread extends Thread {
		private String uid;
		private String title;
		private String content;
		private PushType type;
		private Boolean status;

		public PushWYThread(String uid, String title, String content, PushType type, Boolean status) {
			this.uid = uid;
			this.title = title;
			this.content = content;
			this.type = type;
			this.status = status;
		}

		public void run() {
			sendWY(uid, title, content, type, status);
		}
	}

	/**
	 * 推送消息
	 * 
	 * @param uid
	 * @param title
	 * @param content
	 * @param type
	 * @param res
	 */
	private void send(String uid, String title, String content, PushType type, Boolean status) {
		Token token = (Token) redisService.get(RedisKey.USER_APP_AUTH_TOKEN.key + uid.toString());
		log.info("send:{}", JsonUtil.toJson(token));
		if (token.getClient().intValue() == ClientSource.WXAPP.source) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", title);
			map.put("type", type.id);
			map.put("content", content);
			map.put("status", status);
			CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(token.getAccessToken(),null) );
			userSocketClient.push(JsonUtil.toJson(map), cu.getOpenId());
		} else {
			ReqPush rp = new ReqPush();
			rp.setAlias(uid);
			rp.setTitle(title);
			rp.setContent(content);
			rp.setClient(token.getClient());
			rp.setType(type);
			rp.setData(status.toString());
			pushClient.push(rp);
		}
	}

	private void sendWY(String uid, String title, String content, PushType type, Boolean status) {
		Token token = (Token) redisService.get(RedisKey.STAFF_ENT_AUTH_TOKEN.key + uid.toString());
		log.info("send:{}", JsonUtil.toJson(token));
		if (token.getClient().intValue() == ClientSource.WXAPP.source) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", title);
			map.put("type", type.id);
			map.put("content", content);
			map.put("status", status);
			CacheUser cu = (CacheUser) this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key + token.getAccessToken());
			userSocketClient.push(JsonUtil.toJson(map), cu.getOpenId());
		} else {
			ReqPush rp = new ReqPush();
			rp.setAlias(uid);
			rp.setTitle(title);
			rp.setContent(content);
			rp.setClient(token.getClient());
			rp.setType(type);
			rp.setData(status.toString());
			pushClient.push(rp);
		}
	}

	@Transactional(rollbackFor = RuntimeException.class)
	private void downStall(ReqOrderStall ros, CacheUser cu) {
		ResUserOrder order = this.ordersClusterMapper.findDetail(ros.getOrderId());
		Boolean authStatus = ros.getStallId().intValue() == order.getStallId()
				&& order.getStatus() == OrderStatus.UNPAID.value
				&& order.getUserId().longValue() == cu.getId().longValue();
		if (authStatus) {
			cn.linkmore.prefecture.request.ReqOrderStall stall = new cn.linkmore.prefecture.request.ReqOrderStall();
			stall.setOrderId(order.getId());
			stall.setStallId(order.getStallId());
			stall.setUserId(cu.getId());
			if (this.redisService.exists(RedisKey.ORDER_STALL_DOWN_FAILED.key + order.getId())) {
				Object count = this.redisService.get(RedisKey.ORDER_STALL_DOWN_FAILED.key + order.getId());
				this.redisService.set(RedisKey.ORDER_STALL_DOWN_FAILED.key + order.getId(),
						new Integer(count.toString()) + 1, ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
			} else {
				this.redisService.set(RedisKey.ORDER_STALL_DOWN_FAILED.key + order.getId(), 1,
						ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
			}
			this.stallClient.downlock(stall);
		}
	}

	class DownThread extends Thread {
		private ReqOrderStall ros;
		private CacheUser cu;

		public DownThread(ReqOrderStall ros, CacheUser cu) {
			this.ros = ros;
			this.cu = cu;
		}

		public void run() {
			downStall(ros, cu);
		}
	}

	@Override
	public void down(ReqOrderStall ros, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		Thread thread = new DownThread(ros, cu);
		thread.start();
	}

	@Override
	public void downMsgPush(Long orderId, Long stallId) {
		ResUserOrder order = this.ordersClusterMapper.findDetail(orderId);
		Boolean switchStatus = false;
		Boolean downStatus = true;
		if (this.redisService.exists(RedisKey.ORDER_STALL_DOWN_FAILED.key + order.getId())) {
			Object count = this.redisService.get(RedisKey.ORDER_STALL_DOWN_FAILED.key + order.getId());
			log.info("downMsgPush failed times = {}",count.toString());
			if (Integer.valueOf(count.toString()) > 1) {
				switchStatus = true;
			}
			downStatus = false;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lockDownStatus", downStatus ? OperateStatus.SUCCESS.status : OperateStatus.FAILURE.status);
		param.put("lockDownTime", new Date());
		param.put("id", order.getId());
		this.orderMasterMapper.updateLockStatus(param);
		log.info("downing msg..................orderId:{} switchStatus:{} downStatus:{}", order.getId(), switchStatus, downStatus);
		if (switchStatus && !downStatus) {
			Thread thread = new PushThread(order.getUserId().toString(), "预约切换通知", "车位锁降下失败建议切换车位",
					PushType.ORDER_SWITCH_STATUS_NOTICE, true);
			thread.start();
		} else {
			Thread thread = new PushThread(order.getUserId().toString(), "预约降锁通知", downStatus ? "车位锁降下成功" : "车位锁降下失败",
					PushType.LOCK_DOWN_NOTICE, downStatus);
			thread.start();
		}
	}

	class CancelStallThread extends Thread {
		private Long stallId;

		public CancelStallThread(Long stallId) {
			this.stallId = stallId;
		}

		public void run() {
			stallClient.close(stallId);
		}
	}

	class OfflieStallThread extends Thread {
		private Long stallId;

		public OfflieStallThread(Long stallId) {
			this.stallId = stallId;
		}

		public void run() {
			stallClient.close(stallId);
		}
	}

	@Transactional(rollbackFor = RuntimeException.class)
	private void switching(ReqSwitch rs, CacheUser cu) {
		ResUserOrder order = this.ordersClusterMapper.findDetail(rs.getOrderId());
		Boolean flag = false;
		if (order.getStatus().intValue() == OrderStatus.UNPAID.value
				&& cu.getId().intValue() == order.getUserId().intValue()) {
			Long count = 0L;
			if (order.getBrandId() != null) {
				count = redisService.size(RedisKey.PREFECTURE_BRAND_FREE_STALL.key + order.getBrandId());
			} else {
				count = redisService.size(RedisKey.PREFECTURE_FREE_STALL.key + order.getPreId());
			}
			if (count.intValue() <= 0) {
				Map<String, Object> param = new HashMap<String, Object>();
				Date current = new Date();
				param.put("id", order.getId());
				param.put("endTime", current);
				param.put("updateTime", current);
				param.put("statusTime", current);
				param.put("statusHistory", OrderStatusHistory.CLOSED.code);
				param.put("status", OrderStatus.CLOSED.value);
				this.orderMasterMapper.updateClose(param);
				new CancelStallThread(order.getStallId()).start();
				Thread thread = new PushThread(order.getUserId().toString(), "订单通知", "无空闲车位,订单已关闭",
						PushType.ORDER_AUTO_CLOSE_NOTICE, true);
				thread.start();
				// 关闭订单发送优惠券功能
				couponClient.send(cu.getId());
				this.redisService.set(RedisKey.ORDER_SWITCH_RESULT.key + rs.getOrderId().longValue(),
						SwitchResult.CLOSED.value, ExpiredTime.ORDER_SWITCH_RESULT_TIME.time);
			} else {
				Object sn = null;
				if (order.getBrandId() != null) {
					sn = redisService.pop(RedisKey.PREFECTURE_BRAND_FREE_STALL.key + order.getBrandId());
				} else {
					sn = redisService.pop(RedisKey.PREFECTURE_FREE_STALL.key + order.getPreId());
				}
				log.info("get switch stall sn:{}", sn);
				if (sn != null) {
					ResStallEntity stall = this.stallClient.findByLock(sn.toString().trim());
					log.info("switch stall:{}", JsonUtil.toJson(stall));
					if (stall.getStatus().intValue() == StallStatus.FREE.status) {
						Thread thread = new OfflieStallThread(order.getStallId());
						order.setStallId(stall.getId());
						order.setStallName(stall.getStallName());
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("id", order.getId());
						param.put("stallId", stall.getId());
						param.put("stallName", stall.getStallName());
						param.put("switchTime", new Date());
						param.put("switchStatus", 1);
						this.orderMasterMapper.updateSwitch(param);
						this.stallClient.order(stall.getId());
						thread.start();
						flag = true;
					}
				}
				Thread thread = new PushThread(order.getUserId().toString(), "车位切换通知", flag ? "车位切换成功" : "车位切换失败",
						PushType.ORDER_SWITCH_RESULT_NOTICE, flag);
				thread.start();
				this.redisService.set(RedisKey.ORDER_SWITCH_RESULT.key + rs.getOrderId().longValue(),
						flag ? SwitchResult.SUCCESS.value : SwitchResult.FAILED.value,
						ExpiredTime.ORDER_SWITCH_RESULT_TIME.time);
			}

		} else {
			Thread thread = new PushThread(order.getUserId().toString(), "车位切换通知", "车位切换失败",
					PushType.ORDER_SWITCH_RESULT_NOTICE, false);
			thread.start();
			this.redisService.set(RedisKey.ORDER_SWITCH_RESULT.key + rs.getOrderId().longValue(),
					SwitchResult.FAILED.value, ExpiredTime.ORDER_SWITCH_RESULT_TIME.time);
		}
	}

	class SwitchThread extends Thread {
		private ReqSwitch rs;
		private CacheUser cu;

		public SwitchThread(ReqSwitch rs, CacheUser cu) {
			this.rs = rs;
			this.cu = cu;
		}

		public void run() {
			switching(rs, cu);
		}

	}

	@Override
	public void switchStall(ReqSwitch rs, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		Thread thread = new SwitchThread(rs, cu);
		thread.start();
	}

	@Override
	public List<ResCheckedOrder> list(Long start, String orderFlag ,HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", cu.getId());
		param.put("start", start);
		List<ResUserOrder> list = null;
		log.info("orderFlag = {}", orderFlag);
		if(orderFlag!= null && "1".equals(orderFlag)) {
			list = this.ordersClusterMapper.findUserList(param);
		}else {
			list = this.ordersClusterMapper.findFinishedUserList(param);
		}
		//List<ResUserOrder> list = this.ordersClusterMapper.findUserList(param);
		List<ResCheckedOrder> res = new ArrayList<ResCheckedOrder>();
		ResCheckedOrder ro = null;
		for (ResUserOrder ruo : list) {
			ro = new ResCheckedOrder();
			ro.copy(ruo);
			res.add(ro);
		}
		return res;
	}

	@Override
	public ResOrder current(HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		log.info("..........current order user :{}", JsonUtil.toJson(cu));
		ResUserOrder orders = this.ordersClusterMapper.findUserLatest(cu.getId()); // 查找最新
		if (orders == null) {
			return null;
		}
		
		ResOrder ro = null;
		if (orders != null && (orders.getStatus() == OrderStatus.UNPAID.value
				|| orders.getStatus() == OrderStatus.SUSPENDED.value)) {
			ro = new ResOrder();
			ro.copy(orders);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map<String, Object> param = new HashMap<String,Object>();
			param.put("stallId", orders.getStallId());
			param.put("plateNo", orders.getPlateNo());
			param.put("startTime", sdf.format(orders.getCreateTime()));
			if (orders.getStatus().intValue() == OrderStatus.SUSPENDED.value) {
				param.put("endTime", sdf.format(orders.getStatusTime()));
			} else {
				param.put("endTime", sdf.format(new Date()));
			}
			log.info("..........current order request param:{}", JSON.toJSON(param));
			Map<String, Object> map = this.strategyFeeClient.amount(param);
			log.info("..........current order response result map:{}", JSON.toJSON(map));
			if (map != null) {
				Object object = map.get("chargePrice");
				if (object != null) {
					ro.setTotalAmount(new BigDecimal(object.toString()));
					Map<String,Object> checkParam = new HashMap<String,Object>();
					checkParam.put("plateNo", orders.getPlateNo());
					checkParam.put("preId", orders.getPreId());
					boolean flag = userPlateClient.exists(checkParam);
					log.info("..........current order free plate :{}, flag :{}", orders.getPlateNo(), flag);
					if(flag) {
						ro.setTotalAmount(new BigDecimal(0.00));
					}
				}
			}
			
			ResPrefectureDetail pre = this.prefectureClient.findById(orders.getPreId());
			if (pre != null) {
				ro.setPreLongitude(pre.getLongitude());
				ro.setPreLatitude(pre.getLatitude());
				ro.setPrefectureAddress(pre.getAddress());
				ro.setGuideImage(pre.getRouteGuidance());
				ro.setGuideRemark(pre.getRouteDescription());
				ro.setUnderLayer(pre.getUnderLayer());
				ro.setPathFlag(pre.getPathFlag());
			}
			ResStallEntity stall = this.stallClient.findById(ro.getStallId());
			if (stall != null) {
				ro.setStallName(stall.getStallName());
				ro.setBluetooth(stall.getLockSn());
				//若车位含有楼层，覆盖车区楼层信息
				if(StringUtils.isNotBlank(stall.getFloor())) {
					ro.setUnderLayer(stall.getFloor());
				}
			}
			if(orders.getLockDownStatus() != null && orders.getLockDownStatus().intValue() == 1) {
				ro.setCancelFlag((short)2);
				ro.setDownFlag((short)1);
				log.info("..........current order lock down success");
			} else {
				//根据车位锁编号判断车锁状态是否为降下
				Map<String,Object> lockParam = stallClient.watch(orders.getStallId());
				log.info("..........current order lock down failed response result lock-param = {}", JSON.toJSON(lockParam));
				if(lockParam != null && !lockParam.isEmpty() && "200".equals(String.valueOf(lockParam.get("code"))) &&
						Integer.valueOf(lockParam.get("status").toString()) == LockStatus.DOWN.status) {
					ro.setCancelFlag((short)2);
					ro.setDownFlag((short)1);
				}
			}
			long beginTime = orders.getBeginTime().getTime();
			long now = new Date().getTime();
			if(orders.getStallId() != null) {
				Map<String,Object> feeParam = new HashMap<String,Object>();
				feeParam.put("stallId", orders.getStallId());
				int freeMins = strategyFeeClient.freeMins(feeParam);
				ro.setFreeMins(freeMins);
				log.info("..........current order free mins {}", freeMins);
				if(now >= beginTime + freeMins * 60 * 1000){
					ro.setCancelFlag((short)2);
					ro.setRemainMins(0);
				}else {
					ro.setRemainMins(getSecondTime(beginTime + freeMins * 60 * 1000 - now));
				}
			}
			
			//当前用户当天取消订单数
			List<ResUserOrder> ordersList = ordersClusterMapper.getDayOfCanceOrderlList(cu.getId());
			//4.判断当天取消预约次数是否超过5次
			if(null != ordersList && ordersList.size() >= baseConfig.getCancelNumber()){
				ro.setCancelFlag((short)2);
            }
			
			if(orders.getStatus() == OrderStatus.SUSPENDED.value) {
				//当订单处于挂起状态时，直接结账离场
				ro.setCancelFlag((short)2);
				ro.setDownFlag((short)2);
			}
			log.info("..........current order {}", JSON.toJSON(ro));
		}
		
		return ro;
	}
	
	private static int getSecondTime(Long time) {
		int seconds = (int) (time % 1000 == 0 ? time / 1000 : time / 1000 + 2);
		return seconds;
	}

	@Override
	public Integer downResult(HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		ResUserOrder orders = this.ordersClusterMapper.findUserLatest(cu.getId());
		Integer count = 0;
		Object o = this.redisService.get(RedisKey.ORDER_STALL_DOWN_FAILED.key + orders.getId());
		if (o != null) {
			count = new Integer(o.toString());
		}
		//如果为蓝牙降锁成功，回调时直接获取车位状态
		if(count == 1) {
			long startTime = System.currentTimeMillis();
			Map<String,Object> lockParam = stallClient.watch(orders.getStallId());
			long endTime = System.currentTimeMillis();
			log.info("downResult.....................{}获取降锁状态时间:{}秒", JSON.toJSON(lockParam), (endTime - startTime)/1000);
			if("200".equals(String.valueOf(lockParam.get("code"))) && Integer.valueOf(lockParam.get("status").toString()) == LockStatus.DOWN.status) {
				count = 0;
			}
		}
		log.info(">>>>>>>>>>>>>>>>>>>>downResult orderId ={} fail_num = {}",orders.getId(), count);
		return count;
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<ViewFilter> filters = pageable.getFilters();
		if (StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if (filters != null && filters.size() > 0) {
			for (ViewFilter filter : filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		if (StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.ordersClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResOrder> list = this.ordersClusterMapper.findPage(param);
		return new ViewPage(count, pageable.getPageSize(), list);
	}

	@Override
	public List<ResOrderExcel> exportList(ReqOrderExcel bean) {
		return this.ordersClusterMapper.exportList(bean);
	}

	@Override
	public List<ResPreOrderCount> findPreCountByIds(List<Long> id) {
		return this.ordersClusterMapper.findPreCountByIds(id);
	}

	@Override
	public List<ResOrderPlate> findPlateByPreId(Long preId) {
		List<ResOrderPlate> plates = this.ordersClusterMapper.findPlateByPreId(preId);
		return plates;
	}

	@Override
	public void brandCreate(ReqBrandBooking rb, HttpServletRequest request) {
		log.info("brand order :{}", JsonUtil.toJson(rb));
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		log.info(" order user is {}", JsonUtil.toJson(cu));
		ResBrandPre brand = entBrandPreClient.findById(rb.getBrandId());
		if (brand == null) {
			throw new BusinessException(StatusEnum.ORDER_CREATE_FAIL); // 预约失败请重新预约
		} else {
			if (brand.getLimitStatus().intValue() == 1) {
				log.info("check assign plate no");
				ResVechicleMark vehicleMark = vehicleMarkClient.findById(rb.getPlateId());
				log.info("user plate:{}", JsonUtil.toJson(vehicleMark));
				if (vehicleMark != null) {
					String vehMark = vehicleMark.getVehMark(); // 车牌号
					boolean flag = entBrandUserClient.checkExist(brand.getEntId(), vehMark);

					log.info("exist plate :{}", flag);
					if (!flag) {
						boolean assignFlag = false;
						Set<Object> set = this.redisService.members(RedisKey.ORDER_ASSIGN_STALL.key); // 集合中所有成员元素
						for (Object obj : set) {
							JSONObject json = JSON.parseObject(obj.toString());
							String vm = json.get("plate").toString(); // 车牌
							Long pid = Long.parseLong(json.get("preId").toString()); // 车区id
							log.info("assing:{}", JsonUtil.toJson(json));
							if (pid.longValue() == rb.getPrefectureId() && vehMark.equals(vm)) { // 找到车区
								String lockSn = json.get("lockSn").toString();
								List<ResBrandStall> brandStallList = entBrandPreClient.brandStallList(rb.getBrandId());
								log.info("lockSn={}, brandStallList = {}", lockSn, JSON.toJSON(brandStallList));
								if (CollectionUtils.isNotEmpty(brandStallList)) {
									for (ResBrandStall stall : brandStallList) {
										if (lockSn.equals(stall.getLockSn())) {
											assignFlag = true;
											break;
										}
									}
								}
							}
						}
						log.info("assing :{}", assignFlag);
						if (!assignFlag) {
							log.info("brand user {} create order error with {}", cu.getMobile(), vehMark);
							throw new BusinessException(StatusEnum.ORDER_REASON_BRAND_USER_NONE);
						}
					}
				} else {
					throw new BusinessException(StatusEnum.ORDER_REASON_CARNO_NONE);
				}
			}
		}

		Thread thread = new OrderBrandThread(rb, cu);
		thread.start();
	}

	@Override
	public BigDecimal findPreDayIncome(Long preId) {
		// Date date = getDateByType(type);
		Map<String, Object> map = new HashMap<>();
		map.put("startTime", new Date());
		map.put("preId", preId);
		return ordersClusterMapper.findPreDayIncome(map);
	}

	@Override
	public Map<String, Object> findTrafficFlow(Map<String, Object> map) {
		Date date = getDateByType((short) Short.parseShort(map.get("startTime").toString()));
		short type = Short.parseShort(map.get("startTime").toString());
		map.put("startTime", date);
		Integer integer = this.ordersClusterMapper.findTrafficFlow(map);
		List<ResPreDataList> list = this.ordersClusterMapper.findPreDataList(map);
		int num = 0;
		switch (type) {
		case 0:
			num = 6;
			break;
		case 1:
			num = 14;
			break;
		case 2:
			num = 29;
			break;
		default:
			break;
		}
		List<ResPreDataList> lists = new ArrayList<>();
		ResPreDataList pre = null;
		for (; num >= 0; num--) {
			Date month = DateUtils.getDateByDay(-num);
			Date convert = DateUtils.convert(month, null);
			boolean falg = true;
			for (ResPreDataList resPreDataList : list) {
				if (resPreDataList.getDayTime().getTime() == convert.getTime()) {
					lists.add(resPreDataList);
					falg = false;
					break;
				}
			}
			if (falg) {
				pre = new ResPreDataList();
				pre.setDayNumber(0);
				pre.setDayAmount(new BigDecimal(0));
				pre.setDayTime(month);
				lists.add(pre);
			}
		}
		map = new HashMap<>();
		map.put("number", integer);
		map.put("list", lists);
		return map;
	}

	@Override
	public Integer findTrafficFlowCount(Map<String, Object> param) {
		Date date = getDateByType((short) Short.parseShort(param.get("startTime").toString()));
		param.put("startTime", date);
		return this.ordersClusterMapper.findTrafficFlowCount(param);
	}

	@Override
	public Map<String, Object> findProceeds(Map<String, Object> map) {
		Date date = getDateByType((short) Short.parseShort(map.get("startTime").toString()));
		short type = Short.parseShort(map.get("startTime").toString());
		map.put("startTime", date);
		BigDecimal decimal = this.ordersClusterMapper.findProceeds(map);
		List<ResPreDataList> list = this.ordersClusterMapper.findPreDataList(map);
		int num = 0;
		switch (type) {
		case 0:
			num = 6;
			break;
		case 1:
			num = 14;
			break;
		case 2:
			num = 29;
			break;
		default:
			break;
		}
		List<ResPreDataList> lists = new ArrayList<>();
		ResPreDataList pre = null;
		for (; num >= 0; num--) {
			Date month = DateUtils.getDateByDay(-num);
			Date convert = DateUtils.convert(month, null);
			boolean falg = true;
			for (ResPreDataList resPreDataList : list) {
				if (resPreDataList.getDayTime().getTime() == convert.getTime()) {
					lists.add(resPreDataList);
					falg = false;
					break;
				}
			}
			if (falg) {
				pre = new ResPreDataList();
				pre.setDayNumber(0);
				pre.setDayAmount(new BigDecimal(0));
				pre.setDayTime(month);
				lists.add(pre);
			}
		}

		// map.put("pageSize", 10);
		// map.put("start", getPageNo(map.get("pageNo")));

		map = new HashMap<>();
		map.put("amount", decimal);
		map.put("list", lists);
		return map;
	}

	@Override
	public BigDecimal findProceedsAmount(Map<String, Object> param) {
		Date date = getDateByType((short) Short.parseShort(param.get("startTime").toString()));
		param.put("startTime", date);
		BigDecimal decimal = this.ordersClusterMapper.findProceedsAmount(param);
		return decimal;
	}

	@Override
	public List<ResChargeDetail> findChargeDetail(Map<String, Object> param) {
		param.put("start", getPageNo(param.get("pageNo")));
		param.put("pageSize", 10);
		param.put("startTime", new Date());
		List<ResChargeDetail> list = this.ordersClusterMapper.findChargeDetail(param);
		for (ResChargeDetail detail : list) {
			if (detail.getMonth() == detail.getMonth()) {
				String str = DateUtils.getDurationDetail(detail.getEndTime(), detail.getStartTime());
				detail.setStopTime(str);
			}
		}
		return list;
	}

	/*
	 * @Override public List<ResCharge> findChargeDetailNew(Map<String, Object>
	 * param) { Date date = getDateByType((short)
	 * Short.parseShort(param.get("startTime").toString())); param.put("startTime",
	 * date); List<ResCharge> charges = new ArrayList<>(); ResMonthCount count =
	 * this.ordersClusterMapper.findMonthCountByDate(param); List<ResChargeDetail>
	 * list = this.ordersClusterMapper.findChargeDetail(param); ResCharge charge =
	 * null; List<ResChargeDetail> chargeDetails = null; for (ResMonthCount
	 * resMonthCount : count) { charge = new ResCharge(); chargeDetails = new
	 * ArrayList<>(); for (ResChargeDetail detail : list) { if (detail.getMonth() ==
	 * resMonthCount.getMonth()) { String str = DateUtils.getDuration(new Date(),
	 * detail.getEndTime()); detail.setStopTime(str); chargeDetails.add(detail); } }
	 * charge.setCharge(chargeDetails);
	 * charge.setDate(chargeDetails.get(0).getStartTime()); charges.add(charge); }
	 * return charges; }
	 */
	@Override
	public List<ResTrafficFlow> findTrafficFlowList(Map<String, Object> param) {
		Date date = getDateByType(Short.parseShort(param.get("startTime").toString()));
		param.put("startTime", date);
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(Long.decode(param.get("now").toString()));
		Date now = instance.getTime();
		Map<String, Date> map = getStartEndDate(now);
		param.put("monthStart", map.get("monthStart"));
		param.put("monthEnd", map.get("monthEnd"));
		param.put("pageSize", 10);
		param.put("start", getPageNo(param.get("pageNo")));
		List<Date> dates = DateUtils.getDates(now, Calendar.DAY_OF_MONTH, 10);
		param.put("dates",dates);
		List<ResTrafficFlowList> list = this.ordersClusterMapper.findTrafficFlowList(param);
		List<ResTrafficFlowList> newList = new ArrayList<ResTrafficFlowList>();
		ResTrafficFlowList in = null;
		for (Date da : dates) {
			boolean falg = true;
			for (ResTrafficFlowList resTrafficFlowList : list) {
				if (da.getTime() == resTrafficFlowList.getDate().getTime()) {
					newList.add(resTrafficFlowList);
					falg = false;
					continue;
				}
			}
			if (falg) {
				in = new ResTrafficFlowList();
				in.setDate(da);
				in.setCarDayTotal(0);
				in.setMonth(DateUtils.getFieldDataByDate(da, Calendar.MONTH));
				newList.add(in);
			}
		}
		// List<ResMonthCount> monthCount =
		// this.ordersClusterMapper.findMonthCount(param);
		ResMonthCount monthCount = this.ordersClusterMapper.findMonthCountByDate(param);
		List<ResTrafficFlow> flows = new ArrayList<>();
		ResTrafficFlow flow = null;
		Set<Integer> collect = newList.stream().map(traffic -> traffic.getMonth()).collect(Collectors.toSet());
		if (collect.size() > 1) {
			for (Integer integer : collect) {
				if (integer.shortValue() == monthCount.getMonth()) {
					flow = new ResTrafficFlow();
					List<ResTrafficFlowList> lists = new ArrayList<>();
					for (ResTrafficFlowList resTrafficFlowList : newList) {
						if (resTrafficFlowList.getMonth().equals(integer)) {
							lists.add(resTrafficFlowList);
						}
					}
					flow.setCarMonthTotal(monthCount.getMonthCarCount());
					flow.setTime(lists.get(0).getDate());
					flow.setTrafficFlows(lists);
					if (flows.size() == 0) {
						flows.add(flow);
					} else {
						flows.add(flows.set(0, flow));
					}
				} else {
					map = getStartEndDate(integer);
					param.put("monthStart", map.get("monthStart"));
					param.put("monthEnd", map.get("monthEnd"));
					ResMonthCount resMonthCount = this.ordersClusterMapper.findMonthCountByDate(param);
					flow = new ResTrafficFlow();
					List<ResTrafficFlowList> lists = new ArrayList<>();
					for (ResTrafficFlowList resTrafficFlowList : newList) {
						if (resTrafficFlowList.getMonth().equals(integer)) {
							lists.add(resTrafficFlowList);
						}
					}
					flow.setCarMonthTotal(resMonthCount.getMonthCarCount());
					flow.setTime(lists.get(0).getDate());
					flow.setTrafficFlows(lists);
					flows.add(flow);
				}
			}
		} else {
			flow = new ResTrafficFlow();
			flow.setCarMonthTotal(monthCount.getMonthCarCount());
			flow.setTime(map.get("monthStart"));
			flow.setTrafficFlows(newList);
			flows.add(flow);
		}
		/*
		 * for (ResMonthCount resMothCount : monthCount) { List<ResTrafficFlowList>
		 * collect = list.stream() .filter(month -> month.getMonth() ==
		 * resMothCount.getMonth()).collect(Collectors.toList()); if (collect.size() !=
		 * 0) { flow = new ResTrafficFlow(); flow.setTime(collect.get(0).getDate());
		 * flow.setCarMonthTotal(resMothCount.getMonthCarCount());
		 * flow.setTrafficFlows(collect); } flowLists.add(flow); }
		 */
		return flows;
	}

	@Override
	public List<ResIncome> findIncomeList(Map<String, Object> param) {
		Date date = getDateByType(Short.parseShort(param.get("startTime").toString()));
		param.put("startTime", date);
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(Long.decode(param.get("now").toString()));
		Date now = instance.getTime();
		Map<String, Date> map = getStartEndDate(now);
		param.put("monthStart", map.get("monthStart"));
		param.put("monthEnd", map.get("monthEnd"));
		param.put("pageSize", 10);
		param.put("start", getPageNo(param.get("pageNo")));
		ResMonthCount months = this.ordersClusterMapper.findMonthCountByDate(param);
		List<Date> dates = DateUtils.getDates(now, Calendar.DAY_OF_MONTH, 10);
		param.put("dates", dates);
		List<ResIncomeList> list = this.ordersClusterMapper.findIncomeList(param);
		List<ResIncomeList> newList = new ArrayList<>();
		ResIncomeList in = null;
		for (Date da : dates) {
			boolean falg = true;
			for (ResIncomeList resIncomeList : list) {
				if (da.getTime() == resIncomeList.getDate().getTime()) {
					newList.add(resIncomeList);
					falg = false;
					continue;
				}
			}
			if (falg) {
				in = new ResIncomeList();
				in.setDate(da);
				in.setDayAmount(new BigDecimal(0));
				in.setMonth(DateUtils.getFieldDataByDate(da, Calendar.MONTH));
				newList.add(in);
			}
		}

		ResIncome income = null;
		List<ResIncome> incomes = new ArrayList<>();
		Set<Integer> collect = newList.stream().map(i -> i.getMonth()).collect(Collectors.toSet());
		List<ResIncomeList> lists = new ArrayList<>();
		if (collect.size() > 1) {
			for (Integer integer : collect) {
				income = new ResIncome();
				if (integer.intValue() == months.getMonth()) {
					lists = new ArrayList<>();
					for (ResIncomeList resIncomeList : newList) {
						if (resIncomeList.getMonth().equals(integer)) {
							lists.add(resIncomeList);
						}
					}
					income.setList(lists);
					income.setMonthAmount(months.getMonthAmount());
					income.setDate(newList.get(0).getDate());
					if (incomes.size() == 0) {
						incomes.add(income);
					} else {
						incomes.add(incomes.set(0, income));
					}
				} else {
					lists = new ArrayList<>();
					map = getStartEndDate(newList.get(newList.size() - 1).getMonth());
					param.put("monthStart", map.get("monthStart"));
					param.put("monthEnd", map.get("monthEnd"));
					ResMonthCount monthCount = this.ordersClusterMapper.findMonthCountByDate(param);
					for (ResIncomeList resIncomeList : newList) {
						if (integer.equals(resIncomeList.getMonth())) {
							lists.add(resIncomeList);
						}
					}
					income.setList(lists);
					income.setMonthAmount(monthCount.getMonthAmount());
					income.setDate(lists.get(0).getDate());
					incomes.add(income);
				}
			}
		} else {
			income = new ResIncome();
			income.setList(newList);
			income.setMonthAmount(months.getMonthAmount());
			income.setDate(map.get("monthStart"));
			incomes.add(income);
		}
		/*
		 * for (ResMonthCount resMonthCount : months) { List<ResIncomeList> collect =
		 * list.stream().filter(month -> month.getMonth() == resMonthCount.getMonth())
		 * .collect(Collectors.toList()); if (collect.size() != 0) { income = new
		 * ResIncome(); income.setList(collect);
		 * income.setDate(collect.get(0).getDate());
		 * income.setMonthAmount(resMonthCount.getMonthAmount()); incomes.add(income); }
		 * }
		 */
		return incomes;
	}

	public static Date getDateByType(Short type) {
		Date date = null;
		if (type == 0) {
			date = DateUtils.getPast2Date(+6);
		} else if (type == 1) {
			date = DateUtils.getPast2Date(+14);
		} else if (type == 2) {
			date = DateUtils.getPast2Date(+29);
		}
		return date;
	}

	public static Map<String, Date> getStartEndDate(int date) {
		Map<String, Date> map = new HashMap<>();
		Date monthStart = null;
		Date monthEnd = null;
		if (date == 0) {
			Calendar instance = Calendar.getInstance();
			instance.setTime(new Date());
			instance.set(Calendar.DAY_OF_MONTH, 1);
			monthStart = instance.getTime();
			instance.set(Calendar.DAY_OF_MONTH, 1);
			instance.set(Calendar.MONTH, instance.get(Calendar.MONDAY) + 1);
			monthEnd = instance.getTime();
		} else {
			Calendar instance = Calendar.getInstance();
			instance.setTime(new Date());
			instance.set(Calendar.MONTH, date - 1);
			instance.set(Calendar.DAY_OF_MONTH, 1);
			monthStart = instance.getTime();
			instance.set(Calendar.DAY_OF_MONTH, 1);
			instance.set(Calendar.MONTH, date);
			monthEnd = instance.getTime();
		}
		map.put("monthStart", monthStart);
		map.put("monthEnd", monthEnd);
		return map;
	}

	public static Map<String, Date> getStartEndDate(Date date) {
		Map<String, Date> map = new HashMap<>();
		Date monthStart = null;
		Date monthEnd = null;
		date = DateUtils.getDateByDay(date, -1);
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		instance.set(Calendar.DAY_OF_MONTH, 1);
		monthStart = instance.getTime();
		monthEnd = DateUtils.getDateByMonth(monthStart, +1);
		map.put("monthStart", monthStart);
		map.put("monthEnd", monthEnd);
		return map;
	}

	@Override
	public ResEntOrder findOrderByStallId(Long stallId) {
		return this.ordersClusterMapper.findOrderByStallId(stallId);
	}

	public static int getPageNo(Object pageNo) {
		Integer start = null;
		if (pageNo == null) {
			start = 1;
		} else {
			String str = pageNo.toString();
			start = Integer.parseInt(str);
			if (start == 0) {
				start = 1;
			}
		}
		return (start - 1) * 10;
	}

	@Override
	public void appoint(ReqStallBooking rsb, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		log.info("choose stall request param :{} cu:{}", JsonUtil.toJson(rsb), JsonUtil.toJson(cu));
		if(cu != null) {
			// 争抢
			String robkey = RedisKey.ROB_STALL_ISHAVE.key + rsb.getStallId();
			Boolean have = true;
			try {
				have = this.redisLock.getLock(robkey, cu.getId());
				log.info("用户=======>" + cu.getId() + (have == true ? "已抢到" : "未抢到") + "锁" + robkey);
			} catch (Exception e) {
				log.info("用户争抢锁异常信息{}",e.getMessage());
			}
			if (!have) {
				throw new BusinessException(StatusEnum.APPOINT_FAIL_CHECK);
			}else {
				Thread thread = new StallOrderThread(rsb, cu);
				thread.start();
			}
		}
	}

	@Override
	public ResUserOrder findStallLatest(Long stallId) {
		return this.ordersClusterMapper.findStallLatest(stallId);
	}

	@Override
	public void downWYMsgPush(Long orderId, Long stallId) {
		ResUserOrder order = this.ordersClusterMapper.findDetail(orderId);
		Boolean switchStatus = false;
		Boolean downStatus = true;
		if (this.redisService.exists(RedisKey.ORDER_STALL_DOWN_FAILED.key + order.getId())) {
			Object count = this.redisService.get(RedisKey.ORDER_STALL_DOWN_FAILED.key + order.getId());
			if (Integer.valueOf(count.toString()) > 1) {
				switchStatus = true;
			}
			downStatus = false;
		}

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lockDownStatus", downStatus ? OperateStatus.SUCCESS.status : OperateStatus.FAILURE.status);
		param.put("lockDownTime", new Date());
		param.put("id", order.getId());
		this.orderMasterMapper.updateLockStatus(param);
		log.info("stall downing :{}", switchStatus);

		if (switchStatus && !downStatus) {
			Thread thread = new PushWYThread(order.getUserId().toString(), "预约切换通知", "车位锁降下失败建议切换车位",
					PushType.ORDER_SWITCH_STATUS_NOTICE, true);
			thread.start();
		} else {
			Thread thread = new PushWYThread(order.getUserId().toString(), "预约降锁通知", downStatus ? "车位锁降下成功" : "车位锁降下失败",
					PushType.LOCK_DOWN_NOTICE, downStatus);
			thread.start();
		}
	}

	@Override
	public void updateLockStatus(Map<String, Object> param) {
		param.put("lockDownTime", new Date());
		param.put("id", param.get("orderId"));
		param.put("beginTime",null);
		this.orderMasterMapper.updateLockStatus(param);
	}

	public Integer getPlateLastOrderStatus(String carno) {
		return this.ordersClusterMapper.getPlateLastOrderStatus(carno);
	}

	@Override
	public ResUserOrder getOrderById(Long id) {
		return this.ordersClusterMapper.findDetail(id);
	}
	
	@Override
	public void updateClose(Map<String, Object> param) {
		 this.orderMasterMapper.updateClose(param);
	}

	@Override
	public void updateDetail(Map<String, Object> param) {
		this.ordersDetailMasterMapper.updateT(param);
	}

	@Override
	public void savelog(ResOrderOperateLog resOrderOperateLog) {
		this.ordersDetailMasterMapper.savelog(resOrderOperateLog);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void cancel(Long orderId, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		Orders order = this.ordersClusterMapper.findById(orderId);
		log.info("...........cancel order :{},cu:{}", JsonUtil.toJson(order), JsonUtil.toJson(cu));
		//判断订单是否属于当前登录用户
		if(order.getUserId().equals(cu.getId())){
			try {
				if(order.getOrderNo().indexOf("YL")>=0){
					throw new BusinessException(StatusEnum.ORDER_CHECK_YL_ORDER); // 预约失败
	    		}
				//1.判断订单状态是否为未支付状态
				if(order.getStatus() == 6){
					throw new BusinessException(StatusEnum.ORDER_WAS_PENDING);
				}
				if(order.getStatus() != 1){
					throw new BusinessException(StatusEnum.ORDER_STATUS_EXPIRE);
				}
				//根据车位锁编号判断车锁状态是否为降下
				Map<String,Object> lockParam = stallClient.watch(order.getStallId());
				log.info("...........cancel order response result lock param = {}", JSON.toJSON(lockParam));
				if("200".equals(String.valueOf(lockParam.get("code"))) && Integer.valueOf(lockParam.get("status").toString()) == LockStatus.DOWN.status) {
					throw new BusinessException(StatusEnum.ORDER_LOCK_DOWN);
				}
				long beginTime = order.getBeginTime().getTime();
				long now = new Date().getTime();
				if(order.getStallId() != null) {
					Map<String,Object> feeParam = new HashMap<String,Object>();
					feeParam.put("stallId", order.getStallId());
					int freeMins = strategyFeeClient.freeMins(feeParam);
					log.info("...........cancel order free mins = {}", freeMins);
					if(now - beginTime > freeMins * 60 * 1000){
						throw new BusinessException(StatusEnum.ORDER_CANCEL_TIMEOUT);
					}
				}
				//3.判断当前订单是否超过免费分钟数
				List<ResUserOrder> ordersList = ordersClusterMapper.getDayOfCanceOrderlList(cu.getId());
				//4.判断当天取消预约次数是否超过5次
				if(null != ordersList && ordersList.size() >= baseConfig.getCancelNumber()){
					throw new BusinessException(StatusEnum.ORDER_CANCEL_MORETIMES);
	            }
				
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("endTime", new Date());
				param.put("status", 4);
				param.put("updateTime", new Date());
				param.put("id", order.getId());
				orderMasterMapper.updateClose(param);
				boolean cancelFlag = stallClient.cancel(order.getStallId());
				log.info("...........cancel order cancel falg = {}", cancelFlag);
				if (StringUtils.isNotBlank(order.getDockId())) {
					// 取消预约 新版停车场 推送消息
					Thread thread = new ProduceCancelThread(order);
					thread.start();
				}
			} catch (Exception e) {
				throw new BusinessException(StatusEnum.ORDER_CANCEL_FAILED);
			}
		} else {
			throw new BusinessException(StatusEnum.ORDER_USERID_ERROR);
		}
	}
	
	class ProduceCancelThread extends Thread {
		private Orders order;

		public ProduceCancelThread(Orders order) {
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
				log.info("call park producer error with cancel msg");
			}
		}
	}

	@Transactional(rollbackFor = RuntimeException.class)
	private void order(Long prefectureId, Long plateId, Long brandId, Long stallId, String orderSource, CacheUser cu) {
		ResStallEntity stall = null;
		Orders o = null;
		boolean resetRedis = true;
		short failureReason = 0;
		short bookingStatus = 0;
		log.info("cu:{} booking preId:{},plateId:{},brandId:{},stallId:{} orderSource:{}", cu.getMobile(), prefectureId, plateId,
				brandId, stallId, orderSource);
		try {
			synchronized (this) {
				if (ORDER_USER_SET.contains(cu.getId())) { // ？？？
					bookingStatus = (short) OperateStatus.FAILURE.status;
					failureReason = (short) OrderFailureReason.UNPAID.value;
					throw new BusinessException(StatusEnum.ORDER_CREATE_FAIL); // 预约失败
				}
				ORDER_USER_SET.add(cu.getId());
				if (stallId != null) {
					if (STALL_ID_SET.contains(stallId)) { // ？？？
						bookingStatus = (short) OperateStatus.FAILURE.status;
						failureReason = (short) OrderFailureReason.UNPAID.value;
						throw new BusinessException(StatusEnum.ORDER_CREATE_FAIL); // 预约失败
					}
					STALL_ID_SET.add(stallId);
					log.info(".................1  STALL_ID_SET = {}", JSON.toJSON(STALL_ID_SET));
				}
			}
			ResUserOrder ruo = this.ordersClusterMapper.findUserLatest(cu.getId()); // 找到最新一单
			if (ruo != null && (ruo.getStatus().intValue() == OrderStatus.UNPAID.value
					|| ruo.getStatus().intValue() == OrderStatus.SUSPENDED.value)) {
				bookingStatus = (short) OperateStatus.FAILURE.status;
				failureReason = (short) OrderFailureReason.UNPAID.value;
				throw new BusinessException(StatusEnum.ORDER_CREATE_FAIL); // 有订单
			}
			if (null == cu.getId() || null == prefectureId || null == plateId) {
				bookingStatus = (short) OperateStatus.FAILURE.status;
				failureReason = (short) OrderFailureReason.EXCEPTION.value;
				throw new BusinessException(StatusEnum.ORDER_CREATE_FAIL); // 预约失败请重新预约
			}
			ResVechicleMark vehicleMark = vehicleMarkClient.findById(plateId); // 车牌号管理表

			if (vehicleMark.getUserId().longValue() != cu.getId().longValue()) { // 无空闲车位？？
				bookingStatus = (short) OperateStatus.FAILURE.status;
				failureReason = (short) OrderFailureReason.CARNO_NONE.value;
				throw new BusinessException(StatusEnum.ORDER_REASON_CARNO_NONE);
			}
			if (!this.checkCarFree(vehicleMark.getVehMark())) {
				bookingStatus = (short) OperateStatus.FAILURE.status;
				failureReason = (short) OrderFailureReason.CARNO_BUSY.value;
				throw new BusinessException(StatusEnum.ORDER_REASON_CARNO_BUSY); // 当前车牌号已在预约中，请更换车牌号重新预约
			}

			ResPrefectureDetail pre = prefectureClient.findById(prefectureId);
			log.info("order-pre:{}", JsonUtil.toJson(pre));
			String lockSn = "";
			ResBrandPre brand = null;
			boolean assign = false;
			if (stallId != null) {
				boolean flag = false;
				stall = this.stallClient.findById(stallId);
				if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
					lockSn = stall.getLockSn();
					log.info(".................2  order-stall-lockSn,{}", lockSn);
					if(pre != null  && pre.getCategory() == 2) {
						//根据lockCode查询当前车位是否降下并且上方无车
						//若上方无车则升起地锁
						flag = true;
						Thread thread = new UplockThread(stallId);
						thread.start();
					}else {
						Set<Object> lockSnList = this.redisService
								.members(RedisKey.PREFECTURE_FREE_STALL.key + prefectureId); // 集合中所有成员元素
						log.info(".................3  lockSnList = {}",JSON.toJSON(lockSnList));
						if (CollectionUtils.isNotEmpty(lockSnList)) {
							if (lockSnList.contains(lockSn)) {
								flag = true;
								log.info("current lockSn is free, flag = {}", flag);
								log.info("before remove size = {}",this.redisService.size(RedisKey.PREFECTURE_FREE_STALL.key + prefectureId));
								this.redisService.remove(RedisKey.PREFECTURE_FREE_STALL.key + prefectureId, lockSn);
								log.info("after remove size = {}",this.redisService.size(RedisKey.PREFECTURE_FREE_STALL.key + prefectureId));
								this.redisService.set(RedisKey.PREFECTURE_BUSY_STALL.key + lockSn, lockSn,
										ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
							}
						}
					}
					log.info(">>>>>>>4  current lockSn flag = {}", flag);
					if (!flag) {
						resetRedis = false;
						bookingStatus = (short) OperateStatus.FAILURE.status;
						failureReason = (short) OrderFailureReason.STALL_NONE.value;
						throw new BusinessException(StatusEnum.ORDER_REASON_STALL_ORDERED);
					}
				}
			} else {
				String key = RedisKey.ORDER_ASSIGN_STALL.key; // assign_lock
				Set<Object> set = this.redisService.members(RedisKey.ORDER_ASSIGN_STALL.key); // 集合中所有成员元素
				String vehMark = vehicleMark.getVehMark(); // 车牌号
				for (Object obj : set) {
					JSONObject json = JSON.parseObject(obj.toString());
					String vm = json.get("plate").toString(); // 车牌
					Long pid = Long.parseLong(json.get("preId").toString()); // 车区id
					if (pid.longValue() == prefectureId.longValue() && vehMark.equals(vm)) { // 找到车区
						lockSn = json.get("lockSn").toString();
						Map<String, Object> map = new HashMap<>();
						map.put("lockSn", lockSn);
						map.put("plate", vm);
						map.put("preId", prefectureId);
						String val = JSON.toJSON(map).toString();
						log.info("use the admin assign key {}, val {}", key, val);
						this.redisService.set(RedisKey.PREFECTURE_BUSY_STALL.key + lockSn, lockSn,
								ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
						this.redisService.remove(key, val);
						assign = true;
						log.info("use the admin assign stall:{},plate:{}", lockSn, vehicleMark.getVehMark());
						break;
					}
				}
				if (brandId != null) {
					log.info("brand pre lockSn:{}", lockSn);
					brand = entBrandPreClient.findById(brandId);
					log.info("brandId {},brand {}", JSON.toJSON(brand));
					if ("".equals(lockSn)) {
						Object sn = redisService.pop(RedisKey.PREFECTURE_BRAND_FREE_STALL.key + brandId);
						if (sn != null) {
							this.redisService.set(RedisKey.PREFECTURE_BUSY_STALL.key + sn.toString(), sn.toString(),
									ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
							lockSn = sn.toString();
						}
					}

				} else {
					log.info("common pre lockSn:{}", lockSn);
					// 以下为预约流程
					if ("".equals(lockSn)) {
						Object sn = redisService.pop(RedisKey.PREFECTURE_FREE_STALL.key + prefectureId);
						if (sn != null) {
							this.redisService.set(RedisKey.PREFECTURE_BUSY_STALL.key + sn.toString(), sn.toString(),
									ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
							lockSn = sn.toString();
						}
					}
				}
			}

			if (StringUtils.isEmpty(lockSn)) {
				bookingStatus = (short) OperateStatus.FAILURE.status;
				failureReason = (short) OrderFailureReason.STALL_NONE.value;
				throw new BusinessException(StatusEnum.ORDER_REASON_STALL_NONE); // 无空闲车位，请重新预约
			}
			// 根据lockSn获取车位
			log.info("lock,{}", lockSn);
			stall = this.stallClient.findByLock(lockSn.trim());
			log.info("order-stall:{}", JsonUtil.toJson(stall));
			if (stall == null || stall.getStatus().intValue() != StallStatus.FREE.status) {
				resetRedis = false;
				bookingStatus = (short) OperateStatus.FAILURE.status;
				failureReason = (short) OrderFailureReason.STALL_EXCEPTION.value;
				log.info("{} create order error with {}", cu.getMobile(), JsonUtil.toJson(stall));
				throw new BusinessException(StatusEnum.ORDER_REASON_STALL_EXCEPTION);
			}
			ResUserOrder latest = this.ordersClusterMapper.findStallLatest(stall.getId());
			if (latest != null && latest.getStatus().intValue() == 1) {
				bookingStatus = (short) OperateStatus.FAILURE.status;
				failureReason = (short) OrderFailureReason.STALL_ORDERED.value;
				resetRedis = false;
				log.info("{} create order error latest order {} is unpaid with stall  ", cu.getMobile(),
						JsonUtil.toJson(latest), JsonUtil.toJson(stall));
				throw new BusinessException(StatusEnum.ORDER_REASON_STALL_ORDERED);
			}
			log.info("{} create order with{}", cu.getMobile(), JsonUtil.toJson(stall));
			o = new Orders(); // 插入订单
			o.setOrderNo(this.getOrderNumber());
			o.setUserType((short) 0);
			Date current = new Date();
			o.setPlateNo(vehicleMark.getVehMark());
			o.setUsername(cu.getMobile());
			o.setActualAmount(new BigDecimal(0.0d));
			o.setBeginTime(current);
			o.setCreateTime(current);
			o.setUpdateTime(current);
			o.setEndTime(current);
			o.setStrategyId(pre.getStrategyId());
			// 支付类型1免费2优惠券3账户
			// 初始化支付类型为账户支付
			o.setPayType(OrderPayType.FREE.type);
			o.setPreId(prefectureId);
			o.setStallId(stall.getId());
			o.setPreName(pre.getName());
			o.setStallName(stall.getStallName());
			o.setStatus(OrderStatus.UNPAID.value);
			o.setTotalAmount(new BigDecimal(0.0D));
			o.setUserId(cu.getId());
			o.setUsername(o.getUsername());
			o.setClientType(cu.getClient());
			o.setOrderSource(new Short(orderSource));
			o.setAreaName(stall.getAreaName());
			// 更新车位状态
			// 订单详情
			Long dictId = pre.getBaseDictId();
			if (null != dictId) {
				ResOldDict dict = this.baseDictClient.findOld(dictId);
				if (null != dict) {
					o.setDockId(dict.getCode());
				}
			}
			o.setStallGuidance(pre.getAddress() + stall.getStallName());
			o.setStallType(stall.getType());
			if (brand != null) {
				o.setBrandId(brandId);
				o.setEntId(brand.getEntId());
				o.setStrategyId(brand.getStrategyId());
				o.setPreName(brand.getName());
			}
			o.setStallLocal(o.getPreName() + stall.getStallName());
			this.orderMasterMapper.save(o);

			OrdersDetail od = new OrdersDetail();
			od.setAccountId(cu.getId());
			od.setBeginTime(current);
			od.setCouponsMoney(new BigDecimal(0.0D));
			od.setCreateTime(current);
			od.setDayFee(new BigDecimal(0.0D));
			od.setDayTime(0);
			od.setNightFee(new BigDecimal(0.0D));
			od.setEndTime(new Date());
			od.setNightTime(0);
			od.setOrdNo(o.getOrderNo());
			od.setStallName(stall.getStallName());
			od.setStrategyId(pre.getStrategyId());
			od.setParkName(pre.getName());
			od.setUpdateTime(current);
			od.setOrderId(o.getId());
			this.ordersDetailMasterMapper.save(od);
			this.stallClient.order(stall.getId());
			bookingStatus = (short) OperateStatus.SUCCESS.status;
			failureReason = (short) OrderFailureReason.NONE.value;
			this.userClient.order(cu.getId());
			if (assign) {
				log.info("use the admin assign stall:{},orderNo:{}", lockSn, o.getOrderNo());
				StallAssign sa = stallAssignClusterMapper.findByLockSn(lockSn);
				if (sa != null && sa.getStatus().intValue() == StallAssignStatus.FREE.status) {
					sa.setLockSn(lockSn);
					sa.setOrderId(o.getId());
					sa.setOrderNo(o.getOrderNo());
					sa.setOrderTime(o.getCreateTime());
					sa.setStatus((short) StallAssignStatus.ORDER.status);
					stallAssignMasterMapper.orderUpdate(sa);
				}
			}
			if (StringUtils.isNotBlank(o.getDockId())) {
				Thread thread = new ProduceBookThread(o);
				thread.start();
			}
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
				if (brandId != null) {
					this.redisService.add(RedisKey.PREFECTURE_BRAND_FREE_STALL.key + brandId, stall.getLockSn());
				} else {
					this.redisService.add(RedisKey.PREFECTURE_FREE_STALL.key + stall.getPreId(), stall.getLockSn());
				}
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
				if (brandId != null) {
					this.redisService.add(RedisKey.PREFECTURE_BRAND_FREE_STALL.key + brandId, stall.getLockSn());
				} else {
					this.redisService.add(RedisKey.PREFECTURE_FREE_STALL.key + stall.getPreId(), stall.getLockSn());
				}
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
				if (brandId != null) {
					this.redisService.add(RedisKey.PREFECTURE_BRAND_FREE_STALL.key + brandId, stall.getLockSn());
				} else {
					this.redisService.add(RedisKey.PREFECTURE_FREE_STALL.key + stall.getPreId(), stall.getLockSn());
				}
			}
			throw new RuntimeException("异常");
		} finally {
			ORDER_USER_SET.remove(cu.getId());
			if (stallId != null) {
				log.info(".................5  cu = {}, stallId = {},STALL_ID_SET = {}", cu.getId(), stallId, JSON.toJSON(STALL_ID_SET));
				if (STALL_ID_SET.contains(stallId)) {
					STALL_ID_SET.remove(stallId);
					log.info(".................6  cu = {}, stallId = {},STALL_ID_SET = {}", cu.getId(), stallId,
							JSON.toJSON(STALL_ID_SET));
				}
				String robkey = RedisKey.ROB_STALL_ISHAVE.key + stallId;
				this.redisService.remove(robkey);
			}
			Thread thread = new BookingThread(prefectureId, cu.getId(), bookingStatus, failureReason);
			thread.start();
			String content = "订单预约失败";
			if (stallId != null) {
				content = "当前车位已被占用,请选择其他车位";
			}
			Boolean status = false;
			if (bookingStatus == OperateStatus.SUCCESS.status) {
				content = "订单预约成功";
				status = true;
			}
			
			thread = new PushThread(cu.getId().toString(), "车位预约通知", content, PushType.ORDER_CREATE_NOTICE, status);
			thread.start();
		}

	}

	@Override
	public ResOrder downAppoint(ReqStallBooking rsb, HttpServletRequest request) {
		ResOrder ro = null;
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		log.info("down appoint request param :{} cu:{}", JsonUtil.toJson(rsb), JsonUtil.toJson(cu));
		ResStallEntity stall = this.stallClient.findById(rsb.getStallId());
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			String lockSn = stall.getLockSn();
			ResLockInfo lockInfo = lockClient.lockInfo(lockSn);
			log.info("down appoint lockInfo :{}",JSON.toJSON(lockInfo));
			
			boolean flag = downAppointOrder(rsb.getPrefectureId(), rsb.getPlateId(), null, rsb.getStallId(), rsb.getOrderSource(), cu, true);
			log.info("...........downAppoint 下单状态{}", flag == true ? "成功" : "失败");
			if(flag) {
				ResUserOrder orders = this.ordersClusterMapper.findUserLatest(cu.getId()); // 查找最新
				if (orders == null) {
					return null;
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Map<String, Object> param = new HashMap<String,Object>();
				param.put("stallId", orders.getStallId());
				param.put("plateNo", orders.getPlateNo());
				param.put("startTime", sdf.format(orders.getCreateTime()));
				if (orders.getStatus().intValue() == OrderStatus.SUSPENDED.value) {
					param.put("endTime", sdf.format(orders.getStatusTime()));
				} else {
					param.put("endTime", sdf.format(new Date()));
				}
				log.info("..........current order request param:{}", JSON.toJSON(param));
				Map<String, Object> map = this.strategyFeeClient.amount(param);
				log.info("..........current order response result map:{}", JSON.toJSON(map));
				if (map != null) {
					Object object = map.get("chargePrice");
					if (object != null) {
						orders.setTotalAmount(new BigDecimal(object.toString()));
					}
				}
				
				if (orders != null && orders.getStatus() == OrderStatus.UNPAID.value) {
					ro = new ResOrder();
					ro.copy(orders);
					ResPrefectureDetail pre = this.prefectureClient.findById(orders.getPreId());
					if (pre != null) {
						ro.setPreLongitude(pre.getLongitude());
						ro.setPreLatitude(pre.getLatitude());
						ro.setPrefectureAddress(pre.getAddress());
						ro.setGuideImage(pre.getRouteGuidance());
						ro.setGuideRemark(pre.getRouteDescription());
					}
					if (stall != null) {
						ro.setStallName(stall.getStallName());
						ro.setBluetooth(stall.getLockSn());
					}
					
					if(orders.getStallId() != null) {
						Map<String,Object> feeParam = new HashMap<String,Object>();
						feeParam.put("stallId", orders.getStallId());
						int freeMins = strategyFeeClient.freeMins(feeParam);
						ro.setFreeMins(freeMins);
						log.info("..........current order free mins {}", freeMins);
						ro.setCancelFlag((short)2);
						ro.setRemainMins(0);
					}
					ro.setOrderSource((short)2);
				}
			}
		}
		return ro;
	}

	@Override
	public boolean controlDown(ReqOrderStall ros, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		if (cu == null) {
			throw new BusinessException(StatusEnum.USER_APP_NO_LOGIN);
		}
		ResUserOrder order = this.ordersClusterMapper.findDetail(ros.getOrderId());
		log.info("............control down order{}", JSON.toJSON(order));
		Boolean authStatus = ros.getStallId().intValue() == order.getStallId()
				&& order.getStatus() == OrderStatus.UNPAID.value
				&& order.getUserId().longValue() == cu.getId().longValue();
		if (authStatus) {
			cn.linkmore.prefecture.request.ReqOrderStall stall = new cn.linkmore.prefecture.request.ReqOrderStall();
			stall.setOrderId(order.getId());
			stall.setStallId(order.getStallId());
			stall.setUserId(cu.getId());
			
			/*if (this.redisService.exists(RedisKey.ORDER_STALL_DOWN_FAILED.key + order.getId())) {
				Object count = this.redisService.get(RedisKey.ORDER_STALL_DOWN_FAILED.key + order.getId());
				this.redisService.set(RedisKey.ORDER_STALL_DOWN_FAILED.key + order.getId(),
						new Integer(count.toString()) + 1, ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
			} else {
				this.redisService.set(RedisKey.ORDER_STALL_DOWN_FAILED.key + order.getId(), 1,
						ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
			}*/
			
			Boolean downStatus = this.stallClient.controlDown(stall);
			
			/*Boolean switchStatus = false;
			if (this.redisService.exists(RedisKey.ORDER_STALL_DOWN_FAILED.key + order.getId())) {
				Object count = this.redisService.get(RedisKey.ORDER_STALL_DOWN_FAILED.key + order.getId());
				log.info("downMsgPush failed times = {}",count.toString());
				if (Integer.valueOf(count.toString()) > 1) {
					switchStatus = true;
				}
				downStatus = false;
			}*/
			
			log.info("downing msg..................orderId:{} downStatus:{}", order.getId(), downStatus);
			if(!downStatus) {
				if(this.redisService.exists(RedisKey.ORDER_STALL_DOWN_FAILED.key+ros.getOrderId())) {
					Object object = this.redisService.get(RedisKey.ORDER_STALL_DOWN_FAILED.key+ros.getOrderId());
					log.info("down flag reason = {}", StatusEnum.get((int)object));
					throw new BusinessException(StatusEnum.get((int)object));
				}else {
					log.info("....................the server is unconnecting");
					this.redisService.set(RedisKey.ORDER_STALL_DOWN_FAILED.key + ros.getOrderId(), StatusEnum.DOWN_LOCK_FAIL_RETRY.code,ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
					throw new BusinessException(StatusEnum.DOWN_LOCK_FAIL_RETRY);
				}
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("lockDownStatus", downStatus ? OperateStatus.SUCCESS.status : OperateStatus.FAILURE.status);
			param.put("lockDownTime", new Date());
			param.put("id", order.getId());
			this.orderMasterMapper.updateLockStatus(param);
			return downStatus;
			/*if (switchStatus && !downStatus) {
				Thread thread = new PushThread(order.getUserId().toString(), "预约切换通知", "车位锁降下失败建议切换车位",
						PushType.ORDER_SWITCH_STATUS_NOTICE, true);
				thread.start();
			} else {
				Thread thread = new PushThread(order.getUserId().toString(), "预约降锁通知", downStatus ? "车位锁降下成功" : "车位锁降下失败",
						PushType.LOCK_DOWN_NOTICE, downStatus);
				thread.start();
			}*/
		}
		return false;
	}

	@Override
	public String switchOrderStall(Long orderId, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		ResUserOrder order = this.ordersClusterMapper.findDetail(orderId);
		Boolean flag = false;
		String stallName = null;
		if (order.getStatus().intValue() == OrderStatus.UNPAID.value
				&& cu.getId().intValue() == order.getUserId().intValue()) {
			Long count = 0L;
			
			Integer switchCount = 0;
			Object o = this.redisService.get(RedisKey.ORDER_STALL_DOWN_FAILED.key + order.getId() + order.getUserId());
			if (o != null) {
				switchCount = new Integer(o.toString());
			}
			log.info("switch stall count ={} ", switchCount);
			
			if (this.redisService.exists(RedisKey.ORDER_STALL_DOWN_FAILED.key + order.getId() + order.getUserId())) {
				this.redisService.set(RedisKey.ORDER_STALL_DOWN_FAILED.key + order.getId() + order.getUserId(),
						new Integer(switchCount.toString()) + 1, ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
			} else {
				this.redisService.set(RedisKey.ORDER_STALL_DOWN_FAILED.key + order.getId() + order.getUserId(), 1,
						ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
			}
			
			count = prefectureClient.findByGroupId(order.getStallId(), order.getPreId());
			
			log.info("get the group free stall count ={} ", count);
			
			if (count.intValue() <= 0 || switchCount > 1) {
				Map<String, Object> param = new HashMap<String, Object>();
				Date current = new Date();
				param.put("id", order.getId());
				param.put("endTime", current);
				param.put("updateTime", current);
				param.put("statusTime", current);
				param.put("statusHistory", OrderStatusHistory.CLOSED.code);
				param.put("status", OrderStatus.CLOSED.value);
				this.orderMasterMapper.updateClose(param);
				//new CancelStallThread(order.getStallId()).start();
				stallClient.close(order.getStallId());
				// 关闭订单发送优惠券功能
				couponClient.send(cu.getId());
				this.redisService.set(RedisKey.ORDER_SWITCH_RESULT.key + orderId.longValue(),
						SwitchResult.CLOSED.value, ExpiredTime.ORDER_SWITCH_RESULT_TIME.time);
				throw new BusinessException(StatusEnum.NO_FREE_STALL_CLOSE);
				/*Thread thread = new PushThread(order.getUserId().toString(), "订单通知", "无空闲车位,订单已关闭",
						PushType.ORDER_AUTO_CLOSE_NOTICE, true);
				thread.start();*/
			} else {
				Object sn = null;
				sn = prefectureClient.nearFreeStallLockSn(order.getStallId(), order.getPreId());
				log.info("get switch stall sn:{}", sn);
				if (sn != null) {
					ResStallEntity stall = this.stallClient.findByLock(sn.toString().trim());
					log.info("switch stall:{}", JsonUtil.toJson(stall));
					if (stall.getStatus().intValue() == StallStatus.FREE.status) {
						//Thread thread = new OfflieStallThread(order.getStallId());
						//order.setStallId(stall.getId());
						//order.setStallName(stall.getStallName());
						redisService.remove(RedisKey.PREFECTURE_FREE_STALL.key + order.getPreId(), sn);
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("id", order.getId());
						param.put("stallId", stall.getId());
						param.put("stallName", stall.getStallName());
						param.put("switchTime", new Date());
						param.put("switchStatus", 1);
						this.orderMasterMapper.updateSwitch(param);
						this.stallClient.order(stall.getId());
						//thread.start();
						stallClient.close(order.getStallId());
						stallName = stall.getStallName();
						flag = true;
						
					}
				}
				/*Thread thread = new PushThread(order.getUserId().toString(), "车位切换通知", flag ? "车位切换成功" : "车位切换失败",
						PushType.ORDER_SWITCH_RESULT_NOTICE, flag);
				thread.start();*/
				this.redisService.set(RedisKey.ORDER_SWITCH_RESULT.key + orderId.longValue(),
						flag ? SwitchResult.SUCCESS.value : SwitchResult.FAILED.value,
						ExpiredTime.ORDER_SWITCH_RESULT_TIME.time);
				
				if(!flag) {
					throw new BusinessException(StatusEnum.SWITCH_STALL_FAILED);
				}
			}
		} else {
			/*Thread thread = new PushThread(order.getUserId().toString(), "车位切换通知", "车位切换失败",
					PushType.ORDER_SWITCH_RESULT_NOTICE, false);
			thread.start();*/
			this.redisService.set(RedisKey.ORDER_SWITCH_RESULT.key + orderId.longValue(),
					SwitchResult.FAILED.value, ExpiredTime.ORDER_SWITCH_RESULT_TIME.time);
			throw new BusinessException(StatusEnum.SWITCH_STALL_FAILED);
		}
		
		if(stallName == null) {
			throw new BusinessException(StatusEnum.SWITCH_STALL_FAILED);
		}
		return stallName;
	}

	@Override
	public boolean controlUp(ReqOrderStall ros, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		if (cu == null) {
			throw new BusinessException(StatusEnum.USER_APP_NO_LOGIN);
		}
		ResUserOrder order = this.ordersClusterMapper.findDetail(ros.getOrderId());
		log.info("............control up order{}", JSON.toJSON(order));
		Boolean authStatus = ros.getStallId().intValue() == order.getStallId()
				&& order.getUserId().longValue() == cu.getId().longValue();
		if (authStatus) {
			cn.linkmore.prefecture.request.ReqOrderStall stall = new cn.linkmore.prefecture.request.ReqOrderStall();
			stall.setOrderId(order.getId());
			stall.setStallId(order.getStallId());
			stall.setUserId(cu.getId());
			
			Boolean upStatus = this.stallClient.controlUp(stall);
			
			log.info("uping msg..................orderId:{} upStatus:{}", order.getId(), upStatus);
			if(!upStatus) {
				if(this.redisService.exists(RedisKey.ORDER_STALL_UP_FAILED.key+ros.getOrderId())) {
					Object object = this.redisService.get(RedisKey.ORDER_STALL_UP_FAILED.key+ros.getOrderId());
					log.info("up flag reason = {}", StatusEnum.get((int)object));
					throw new BusinessException(StatusEnum.get((int)object));
				}else {
					log.info("....................the server is unconnecting");
					this.redisService.set(RedisKey.ORDER_STALL_UP_FAILED.key + ros.getOrderId(), StatusEnum.UP_LOCK_FAIL_RETRY_OWNER.code,ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
					throw new BusinessException(StatusEnum.UP_LOCK_FAIL_RETRY_OWNER);
				}
			}
			Date current = new Date();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", order.getId());
			param.put("status", OrderStatus.COMPLETED.value);
			param.put("updateTime", current);
			param.put("endTime", current);
			param.put("payTime", current);
			//param.put("tradeId", payTradeRecord.getId());
			this.orderMasterMapper.updatePayment(param);
			// 3.更新优惠券信息
			if (null != order.getCouponId()) {
				ReqCouponPay rcp = new ReqCouponPay();
				rcp.setCouponId(order.getCouponId());
				rcp.setOrderAmount(order.getTotalAmount());
				rcp.setUsedAmount(order.getCouponAmount());
				this.couponClient.pay(rcp);
			}
			this.userClient.checkout(order.getUserId());
			// 结账调用新版推送消息
			/*Thread thread = new ProduceCheckBookingThread(order);
			thread.start();
			thread = new PushThread(order.getUserId().toString(), "订单支付通知", "支付成功", PushType.ORDER_COMPLETE_NOTICE, true);
			thread.start();*/
			return upStatus;
		}
		return false;
	}

	@Override
	public List<ResStaffPreOwnerStall> findPresOrder(ReqStaffPreOwnerStall reqStaffPreOwnerStall) {
		return this.ordersClusterMapper.findPresOrder(reqStaffPreOwnerStall);
	}

	@Override
	public ResPreOrderDetails findPreOrderDetails(ReqPreDetails reqPreDetails) {
		return 	this.ordersClusterMapper.findPreOrderDetails(reqPreDetails);
	}

	@Override
	public ResTempStallReportForms findTempStallReportForms(ReqPreDetails details) {
		return 	this.ordersClusterMapper.findTempStallReportForms(details);
	}

	
}
