package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkmore.lock.bean.LockBean;
import com.linkmore.lock.factory.LockFactory;
import com.linkmore.lock.response.ResponseMessage;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.enterprise.controller.app.response.OwnerPre;
import cn.linkmore.enterprise.controller.app.response.OwnerStall;
import cn.linkmore.enterprise.dao.cluster.OwnerStallClusterMapper;
import cn.linkmore.enterprise.entity.EntOwnerPre;
import cn.linkmore.enterprise.entity.EntOwnerStall;
import cn.linkmore.enterprise.service.OwnerStallService;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.TokenUtil;

@Service
public class OwnerStallServiceImpl implements OwnerStallService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RedisService redisService;
	
	
	@Autowired
	private LockFactory lockFactory;

	@Autowired
	private OwnerStallClusterMapper ownerStallClusterMapper;

	@Override
	public List<OwnerPre> findStall(HttpServletRequest request) {
		try {
			// 获取用户信息
			CacheUser cu = (CacheUser) this.redisService
					.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
			Long userId = 2778L;

			log.info("用户id>>>" + userId);

			List<EntOwnerPre> prelist = ownerStallClusterMapper.findPre(userId);
			log.info("车区>>>" + prelist.size());
			
			List<EntOwnerStall> stalllist = ownerStallClusterMapper.findStall(userId);
			log.info("车位>>>" + stalllist.size());
			
			//查询锁状态
			List<String> locks = new ArrayList<>();
			for (EntOwnerPre pre : prelist) {
				locks.add(String.valueOf(pre.getPreId()));
			}
			ResponseMessage<LockBean> res =	 lockFactory.findAvaiLocks(locks);
			List<LockBean> locklist =res.getDataList();
				for (LockBean lockBean : locklist) {
					lockBean.getLockState();     //升降
					lockBean.getOnlineState();  //在线 离线
					lockBean.getParkingState(); //有车无车
				}
			
			List<OwnerPre> list = new ArrayList<>();
			
			for (EntOwnerPre pre : prelist) {
				OwnerPre ownerpre = new OwnerPre();
				ownerpre.setPreName(pre.getPreName());
				ownerpre.setAddress(pre.getAddress());
				ownerpre.setLatitude(pre.getLatitude());
				ownerpre.setLongitude(pre.getLongitude());
				
				List<OwnerStall> ownerstalllist = new ArrayList<>();
	
				for (EntOwnerStall enttall : stalllist) {
					if(pre.getPreId().equals(  enttall.getPreId()  )) {
						OwnerStall OwnerStall = new OwnerStall();
						OwnerStall.setStallId(enttall.getStallId());
						OwnerStall.setMobile(enttall.getMobile());
						OwnerStall.setPlate(enttall.getPlate());
						OwnerStall.setStallName(enttall.getStallName());
						OwnerStall.setStartTime(enttall.getStartTime());
						OwnerStall.setEndTime(enttall.getEndTime());
						ownerstalllist.add(OwnerStall);
					}
				}
				
				ownerpre.setStalls(ownerstalllist);
				list.add(ownerpre);
			}
			
			return  list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
