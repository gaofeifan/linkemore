package cn.linkmore.prefecture.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqLockOperateLog;
import cn.linkmore.prefecture.request.ReqLockOperateLogExcel;
import cn.linkmore.prefecture.response.ResLockOperateLog;

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
	int save(ReqLockOperateLog reqLock);
	
	int delete(List<Long> ids);
	
	int update(ReqLockOperateLog reqLock);

	ViewPage findPage(ViewPageable pageable);

	List<ResLockOperateLog> findListById(Long id);

	List<ResLockOperateLog> exportList(ReqLockOperateLogExcel bean);
	
}
