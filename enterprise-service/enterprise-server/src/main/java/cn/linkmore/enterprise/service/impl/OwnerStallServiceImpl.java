package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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
import cn.linkmore.enterprise.controller.app.request.ReqToothAuth;
import cn.linkmore.enterprise.controller.app.request.ReqWatchStatus;
import cn.linkmore.enterprise.controller.app.response.OwnerPre;
import cn.linkmore.enterprise.controller.app.response.OwnerRes;
import cn.linkmore.enterprise.controller.app.response.OwnerStall;
import cn.linkmore.enterprise.dao.cluster.EntRentedRecordClusterMapper;
import cn.linkmore.enterprise.dao.cluster.OwnerStallClusterMapper;
import cn.linkmore.enterprise.dao.master.EntRentedRecordMasterMapper;
import cn.linkmore.enterprise.entity.EntOwnerPre;
import cn.linkmore.enterprise.entity.EntOwnerStall;
import cn.linkmore.enterprise.entity.EntRentedRecord;
import cn.linkmore.enterprise.service.OwnerStallService;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.request.ReqControlLock;
import cn.linkmore.redis.RedisLock;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.MapUtil;
import cn.linkmore.util.StringUtil;
import cn.linkmore.util.TokenUtil;

@Service
public class OwnerStallServiceImpl implements OwnerStallService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RedisService redisService;
	
	@Autowired
	private RedisLock redisLock;

	@Autowired
	private OwnerStallClusterMapper ownerStallClusterMapper;

	@Autowired
	private EntRentedRecordMasterMapper entRentedRecordMasterMapper;

	@Autowired
	private EntRentedRecordClusterMapper entRentedRecordClusterMapper;

	@Autowired
	private StallClient stallClient;

	@Override
	public OwnerRes findStall(HttpServletRequest request, ReqLocation location) {
		System.out.println("findStall");
		CacheUser user = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
		if (user == null) {
			throw new BusinessException(StatusEnum.USER_APP_NO_LOGIN);
		}
		OwnerRes res = new OwnerRes();
		Boolean isHave = false;
		int num = 0;

		try {
			Long userId = user.getId();

			// 查询是否有未完成进程
			EntRentedRecord record = entRentedRecordClusterMapper.findByUser(userId);

			//List<EntOwnerPre> prelist = ownerStallClusterMapper.findPre(userId);
			List<EntOwnerPre> prelist=null;
			List<EntOwnerStall> stalllist = ownerStallClusterMapper.findStall(userId);
			
			Set<Long> ids=new HashSet<Long>();
			if(CollectionUtils.isNotEmpty(stalllist)&& stalllist.size()>0) {
				for(EntOwnerStall entOwnerStall:stalllist) {
					ids.add(entOwnerStall.getPreId());
				}
				prelist = ownerStallClusterMapper.findPreByIds(ids);
			}

			log.info("车位>>>" + stalllist.size() + "车区>>>" + prelist.size() + "用户>>>" + JSON.toJSONString(user));
			System.out.println("车位>>>" + stalllist.size() + "车区>>>" + prelist.size() + "用户>>>" + JSON.toJSONString(user));

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
								OwnerStall.setStallId(enttall.getStallId());
								OwnerStall.setMobile(enttall.getMobile());
								OwnerStall.setPlate(enttall.getPlate());
								OwnerStall.setStallName(enttall.getStallName());
								OwnerStall.setStartTime(handleTime(enttall.getStartTime()));
								OwnerStall.setEndTime(handleTime(enttall.getEndTime()));
								OwnerStall.setImageUrl(enttall.getImageUrl());
								OwnerStall.setRouteGuidance(enttall.getRouteGuidance());
								OwnerStall.setStallLocal(enttall.getStallLocal());
								OwnerStall.setLockSn(enttall.getLockSn());
								OwnerStall.setLockStatus(enttall.getLockStatus());
								OwnerStall.setStatus(enttall.getStatus() == 1l ? 1 : 2l);
								ownerstalllist.add(OwnerStall);
								num++;
								isHave = true;
								break;
							}
						}
						ownerpre.setStalls(ownerstalllist);
						list.add(ownerpre);
						break;
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
							OwnerStall.setStallId(enttall.getStallId());
							OwnerStall.setMobile(enttall.getMobile());
							OwnerStall.setPlate(enttall.getPlate());
							OwnerStall.setStallName(enttall.getStallName());
							OwnerStall.setStartTime(handleTime(enttall.getStartTime()));
							OwnerStall.setEndTime(handleTime(enttall.getEndTime()));
							OwnerStall.setImageUrl(enttall.getImageUrl());
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
	public void control(ReqConStall reqOperatStall, HttpServletRequest request) {
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
		stallClient.controllock(reqc);
		log.info("用户>>>" + user.getId() + "调用>>>" + reqOperatStall.getStallId());
	}

	@Override
	public void watch(ReqWatchStatus reqWatchStatus, HttpServletRequest request) {
		CacheUser user = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
		if (user == null) {
			throw new BusinessException(StatusEnum.USER_APP_NO_LOGIN);
		}
		String rediskey = RedisKey.ACTION_STALL_DOING.key + reqWatchStatus.getStallId();
		String val = String.valueOf(this.redisService.get(rediskey));
		Map<String, Object> map = new HashMap<>();
		map = stallClient.watch(reqWatchStatus.getStallId());
		Boolean control = true;
		Boolean blue = true;
		if (map != null && !map.isEmpty()) {
			if ("200".equals(String.valueOf(map.get("code")))
					&& String.valueOf(map.get("status")).equals(String.valueOf(reqWatchStatus.getStatus() - 1))) {
				blue = true;
			} else {
				blue = false;
			}
		}
		if (StringUtil.isNotBlank(val)) {
			if (val.equals(String.valueOf(user.getId()))) {
				control = false;
			}
		}
		log.info("用户>>>" + user.getId() + ">>>" + rediskey);
		if (!control && !blue) {
			throw new BusinessException(
					reqWatchStatus.getStatus() == 2 ? StatusEnum.ORDER_LOCKUP_FAIL : StatusEnum.ORDER_LOCKDOWN_FAIL);
		}
	}

	public static String handleTime(String time) {
		time = time.replace(" ", "");
		time = time.replace("-", "");
		time = time.replace(":", "");
		String newtime = null;
		if (time != null) {
			newtime = time.substring(0, 4) + "年" + time.substring(4, 6) + "月" + time.substring(6, 8) + "日"
					+ time.substring(8, 10) + "时" + time.substring(10, 12) + "分";
		}
		return newtime;
	}

	@Override
	public Boolean owner(HttpServletRequest request) {
		CacheUser user = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
		Boolean is = false;
		if (user != null) {
			List<EntOwnerStall> stalllist = ownerStallClusterMapper.findStall(user.getId());
			if (stalllist.size() > 0) {
				is = true;
			}
		}
		log.info("用户>>>" + JSON.toJSONString(user));
		return is;
	}

	@Override
	public void tooth(ReqToothAuth reqToothAuth) {
		String userid = String.valueOf(reqToothAuth.getUserId());
		String robkey = RedisKey.ROB_STALL_ISHAVE.key + reqToothAuth.getStallId();
		Boolean have = false;
			have = this.redisLock.getLock(robkey, userid);
			log.info("用户=======>" + reqToothAuth.getUserId() + (have == true ? "已得到" : "未得到") + "锁" + robkey);
			if (!have) {
				throw new BusinessException(StatusEnum.STALL_HIVING_DO);
			}
			if(have) {
				Map<String, Object> pam = new HashMap<>();
				pam.put("stallId", reqToothAuth.getStallId());
				pam.put("userId", userid);
				Integer using = entRentedRecordClusterMapper.findUsingRecord(pam);
				log.info("用户=======>" + using);
				if (using>0) {
					this.redisService.remove(robkey);
					throw new BusinessException(StatusEnum.STALL_AlREADY_CONTROL);
				}
			}
	}

}
