package cn.linkmore.enterprise.service.impl;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.enterprise.controller.staff.request.SraffReqConStall;
import cn.linkmore.enterprise.service.StaffPrefectureService;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.request.ReqControlLock;
import cn.linkmore.redis.RedisLock;
import cn.linkmore.redis.RedisService;
import cn.linkmore.task.TaskPool;

@Service
public class StaffPrefectureServiceImpl implements StaffPrefectureService{

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RedisLock redisLock;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private StallClient stallClient;

	private static final int TIMEOUT= 30*1000;
	
	@Override
	public void control(SraffReqConStall reqOperatStall, HttpServletRequest request) {
		String userid = null;
		long time = System.currentTimeMillis()+TIMEOUT;
		String robkey = RedisKey.MANAGER_STALL.key + reqOperatStall.getStallId();
		if(!redisLock.lock( String.valueOf(robkey),String.valueOf(time))) {
			log.info("no get it,wait a moment");
			throw new BusinessException(StatusEnum.STALL_HIVING_DO);
		}
		String reskey = (reqOperatStall.getState()==1?RedisKey.MANAGER_STALL_DOWN.key:RedisKey.MANAGER_STALL_UP.key);
		StringBuilder sb = new StringBuilder(reskey).append(userid);
		redisService.set(String.valueOf(sb),reqOperatStall.getStallId());
		ReqControlLock reqc = new ReqControlLock();
		reqc.setStallId(reqOperatStall.getStallId());
		reqc.setStatus(reqOperatStall.getState());
		reqc.setKey(reskey);
		
		TaskPool.getInstance().task(
				new Runnable() {
					@Override
					public void run() {
						stallClient.controllock(reqc);
					}
			});
	}
	
	
	
	
}
