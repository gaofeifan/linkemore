package cn.linkmore.prefecture.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.linkmore.lock.bean.LockBean;
import com.linkmore.lock.factory.LockFactory;
import com.linkmore.lock.response.ResponseMessage;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.prefecture.dao.cluster.StallClusterMapper;
import cn.linkmore.prefecture.dao.master.StallMasterMapper;
import cn.linkmore.prefecture.entity.Prefecture;
import cn.linkmore.prefecture.entity.Stall;
import cn.linkmore.prefecture.lock.FreeLockPool;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.prefecture.fee.InitLockFactory;
import cn.linkmore.prefecture.service.StallService;
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
	private FreeLockPool freeLockPool;

	@Override
	public boolean order(String lockSn) {
		Stall stall = stallClusterMapper.findByLockSn(lockSn.trim());
		// 更新车位状态
		stall.setStatus(Stall.STATUS_USED);
		stall.setLockStatus(Stall.LOCK_STATUS_UP);
		stall.setBindOrderStatus(Stall.BIND_ORDER_STATUS_NONE);
		stallMasterMapper.order(stall);
		return true;
	}

	@Override
	public boolean cancel(Long stallId) {
		Stall stall = stallClusterMapper.findById(stallId);
		stall.setStatus(Stall.STATUS_FREE);
		stall.setLockStatus(Stall.LOCK_STATUS_UP);
		stall.setBindOrderStatus(Stall.BIND_ORDER_STATUS_NONE);
		stallMasterMapper.cancel(stall);
		return true;
	}
	
	@Override
	public boolean checkout(Long stallId) {
		boolean flag = false;
		LockFactory lockFactory = InitLockFactory.getInstance();
		Stall stall = stallClusterMapper.findById(stallId);
		if(stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			ResponseMessage<LockBean> res=lockFactory.lockDown(stall.getLockSn());
			int code = res.getMsgCode();
			if (code == 200) {
				flag = true;
				stall.setLockStatus(Stall.LOCK_STATUS_UP);
				freeLockPool.addFreeLock(stall.getPreId(), stall.getLockSn());
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
		boolean flag = true;
		LockFactory lockFactory = InitLockFactory.getInstance();
		Stall stall = stallClusterMapper.findById(stallId);
		if(stall != null && StringUtils.isNotBlank(stall.getLockSn())) {
			ResponseMessage<LockBean> res=lockFactory.lockDown(stall.getLockSn());
			int code = res.getMsgCode();
	    	if(code != 200){
	    		 flag = false;
	    		 throw new BusinessException(StatusEnum.ORDER_LOCKDOWN_FAIL); 
	    	}
			stall.setLockStatus(Stall.LOCK_STATUS_DOWN);
			stallMasterMapper.lockdown(stall);
		}
		return flag;
	}

	@Override
	public boolean uplock(Long stallId) {
		boolean flag = true;
		LockFactory lockFactory = InitLockFactory.getInstance();
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
		return ObjectUtils.copyObject(stall, detail);
	}
	
}
