package cn.linkmore.prefecture.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import com.linkmore.lock.bean.LockBean;
import com.linkmore.lock.factory.LockFactory;
import com.linkmore.lock.response.ResponseMessage;

import cn.linkmore.bean.common.Constants;
import cn.linkmore.bean.common.Constants.BindOrderStatus;
import cn.linkmore.bean.common.Constants.ExpiredTime;
import cn.linkmore.bean.common.Constants.LockStatus;
import cn.linkmore.bean.common.Constants.PushType;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.Constants.StallStatus;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.common.security.Token;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.response.ResEntExcStallStatus;
import cn.linkmore.enterprise.response.ResEntStaff;
import cn.linkmore.notice.client.EntSocketClient;
import cn.linkmore.order.client.EntOrderClient;
import cn.linkmore.order.client.FeignUnusualOrderClient;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.response.ResOrderPlate;
import cn.linkmore.order.response.ResUnusualOrder;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.prefecture.client.EntRentedRecordClient;
import cn.linkmore.prefecture.client.EntStaffClient;
import cn.linkmore.prefecture.client.FeignStallExcStatusClient;
import cn.linkmore.prefecture.controller.staff.request.ReqStaffStallList;
import cn.linkmore.prefecture.controller.staff.response.ResStaffPreList;
import cn.linkmore.prefecture.controller.staff.response.ResStaffStallList;
import cn.linkmore.prefecture.dao.cluster.AdminAuthPreClusterMapper;
import cn.linkmore.prefecture.dao.cluster.AdminAuthStallClusterMapper;
import cn.linkmore.prefecture.dao.cluster.AdminUserAuthClusterMapper;
import cn.linkmore.prefecture.dao.cluster.EntRentRecordClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StallClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StallLockClusterMapper;
import cn.linkmore.prefecture.dao.master.EntRentRecordMasterMapper;
import cn.linkmore.prefecture.dao.master.StallLockMasterMapper;
import cn.linkmore.prefecture.dao.master.StallMasterMapper;
import cn.linkmore.prefecture.entity.AdminAuthPre;
import cn.linkmore.prefecture.entity.AdminAuthStall;
import cn.linkmore.prefecture.entity.EntRentRecord;
import cn.linkmore.prefecture.entity.Stall;
import cn.linkmore.prefecture.entity.StallLock;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqControlLock;
import cn.linkmore.prefecture.request.ReqOrderStall;
import cn.linkmore.prefecture.request.ReqStall;
import cn.linkmore.prefecture.response.ResAdminAuthStall;
import cn.linkmore.prefecture.response.ResAdminUserAuth;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.prefecture.response.ResStallLock;
import cn.linkmore.prefecture.response.ResStallOps;
import cn.linkmore.prefecture.service.AdminAuthService;
import cn.linkmore.prefecture.service.AdminUserService;
import cn.linkmore.prefecture.service.PrefectureService;
import cn.linkmore.prefecture.service.StallService;
import cn.linkmore.redis.RedisService;
import cn.linkmore.third.client.PushClient;
import cn.linkmore.third.client.SendClient;
import cn.linkmore.third.request.ReqPush;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.TokenUtil;
import io.swagger.annotations.ApiModelProperty;

/**
 * Service实现类 - 车位信息
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class StallServiceImpl implements StallService {
	@Autowired
	private StallMasterMapper stallMasterMapper;
	@Autowired
	private StallClusterMapper stallClusterMapper;
	@Autowired
	private RedisService redisService;
	@Autowired
	private LockFactory lockFactory;
	@Autowired
	private OrderClient orderClient;
	@Autowired
	private EntOrderClient entOrderClient;
	@Autowired
	private StallLockMasterMapper stallLockMasterMapper;
	@Autowired
	private StallLockClusterMapper stallLockClusterMapper;
	@Autowired
	private PushClient pushClient;
	@Autowired
	private SendClient sendClient;	
	@Autowired
	private EntRentRecordMasterMapper entRentedRecordMasterMapper;
	@Autowired
	private EntRentRecordClusterMapper entRentedRecordClusterMapper;
	@Autowired
	private EntSocketClient entSocketClient;
	@Autowired
	private EntRentedRecordClient entRentedRecordClient;
	@Autowired
	private EntStaffClient entStaffClient;
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private PrefectureService prefectureService;
	@Autowired
	private AdminAuthStallClusterMapper adminAuthStallClusterMapper;
	@Autowired
	private FeignUnusualOrderClient feignUnusualOrderClient;
	@Autowired
	private AdminUserAuthClusterMapper adminUserAuthClusterMapper;
	@Autowired
	private AdminAuthPreClusterMapper adminAuthPreClusterMapper;
	@Autowired
	private FeignStallExcStatusClient feignStallExcStatusClient;
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void order(Long id) {
		Stall stall = new Stall();
		stall.setId(id);
		stall.setStatus(StallStatus.USED.status);
		stall.setLockStatus(LockStatus.UP.status);
		stall.setBindOrderStatus((short) BindOrderStatus.FREE.status);
		stallMasterMapper.order(stall);
	}

	@Override
	public boolean cancel(Long stallId) {
		Boolean flag = false;
		Stall stall = stallClusterMapper.findById(stallId);
		if (stall != null) {
			stall.setStatus(StallStatus.FREE.status);
			stall.setLockStatus(LockStatus.UP.status);
			stall.setBindOrderStatus((short) BindOrderStatus.FREE.status);
			stallMasterMapper.cancel(stall);
			flag = true;
		}
		return flag;
	}

	class StallUpThread extends Thread {
		private Stall stall;

		public StallUpThread(Stall stall) {
			this.stall = stall;
		}

		public void run() {
			ResponseMessage<LockBean> res = lockFactory.lockUp(stall.getLockSn());
			int code = res.getMsgCode();
			log.info("lock msg:{}", JsonUtil.toJson(res));
			if (code == 200) {
				redisService.add(RedisKey.PREFECTURE_FREE_STALL.key + stall.getPreId(), stall.getLockSn());
			}
		}
	}

	@Override
	public boolean checkout(Long stallId) {
		log.info("checkout stall :{}", stallId);
		boolean flag = false;
		Stall stall = stallClusterMapper.findById(stallId);
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			flag = true;
			stall.setUpdateTime(new Date());
			stall.setStatus(StallStatus.FREE.status);
			stall.setLockStatus(LockStatus.UP.status);
			stall.setBindOrderStatus((short) BindOrderStatus.FREE.status);
			this.stallMasterMapper.checkout(stall);
			new StallUpThread(stall).start();
		}
		return flag;
	}

	private void downing(ReqOrderStall reqos) {
		Stall stall = stallClusterMapper.findById(reqos.getStallId());
		log.info("stall:{}", JsonUtil.toJson(stall));
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			log.info("downing... name:{},sn:{}", stall.getStallName(), stall.getLockSn());
			ResponseMessage<LockBean> res = lockFactory.lockDown(stall.getLockSn());
			log.info("res:{}", JsonUtil.toJson(res));
			int code = res.getMsgCode();
			if (code == 200) {
				stall.setLockStatus(LockStatus.DOWN.status);
				stallMasterMapper.lockdown(stall);
				this.redisService.remove(RedisKey.ORDER_STALL_DOWN_FAILED.key + reqos.getOrderId());
			}
			orderClient.downMsgPush(reqos.getOrderId(), reqos.getStallId());
		}
	}

	class DownThread extends Thread {
		private ReqOrderStall reqos;

		public DownThread(ReqOrderStall reqos) {
			this.reqos = reqos;
		}

		public void run() {
			downing(reqos);
		}
	}

	@Override
	public void downlock(ReqOrderStall reqos) {
		Thread thread = new DownThread(reqos);
		thread.start();
	}

	@Override
	public boolean uplock(Long stallId) {
		boolean flag = true;
		Stall stall = stallClusterMapper.findById(stallId);
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			log.info("uping... name:{},sn:{}", stall.getStallName(), stall.getLockSn());
			ResponseMessage<LockBean> res = lockFactory.lockUp(stall.getLockSn());
			log.info("res:{}", JsonUtil.toJson(res));
			int code = res.getMsgCode();
			if (code != 200) {
				// 此处为升锁操作
				flag = false;
				throw new BusinessException(StatusEnum.ORDER_LOCKUP_FAIL);
			}
			stall.setLockStatus(LockStatus.UP.status);
			stallMasterMapper.lockdown(stall);
		}
		return flag;
	}

	@Override
	public List<ResStall> findStallsByPreId(Long preId) {
		List<ResStall> resStall = stallClusterMapper.findStallsByPreId(preId);
		return resStall;
	}

	@Override
	public ResStallEntity findById(Long stallId) {
		ResStallEntity detail = new ResStallEntity();
		Stall stall = stallClusterMapper.findById(stallId);
		if (stall != null) {
			return ObjectUtils.copyObject(stall, detail);
		}
		return null;
	}

	@Override
	public ResStallEntity findByLock(String sn) {
		ResStallEntity detail = new ResStallEntity();
		Stall stall = this.stallClusterMapper.findByLockSn(sn);
		if (stall != null) {
			return ObjectUtils.copyObject(stall, detail);
		}
		return null;
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
		Integer count = this.stallClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		param.put("property", "stall_name");
		param.put("direction", "ASC");
		List<Stall> list = this.stallClusterMapper.findPage(param);
		return new ViewPage(count, pageable.getPageSize(), list);
	}

	@Override
	public int save(ReqStall reqStall) {
		Date now = new Date();
		reqStall.setSellCount(0);
		reqStall.setGatewayId(0l);
		reqStall.setStatus(4);
		reqStall.setBindOrderStatus((short) 0);
		reqStall.setCreateTime(now);
		reqStall.setUpdateTime(now);
		Stall stall = new Stall();
		stall = ObjectUtils.copyObject(reqStall, stall);
		log.info("--------save--------interface------stall = {}", JSON.toJSON(stall));
		return this.stallMasterMapper.save(stall);
	}

	@Override
	public int update(ReqStall reqStall) {
		Date now = new Date();
		reqStall.setUpdateTime(now);
		Stall stall = new Stall();
		stall = ObjectUtils.copyObject(reqStall, stall);
		return stallMasterMapper.update(stall);
	}

	@Override
	public int check(ReqCheck reqCheck) {
		Map<String, Object> param = new HashMap<>();
		param.put("stallName", reqCheck.getProperty());
		param.put("preId", reqCheck.getValue());
		if (null != reqCheck.getId()) {
			param.put("id", reqCheck.getId());
		}
		return this.stallClusterMapper.check(param);
	}

	@Override
	public int bind(ReqStall stall) {
		Date now = new Date();
		ResStallLock lock = stallLockClusterMapper.findById(stall.getLockId());
		String sn = lock.getSn();
		stall.setLockStatus(null);
		stall.setLockSn(sn);
		stall.setStatus(4);
		stall.setLockBattery(0);
		lock.setPrefectureId(stall.getPreId());
		lock.setBindTime(now);
		lock.setStallId(stall.getId());
		stall.setUpdateTime(now);
		StallLock stallLock = new StallLock();
		stallLock = ObjectUtils.copyObject(lock, stallLock);
		stallLockMasterMapper.updateBind(stallLock);

		Stall sta = new Stall();
		sta = ObjectUtils.copyObject(stall, sta);
		log.info("{}:{}>>{},返回结果{}", "绑定车位锁", "车位(" + stall.getStallName() + "),车位锁(" + sn + ")", "绑定成功", 200);
		return stallMasterMapper.update(sta);
	}

	@Override
	public int updateStatus(ReqStall reqStall) {
		Date now = new Date();
		Integer status = reqStall.getStatus();
		String sn = reqStall.getLockSn();
		// Long preId = reqStall.getPreId();
		reqStall.setUpdateTime(now);
		if (status.intValue() == 4) {
			Stall stall = new Stall();
			stall = ObjectUtils.copyObject(reqStall, stall);
			stallMasterMapper.update(stall);
			log.info("下线成功，车位：" + stall.getStallName() + " 序列号：{}", sn);
		} else {
			try {
				ResponseMessage<LockBean> res = lockFactory.getLockInfo(sn);
				LockBean lockBean = res.getData();
				if (lockBean == null) {
					throw new RuntimeException("车位锁通讯失败");
				}
				/*
				 * if (lockBean.getOpenState() != 1) { lockFactory.lockUp(sn); } int openState =
				 * lockBean.getOpenState(); reqStall.setLockStatus(openState);
				 * reqStall.setLockBattery((int) lockBean.getVoltage());
				 */

				Stall stall = new Stall();
				stall = ObjectUtils.copyObject(reqStall, stall);

				stallMasterMapper.update(stall);
				log.info("上线成功，车位：" + reqStall.getStallName() + " 序列号：{}", sn);
			} catch (Exception e) {
				log.info("lock open err:{}", e.toString());
				throw new RuntimeException("车位锁通讯失败");
			}
		}
		return 0;
	}

	@Override
	public List<ResStall> findList(Map<String, Object> param) {
		List<ResStall> list = this.stallClusterMapper.findList(param);
		return list;
	}

	@Override
	public void saveAndBind(Long preId, String stallName, String sn) {
		Date now = new Date();
		StallLock lock = new StallLock();
		lock.setSn(sn);
		lock.setCreateTime(now);
		this.stallLockMasterMapper.save(lock);
		ReqStall stall = new ReqStall();
		stall.setStallName(stallName);
		stall.setLockStatus(null);
		stall.setLockSn(sn);
		stall.setStatus(4);
		stall.setLockBattery(0);
		stall.setLockId(lock.getId());
		stall.setPreId(preId);
		this.save(stall);
		lock.setPrefectureId(stall.getPreId());
		lock.setBindTime(now);
		lock.setStallId(stall.getId());
		stallLockMasterMapper.updateBind(lock);
	}

	@Override
	public List<ResStallOps> findListByParam(Map<String, Object> param) {
		return this.stallClusterMapper.findListByParam(param);
	}

	@Override
	public void close(Long id) {
		Stall stall = stallClusterMapper.findById(id);
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			Integer count = (Integer) this.redisService.get(RedisKey.STALL_ORDER_CLOSED.key + id);
			if (count == null) {
				count = 0;
			}
			if (count < 3) {
				count = count + 1;
				stall.setStatus(StallStatus.FREE.status);
				stall.setLockStatus(LockStatus.UP.status);
				stall.setBindOrderStatus((short) BindOrderStatus.FREE.status);
				stall.setUpdateTime(new Date());
				this.stallMasterMapper.checkout(stall);
				this.redisService.set(RedisKey.STALL_ORDER_CLOSED.key + id, count,
						ExpiredTime.STALL_ORDER_CLOSED_TIME.time);
				// this.redisService.add(RedisKey.PREFECTURE_FREE_STALL.key + stall.getPreId(),
				// stall.getLockSn());
			} else {
				this.redisService.remove(RedisKey.STALL_ORDER_CLOSED.key + id);
				stall.setStatus(StallStatus.OUTLINE.status);
				stall.setBindOrderStatus((short) BindOrderStatus.FREE.status);
				this.redisService.remove(RedisKey.STALL_ORDER_CLOSED.key + id);
				this.stallMasterMapper.offline(stall);
			}
		}
	}

	@Override
	public List<ResStall> findStallList(List<Long> stallIds) {
		List<ResStall> list = this.stallClusterMapper.findTreeList(stallIds);
		return list;
	}

	@Override
	public List<ResStall> findPreStallList(Map<String, Object> param) {
		List<ResStall> list = this.stallClusterMapper.findPreStallList(param);
		return list;
	}

	@Override
	public int updateBrand(Map<String, Object> param) {
		log.info("param = {}", param);
		String brand = (String) param.get("brand");
		List<Long> ids = (List<Long>) param.get("list");
		if ("0".equals(brand)) {
			return this.stallMasterMapper.updateBrand(ids);
		} else {
			return this.stallMasterMapper.updateBrandStall(ids);
		}
	}

	@Override
	public Map<String, Object> lockStatus(List<String> parkcodes) {
		Map<String, Object> map = new HashMap<>();
		ResponseMessage<LockBean> lb = lockFactory.findAvaiLocks(parkcodes);
		if (lb != null) {
			map.put("data", lb);
		}
		return map;
	}

	@Override
	public void controling(ReqControlLock reqc) { // 控制锁
		new Thread(new Runnable() {
			@Override
			public void run() {
				String uid = String.valueOf(redisService.get(reqc.getKey()));
				Stall stall = stallClusterMapper.findById(reqc.getStallId());
				log.info("stall:{}", JsonUtil.toJson(stall));
				if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
					log.info("<<<<<<<<<controling>>>>>>>>>>>>name:{},sn:{}", stall.getStallName(), stall.getLockSn());
					ResponseMessage<LockBean> res = null;
					// 1 降下 2 升起
					Stopwatch stopwatch = Stopwatch.createStarted();
					if (reqc.getStatus() == 1) {
						res = lockFactory.lockDown(stall.getLockSn());
					} else if (reqc.getStatus() == 2) {
						res = lockFactory.lockUp(stall.getLockSn());
					}
					log.info("<<<<<<<<<respose>>>>>>>>>"+res.getMsg()+"<<<code>>>"+res.getMsgCode());
					int code = res.getMsgCode();
					stopwatch.stop();
					log.info("<<<<<<<<<using time>>>>>>>>>"+String.valueOf(stopwatch.elapsed(TimeUnit.MILLISECONDS)));
					sendMsg(uid, reqc.getStatus(), code);
					String robkey= RedisKey.ROB_STALL_ISHAVE.key+reqc.getStallId();
					EntRentRecord record = entRentedRecordClusterMapper.findByUser(Long.valueOf(uid));	
					if (code == 200) {
						if(reqc.getStatus() == 2) {
							log.info("<<<<<<<<<up success>>>>>>>>>"+record.getId());
							//未完成记录同一用户只有一单
							if(Objects.nonNull(record)) {
							EntRentRecord up = new EntRentRecord();
							up.setLeaveTime(new Date());
							up.setStatus(1L);
							up.setId(record.getId());
							entRentedRecordMasterMapper.updateByIdSelective(up);
							}
							redisService.remove(robkey);
						}else {
							log.info("<<<<<<<<<down success>>>>>>>>>"+record.getId());
						}
						stall.setLockStatus(reqc.getStatus()==1?2:1);
						stall.setStatus(reqc.getStatus()==1?2:1);
						stallMasterMapper.lockdown(stall);
						redisService.remove(reqc.getKey());
					}else {
						if(reqc.getStatus() == 1) {
							log.info("<<<<<<<<<down fail>>>>>>>>>>"+record.getId());
							//降锁失败 取消绑定
							if(Objects.nonNull(record)) {
							EntRentRecord up = new EntRentRecord();
							up.setLeaveTime(new Date());
							up.setStatus(2L);
							up.setId(record.getId());
							entRentedRecordMasterMapper.updateByIdSelective(up);
							}
							redisService.remove(robkey);
						}else {
							log.info("<<<<<<<<<up fail>>>>>>>>>>"+record.getId());
						}
					}
				}
			}
		}).start();
	}
	
	
	@Override
	public void operLockWY(ReqControlLock reqc) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String uid = String.valueOf(redisService.get(reqc.getKey()));
				Stall stall = stallClusterMapper.findById(reqc.getStallId());
				log.info("stall:{}", JsonUtil.toJson(stall));
				if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
					log.info("downing>>>>>> name:{},sn:{}", stall.getStallName(), stall.getLockSn());
					ResponseMessage<LockBean> res = null;
					// 1 降下 2 升起
					Stopwatch stopwatch = Stopwatch.createStarted();
					if (reqc.getStatus() == 1) {
						res = lockFactory.lockDown(stall.getLockSn());
					} else if (reqc.getStatus() == 2) {
						res = lockFactory.lockUp(stall.getLockSn());
					}
					log.info("res>>>>>>>"+res.getMsg());
					int code = res.getMsgCode();
					stopwatch.stop();
					log.info("usingtime>>>"+String.valueOf(stopwatch.elapsed(TimeUnit.MILLISECONDS)));
					sendWYMsg(uid,reqc.getStatus(),code);
					if (code == 200) {
						if(reqc.getStatus() == 1 ) {
							stall.setLockStatus(2);
							if(stall.getType() == 2) {
								if(stall.getStatus() != 4) {
									stall.setStatus(2);
								}
							}
						}else if(reqc.getStatus() == 2 ) {
							if(stall.getType() == 2) {
								if(stall.getStatus() != 4) {
									stall.setStatus(1);
								}
							}
							stall.setLockStatus(1);
						}
						stallMasterMapper.lockdown(stall);
						if(reqc.getStatus() == 1) {
							downLock(reqc.getStallId(),1);
							redisService.remove(reqc.getKey());
						}
						
					}else {
						if(reqc.getStatus() == 1) {
							downLock(reqc.getStallId(),0);
						}
					}
				}
			}
		}).start();
	}

	@Override
	public Map<String, Object> watch(Long stallId) {
		log.info("stall:=====================");
		Map<String, Object>  map = new HashMap<>();
		Stall stall = stallClusterMapper.findById(stallId);
		log.info("stall:{}", JsonUtil.toJson(stall));
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			ResponseMessage<LockBean> res =	lockFactory.getLockInfo(stall.getLockSn());
				map.put("code",res.getMsgCode());
				if(res.getMsgCode()==200) {
					LockBean  Lockbean = res.getData();
					map.put("status",Lockbean.getLockState());
					map.put("onlineState",Lockbean.getOnlineState());
					map.put("parkingState",Lockbean.getParkingState());
				}
		}
		return map;
	}

	public void sendMsg(String uid, Integer status, int code) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					send(uid, status, code);
				} catch (Exception e) {
					log.info("sendMsg>>>"+uid);
				}
			}
		}).start();
	}
	public void sendWYMsg(String uid, Integer status, int code) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					sendWY(uid, status, code);
				} catch (Exception e) {
					log.info("sendWYMsg>>>"+uid);
				}
			}
		}).start();
	}

	public void downLock(Long stallId,int status) {
//		ResUserOrder latest = this.entOrderClient.findStallLatest(stallId);
//		Map<String,Object> param = new HashMap<>();
//		param.put( "lockDownStatus",status);
//		param.put("lockDownTime", new Date());
//		param.put("orderId", latest.getId());
		this.entRentedRecordClient.updateDownTime(stallId);
//		this.entOrderClient.updateLockStatus(param);
	}
	
	private void send(String uid, Integer lockstatus, int code) {
		String title = "车位锁操作通知";
		String content = "车位锁" + (lockstatus == 1 ? "降下" : "升起") + (code == 200 ? "成功 " : "失败");
		PushType type =PushType.LOCK_CONTROL_NOTICE;
		String bool = (code == 200 ? "true" : "false");
		Token token = (Token) redisService.get(RedisKey.USER_APP_AUTH_TOKEN.key + uid.toString());
		log.info("send>>>", JsonUtil.toJson(token));
		if(token!=null) {
			ReqPush rp = new ReqPush();
			rp.setAlias(uid);
			rp.setTitle(title);
			rp.setContent(content);
			rp.setClient(token.getClient());
			rp.setType(type);
			rp.setData(bool);
			pushClient.push(rp);
		}
	}
	private void sendWY(String uid, Integer lockstatus, int code) {
		String title = "车位锁操作通知";
		String content = "车位锁" + (lockstatus == 1 ? "降下" : "升起") + (code == 200 ? "成功 " : "失败");
		String bool = (code == 200 ? "true" : "false");
		Token token = (Token) redisService.get(RedisKey.STAFF_ENT_AUTH_TOKEN.key + uid.toString());
		log.info("send>>>", JsonUtil.toJson(token));
		if(token!=null) {
			if(token.getClient() == Constants.ClientSource.WXAPP.source) {
				CacheUser cu = (CacheUser) redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key + token.getAccessToken());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("title", title);
				map.put("type",PushType.LOCK_CONTROL_NOTICE);
				map.put("content",content);
				map.put("data",token.getAccessToken());
				map.put("alias", cu.getId());
				ResEntStaff staff = this.entStaffClient.findById(cu.getId());
				entSocketClient.push(JsonUtil.toJson(map), staff.getOpenId());
			}else {
				ReqPush rp = new ReqPush();
				rp.setAlias(uid);
				rp.setTitle(title);
				rp.setContent(content);
				rp.setClient(token.getClient());
				rp.setType(PushType.LOCK_CONTROL_NOTICE);
				rp.setData(token.getAccessToken());
				sendClient.send(rp);
			}
		}
	}

	@Override
	public List<ResStaffPreList> findPreList(HttpServletRequest request, Long cityId) {
		CacheUser cu = (CacheUser)this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+TokenUtil.getKey(request)); 
		Map<String, Object> map = new HashMap<>();
		map.put("userId", cu.getId());
		List<AdminAuthPre> pres = this.adminAuthPreClusterMapper.findList(map);
		List<Long> list = pres.stream().map(pre -> pre.getPreId()).collect(Collectors.toList());
		map.put("preIds", list);
		map.put("cityId", cityId);
		List<ResPre> pre = this.prefectureService.findPreByIds(map);
		List<ResAdminAuthStall> adminStalls = this.adminAuthStallClusterMapper.findStallList(map);
		List<Long> collect = adminStalls.stream().map(au -> au.getStallId()).collect(Collectors.toList());
		List<Stall> stalls = this.stallClusterMapper.findAll();
		map = new HashMap<>();
		List<ResUnusualOrder> unusualOrders = feignUnusualOrderClient.findList(map);
		List<ResStaffPreList> resPres = new ArrayList<>();
		ResStaffPreList preList = null; 
		for (ResPre resPre : pre) {
			preList = new ResStaffPreList();
			int preTypeStalls = 0;
			int preUseTypeStalls = 0; 
			int orderNum = 0;
			for (Stall stall : stalls) {
				if(stall.getType() != 0) {
					continue;
				}
				if(collect.contains(stall.getId())) {
					preTypeStalls++;
					if(stall.getStatus() == 2) {
						preUseTypeStalls++;
					}
				}
			}
			for (ResUnusualOrder resUnusualOrder : unusualOrders) {
				if(resUnusualOrder.getPrefectureId().equals(resPre.getId())) {
					orderNum++;
				}
			}
			preList.setPreId(resPre.getId());
			preList.setPreName(resPre.getName());
			preList.setUnusualOrder(orderNum);
			preList.setPreTypeStalls(preTypeStalls);
			preList.setPreUseTypeStalls(preUseTypeStalls);
			resPres.add(preList);
		}
		return resPres;
	}

	@Override
	public List<ResStaffStallList> findStallList(HttpServletRequest request, ReqStaffStallList staffList) {
		CacheUser cu = (CacheUser)this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+TokenUtil.getKey(request));
		if(!checkStaffPreAuth(cu.getId(), staffList.getPreId())) {
			throw new BusinessException(StatusEnum.STAFF_PREFECTURE_EXISTS);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("userId", cu.getId());
		List<ResAdminAuthStall> stallAuthList = this.adminAuthStallClusterMapper.findStallList(map);
		map.put("preId", staffList.getPreId());
		map.put("type", 0);
		if(StringUtils.isNotBlank(staffList.getStallName())) {
			map.put("stallNameLike", "%"+staffList.getStallName()+"%");
		}
		// 预留功能根据状态查询
		if(staffList.getStatus() != null) {
			int status = 0;
			switch (staffList.getStatus()) {
			case 0:
				status = 1;
				break;
			case 1:
				status = 2;
				break;
			case 2:
				status = 4;
				break;
			default:
				throw new BusinessException(StatusEnum.STALL_UNKNOW_TYPE);
			}
			map.put("status", status);
		}
		List<ResStall> stallList = this.stallClusterMapper.findPreStallList(map);
		log.info("【 ResStall list 】 "+JsonUtil.toJson(stallList));
		List<ResStaffStallList> staffStallLists = new ArrayList<>();
		ResStaffStallList ResStaffStallList;
		List<ResOrderPlate> plates = this.entOrderClient.findPlateByPreId(staffList.getPreId());
		log.info("【 ResOrderPlate 】 "+JsonUtil.toJson(plates));
		List<Long> stallAuthIds = stallAuthList.stream().map(stall -> stall.getStallId()).collect(Collectors.toList());
		ResPrefectureDetail detail = this.prefectureService.findById(staffList.getPreId());
		log.info("【 ResPrefectureDetail 】 "+JsonUtil.toJson(detail));
		ResponseMessage<LockBean> lock = lockFactory.findAvailableLock(detail.getGateway());
		List<LockBean> bockBeans = null;
		List<ResEntExcStallStatus> excStallList = feignStallExcStatusClient.findAll();
		if(lock != null) {
			bockBeans = lock.getDataList();
			log.info("【lockBean list 】 "+JsonUtil.toJson(bockBeans));
		}
		for (ResStall resStall : stallList) {
			if(!stallAuthIds.contains(resStall.getId())) {
				continue;
			}
			ResStaffStallList = new ResStaffStallList();
			if(resStall.getStatus() == 2) {
				for (ResOrderPlate resOrderPlate : plates) {
					if(resOrderPlate.getStallId().equals(resStall.getId())) {
						ResStaffStallList.setPlateNo(resOrderPlate.getPlateNo());
					}
				}
			}
			for (ResEntExcStallStatus resEntExcStallStatus : excStallList) {
				if(resEntExcStallStatus.getStallId().equals(resStall.getId())) {
					ResStaffStallList.setExcStatus(false);
				}
			}
			if(bockBeans != null) {
				for (LockBean lockBean : bockBeans) {
					if(lockBean.getLockCode().equals(resStall.getLockSn())) {
						switch (lockBean.getLockState()) {
						case 0:
							ResStaffStallList.setLockStatus(2);
							break;
						default:
							ResStaffStallList.setLockStatus(lockBean.getLockState());
							break;
						}
					}
				}
			}else{
				ResStaffStallList.setLockStatus(resStall.getLockStatus());
			}
			ResStaffStallList.setStatus(resStall.getStatus());
			ResStaffStallList.setStallId(resStall.getId());
			ResStaffStallList.setStallName(resStall.getStallName());
			staffStallLists.add(ResStaffStallList);
		}
		return staffStallLists;
	}
	
	
	
	public Boolean checkStaffPreAuth(Long userId, Long preId) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("preId", preId);
		List<AdminAuthPre> list = this.adminAuthPreClusterMapper.findList(map );
		return list != null && list.size() != 0 ? true : false;
	}
	
	
}
