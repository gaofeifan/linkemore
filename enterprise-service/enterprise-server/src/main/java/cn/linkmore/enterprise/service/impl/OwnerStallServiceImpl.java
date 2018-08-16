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
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.JsonUtil;
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

	public static void main(String[] args) {
		LockFactory lockFactory = LockFactory.getInstance();
		ResponseMessage<LockBean> res = lockFactory.getLockInfo("FFAEE5D0E27E");
		System.out.println(JSON.toJSONString(res));
	}

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
							ResponseMessage<LockBean> res = lockFactory.getLockInfo("FF180A6A6E80");
							OwnerStall.setLockStatus(res.getData().getLockState());// 升降
							OwnerStall.setStatus(res.getData().getParkingState()); // 有车无车
							res.getData().getParkingState(); // 在线 离线
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
	public void control(ReqOperatStall reqOperatStall, HttpServletRequest request) {
		//鉴权
		String key = TokenUtil.getKey(request);
		CacheUser user = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
		if(user == null) {
			throw new RuntimeException(StatusEnum.USER_APP_NO_LOGIN.label);
		}
		
		//操作锁
		this.redisService.set(RedisKey.ORDER_STALL_DOWN_FAILED.key + "订单号 ", 1,
				ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
		
		new Thread(new Runnable() {
	        @Override
	        public void run() {
	        	ResponseMessage<LockBean> res = null;
	        	//1 降下 2 升起
	        	log.info("downing... name:{},sn:{}","  锁名"," 编码 "); 
				if(reqOperatStall.getState().equals("1")){
				 res = 	lockFactory.lockDown(reqOperatStall.getStallId().toString());
				}else if(reqOperatStall.getState().equals("2")){
			     res = lockFactory.lockUp(reqOperatStall.getStallId().toString());
				}
				int code = res.getMsgCode();
				log.info("lock msg:{}", JsonUtil.toJson(res));
				if (code == 200) {  
					redisService.add(RedisKey.PREFECTURE_FREE_STALL.key, " 锁编码 "); 
				}
	        }
	    }).start();
	}

}
