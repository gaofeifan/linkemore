package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.linkmore.bean.common.Constants.ExpiredTime;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.enterprise.controller.app.request.ReqConStall;
import cn.linkmore.enterprise.controller.app.request.ReqLocation;
import cn.linkmore.enterprise.controller.app.request.ReqUserRentStall;
import cn.linkmore.enterprise.controller.app.response.OwnerPre;
import cn.linkmore.enterprise.controller.app.response.OwnerRes;
import cn.linkmore.enterprise.controller.app.response.OwnerStall;
import cn.linkmore.enterprise.controller.app.response.ResCurrentOwner;
import cn.linkmore.enterprise.dao.cluster.EntRentedRecordClusterMapper;
import cn.linkmore.enterprise.dao.cluster.OwnerStallClusterMapper;
import cn.linkmore.enterprise.dao.master.EntRentedRecordMasterMapper;
import cn.linkmore.enterprise.entity.EntOwnerPre;
import cn.linkmore.enterprise.entity.EntOwnerStall;
import cn.linkmore.enterprise.entity.EntRentedRecord;
import cn.linkmore.enterprise.service.OwnerStallService;
import cn.linkmore.enterprise.service.PrefectureService;
import cn.linkmore.enterprise.service.UserRentStallService;
import cn.linkmore.prefecture.client.FeignLockClient;
import cn.linkmore.prefecture.client.OpsPrefectureClient;
import cn.linkmore.prefecture.client.PrefectrueClient;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.request.ReqControlLock;
import cn.linkmore.prefecture.response.ResLockInfo;
import cn.linkmore.prefecture.response.ResLockInfos;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.redis.RedisLock;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.DateUtils;
import cn.linkmore.util.MapUtil;
import cn.linkmore.util.TokenUtil;
@Service
public class UserRentStallServiceImpl implements UserRentStallService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RedisService redisService;
	@Autowired
	private OwnerStallService ownerStallService;
	@Autowired
	private PrefectureClient prefectrueClient;
	@Autowired
	private RedisLock redisLock;
	@Autowired
	private OwnerStallClusterMapper ownerStallClusterMapper;
	@Autowired
	private EntRentedRecordMasterMapper entRentedRecordMasterMapper;
	@Autowired
	private EntRentedRecordClusterMapper entRentedRecordClusterMapper;
	@Autowired
	private FeignLockClient feignLockClient;
	@Autowired
	private StallClient stallClient;

	
	
	@Override
	public OwnerRes findStall(HttpServletRequest request, ReqLocation location) {
		CacheUser user = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
		OwnerRes res = new OwnerRes();
		Boolean isHave = false;
		int num = 0;
		try {
			Long userId = user.getId();
			// 查询是否有未完成进程
			EntRentedRecord record = entRentedRecordClusterMapper.findByUser(userId);
			List<EntOwnerPre> prelist = ownerStallClusterMapper.findPre(userId);
			List<EntOwnerStall> stalllist = ownerStallClusterMapper.findStall(userId);
			List<Long> collect = prelist.stream().map(pre -> pre.getPreId()).collect(Collectors.toList());
			if(prelist == null || prelist.size() == 0 || stalllist == null || stalllist.size() == 0) {
				return res;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("preIds", collect);
			List<ResPre> preList = this.prefectrueClient.findPreByIds(map );
			List<String> gateways = preList.stream().map(pre -> pre.getGateway()).collect(Collectors.toList());
			List<ResLockInfos> lockInfos = this.feignLockClient.lockLists(gateways);
			Map<Long,List<ResLockInfo>> tempMap = new HashMap<>();
			for (ResLockInfos info : lockInfos) {
				for (ResPre resPre : preList) {
					if(resPre.getGateway().equals(info.getGroupId())) {
						tempMap.put(resPre.getId(), info.getInfos());
						break;
					}
				}

			}
			log.info("车位>>>" + stalllist.size() + "车区>>>" + prelist.size() + "用户>>>" + JSON.toJSONString(user));
			List<OwnerPre> list = new ArrayList<>();
			if (record != null) { // 未完成进程
				for (EntOwnerPre pre : prelist) {
					if (pre.getPreId().equals(record.getPreId())) {
						OwnerPre ownerpre = new OwnerPre();
						ownerpre.setPreId(pre.getPreId());
						ownerpre.setPreName(pre.getPreName());
						ownerpre.setAddress(pre.getAddress());
						ownerpre.setLatitude(pre.getLatitude());
						ownerpre.setLongitude(pre.getLongitude());
						List<OwnerStall> ownerstalllist = new ArrayList<>();
						for (EntOwnerStall enttall : stalllist) {
							if (enttall.getStallId().equals(record.getStallId())) {
								OwnerStall OwnerStall = new OwnerStall();
								if(tempMap != null && !tempMap.isEmpty()) {
									for (Entry<Long, List<ResLockInfo>> info : tempMap.entrySet()) {
										if(info.getKey() == pre.getPreId()) {
											for (ResLockInfo inf : info.getValue()) {
												if(inf.getLockCode().equals(enttall.getLockSn())) {
													OwnerStall.setBattery(inf.getElectricity());
													OwnerStall.setGatewayStatus(inf.getOnlineState());
												}
											}
										}
									}
								}
								OwnerStall.setStallId(enttall.getStallId());
								OwnerStall.setMobile(enttall.getMobile());
								OwnerStall.setPlate(enttall.getPlate());
								OwnerStall.setStallName(enttall.getStallName());
								OwnerStall.setImageUrl(enttall.getImageUrl());
								OwnerStall.setRouteGuidance(enttall.getRouteGuidance());
								OwnerStall.setStallLocal(enttall.getStallLocal());
								OwnerStall.setLockSn(enttall.getLockSn());
								OwnerStall.setStallEndTime(DateUtils.convert(enttall.getStartTime(), null));
								OwnerStall.setLockStatus(enttall.getLockStatus());
								OwnerStall.setStatus(enttall.getStatus() == 1l ? 1 : 2l);
								ownerstalllist.add(OwnerStall);
								num++;
								isHave = true;
							}
						}
						ownerpre.setStalls(ownerstalllist);
						list.add(ownerpre);
					}
				}
			} else {
				for (EntOwnerPre pre : prelist) {
					OwnerPre ownerpre = new OwnerPre();
					ownerpre.setPreId(pre.getPreId());
					ownerpre.setPreName(pre.getPreName());
					ownerpre.setAddress(pre.getAddress());
					ownerpre.setLatitude(pre.getLatitude());
					ownerpre.setLongitude(pre.getLongitude());
					ownerpre.setDistance(MapUtil.getDistance(location.getLatitude(), location.getLongitude(),
							new Double(pre.getLatitude()), new Double(pre.getLongitude())));
					List<OwnerStall> ownerstalllist = new ArrayList<>();
					for (EntOwnerStall enttall : stalllist) {
						if (pre.getPreId().equals(enttall.getPreId())) {
							OwnerStall OwnerStall = new OwnerStall();
							if(tempMap != null && !tempMap.isEmpty()) {
								for (Entry<Long, List<ResLockInfo>> info : tempMap.entrySet()) {
									if(info.getKey() == pre.getPreId()) {
										for (ResLockInfo inf : info.getValue()) {
											if(inf.getLockCode().equals(enttall.getLockSn())) {
												OwnerStall.setBattery(inf.getElectricity());
												OwnerStall.setGatewayStatus(inf.getOnlineState());
											}
										}
									}
								}
							}
							OwnerStall.setStallId(enttall.getStallId());
							OwnerStall.setMobile(enttall.getMobile());
							OwnerStall.setPlate(enttall.getPlate());
							OwnerStall.setStallName(enttall.getStallName());
//							OwnerStall.setStartTime(handleTime(enttall.getStartTime()));
//							OwnerStall.setEndTime(handleTime(enttall.getEndTime()));
							OwnerStall.setImageUrl(enttall.getImageUrl());
							OwnerStall.setStallEndTime(DateUtils.convert(enttall.getStartTime(), null));
							OwnerStall.setRouteGuidance(enttall.getRouteGuidance());
							OwnerStall.setStallLocal(enttall.getStallLocal());
							OwnerStall.setLockSn(enttall.getLockSn());
							OwnerStall.setLockStatus(enttall.getLockStatus());
							OwnerStall.setStatus(enttall.getStatus() == 1l ? 1 : 2l);
							num++;
							ownerstalllist.add(OwnerStall);
						}
					}
					ownerpre.setStalls(ownerstalllist);
					list.add(ownerpre);
				}
				// 排序
				Collections.sort(list, new Comparator<OwnerPre>() {
					public int compare(OwnerPre pre1, OwnerPre pre2) {
						return Double.valueOf(pre1.getDistance()).compareTo(Double.valueOf(pre2.getDistance()));
					}
				});
			}
			res.setRes(list);
			res.setIsHave(isHave);
			res.setNum(num);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(StatusEnum.SERVER_EXCEPTION);
		}
	}

	@Override
	public Boolean control(ReqUserRentStall reqOperatStall, HttpServletRequest request) {
		CacheUser user = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
		if (user == null) {
			throw new BusinessException(StatusEnum.USER_APP_NO_LOGIN);
		}
		Boolean isAllow = false;
		List<EntOwnerStall> stalllist = ownerStallClusterMapper.findStall(user.getId());
		EntRentedRecord newrecord = new EntRentedRecord();
		for (EntOwnerStall entOwnerStall : stalllist) {
			if (reqOperatStall.getStallId().equals(entOwnerStall.getStallId())) {
				newrecord.setStallId(entOwnerStall.getStallId());
				newrecord.setUserId(user.getId());
				newrecord.setStatus(0L);
				newrecord.setDownTime(new Date());
				newrecord.setPreId(entOwnerStall.getPreId());
				newrecord.setStallName(entOwnerStall.getStallName());
				newrecord.setEntId(entOwnerStall.getEntId());
				newrecord.setPreName(entOwnerStall.getPreName());
				newrecord.setEntPreId(entOwnerStall.getEntPreId());
				newrecord.setPlateNo(entOwnerStall.getPlate());
				isAllow = true;
				break;
			}
		}
		if (!isAllow) {
			log.info(user.getId() + ">>>STAFF_STALL_EXISTS");
			throw new BusinessException(StatusEnum.STAFF_STALL_EXISTS);
		}

		// 争抢
		String robkey = RedisKey.ROB_STALL_ISHAVE.key + reqOperatStall.getStallId();
		Integer using = 0;
		Boolean have = true;
		try {
			have = this.redisLock.getLock(robkey, user.getId());
			log.info("用户=======>" + user.getId() + (have == true ? "已抢到" : "未抢到") + "锁" + robkey);
		} catch (Exception e) {
			
		}
		if (!have) {
			throw new BusinessException(StatusEnum.STALL_HIVING_DO);
		}
		Map<String, Object> pam = new HashMap<>();
		pam.put("stallId", reqOperatStall.getStallId());
		pam.put("userId", user.getId());
		using = entRentedRecordClusterMapper.findUsingRecord(pam);
		if (using>0) {
			this.redisService.remove(robkey);
			throw new BusinessException(StatusEnum.STALL_AlREADY_CONTROL);
		}
	
		// 未完成记录同一用户只有一单
		EntRentedRecord record = entRentedRecordClusterMapper.findByUser(user.getId());

		if (reqOperatStall.getState() == 2) {
			log.info("用户>>>" + user.getId() + "升锁>>>" + reqOperatStall.getStallId());
		} else if (reqOperatStall.getState() == 1) {
			log.info("用户>>>" + user.getId() + "降锁>>>" + reqOperatStall.getStallId());
			if (!Objects.nonNull(record)) {
				try {
					entRentedRecordMasterMapper.saveSelective(newrecord);
				} catch (Exception e) {
					e.printStackTrace();
				}
				log.info("用户>>>" + user.getId() + "record>>>" + reqOperatStall.getStallId());
			}
		}
		// 放入缓存
		String rediskey = RedisKey.ACTION_STALL_DOING.key + reqOperatStall.getStallId();
		this.redisService.set(rediskey, user.getId(), ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
		log.info("用户>>>" + user.getId() + "缓存>>>" + rediskey);
		// 调用
		ReqControlLock reqc = new ReqControlLock();
		reqc.setKey(rediskey);
		reqc.setStallId(reqOperatStall.getStallId());
		reqc.setStatus(reqOperatStall.getState());
		reqc.setUserId(user.getId());
		Boolean control = stallClient.appControl(reqc);
		if(control == null || !control) {
			if(this.redisService.exists(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId())) {
				Object object = this.redisService.get(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId());
				this.redisService.remove(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId());
				if(control == null) {
					control = false;
				}
				throw new BusinessException(StatusEnum.get((int)object));
				
			}else {
				control = true;
			}
		}
		log.info("用户>>>" + user.getId() + "调用>>>" + reqOperatStall.getStallId());
		return control;
	}



	@Override
	public Boolean owner(HttpServletRequest request) {
		Boolean owner = this.ownerStallService.owner(request);
		return owner;
	}



	@Override
	public ResCurrentOwner current(HttpServletRequest request) {
		CacheUser user = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
		EntRentedRecord record = this.entRentedRecordClusterMapper.findByUser(user.getId());
		ResCurrentOwner owner = new ResCurrentOwner();
		if(record == null) {
			owner.setStatus(false);
		}else {
			List<EntOwnerStall> stalllist = ownerStallClusterMapper.findStall(user.getId());
			owner.setStatus(true);
			owner.setStallNumber(stalllist.size());
			owner.setPreId(record.getPreId());
			owner.setPreName(record.getPreName());
			owner.setStallId(record.getStallId());
			owner.setStallName(record.getStallName());
		}
		return owner;
	}
	
	

	
}
