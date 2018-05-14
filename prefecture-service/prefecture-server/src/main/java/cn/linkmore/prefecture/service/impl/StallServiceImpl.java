package cn.linkmore.prefecture.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.prefecture.dao.cluster.StallClusterMapper;
import cn.linkmore.prefecture.dao.master.StallMasterMapper;
import cn.linkmore.prefecture.entity.Stall;
import cn.linkmore.prefecture.service.StallService;
/**
 * Service实现类 - 车区信息
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

	@Override
	public void order(String lockSn) {
		Stall stall = stallClusterMapper.findByLockSn(lockSn.trim());
		// 更新车位状态
		stall.setStatus(Stall.STATUS_USED);
		stall.setLockStatus(Stall.LOCK_STATUS_UP);
		stall.setBindOrderStatus(Stall.BIND_ORDER_STATUS_NONE);
		stallMasterMapper.order(stall);
	}

	@Override
	public void cancel(Long stallId) {
		Stall stall = stallClusterMapper.findById(stallId);
		stall.setStatus(Stall.STATUS_FREE);
		stall.setLockStatus(Stall.LOCK_STATUS_UP);
		stall.setBindOrderStatus(Stall.BIND_ORDER_STATUS_NONE);
		stallMasterMapper.cancel(stall);
	}

	@Override
	public void lockdown(Long stallId) {
		Stall stall = stallClusterMapper.findById(stallId);
		stall.setLockStatus(Stall.LOCK_STATUS_DOWN);
		stallMasterMapper.lockdown(stall);   
	}

	@Override
	public void pay(Long stallId) {
		Stall stall = stallClusterMapper.findById(stallId);
		stall.setUpdateTime(new Date());
		stall.setBindOrderStatus(Stall.BIND_ORDER_STATUS_NONE);
		stall.setStatus(Stall.STATUS_FREE);
		this.stallMasterMapper.pay(stall);
	}
	
}
