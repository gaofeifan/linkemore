package cn.linkmore.prefecture.service;

import cn.linkmore.prefecture.entity.LockOperateLog;
import cn.linkmore.prefecture.entity.StrategyBase;
import cn.linkmore.prefecture.request.ReqLockOperateLog;

/**
 * Service接口 - 锁操作信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
public interface LockOperateLogService {
	/**
	 * 根据订单详情中计费策略id查询
	 * @param id
	 * @return
	 */
	void save(ReqLockOperateLog reqLock);
	
}
