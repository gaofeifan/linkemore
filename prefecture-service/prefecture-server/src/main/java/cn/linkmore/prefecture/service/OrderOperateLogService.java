package cn.linkmore.prefecture.service;

import java.util.List;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqOrderOperateLog;
import cn.linkmore.prefecture.request.ReqOrderOperateLogExcel;
import cn.linkmore.prefecture.response.ResOrderOperateLog;
import cn.linkmore.prefecture.response.ResOrderOperateLogEntity;


/**
 * Service - 车位订单操作记录
 * @author jiaohanbin
 *
 */
public interface OrderOperateLogService {

	ResOrderOperateLog find(Long id);
	
	int delete(List<Long> ids);
	
	int save(ReqOrderOperateLog stallOperateLog);
	
	int update(ReqOrderOperateLog stallOperateLog);

	ViewPage findPage(ViewPageable pageable);

	List<ResOrderOperateLogEntity> findListById(Long id);

	List<ResOrderOperateLogEntity> exportList(ReqOrderOperateLogExcel bean);
	

}
