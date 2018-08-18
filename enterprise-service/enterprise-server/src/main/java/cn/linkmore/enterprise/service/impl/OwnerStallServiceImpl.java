package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.List;
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
			//鉴权
			String key = TokenUtil.getKey(request);
			CacheUser user = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
			if(user!= null) {
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
						OwnerStall.setStartTime(enttall.getStartTime());
						OwnerStall.setEndTime(enttall.getEndTime());
						// 插入锁状态
						try {
							ResStallEntity  ress = stallClient.findById(enttall.getStallId());
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

	@Override
	public Boolean control(ReqOperatStall reqOperatStall, HttpServletRequest request) {
		//鉴权
		String key = TokenUtil.getKey(request);
		CacheUser user = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		if(user != null) {
			throw new RuntimeException(StatusEnum.USER_APP_NO_LOGIN.label);
		}
		//放入缓存
		/*String rediskey = (reqOperatStall.getState()==1?RedisKey.ACTION_STALL_DOWN_FAILED.key:RedisKey.ACTION_STALL_UP_FAILED.key)
				+user.getMobile()+reqOperatStall.getStallId();*/
		String rediskey = "19310151716";
		this.redisService.set(rediskey,1,ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
		//调用
		ReqControlLock reqc = new ReqControlLock();
		reqc.setKey(rediskey);
		reqc.setStallId(reqOperatStall.getStallId());
		reqc.setStatus(reqOperatStall.getState());
		 stallClient.controllock(reqc);
		 return true;
	}

}
