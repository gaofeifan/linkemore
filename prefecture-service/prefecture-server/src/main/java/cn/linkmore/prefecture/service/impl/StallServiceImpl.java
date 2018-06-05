package cn.linkmore.prefecture.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.linkmore.lock.bean.LockBean;
import com.linkmore.lock.factory.LockFactory;
import com.linkmore.lock.response.ResponseMessage;
import cn.linkmore.bean.common.Constants.BindOrderStatus;
import cn.linkmore.bean.common.Constants.LockStatus;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.Constants.StallStatus;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.prefecture.dao.cluster.StallClusterMapper;
import cn.linkmore.prefecture.dao.master.StallMasterMapper;
import cn.linkmore.prefecture.entity.Stall;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.prefecture.service.StallService;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.ObjectUtils;
/**
 * Service实现类 - 车位信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class StallServiceImpl implements StallService {
	
	@Autowired
	private StallMasterMapper stallMasterMapper;
	@Autowired
	private StallClusterMapper stallClusterMapper;
	@Autowired
	private RedisService redisService;
	@Autowired
	private LockFactory lockFactory;
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void order(Long id) { 
		Stall stall = new Stall();
		stall.setId(id); 
		stall.setStatus(StallStatus.USED.status);
		stall.setLockStatus(LockStatus.UP.status);
		stall.setBindOrderStatus((short)BindOrderStatus.FREE.status);
		stallMasterMapper.order(stall); 
	}

	@Override
	public boolean cancel(Long stallId) {
		Boolean flag = false;
		Stall stall = stallClusterMapper.findById(stallId);
		if(stall != null) {
			stall.setStatus(Stall.STATUS_FREE);
			stall.setLockStatus(Stall.LOCK_STATUS_UP);
			stall.setBindOrderStatus(Stall.BIND_ORDER_STATUS_NONE);
			stallMasterMapper.cancel(stall);
			flag = true;
		}
		return flag;
	}
	
	@Override
	public boolean checkout(Long stallId) {
		boolean flag = false;
		Stall stall = stallClusterMapper.findById(stallId);
		if(stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			ResponseMessage<LockBean> res=lockFactory.lockDown(stall.getLockSn());
			int code = res.getMsgCode();
			log.info("lock msg:{}",JsonUtil.toJson(res));
			if (code == 200) {
				flag = true;
				stall.setLockStatus(Stall.LOCK_STATUS_UP); 
				this.redisService.add(RedisKey.PREFECTURE_FREE_STALL.key +stall.getPreId(), stall.getLockSn());
			}
			stall.setUpdateTime(new Date());
			stall.setBindOrderStatus(Stall.BIND_ORDER_STATUS_NONE);
			stall.setStatus(Stall.STATUS_FREE);
			this.stallMasterMapper.checkout(stall);
		}
		
		return flag;
	}

	@Override
	public boolean downlock(Long stallId) {
		boolean flag = false;
		Stall stall = stallClusterMapper.findById(stallId);
		log.info("stall:{}",JsonUtil.toJson(stall));
		if(stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			log.info("download");
			ResponseMessage<LockBean> res=lockFactory.lockDown(stall.getLockSn());
			log.info("res:{}",JsonUtil.toJson(res));
			int code = res.getMsgCode();
	    	if(code == 200){ 
	    		flag = true;
	    		stall.setLockStatus(Stall.LOCK_STATUS_DOWN);
				stallMasterMapper.lockdown(stall);
	    	}
			
		}
		return flag;
	}

	@Override
	public boolean uplock(Long stallId) {
		boolean flag = true;
		Stall stall = stallClusterMapper.findById(stallId);
		if(stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			ResponseMessage<LockBean> res=lockFactory.lockUp(stall.getLockSn());
			int code = res.getMsgCode();
			if (code != 200){
	    		 //此处为升锁操作
	    		 flag = false;
	    		 throw new BusinessException(StatusEnum.ORDER_LOCKUP_FAIL); 
	    	}
			stall.setLockStatus(Stall.LOCK_STATUS_UP);
			stallMasterMapper.lockdown(stall);
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
		if(stall != null) {
			return ObjectUtils.copyObject(stall, detail);
		}
		return null;
	}

	@Override
	public ResStallEntity findByLock(String sn) {
		ResStallEntity detail = new ResStallEntity();
		Stall stall = this.stallClusterMapper.findByLockSn(sn);
		if(stall != null) {
			return ObjectUtils.copyObject(stall, detail);
		}
		return null;
	}
	
}
