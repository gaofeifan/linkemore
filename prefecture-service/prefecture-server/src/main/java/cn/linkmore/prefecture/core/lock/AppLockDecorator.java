package cn.linkmore.prefecture.core.lock;

import java.util.Date;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.linkmore.bean.common.Constants.ExpiredTime;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.prefecture.config.StartupRunner;
import cn.linkmore.prefecture.dao.cluster.EntRentRecordClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StallClusterMapper;
import cn.linkmore.prefecture.dao.master.EntRentRecordMasterMapper;
import cn.linkmore.prefecture.dao.master.StallMasterMapper;
import cn.linkmore.prefecture.entity.EntRentRecord;
import cn.linkmore.prefecture.entity.Stall;
import cn.linkmore.prefecture.request.ReqControlLock;
import cn.linkmore.prefecture.response.ResLockMessage;
import cn.linkmore.util.JsonUtil;

public class AppLockDecorator extends LockDecorator {

	private ReqControlLock reqc;
	private StallClusterMapper stallClusterMapper = StartupRunner.get().getBean(StallClusterMapper.class);
	private EntRentRecordMasterMapper entRentedRecordMasterMapper = StartupRunner.get().getBean(EntRentRecordMasterMapper.class); 
	private EntRentRecordClusterMapper entRentedRecordClusterMapper = StartupRunner.get().getBean(EntRentRecordClusterMapper.class); 
	private StallMasterMapper stallMasterMapper = StartupRunner.get().getBean(StallMasterMapper.class);
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	public AppLockDecorator(LockService lockService, ReqControlLock reqc) {
		super(lockService);
		this.reqc = reqc;
	}


	@Override
	public boolean upLock(String sn) {
		return operate();
	}

	@Override
	public Boolean downLock(String sn) {
		return operate();
	}


	private boolean operate() {
		ResLockMessage res =  null;
		Stall stall = stallClusterMapper.findById(reqc.getStallId());
		if (reqc.getStatus() == 1) {
			res = super.downLockMes(stall.getLockSn());
		} else if (reqc.getStatus() == 2) {
			res = super.upLockMes(stall.getLockSn());
		}
		log.info("降锁返回结果"+JsonUtil.toJson(res));
		int code = res.getCode();
		EntRentRecord record = entRentedRecordClusterMapper.findByUser(reqc.getUserId());
		boolean falg = false;
		if (code == 200) {
			if(redisService.exists(RedisKey.OWNER_CONTROL_LOCK.key + reqc.getStallId() + reqc.getUserId() +reqc.getStatus())) {
				this.redisService.remove(RedisKey.OWNER_CONTROL_LOCK.key + reqc.getStallId() + reqc.getUserId() +reqc.getStatus());
			}
			if(this.redisService.exists(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId())) {
				this.redisService.remove(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId());
			}
			falg = true;
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
			if(code == 500) {
				if(redisService.exists(RedisKey.OWNER_CONTROL_LOCK.key + reqc.getStallId() + reqc.getUserId() +reqc.getStatus())) {
					if(reqc.getStatus() == 1) {
						this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId(), StatusEnum.DOWN_LOCK_FAIL_CHANGE_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
					}else if(reqc.getStatus() == 2){
						this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId(), StatusEnum.UP_LOCK_FAIL_CHANGE_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
					}
					redisService.remove(RedisKey.OWNER_CONTROL_LOCK.key + reqc.getStallId() + reqc.getUserId() +reqc.getStatus());
				}else {
					redisService.set(RedisKey.OWNER_CONTROL_LOCK.key + reqc.getStallId() + reqc.getUserId() +reqc.getStatus(),"",ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
					if(reqc.getStatus() == 1) {
//						throw new BusinessException(StatusEnum.DOWN_LOCK_FAIL_RETRY_OWNER);
						this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId(), StatusEnum.DOWN_LOCK_FAIL_RETRY_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
					}else if(reqc.getStatus() == 2){
						this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId(), StatusEnum.UP_LOCK_FAIL_RETRY_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
//						throw new BusinessException(StatusEnum.UP_LOCK_FAIL_RETRY_OWNER);
					}
				}
			}else {
				if(reqc.getStatus() == 1) {
					this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId(), StatusEnum.DOWN_LOCK_FAIL_CHANGE_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
				}else if(reqc.getStatus() == 2){
					this.redisService.set(RedisKey.OWNER_CONTROL_LOCK.key+reqc.getStallId(), StatusEnum.UP_LOCK_FAIL_CHANGE_OWNER.code,ExpiredTime.STALL_LOCK_BOOKING_EXP_TIME.time);
				}
			}
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
		return falg;
	}
	
	
}
