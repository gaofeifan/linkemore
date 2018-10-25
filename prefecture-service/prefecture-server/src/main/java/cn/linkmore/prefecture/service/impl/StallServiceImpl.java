package cn.linkmore.prefecture.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import cn.linkmore.common.client.BaseDictClient;
import cn.linkmore.common.response.ResBaseDict;
import cn.linkmore.enterprise.response.ResEntExcStallStatus;
import cn.linkmore.enterprise.response.ResEntStaff;
import cn.linkmore.notice.client.EntSocketClient;
import cn.linkmore.notice.client.UserSocketClient;
import cn.linkmore.order.client.EntOrderClient;
import cn.linkmore.order.client.FeignUnusualOrderClient;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.response.ResOrderPlate;
import cn.linkmore.order.response.ResUnusualOrder;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.prefecture.client.EntRentedRecordClient;
import cn.linkmore.prefecture.client.EntStaffClient;
import cn.linkmore.prefecture.client.FeignStallExcStatusClient;
import cn.linkmore.prefecture.controller.staff.request.ReqAssignStall;
import cn.linkmore.prefecture.controller.staff.request.ReqStaffStallList;
import cn.linkmore.prefecture.controller.staff.response.ResStaffPreList;
import cn.linkmore.prefecture.controller.staff.response.ResStaffStallDetail;
import cn.linkmore.prefecture.controller.staff.response.ResStaffStallList;
import cn.linkmore.prefecture.dao.cluster.AdminAuthCityClusterMapper;
import cn.linkmore.prefecture.dao.cluster.AdminAuthPreClusterMapper;
import cn.linkmore.prefecture.dao.cluster.AdminAuthStallClusterMapper;
import cn.linkmore.prefecture.dao.cluster.AdminUserAuthClusterMapper;
import cn.linkmore.prefecture.dao.cluster.EntRentRecordClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StallClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StallLockClusterMapper;
import cn.linkmore.prefecture.dao.master.EntRentRecordMasterMapper;
import cn.linkmore.prefecture.dao.master.StallLockMasterMapper;
import cn.linkmore.prefecture.dao.master.StallMasterMapper;
import cn.linkmore.prefecture.entity.AdminAuthCity;
import cn.linkmore.prefecture.entity.AdminAuthPre;
import cn.linkmore.prefecture.entity.EntRentRecord;
import cn.linkmore.prefecture.entity.Stall;
import cn.linkmore.prefecture.entity.StallAssign;
import cn.linkmore.prefecture.entity.StallLock;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqControlLock;
import cn.linkmore.prefecture.request.ReqOrderStall;
import cn.linkmore.prefecture.request.ReqStall;
import cn.linkmore.prefecture.response.ResAdminAuthStall;
import cn.linkmore.prefecture.response.ResAdminUser;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStallAssign;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.prefecture.response.ResStallLock;
import cn.linkmore.prefecture.response.ResStallOperateLog;
import cn.linkmore.prefecture.response.ResStallOps;
import cn.linkmore.prefecture.service.AdminUserService;
import cn.linkmore.prefecture.service.PrefectureService;
import cn.linkmore.prefecture.service.StallAssignService;
import cn.linkmore.prefecture.service.StallOperateLogService;
import cn.linkmore.prefecture.service.StallService;
import cn.linkmore.redis.RedisService;
import cn.linkmore.task.TaskPool;
import cn.linkmore.third.client.PushClient;
import cn.linkmore.third.client.SendClient;
import cn.linkmore.third.request.ReqPush;
import cn.linkmore.util.DateUtils;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.TokenUtil;

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
	private StallAssignService assignService;
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
	private AdminAuthCityClusterMapper adminAuthCityClusterMapper;
	@Autowired
	private StallLockMasterMapper stallLockMasterMapper;
	@Autowired
	private StallLockClusterMapper stallLockClusterMapper;
	@Autowired
	private PushClient pushClient;
	@Autowired
	private SendClient sendClient;
	@Autowired
	private EntRentedRecordClient entRentedRecordClient;
	@Autowired
	private EntRentRecordMasterMapper entRentedRecordMasterMapper;
	@Autowired
	private EntRentRecordClusterMapper entRentedRecordClusterMapper;
	@Autowired
	private EntSocketClient entSocketClient;
	@Autowired
	private UserSocketClient userSocketClient;
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
	@Autowired
	private BaseDictClient baseDictClient;
	@Autowired
	private StallOperateLogService stallOperateLogService;
	private static final String DOWN_CAUSE = "cause_down";
	private static final String BATTERY = "battery-change";
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
			redisService.add(RedisKey.PREFECTURE_FREE_STALL.key + stall.getPreId(), stall.getLockSn());
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
			log.info("checkout.....................lock msg:{}", JsonUtil.toJson(res));
			if (code == 200) {
				redisService.add(RedisKey.PREFECTURE_FREE_STALL.key + stall.getPreId(), stall.getLockSn());
			}
		}
	}

	@Override
	public boolean checkout(Long stallId) {
		log.info("checkout..................... stallId :{}", stallId);
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
			log.info("checkout..................... stall-up-thread start");
		}
		return flag;
	}

	private void downing(ReqOrderStall reqos) {
		Stall stall = stallClusterMapper.findById(reqos.getStallId());
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			log.info("downing.....................stall:{},lockSn:{}", JsonUtil.toJson(stall), stall.getLockSn());
			long startTime = System.currentTimeMillis();
			ResponseMessage<LockBean> res = lockFactory.lockDown(stall.getLockSn());
			long endTime = System.currentTimeMillis();
			log.info("downing.....................{}降锁成功时间:{}秒",stall.getStallName(), (endTime - startTime)/1000);
			int code = res.getMsgCode();
			if (code == 200) {
				log.info("downing.....................success");
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
		/*boolean flag = true;
		Stall stall = stallClusterMapper.findById(stallId);
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			log.info("uping.....................name:{},sn:{}", stall.getStallName(), stall.getLockSn());
			ResponseMessage<LockBean> res = lockFactory.lockUp(stall.getLockSn());
			log.info("uping.....................res:{}", JsonUtil.toJson(res));
			int code = res.getMsgCode();
			if (code != 200) {
				// 此处为升锁操作
				flag = false;
				throw new BusinessException(StatusEnum.ORDER_LOCKUP_FAIL);
			}
			stall.setLockStatus(LockStatus.UP.status);
			stallMasterMapper.lockdown(stall);
		}
		return flag;*/
		boolean flag = true;
		Stall stall = stallClusterMapper.findById(stallId);
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			log.info("uping.....................name:{},sn:{}", stall.getStallName(), stall.getLockSn());
			LockBean lock = lockFactory.getLockInfo(stall.getLockSn()).getData();
			if(lock != null && lock.getLockState() == LockStatus.DOWN.status && lock.getParkingState() == 0) {
				ResponseMessage<LockBean> res = lockFactory.lockUp(stall.getLockSn());
				log.info("uping.....................lock:{}, res:{}", JsonUtil.toJson(lock), JsonUtil.toJson(res));
				int code = res.getMsgCode();
				if (code != 200) {
					// 此处为升锁操作
					flag = false;
					throw new BusinessException(StatusEnum.ORDER_LOCKUP_FAIL);
				}
				stall.setLockStatus(LockStatus.UP.status);
				stallMasterMapper.lockdown(stall);
			}
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
	public void install(ReqStall reqStall) {
		Date now = new Date();
		reqStall.setSellCount(0);
		reqStall.setGatewayId(0l);
		reqStall.setStatus(4);
		reqStall.setBindOrderStatus((short) 0);
		reqStall.setCreateTime(now);
		reqStall.setUpdateTime(now);
		
		reqStall.setLockStatus(null);
		reqStall.setLockSn(reqStall.getLockSn());
		reqStall.setStatus(4);
		reqStall.setLockBattery(0);

		Stall stall = new Stall();
		stall = ObjectUtils.copyObject(reqStall, stall);
		//插入车位
		this.stallMasterMapper.save(stall);
		
		ResStallLock lock = stallLockClusterMapper.findById(stall.getLockId());
		lock.setBindTime(now);
		lock.setPrefectureId(reqStall.getPreId());
		lock.setStallId(reqStall.getId());
		
		StallLock stallLock = new StallLock();
		stallLock = ObjectUtils.copyObject(lock, stallLock);
		//更新锁
		this.stallLockMasterMapper.updateBind(stallLock);
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
					log.info("<<<<<<<<<respose>>>>>>>>>" + res.getMsg() + "<<<code>>>" + res.getMsgCode());
					int code = res.getMsgCode();
					stopwatch.stop();
					log.info("<<<<<<<<<using time>>>>>>>>>" + String.valueOf(stopwatch.elapsed(TimeUnit.MILLISECONDS)));
					sendMsg(uid, reqc.getStatus(), code);
					String robkey = RedisKey.ROB_STALL_ISHAVE.key + reqc.getStallId();
					EntRentRecord record = entRentedRecordClusterMapper.findByUser(Long.valueOf(uid));
					if (code == 200) {
						redisService.remove(reqc.getKey());
						if (reqc.getStatus() == 2) {
							log.info("<<<<<<<<<up success>>>>>>>>>");
							// 未完成记录同一用户只有一单
							if (Objects.nonNull(record)) {
								EntRentRecord up = new EntRentRecord();
								up.setLeaveTime(new Date());
								up.setStatus(1L);
								up.setId(record.getId());
								entRentedRecordMasterMapper.updateByIdSelective(up);
							}
						} else {
							log.info("<<<<<<<<<down success>>>>>>>>>");
						}
						stall.setLockStatus(reqc.getStatus() == 1 ? 2 : 1);
						stall.setStatus(reqc.getStatus() == 1 ? 2 : 1);
						stallMasterMapper.lockdown(stall);
					} else {
						if (reqc.getStatus() == 1) {
							log.info("<<<<<<<<<down fail>>>>>>>>>>");
							// 降锁失败 取消绑定
							if (Objects.nonNull(record)) {
								EntRentRecord up = new EntRentRecord();
								up.setLeaveTime(new Date());
								up.setStatus(2L);
								up.setId(record.getId());
								entRentedRecordMasterMapper.updateByIdSelective(up);
							}
						} else {
							log.info("<<<<<<<<<up fail>>>>>>>>>>");
						}
					}
					redisService.remove(robkey);
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
					log.info("res>>>>>>>" + res.getMsg());
					int code = res.getMsgCode();
					stopwatch.stop();
					log.info("usingtime>>>" + String.valueOf(stopwatch.elapsed(TimeUnit.MILLISECONDS)));
					sendWYMsg(uid, reqc.getStatus(), code);
					if (code == 200) {
						if (reqc.getStatus() == 1) {
							stall.setLockStatus(2);
							if (stall.getType() == 2) {
								if (stall.getStatus() != 4) {
									stall.setStatus(2);
								}
							}
						} else if (reqc.getStatus() == 2) {
							if (stall.getType() == 2) {
								if (stall.getStatus() != 4) {
									stall.setStatus(1);
								}
							}
							stall.setLockStatus(1);
						}
						stallMasterMapper.lockdown(stall);
						if (reqc.getStatus() == 1) {
							downLock(reqc.getStallId(), 1);
							redisService.remove(reqc.getKey());
						}

					} else {
						if (reqc.getStatus() == 1) {
							downLock(reqc.getStallId(), 0);
						}
					}
				}
			}
		}).start();
	}

	@Override
	public Map<String, Object> watch(Long stallId) {
		log.info("stall:=====================");
		Map<String, Object> map = new HashMap<>();
		Stall stall = stallClusterMapper.findById(stallId);
		log.info("stall:{}", JsonUtil.toJson(stall));
		if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			ResponseMessage<LockBean> res = lockFactory.getLockInfo(stall.getLockSn());
			map.put("code", res.getMsgCode());
			if (res.getMsgCode() == 200) {
				LockBean Lockbean = res.getData();
				map.put("status", Lockbean.getLockState());
				map.put("onlineState", Lockbean.getOnlineState());
				map.put("parkingState", Lockbean.getParkingState());
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
					log.info("sendMsg>>>" + uid);
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
					log.info("sendWYMsg>>>" + uid);
				}
			}
		}).start();
	}

	public void downLock(Long stallId, int status) {
		// ResUserOrder latest = this.entOrderClient.findStallLatest(stallId);
		// Map<String,Object> param = new HashMap<>();
		// param.put( "lockDownStatus",status);
		// param.put("lockDownTime", new Date());
		// param.put("orderId", latest.getId());
		this.entRentedRecordClient.updateDownTime(stallId);
		// this.entOrderClient.updateLockStatus(param);
	}

	private void send(String uid, Integer lockstatus, int code) {
		String title = "车位锁操作通知";
		String content = "车位锁" + (lockstatus == 1 ? "降下" : "升起") + (code == 200 ? "成功 " : "失败");
		PushType type = PushType.LOCK_CONTROL_NOTICE;
		String bool = (code == 200 ? "true" : "false");
		Token token = (Token) redisService.get(RedisKey.USER_APP_AUTH_TOKEN.key + uid.toString());
		log.info("send>>>" + JsonUtil.toJson(token));
		if (token != null) {
			if (token.getClient() == Constants.ClientSource.WXAPP.source) {
				log.info("..........socket start...............");
				CacheUser cu = (CacheUser) redisService.get(RedisKey.USER_APP_AUTH_TOKEN.key + token.getAccessToken());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("title", title);
				map.put("type", type);
				map.put("content", content);
				map.put("data", token.getAccessToken());
				map.put("alias", cu.getId());
				ResEntStaff staff = this.entStaffClient.findById(cu.getId());
				userSocketClient.push(JsonUtil.toJson(map), staff.getOpenId());
			} else {
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
	}

	private void sendWY(String uid, Integer lockstatus, int code) {
		String title = "车位锁操作通知";
		String content = "车位锁" + (lockstatus == 1 ? "降下" : "升起") + (code == 200 ? "成功 " : "失败");
		String bool = (code == 200 ? "true" : "false");
		Token token = (Token) redisService.get(RedisKey.STAFF_ENT_AUTH_TOKEN.key + uid.toString());
		log.info("send>>>", JsonUtil.toJson(token));
		if (token != null) {
			if (token.getClient() == Constants.ClientSource.WXAPP.source) {
				CacheUser cu = (CacheUser) redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key + token.getAccessToken());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("title", title);
				map.put("type", PushType.LOCK_CONTROL_NOTICE);
				map.put("content", content);
				map.put("data", token.getAccessToken());
				map.put("alias", cu.getId());
				ResEntStaff staff = this.entStaffClient.findById(cu.getId());
				entSocketClient.push(JsonUtil.toJson(map), staff.getOpenId());
			} else {
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
		CacheUser cu = (CacheUser) this.redisService
				.get(RedisKey.STAFF_STAFF_AUTH_USER.key + TokenUtil.getKey(request));
		if (!checkStaffCityAuth(cu.getId(), cityId)) {
			throw new BusinessException(StatusEnum.STAFF_CITY_EXISTS);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("userId", cu.getId());
		List<AdminAuthPre> pres = this.adminAuthPreClusterMapper.findList(map);
		List<Long> list = pres.stream().map(pre -> pre.getPreId()).collect(Collectors.toList());
		map.put("preIds", list);
		map.put("cityId", cityId);
		map.put("categorys", Arrays.asList(0,1));
		List<ResPre> pre = this.prefectureService.findPreByIds(map);
		List<ResStaffPreList> resPres = new ArrayList<>();
		if(pre == null || pre.size() ==0 ) {
			return resPres;
		}
		List<ResAdminAuthStall> adminStalls = this.adminAuthStallClusterMapper.findStallList(map);
		List<Long> collect = adminStalls.stream().map(au -> au.getStallId()).collect(Collectors.toList());
		List<Stall> stalls = this.stallClusterMapper.findAll();
		map = new HashMap<>();
		List<ResUnusualOrder> unusualOrders = feignUnusualOrderClient.findList(map);
		ResStaffPreList preList = null;
		for (ResPre resPre : pre) {
			preList = new ResStaffPreList();
			int preTypeStalls = 0;
			int preUseTypeStalls = 0;
			int preLeisureTypeStalls = 0;
			int preFaultTypeStalls = 0;
			int orderNum = 0;
			for (Stall stall : stalls) {
				if (stall.getType() != 0 || !stall.getPreId().equals(resPre.getId())) {
					continue;
				}
				if (collect.contains(stall.getId())) {
					preTypeStalls++;
					if (stall.getStatus() == 2) {
						preUseTypeStalls++;
					} else if (stall.getStatus() == 1) {
						preLeisureTypeStalls++;
					} else if (stall.getStatus() == 4) {
						preFaultTypeStalls++;
					}
				}
			}
			if (unusualOrders != null) {
				for (ResUnusualOrder resUnusualOrder : unusualOrders) {
					if (resUnusualOrder.getPrefectureId().equals(resPre.getId())) {
						orderNum++;
					}
				}
			}
			preList.setPreLeisureTypeStalls(preLeisureTypeStalls);
			preList.setPreFaultTypeStalls(preFaultTypeStalls);
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
		CacheUser cu = (CacheUser) this.redisService
				.get(RedisKey.STAFF_STAFF_AUTH_USER.key + TokenUtil.getKey(request));
		if (!checkStaffPreAuth(cu.getId(), staffList.getPreId())) {
			throw new BusinessException(StatusEnum.STAFF_PREFECTURE_EXISTS);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("userId", cu.getId());
		List<ResAdminAuthStall> stallAuthList = this.adminAuthStallClusterMapper.findStallList(map);
		map.put("preId", staffList.getPreId());
		map.put("type", 0);
		if (StringUtils.isNotBlank(staffList.getStallName())) {
			map.put("stallNameLike", "%" + staffList.getStallName() + "%");
		}
		// 预留功能根据状态查询
		if (staffList.getStatus() != null) {
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
		log.info("【 ResStall list 】 " + JsonUtil.toJson(stallList));
		List<ResStaffStallList> staffStallLists = new ArrayList<>();
		ResStaffStallList ResStaffStallList;
		List<ResOrderPlate> plates = this.entOrderClient.findPlateByPreId(staffList.getPreId());
		log.info("【 ResOrderPlate 】 " + JsonUtil.toJson(plates));
		List<Long> stallAuthIds = stallAuthList.stream().map(stall -> stall.getStallId()).collect(Collectors.toList());
		ResPrefectureDetail detail = this.prefectureService.findById(staffList.getPreId());
		log.info("【 ResPrefectureDetail 】 " + JsonUtil.toJson(detail));
		ResponseMessage<LockBean> lock = lockFactory.findAvailableLock(detail.getGateway());
		List<LockBean> bockBeans = null;
		List<ResEntExcStallStatus> excStallList = feignStallExcStatusClient.findAll();
		if (lock != null) {
			bockBeans = lock.getDataList();
			log.info("【lockBean list 】 " + JsonUtil.toJson(bockBeans));
		}
		for (ResStall resStall : stallList) {
			ResStaffStallList = new ResStaffStallList();
			if (!stallAuthIds.contains(resStall.getId())) {
				continue;
			}
			if (resStall.getStatus() == 2) {
				// List<String> list = ObjectUtils.findFieldVlaue(plates, "plateNo", new
				// String[]{"stallId"}, new Object[]{resStall.getId()});
				for (ResOrderPlate resOrderPlate : plates) {
					if (resOrderPlate.getStallId().equals(resStall.getId())) {
						ResStaffStallList.setPlateNo(resOrderPlate.getPlateNo());
					}
				}
				
			}else if(resStall.getStatus() == 1) {
				// 指定车位锁
				int assignStatus = 1;
				String lockSn = resStall.getLockSn();
				Set<Object> set = redisService.members(Constants.RedisKey.ORDER_ASSIGN_STALL.key);
				log.info("指定锁池个数: {}", set.size());
				log.info("指定锁池: {}", set.toString());
				Long preId = resStall.getPreId();
				for (Object obj : set) {
					JSONObject json = JSON.parseObject(obj.toString());
					String sn = json.get("lockSn").toString();
					Long pid = Long.parseLong(json.get("preId").toString());
					if (pid.longValue() == preId.longValue() && lockSn.equals(sn)) {
						assignStatus = 0;
						ResStaffStallList.setPlateNo(json.get("plate").toString());
						break;
					}
				}
				ResStaffStallList.setAssignStatus(assignStatus);
			}
			if (resStall.getStatus() != 4) {
				for (ResEntExcStallStatus resEntExcStallStatus : excStallList) {
					if (resEntExcStallStatus.getStallId().equals(resStall.getId())) {
						ResStaffStallList.setExcStatus(false);
					}
				}
			}
			if(resStall.getBindOrderStatus() != null && resStall.getBindOrderStatus() != 0) {
				ResStaffStallList.setExcStatus(false);
			}
			boolean falg = true;
			if (bockBeans != null) {
				for (LockBean lockBean : bockBeans) {
					if (lockBean.getLockCode().equals(resStall.getLockSn())) {
						falg = false;
						switch (lockBean.getLockState()) {
						case 0:
							ResStaffStallList.setLockStatus(2);
							break;
						case 2:
							ResStaffStallList.setLockStatus(1);
							break;
						case 3:
							ResStaffStallList.setLockStatus(2);
							break;
						case 1:
							ResStaffStallList.setLockStatus(1);
							break;
						}
						break;
					}
				}
			}
			if (falg) {
				ResStaffStallList.setLockStatus(resStall.getLockStatus());
			}
			ResStaffStallList.setStatus(resStall.getStatus());
			ResStaffStallList.setStallId(resStall.getId());
			ResStaffStallList.setStallName(resStall.getStallName());
			staffStallLists.add(ResStaffStallList);
		}
		return staffStallLists;
	}

	@Override
	public Boolean checkStaffCityAuth(Long userId, Long cityId) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("cityId", cityId);
		List<AdminAuthCity> list = this.adminAuthCityClusterMapper.findList(map);
		return list != null && list.size() != 0 ? true : false;
	}

	@Override
	public Boolean checkStaffPreAuth(Long userId, Long preId) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("preId", preId);
		List<AdminAuthPre> list = this.adminAuthPreClusterMapper.findList(map);
		return list != null && list.size() != 0 ? true : false;
	}

	@Override
	public Boolean checkStaffStallAuth(Long userId, Long stallId) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("stallId", stallId);
		List<ResAdminAuthStall> list = this.adminAuthStallClusterMapper.findStallList(map);
		return list != null && list.size() != 0 ? true : false;
	}
	
	/**
	 * 管理版锁操作
	 */ 
	@Override
	public void operating(ReqControlLock reqc) {
		TaskPool.getInstance().task(new Runnable() {
			@Override
			public void run() {
				String uid = String.valueOf(redisService.get(reqc.getKey()));
				Stall stall = stallClusterMapper.findById(reqc.getStallId());
				if (stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
					log.info("operating············name:{},··········sn:{}", stall.getStallName(), stall.getLockSn());
					ResponseMessage<LockBean> res = null;
					// 1 降下 2 升起
					if (reqc.getStatus() == 1) {
						res = lockFactory.lockDown(stall.getLockSn());
					} else if (reqc.getStatus() == 2) {
						res = lockFactory.lockUp(stall.getLockSn());
					}
					log.info(" operating··············" + res.getMsg() + " code·············" + res.getMsgCode());
					int code = res.getMsgCode();
					sendMsgT(uid, reqc.getStatus(), code);
					if (code == 200) {
						redisService.remove(reqc.getKey());
						stall.setLockStatus(reqc.getStatus() == 1 ? 2 : 1);
						stall.setStatus(reqc.getStatus() == 1 ? 2 : 1);
						stallMasterMapper.lockdown(stall);
					}
				}
			}
		});
	}
	
	
	void	sendMsgT(String uid, Integer lockstatus, int code){
		TaskPool.getInstance().task( new Runnable() {
			@Override
			public void run() {
				String title = "车位锁操作通知";
				String content = "车位锁" + (lockstatus == 1 ? "降下" : "升起") + (code == 200 ? "成功 " : "失败");
				PushType type = PushType.LOCK_CONTROL_NOTICE;
				String bool = (code == 200 ? "true" : "false");
				Token token = (Token) redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key + uid.toString());
				log.info("send>>>" + JsonUtil.toJson(token));
				if (token != null) {
					if (token.getClient() == Constants.ClientSource.WXAPP.source) {
						log.info("..........socket start...............");
						/*CacheUser cu = (CacheUser) redisService.get(RedisKey.USER_APP_AUTH_TOKEN.key + token.getAccessToken());
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("title", title);
						map.put("type", type);
						map.put("content", content);
						map.put("data", token.getAccessToken());
						map.put("alias", cu.getId());
						ResEntStaff staff = entStaffClient.findById(cu.getId());
						userSocketClient.push(JsonUtil.toJson(map), staff.getOpenId());*/
					} else {
						ReqPush rp = new ReqPush();
						rp.setAlias(uid);
						rp.setTitle(title);
						rp.setContent(content);
						rp.setClient(token.getClient());
						rp.setType(type);
						rp.setData(bool);
						sendClient.give(rp);
					}
				}
			}
		});
	}
	

	@Override
	public ResStaffStallDetail findStaffStallDetails(HttpServletRequest request, Long stallId) {
		CacheUser cu = (CacheUser) this.redisService
				.get(RedisKey.STAFF_STAFF_AUTH_USER.key + TokenUtil.getKey(request));
		if (!checkStaffStallAuth(cu.getId(), stallId)) {
			throw new BusinessException(StatusEnum.STAFF_STALL_EXISTS);
		}
		ResStaffStallDetail detail = new ResStaffStallDetail();
		Stall stall = this.stallClusterMapper.findById(stallId);
		ResponseMessage<LockBean> lockInfo = this.lockFactory.getLockInfo(stall.getLockSn());
		List<ResBaseDict> baseDict = this.baseDictClient.findList(DOWN_CAUSE);

		LockBean lockBean = null;
		if (lockInfo != null) {
			lockBean = lockInfo.getData();
		}
		if (lockBean != null) {
			detail.setBetty(lockBean.getElectricity());
			detail.setStallId(stall.getId());
			detail.setCarStatus(lockBean.getParkingState());
			switch (lockBean.getLockState()) {
			case 0:
				detail.setLockStatus(2);
				break;
			case 2:
				detail.setLockStatus(1);
				break;
			case 3:
				detail.setLockStatus(2);
				break;
			case 1:
				detail.setLockStatus(1);
				break;
			}
		} else {
			detail.setBetty(0);
			detail.setLockStatus(stall.getLockStatus());
		}
		detail.setStallName(stall.getStallName());
		detail.setLockSn(stall.getLockSn());
		detail.setStatus(stall.getStatus());
		if (stall.getStatus() == 4) {
			ResStallOperateLog stallOperateLog = this.stallOperateLogService.findByStallId(stallId);
			if (stallOperateLog != null) {
				detail.setFaultId(stallOperateLog.getRemarkId());
				detail.setFaultName(stallOperateLog.getRemark());
			}
		} else {
			if (stall.getStatus() == 2) {
				ResUserOrder resUserOrder = this.entOrderClient.findStallLatest(stallId);
				log.info("【resUserOrder】==" + JsonUtil.toJson(resUserOrder));
				detail.setStartTime(resUserOrder.getBeginTime());
				detail.setDownTime(resUserOrder.getLockDownTime());
				detail.setOrderNo(resUserOrder.getOrderNo());
				detail.setMobile(resUserOrder.getUsername());
				detail.setOrderStatus(resUserOrder.getStatus().shortValue());
				String date = DateUtils.getDurationDetail(new Date(), resUserOrder.getBeginTime());
				detail.setStartDate(date);
				detail.setOrderId(resUserOrder.getId());
				if (resUserOrder.getOrderNo().contains("WX")) {
					detail.setOrderType("微信");
				} else if (resUserOrder.getOrderNo().contains("YL")) {
					detail.setOrderType("银联");
				} else {
					detail.setOrderType("APP");
				}
				detail.setPlate(resUserOrder.getPlateNo());
			}
			detail.setOnoffStatus(true);
			ResEntExcStallStatus entExcStall = feignStallExcStatusClient.findByStallId(stallId);
			if (entExcStall != null) {
				detail.setExcCode(entExcStall.getExcStatus());
			}
		}

		if (detail.getExcCode() != null || detail.getBetty() <= 30) {
			for (ResBaseDict resBaseDict : baseDict) {
				if (detail.getExcCode() != null) {
					if (detail.getExcCode().equals(resBaseDict.getId())) {
						detail.setExcName(resBaseDict.getName());
					}
				} else {
					if (BATTERY.equals(resBaseDict.getCode())) {
						detail.setResetStatus(false);
						detail.setExcCode(resBaseDict.getId());
						detail.setExcName(resBaseDict.getName());
					}
				}
			}
		}
		if(stall.getBindOrderStatus() != null && stall.getBindOrderStatus() == 1) {
			detail.setResetStatus(false);
			detail.setExcCode(0L);
			detail.setExcName("订单挂起未释放");
		}else if(stall.getBindOrderStatus() != null && stall.getBindOrderStatus() == 2) {
			detail.setOrderStatus((short)7);
		}
		// 指定车位锁
		int assignStatus = 1;
		String lockSn = stall.getLockSn();
		Set<Object> set = redisService.members(Constants.RedisKey.ORDER_ASSIGN_STALL.key);
		log.info("指定锁池个数: {}", set.size());
		log.info("指定锁池: {}", set.toString());
		Long preId = stall.getPreId();
		for (Object obj : set) {
			JSONObject json = JSON.parseObject(obj.toString());
			String sn = json.get("lockSn").toString();
			Long pid = Long.parseLong(json.get("preId").toString());
			if (pid.longValue() == preId.longValue() && lockSn.equals(sn)) {
				assignStatus = 0;
				detail.setAssignPlate(json.get("plate").toString());
				break;
			}
		}
		detail.setAssignStatus(assignStatus);
		return detail;
	}

	@Override
	public String staffAssign(ReqAssignStall bean, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService
				.get(RedisKey.STAFF_STAFF_AUTH_USER.key + TokenUtil.getKey(request));
		String plate = "";
		try {
			String lockSn = bean.getLockSn();
			plate = bean.getPlate().toUpperCase();
			Long preId = bean.getPreId();
			String key = "";
			String val = "";
			// 查询空闲锁
			key = Constants.RedisKey.PREFECTURE_FREE_STALL.key + preId;
			val = lockSn;
			Stall stall = this.stallClusterMapper.findByLockSn(lockSn);
			if (stall.getStatus().intValue() == Stall.STATUS_FREE) {
				ResAdminUser user = this.adminUserService.find(cu.getId());
				// 缓存指定车位
				Map<String, Object> map = new HashMap<>();
				map.put("lockSn", lockSn);
				map.put("plate", plate);
				map.put("preId", preId);
				key = Constants.RedisKey.ORDER_ASSIGN_STALL.key;
				val = JSON.toJSON(map).toString();
				this.redisSetOper(0, key, val);
				// 从空闲锁池中删除
				key = Constants.RedisKey.PREFECTURE_FREE_STALL.key + preId;
				val = lockSn;
				this.redisSetOper(1, key, val);
				log.info("assign success  stall:{} ,plate:{} ", stall.getStallName(), plate);

				StallAssign sa = new StallAssign();
				sa.setCreateTime(new Date());
				sa.setLockSn(lockSn);
				sa.setCarno(plate);
				sa.setStaffId(cu.getId());
				sa.setStaffName(user.getRealname());
				sa.setPreId(stall.getPreId());
				sa.setStallId(stall.getId());
				sa.setStallName(stall.getStallName());
				sa.setStatus(StallAssign.STATUS_ASSIGN);
				this.assignService.save(sa);
			} else {
				log.info("assign fail  stall:{} ,plate:{} ", stall.getStallName(), plate);
				throw new BusinessException(StatusEnum.STALL_OPERATE_ASSIGN_FREE);
			}
		} catch (Exception e) {
			log.info("assign error plate:{} ", plate);
			throw new BusinessException(StatusEnum.STALL_OPERATE_ASSIGN);
		}
		return null;
	}

	private boolean redisSetOper(int type, String key, String val) {
		boolean member = true;
		if (type == 0) {
			redisService.add(key, val);
		} else if (type == 1) {
			redisService.remove(key, val);
		} else if (type == 3) {
			Set<Object> set = redisService.members(key);
			member = set.contains(val);
		}
		return member;
	}

	@Override
	public void staffAssignDel(ReqAssignStall bean) {
		try {
			String lockSn = bean.getLockSn();
			String plate = bean.getPlate().toUpperCase();
			Long preId = bean.getPreId();
			String key = "";
			String val = "";
			// 删除指定车位
			Map<String, Object> map = new HashMap<>();
			map.put("lockSn", lockSn);
			map.put("plate", plate);
			map.put("preId", preId);
			key = Constants.RedisKey.ORDER_ASSIGN_STALL.key;
			val = JSON.toJSON(map).toString();
			this.redisSetOper(1, key, val);
			// 归还到空闲锁池
			key = Constants.RedisKey.PREFECTURE_FREE_STALL.key + preId;
			val = lockSn;
			this.redisSetOper(0, key, val);
			ResStallAssign sa = this.assignService.find(lockSn);
			if (sa != null) {
				sa.setCancelTime(new Date());
				sa.setStatus(StallAssign.STATUS_CANCEL);
				this.assignService.cancel(ObjectUtils.copyObject(sa, new StallAssign()));
			}
		} catch (Exception e) {
			throw new BusinessException(StatusEnum.STALL_OPERATE_ASSIGN_DELETE);
		}
	}

	@Override
	public List<ResStall> findStallsByPreIds(Map<String, Object> map) {
		return this.stallClusterMapper.findStallsByPreIds(map);
	}

	@Override
	public void reset(Long stallId, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService
				.get(RedisKey.STAFF_STAFF_AUTH_USER.key + TokenUtil.getKey(request));
		if(checkStaffStallAuth(cu.getId(), stallId)) {
			Map<String, Object> map = new HashMap<>();
			map.put("stallId", stallId);
			map.put("status", 1);
			this.feignStallExcStatusClient.updateExcStatus(map);
		}else {
			throw new BusinessException(StatusEnum.STAFF_STALL_EXISTS);
		}
	}
	
	
	

}
