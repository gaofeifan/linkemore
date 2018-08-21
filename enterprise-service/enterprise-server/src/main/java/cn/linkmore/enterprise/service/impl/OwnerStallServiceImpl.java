package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.linkmore.lock.bean.LockBean;
import com.linkmore.lock.factory.LockFactory;
import com.linkmore.lock.response.ResponseMessage;

import cn.linkmore.bean.common.Constants.ExpiredTime;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.enterprise.controller.app.response.OwnerPre;
import cn.linkmore.enterprise.controller.app.response.OwnerStall;
import cn.linkmore.enterprise.controller.ent.request.ReqOperatStall;
import cn.linkmore.enterprise.dao.cluster.OwnerStallClusterMapper;
import cn.linkmore.enterprise.entity.EntOwnerPre;
import cn.linkmore.enterprise.entity.EntOwnerStall;
import cn.linkmore.enterprise.service.OwnerStallService;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.request.ReqControlLock;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.TokenUtil;

@Service
public class OwnerStallServiceImpl implements OwnerStallService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RedisService redisService;

	@Autowired
	private OwnerStallClusterMapper ownerStallClusterMapper;

	@Autowired
	private StallClient stallClient;

	@Override
	public List<OwnerPre> findStall(HttpServletRequest request) {
		try {
			// 鉴权
			String key = TokenUtil.getKey(request);
			CacheUser user = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + key);
			if (user != null) {
				throw new RuntimeException(StatusEnum.USER_APP_NO_LOGIN.label);
			}
			Long userId = 2743L;

			log.info("用户id>>>" + userId);

			List<EntOwnerPre> prelist = ownerStallClusterMapper.findPre(userId);
			log.info("车区>>>" + prelist.size());

			List<EntOwnerStall> stalllist = ownerStallClusterMapper.findStall(userId);
			log.info("车位>>>" + stalllist.size());

			List<OwnerPre> list = new ArrayList<>();

			for (EntOwnerPre pre : prelist) {
				OwnerPre ownerpre = new OwnerPre();
				ownerpre.setPreId(pre.getPreId());
				ownerpre.setPreName(pre.getPreName());
				ownerpre.setAddress(pre.getAddress());
				ownerpre.setLatitude(pre.getLatitude());
				ownerpre.setLongitude(pre.getLongitude());
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
						// 插入锁状态
						try {
							ResStallEntity ress = stallClient.findById(enttall.getStallId());
							OwnerStall.setStatus(ress.getStatus());
							OwnerStall.setLockStatus(ress.getLockStatus());
						} catch (Exception e) {
							e.printStackTrace();
						}
						ownerstalllist.add(OwnerStall);
					}
				}
				ownerpre.setStalls(ownerstalllist);
				list.add(ownerpre);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String handleTime(String time) {
		time = time.replace(" ", "");
		time = time.replace("-", "");
		time = time.replace(":", "");
		String newtime = null;
		if(time!=null ) {
			newtime = time.substring(0,4)+"年"+ time.substring(4,6)+"月"+ time.substring(6,8)+"日"+ time.substring(8,10)+"时" +  time.substring(10,12)+"分";
		}
		return newtime;
	}
	
	public static void main(String[] args) {
		System.out.println( handleTime("2018-08-21 10:24:51"));
	}

	@Override
	public Integer control(ReqOperatStall reqOperatStall, HttpServletRequest request) {
		Map<String, Object> value = new HashMap<>();
		Integer res = 0;
		// 鉴权
		String key = TokenUtil.getKey(request);
		CacheUser user = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + key);
		if (user != null) {
			return -1;
		}
		String rediskey = RedisKey.ACTION_STALL_DOING.key + reqOperatStall.getStallId();
		value = (Map<String, Object>) this.redisService.get(rediskey);
		if (!value.isEmpty() && value.get("userId").equals(user.getId())) {
			return -2;
		};
		value.clear();
		value.put("userId", user.getId());
		// 放入缓存
		this.redisService.set(rediskey, value, ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
		// 调用
		ReqControlLock reqc = new ReqControlLock();
		reqc.setKey(rediskey);
		reqc.setStallId(reqOperatStall.getStallId());
		reqc.setStatus(reqOperatStall.getState());
		stallClient.controllock(reqc);
		return res;
	}

	@Override
	public Integer watch(Long stallId, HttpServletRequest request) {
		CacheUser user = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key +  TokenUtil.getKey(request));
		String rediskey = RedisKey.ACTION_STALL_DOING.key + stallId;
		if (user != null) {
			return -1;
		}
		Map<String, Object> value = (Map<String, Object>) this.redisService.get(rediskey);
		if(!value.isEmpty()) {
			return 1;
		}
		return 1;
	}

}
