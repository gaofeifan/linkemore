package cn.linkmore.prefecture.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.prefecture.dao.master.LockOperateLogMasterMapper;
import cn.linkmore.prefecture.entity.LockOperateLog;
import cn.linkmore.prefecture.request.ReqLockOperateLog;
import cn.linkmore.prefecture.service.LockOperateLogService;
/**
 * Service实现类 - 锁操作日志信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class LockOperateLogServiceImpl implements LockOperateLogService {
	
	@Autowired
	private LockOperateLogMasterMapper lockOperateLogMasterMapper;

	@Override
	public void save(ReqLockOperateLog reqLockOperateLog) {
		if(reqLockOperateLog != null) {
			LockOperateLog log = new LockOperateLog();
			log.setLockSn(reqLockOperateLog.getLockSn());
			log.setStallId(reqLockOperateLog.getStallId());
			log.setCreateTime(new Date());
			log.setOperation(reqLockOperateLog.getOperation());
			log.setOperatorId(reqLockOperateLog.getOperatorId());
			log.setSource(reqLockOperateLog.getSource());
			log.setStatus(reqLockOperateLog.getStatus());
			lockOperateLogMasterMapper.save(log);
		}
	}
}
