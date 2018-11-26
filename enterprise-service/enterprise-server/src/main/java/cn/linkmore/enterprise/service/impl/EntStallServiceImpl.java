/**
 * 
 */
package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.common.comm.ResponseMessage;
import cn.linkmore.account.client.UserClient;
import cn.linkmore.bean.common.Constants.ExpiredTime;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.Constants.StallStatus;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.common.client.BaseDictClient;
import cn.linkmore.common.response.ResBaseDict;
import cn.linkmore.enterprise.controller.ent.request.ReqStallExcCause;
import cn.linkmore.enterprise.controller.ent.response.ResDetailStall;
import cn.linkmore.enterprise.controller.ent.response.ResEntStalls;
import cn.linkmore.enterprise.controller.ent.response.ResEntTypeStalls;
import cn.linkmore.enterprise.controller.ent.response.ResStallName;
import cn.linkmore.enterprise.dao.cluster.EntAuthPreClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntAuthStallClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntPrefectureClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntRentedRecordClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntStaffAuthClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntStaffClusterMapper;
import cn.linkmore.enterprise.entity.EntAuthPre;
import cn.linkmore.enterprise.entity.EntPrefecture;
import cn.linkmore.enterprise.entity.EntRentUser;
import cn.linkmore.enterprise.entity.EntRentedRecord;
import cn.linkmore.enterprise.entity.EntStaff;
import cn.linkmore.enterprise.entity.EntStaffAuth;
import cn.linkmore.enterprise.entity.StallExcStatus;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.enterprise.service.EntRentUserService;
import cn.linkmore.enterprise.service.EntStallService;
import cn.linkmore.enterprise.service.EnterpriseService;
import cn.linkmore.enterprise.service.StallExcStatusService;
import cn.linkmore.order.client.EntOrderClient;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.response.ResEntOrder;
import cn.linkmore.order.response.ResOrderPlate;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.prefecture.client.FeignLockClient;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.prefecture.client.StallBatteryLogClient;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.client.StallOperateLogClient;
import cn.linkmore.prefecture.request.ReqControlLock;
import cn.linkmore.prefecture.request.ReqStallOperateLog;
import cn.linkmore.prefecture.response.ResLockInfo;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStallBatteryLog;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.prefecture.response.ResStallOperateLog;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.DateUtils;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.StringUtil;
import cn.linkmore.util.TokenUtil;

/**
 * @author luzhishen
 * @Date 2018年7月20日
 * @Version v1.0
 */
@Service
public class EntStallServiceImpl implements EntStallService {

	public static final String CAUSE_DOWN = "cause_down";
	public static final String STALL_LOCK_OPER_STATUS = "STALL:LOCK:OPER:STATUS";
	private Logger log = LoggerFactory.getLogger(getClass());
	private static final String DOWN_CAUSE = "cause_down";
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private UserClient userClient;
	@Autowired
	private PrefectureClient prefectrueClient;
	@Autowired
	private StallBatteryLogClient stallBatteryLogClient;
	@Autowired
	private StallOperateLogClient stallOperateLogClient;
	@Autowired
	private BaseDictClient dictClient;
	@Autowired
	private StallExcStatusService stallExcStatusService;
	@Autowired
	private EntStaffAuthClusterMapper entStaffAuthClusterMapper;
	@Autowired
	private EntRentedRecordClusterMapper rentedRecordClusterMapper;
	@Autowired
	private EntAuthPreClusterMapper entAuthPreClusterMapper;

	@Autowired
	private FeignLockClient feignLockClient;
	@Autowired
	private EntPrefectureClusterMapper entPrefectureClusterMapper;

	@Autowired
	private EntAuthStallClusterMapper entAuthStallClusterMapper;

	@Autowired
	private RedisService redisService;

	@Autowired
	private StallClient stallClient;

	@Autowired
	private EntOrderClient orderClient;

	@Autowired
	private OrderClient feignOrderClient;

	@Autowired
	private EntRentUserService entRentUserService;

	@Autowired
	private EntStaffClusterMapper entStaffClusterMapper;

	public static final String DOWN = "down-";

	@Override
	public List<ResEntStalls> selectEntStalls(HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser) this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key + key);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("staffId", ru.getId());
		List<EntStaffAuth> entStaffAuths = entStaffAuthClusterMapper.findList(map);
		int size = entStaffAuths.size();
		if (size == 0) {
			return new ArrayList<ResEntStalls>();
		}
		EntStaffAuth entStaffAuth = entStaffAuths.get(0);
		Map<String, Object> param = new HashMap<>();
		Set<Long> list = entStaffAuths.stream().map(ent -> ent.getAuthId()).collect(Collectors.toSet());
		param.put("authIds", new ArrayList<>(list));
		// param.put("authId", entStaffAuth.getAuthId());
		List<EntAuthPre> entAuthPres = entAuthPreClusterMapper.findList(param);
		int preSize = entAuthPres.size();
		EntAuthPre entAuthPre = null;
		// List<EntRentUser> rentUsers = entRentUserService.findAll();
		List<ResEntStalls> entStallList = new ArrayList<>();
		ResEntStalls resEntStalls = null;
		Map<String, Object> params = null;
		List<ResStall> stalls = null;
		EntPrefecture entPrefeture = null;
		// Set<Long> collect = entAuthPres.stream().map(ent ->
		// ent.getAuthId()).collect(Collectors.toSet());
		map.put("authIds", new ArrayList<>(list));
		List<Long> stallListByIds = this.entAuthStallClusterMapper.findStallListByIds(map);
		for (int i = 0; i < preSize; i++) {
			entAuthPre = entAuthPres.get(i);
			if (entAuthPre == null) {
				continue;
			}
			entPrefeture = entPrefectureClusterMapper.findByPreId(entAuthPre.getPreId());
			if (entPrefeture == null) {
				continue;
			}
			resEntStalls = new ResEntStalls();
			resEntStalls.setPreId(entAuthPre.getPreId());
			resEntStalls.setPreName(entPrefeture.getPreName());

			params = new HashMap<String, Object>();
			params.put("preId", entAuthPre.getPreId());
			stalls = stallClient.findStallList(params);
			// param.put("authId", entAuthPre.getAuthId());
			int preStalls = 0;
			int preUseStalls = 0;
			int preVipTypeStalls = 0;
			int preVipUseTypeStalls = 0;
			int preRentTypeStalls = 0;
			int preRentUseTypeStalls = 0;
			int preTempTypeStalls = 0;
			int preTempUseTypeStalls = 0;

			Map<String, ResEntTypeStalls> typeSum = new HashMap<>();
			for (int j = 0; j < stalls.size(); j++) {
				ResStall resStall = stalls.get(j);
				if (!stallListByIds.contains(resStall.getId()) || resStall.getType() == 0) {
					continue;
				}
				if (resStall.getStatus() == StallStatus.USED.status) {
					preUseStalls++;
				}
			}
			for (int j = 0; j < stalls.size(); j++) {
				ResStall resStall = stalls.get(j);
				if (!stallListByIds.contains(resStall.getId()) || resStall.getType() == 0) {
					continue;
				}
				// 临停使用 || 临停
				if (resStall.getType() == 1 && resStall.getStatus() == StallStatus.USED.status) {
					preTempUseTypeStalls++;
				}
				if (resStall.getType() == 1) {
					preTempTypeStalls++;
				}
				// 长租使用||长租
				if (resStall.getType() == 2 && resStall.getStatus() == StallStatus.USED.status) {
					preRentUseTypeStalls++;
				}
				if (resStall.getType() == 2) {
					preRentTypeStalls++;
				}
				preStalls++;
				/*
				 * StringBuilder sb = new StringBuilder(); for (EntRentUser rentUser :
				 * rentUsers) { if(rentUser.getStallId().equals(resStall.getId())) {
				 * sb.append(rentUser.getPlate()).append("/"); } }
				 */
				// vip使用 ||vip
				if (resStall.getType() == 3 && resStall.getStatus() == StallStatus.USED.status) {
					preVipUseTypeStalls++;
				}
				if (resStall.getType() == 3) {
					preVipTypeStalls++;
				}
			}

			resEntStalls.setPreStalls(preStalls);
			resEntStalls.setPreUseStalls(preUseStalls);

			ResEntTypeStalls vipStalls = new ResEntTypeStalls();
			vipStalls.setType((short) 3);
			vipStalls.setTypeName("vip车位");
			vipStalls.setPreTypeStalls(preVipTypeStalls);
			vipStalls.setPreUseTypeStalls(preVipUseTypeStalls);
			typeSum.put("vip", vipStalls);
			ResEntTypeStalls rentStalls = new ResEntTypeStalls();
			rentStalls.setType((short) 2);
			rentStalls.setTypeName("长租车位");
			rentStalls.setPreTypeStalls(preRentTypeStalls);
			rentStalls.setPreUseTypeStalls(preRentUseTypeStalls);
			typeSum.put("rent", rentStalls);
			ResEntTypeStalls tempStalls = new ResEntTypeStalls();
			tempStalls.setType((short) 1);
			tempStalls.setTypeName("临停车位");
			tempStalls.setPreTypeStalls(preTempTypeStalls);
			tempStalls.setPreUseTypeStalls(preTempUseTypeStalls);
			typeSum.put("temp", tempStalls);
			resEntStalls.setTypeStalls(typeSum);
			entStallList.add(resEntStalls);
		}
		return entStallList;
	}

	@Override
	public List<cn.linkmore.enterprise.controller.ent.response.ResStall> selectStalls(HttpServletRequest request, Long preId, Short type, String name) {
		
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser) this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key + key);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("staffId", ru.getId());
		map.put("status", 1);
		List<EntStaffAuth> entStaffAuths = entStaffAuthClusterMapper.findList(map);
		int size = entStaffAuths.size();
		List<ResStallName> stallNames = new ArrayList<>();
		if (size == 0) {
			return new ArrayList<cn.linkmore.enterprise.controller.ent.response.ResStall>();
		}
		// EntStaffAuth entStaffAuth = entStaffAuths.get(0);
		Map<String, Object> param = new HashMap<String, Object>();
		List<Long> list = entStaffAuths.stream().map(ent -> ent.getAuthId()).collect(Collectors.toList());
		param.put("authIds", list);
		// param.put("authId", entStaffAuth.getAuthId());
		// List<Long> stallIds= entAuthStallClusterMapper.findStallList(param);
		List<Long> stallIds = entAuthStallClusterMapper.findStallListByIds(param);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("list", stallIds);
		params.put("stallName", name);
		log.info("-----------params = {}", JSON.toJSON(params));
		List<ResStall> stalls = this.stallClient.findPreStallList(params); // 根据stallid 集合查询车位
		log.info("-----------stall-list = {}", JSON.toJSON(stalls));
		List<ResOrderPlate> orders = orderClient.findPlateByPreId(preId);
		List<Long> collect = stalls.stream().map(stall -> stall.getId()).collect(Collectors.toList());
		List<StallExcStatus> stallExcList = null;
		if (collect != null && collect.size() > 0) {
			stallExcList = this.stallExcStatusService.findExcStatusList(collect);
		}
		ResPrefectureDetail pref = this.prefectrueClient.findById(preId);
//		ResponseMessage<LockBean> locks = lockFactory.findAvailableLock(pref.getGateway());
		List<ResLockInfo> lockBeans = feignLockClient.lockList(pref.getGateway());
//		List<LockBean> lockBeans = locks.getDataList();
		// 设置车位对应的车牌号
		log.debug(JsonUtil.toJson(lockBeans));
		List<cn.linkmore.enterprise.controller.ent.response.ResStall> stallList = new ArrayList<>();
		cn.linkmore.enterprise.controller.ent.response.ResStall stall = null;
		// lockFactory.
		for (ResStall resStall : stalls) {
			if (orders == null) {
				break;
			}
			if (resStall.getPreId().equals(preId)) {
				stall = ObjectUtils.copyObject(resStall, new cn.linkmore.enterprise.controller.ent.response.ResStall());
				stall.setType(resStall.getType());
				stall.setStallId(resStall.getId());
				for (ResLockInfo lock : lockBeans) {
					if (lock.getLockCode().equals(stall.getLockSn())) {
						if (lock.getLockState() == 1) {
							log.info(lock.getLockCode() + "===" + lock.getLockState());
							stall.setLockStatus(lock.getLockState());
						} else {
							stall.setLockStatus(2);
						}
					}
				}
				if (lockBeans != null) {
					for (ResLockInfo lock : lockBeans) {
						if (lock.getLockCode().equals(stall.getLockSn())) {
							if (lock.getElectricity() <= 30) {
								stall.setExcStatus(false);
							}
						}
					}
				}
				if (resStall.getType() != 2) {
					for (ResOrderPlate orderPlate : orders) {
						if (resStall.getId().equals(orderPlate.getStallId())) {
							stall.setPlateNo(orderPlate.getPlateNo());
							break;
						}
					}
				}
				// 设置车位锁异常状态
				for (StallExcStatus stallExcStatus : stallExcList) {
					if (stallExcStatus.getStallId().equals(resStall.getId())) {
						stall.setExcStatus(false);
						break;
					}
				}
				stallList.add(stall);
			}
		}
	/*	ResStallName stallName = null;
		StringBuilder sb = new StringBuilder();
		int end = 0;
		stallName = new ResStallName();
		stallName.setId("0");
		cn.linkmore.enterprise.controller.ent.response.ResStall resStall = null;
		for (int i = 0; i < stallList.size(); i++) {
			resStall = stallList.get(i);
			resStall.setpId(stallName.getId());
			stallName.getStalls().add(resStall);
			if (i == end) {
				sb.append(stallList.get(i).getStallName()).append("-");
				end += 5;
				if (i > 1 && (i + 1) == stallList.size()) {
					// sb.append(stallList.get(i).getStallName());
					stallName.setStallName(stallList.get(i).getStallName());
					stallNames.add(stallName);
				}
			} else if (i == (end - 1)) {
				sb.append(stallList.get(i).getStallName());
				stallName.setStallName(sb.toString());
				Integer id = Integer.parseInt(stallName.getId());
				sb = new StringBuilder();
				stallNames.add(stallName);
				stallName = new ResStallName();
				stallName.setId(id + 1 + "");
			} else if (i == stallList.size() - 1) {
				sb.append(stallList.get(i).getStallName());
				stallName.setStallName(sb.toString());
				stallNames.add(stallName);
			}
			if (stallList.size() == 1) {
				stallName.setStallName(stallList.get(i).getStallName());
				stallNames.add(stallName);
			}
		}*/
		return stallList;
	}

	@Override
	public ResDetailStall selectEntDetailStalls(Long stallId, HttpServletRequest request) {
		if (!checkAuth(stallId, request)) {
			throw new BusinessException(StatusEnum.STAFF_STALL_EXISTS);
		}
		ResStallEntity resStallEntity = this.stallClient.findById(stallId);
		if (StringUtil.isBlank(resStallEntity.getLockSn())) {
			return null;
		}
//		ResponseMessage<LockBean> res = lockFactory.getLockInfo(resStallEntity.getLockSn());
		ResLockInfo lockBean = feignLockClient.lockInfo(resStallEntity.getLockSn());
		ResDetailStall resDetailStall = new ResDetailStall();
		resDetailStall.setSlaveCode(resStallEntity.getLockSn());
		/*
		 * if(lockBean == null){ return resDetailStall; }
		 */
		resDetailStall.setInductionState(lockBean.getInductionState());
		ResEntOrder resEntOrder = this.orderClient.findOrderByStallId(resStallEntity.getId());
		if (resStallEntity.getStatus() != 1 && resStallEntity.getStatus() != 4) {
			if (resEntOrder != null) {
				resDetailStall.setPlate(resEntOrder.getPlate());
			}
		}
		if (resStallEntity.getType() != null && resStallEntity.getType() == 2) {
			List<String> paltes = new ArrayList<>();
			StringBuilder sb = new StringBuilder();
			List<EntRentUser> rentUsers = this.entRentUserService.findAll();
			for (EntRentUser entRentUser : rentUsers) {
				if (entRentUser.getStallId().equals(resStallEntity.getId())) {
					if (new Date().getTime() >= entRentUser.getEndTime().getTime()) {
						continue;
					}
					if (!paltes.contains(entRentUser.getPlate())) {
						sb.append(entRentUser.getPlate()).append("/");
						paltes.add(entRentUser.getPlate());
					}
				}
			}
			/*
			 * EntRentedRecord record =
			 * this.rentedRecordClusterMapper.findByStallId(resStallEntity.getId());
			 * if(record == null) { EntRentUser entRentUser =
			 * this.entRentUserService.findByStallId(resStallEntity.getId()); if(entRentUser
			 * != null) { resDetailStall.setMobile(entRentUser.getMobile()); } }else {
			 * ResUser resUser = userClient.findById(record.getUserId()); if(resUser !=
			 * null) { resDetailStall.setMobile(resUser.getMobile()); } }
			 */
			// this.entAuthStallClusterMapper.findByStall(resStallEntity.getId());
			// this.enterpriseService.findById(ent)
			// resDetailStall.setMobile(record.get);
			resDetailStall.setPlate(sb.length() != 0 ? sb.substring(0, sb.length() - 1) : null);
			EntRentUser entRentUser = this.entRentUserService.findByStallId(resStallEntity.getId());
			if (entRentUser != null && new Date().getTime() <= entRentUser.getEndTime().getTime()) {
				if (entRentUser.getType() != null && entRentUser.getType() == 1) {
					ResEnterprise enterprise = this.enterpriseService.findById(entRentUser.getEntId());
					if (enterprise != null) {
						resDetailStall.setMobile(enterprise.getTellphone());
					}
				} else {
					resDetailStall.setMobile(entRentUser.getMobile());
				}
			}
			if (resStallEntity.getStatus() == 2) {
				EntRentedRecord record = this.rentedRecordClusterMapper.findByStallId(resStallEntity.getId());
				if (record != null && record.getDownTime() != null) {
					resDetailStall.setDownTime(record.getDownTime());
				}
			}
		} else if (resStallEntity.getType() != null && resStallEntity.getType() == 1) {
			if (resStallEntity.getStatus() == 2) {
				resDetailStall.setDownTime(resEntOrder.getLockDownTime());
				resDetailStall.setOrderNo(resEntOrder.getOrderNo());
				resDetailStall.setStartTime(resEntOrder.getBeginTime());
				resDetailStall.setMobile(resEntOrder.getMobile());
				String duration = DateUtils.getDuration(new Date(), resEntOrder.getBeginTime());
				resDetailStall.setStartDate(duration);
			}
		}

		if (ResDetailStall.DOWN_STATUS.equals(resStallEntity.getStatus())) {
			ResStallOperateLog operLog = this.stallOperateLogClient.findByStallId(resDetailStall.getStallId());
			if (operLog.getOperation().equals(2)) {
				resDetailStall.setExcCode(operLog.getRemarkId());
				resDetailStall.setExcName(operLog.getRemark());
			}
		} else {
			StallExcStatus excStatus = this.stallExcStatusService.findExcStatus(resStallEntity.getId());
			if (excStatus != null) {
				resDetailStall.setExcCode(excStatus.getExcStatus());
				resDetailStall.setExcName(excStatus.getExcRemark());
			}
		}
		// 当车位状态为下线或者故障时，该车位下线，显示上线操作按钮 当车位状态为空闲或者使用中该车位上线 显示下线操作按钮
		if (resStallEntity.getStatus() == 4 || resStallEntity.getStatus() == 5) {
			ResStallOperateLog operateLog = this.stallOperateLogClient.findByStallId(resStallEntity.getId());
			if (operateLog != null) {
				resDetailStall.setFaultId(operateLog.getRemarkId());
				resDetailStall.setFaultName(operateLog.getRemark());
			}
			resDetailStall.setResetStatus(false);
			resDetailStall.setOnoffStatus(false);
		} else {
			resDetailStall.setOnoffStatus(true);
		}

		/*
		 * if(lockBean.getLockState() == 1) {
		 * resDetailStall.setStatus(lockBean.getLockState()); }else
		 * if(lockBean.getLockState() == 0) { resDetailStall.setStatus(2); }
		 */

		if (lockBean != null) {
			resDetailStall.setBetty(lockBean.getElectricity());
			resDetailStall.setStatus(lockBean.getLockState());
			if (lockBean.getElectricity() <= 30) {
				List<ResBaseDict> findList = this.dictClient.findList(CAUSE_DOWN);
				for (ResBaseDict resBaseDict : findList) {
					if ("battery-change".equals(resBaseDict.getCode())) {
						resDetailStall.setExcName(resBaseDict.getName());
						resDetailStall.setExcCode(resBaseDict.getId());
					}
				}
			}
		}
		resDetailStall.setStallId(resStallEntity.getId());
		return resDetailStall;
	}

	@Override
	@Transactional
	public Map<String, Object> operatStalls(HttpServletRequest request, Long stallId, Integer state) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser) this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key + key);
		if (!checkAuth(stallId, request)) {
			throw new BusinessException(StatusEnum.STAFF_STALL_EXISTS);
		}
		ResStallEntity resStallEntity = this.stallClient.findById(stallId);
		/*
		 * if(resStallEntity.getStatus() == 4) { throw new
		 * BusinessException(StatusEnum.STALL_OPERATE_OFFLINED); }
		 */
		// 需要异步记录操作锁
		Map<String, Object> result = new HashMap<>();
		if (StringUtil.isBlank(resStallEntity.getLockSn())) {
			result.put("result", false);
			result.put("result", "操作失败");
			return result;
		}

		redisService.set(STALL_LOCK_OPER_STATUS + stallId, ru.getId());
		new Thread(new Runnable() {

			@Override
			public void run() {
				ReqControlLock reqc = new ReqControlLock();
				reqc.setStallId(stallId);
				reqc.setStatus(state);
				reqc.setKey(STALL_LOCK_OPER_STATUS + stallId);
				if (state == 1) {
					redisService.set(RedisKey.ENT_STALL_DOING.key + stallId, ru.getId(),
							ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
				}
				// 1 降下 2 升起
				stallClient.operLockWY(reqc);
			}
		}).start();

		return result;
	}

	@Override
	@Transactional
	public Map<String, Object> change(HttpServletRequest request, Long stallId, int i, Long remarkId, String remark) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser) this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key + key);
		if (!checkAuth(stallId, request)) {
			throw new BusinessException(StatusEnum.STAFF_STALL_EXISTS);
		}
		ResStallEntity entity = stallClient.findById(stallId);
		int result;
		ResBaseDict baseDict = this.dictClient.find(remarkId);
		ReqStallOperateLog sol = new ReqStallOperateLog();
		sol.setCreateTime(new Date());
		sol.setOperation((short) i);
		sol.setOperatorId(ru.getId());
		sol.setSource((short) 1);
		sol.setStallId(stallId);
		sol.setRemarkId(remarkId);
		if (baseDict != null) {
			sol.setRemark(baseDict.getName());
		} else {
			sol.setRemark(remark);
		}
		sol.setStatus(1);
		// this.stallOperateLogClient.save(sol);
		// this.change(request, stallId, i);
		Map<String, Object> param = new HashMap<>();
		param.put("stallId", stallId);
		param.put("status", 1);
		this.stallExcStatusService.updateExcStatus(param);
		ResUserOrder order = this.orderClient.findStallLatest(stallId);
		if (i == 1) {
			if (entity.getStatus().intValue() != ResStallEntity.STATUS_OUTLINE) {
				throw new BusinessException(StatusEnum.STALL_OPERATE_UNOFFLINE);
			}
			if (entity.getType() == 2) {

			} else if (order != null && order.getStatus().intValue() == ResUserOrder.ORDERS_STATUS_UNPAY) {
				throw new BusinessException(StatusEnum.STALL_OPERATE_ORDERING);
			}
			List<Long> ids = new ArrayList<>();
			ids.add(stallId);
			try {
				result = this.stallClient.changedUp(ids);
			} catch (Exception e) {
				throw new BusinessException(StatusEnum.STALL_LOCK_OFFLINE);
			}
		} else if (i == 2) {
			if (entity.getStatus().intValue() > ResStallEntity.STATUS_USED) {
				throw new BusinessException(StatusEnum.STALL_OPERATE_OFFLINED);
			}
			if (entity.getType() == 2) {
				if (entity.getStatus() == 2) {
					throw new BusinessException(StatusEnum.STALL_AlREADY_CONTROL);
				}
			} else if (order != null && order.getStatus().intValue() == 2) {
				throw new BusinessException(StatusEnum.STALL_OPERATE_ORDERING);
			}
			if ((remarkId != null) && (remarkId.longValue() > 0L)) {
				ResBaseDict dict = this.dictClient.find(remarkId);
				if (dict == null) {
					throw new BusinessException(StatusEnum.DOWN_CAUSE_EXISTS);
				}
				if ("battery-change".equals(dict.getCode())) {
					ResStallBatteryLog sbl = new ResStallBatteryLog();
					sbl.setAdminId(ru.getId());
					sbl.setAdminName(ru.getMobile());
					sbl.setCreateTime(new Date());
					sbl.setVoltage(0d);
					sbl.setStallId(stallId);
					sol.setSource((short) 2);
					this.stallBatteryLogClient.save(sbl);
				}
			}
			this.stallOperateLogClient.save(sol);
			try {
				result = this.stallClient.changedDown(stallId);
			} catch (Exception e) {
				throw new BusinessException(StatusEnum.STALL_LOCK_OFFLINE);
			}
		}
		return new HashMap<>();
	}

	@Override
	public List<Long> findStaffId(Map<String, Long> map) {
		return this.entStaffAuthClusterMapper.findByStaffId(map);
	}

	@Override
	public void reset(Long stallId, HttpServletRequest request) {
		if (checkAuth(stallId, request)) {
			Map<String, Object> map = new HashMap<>();
			map.put("stallId", stallId);
			map.put("status", 1);
			this.stallExcStatusService.updateExcStatus(map);
		} else {
			throw new BusinessException(StatusEnum.STAFF_STALL_EXISTS);
		}

	}

	@Override
	public List<ResBaseDict> downCause() {
		return this.dictClient.findList(DOWN_CAUSE);
	}

	public boolean checkAuth(Long stallId, HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser) this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key + key);
		Map<String, Object> map = new HashMap<>();
		map.put("stallId", stallId);
		map.put("staffId", ru.getId());
		Integer flag = this.entAuthStallClusterMapper.checkAuth(map);
		if (flag > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void saveStallExcCause(List<ReqStallExcCause> causes) {
		List<String> list = new ArrayList<>();
		for (ReqStallExcCause reqStallExcCause : causes) {
			list.add(DOWN + reqStallExcCause.getReportSource());
		}
		List<ResBaseDict> dicts = this.dictClient.findList(CAUSE_DOWN);
		dicts = dicts.stream().filter(dict -> list.contains(dict.getCode())).collect(Collectors.toList());
		List<StallExcStatus> excs = new ArrayList<>();
		List<ResStall> stalls = this.stallClient.findStallList(new HashMap<String, Object>());
		StallExcStatus excStall = new StallExcStatus();
		for (ReqStallExcCause reqStallExcCause : causes) {
			excStall.setCreateTime(new Date());
			excStall.setStatus((short) 0);
			if (dicts != null && dicts.size() != 0) {
				for (ResBaseDict resBaseDict : dicts) {
					if ((DOWN + reqStallExcCause.getReportSource()).equals(resBaseDict.getCode())) {
						excStall.setExcRemark(resBaseDict.getName());
						excStall.setExcStatus(resBaseDict.getId());
					}
				}
				for (ResStall resStallOps : stalls) {
					if (reqStallExcCause.getStallLock().equals(resStallOps.getLockSn())) {
						excStall.setStallId(resStallOps.getId());
					}
				}
				if (excStall.getExcStatus() != null && excStall.getStallId() != null) {
					excs.add(excStall);
				}
			}
		}
		if (excs.size() != 0) {
			this.stallExcStatusService.saveBatch(excs);
		}
	}

	@Override
	public List<cn.linkmore.enterprise.controller.ent.response.ResStallBatteryLog> findLockChangeRecord(Long stallId) {
		List<ResStallBatteryLog> list = this.stallBatteryLogClient.findBatteryLogList(stallId);
		List<cn.linkmore.enterprise.controller.ent.response.ResStallBatteryLog> lists = new ArrayList<>();
		List<EntStaff> staffs = this.entStaffClusterMapper.findAll();
		for (ResStallBatteryLog resStallBatteryLog : list) {
			for (EntStaff entStaff : staffs) {
				if (resStallBatteryLog.getAdminId().equals(entStaff.getId())) {
					resStallBatteryLog.setAdminName(entStaff.getRealname());
				}
			}
			lists.add(ObjectUtils.copyObject(resStallBatteryLog,
					new cn.linkmore.enterprise.controller.ent.response.ResStallBatteryLog()));
		}
		return lists;
	}

	@Override
	public Integer downResult(HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser) this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key + key);
		ResUserOrder orders = this.feignOrderClient.last(ru.getId());
		Integer count = 0;
		Object o = this.redisService.get(RedisKey.ORDER_STALL_DOWN_FAILED.key + orders.getId());
		if (o != null) {
			count = new Integer(o.toString());
		}
		return count;
	}

}
