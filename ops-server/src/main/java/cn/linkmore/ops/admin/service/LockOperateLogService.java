package cn.linkmore.ops.admin.service;

import java.util.List;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqLockOperateLog;
import cn.linkmore.prefecture.request.ReqLockOperateLogExcel;
import cn.linkmore.prefecture.response.ResLockOperateLog;


/**
 * Service - 车位升降
 * @author jiaohanbin
 * @version 2.0
 */
public interface LockOperateLogService {

	//LockOperateLog find(Long id);
	
	//int delete(List<Long> ids);
	
	//int save(ReqLockOperateLog stallOperateLog);
	
	//int update(ReqLockOperateLog stallOperateLog);

	ViewPage findPage(ViewPageable pageable);

	List<ResLockOperateLog> findListById(Long id);

	List<ResLockOperateLog> exportList(ReqLockOperateLogExcel bean);
	

}
