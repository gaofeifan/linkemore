package cn.linkmore.ops.admin.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqOrderOperateLogExcel;
import cn.linkmore.prefecture.response.ResOrderOperateLogEntity;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.response.ResStall;


/**
 * Service - 车位订单操作记录
 * @author jiaohanbin
 * @version 2.0
 *
 */
public interface OrderOperateLogService {

	//OrderOperateLog find(Long id);
	
	//int delete(List<Long> ids);
	
	//int save(ReqOrderOperateLog stallOperateLog);
	
	//int update(ReqOrderOperateLog stallOperateLog);

	ViewPage findPage(ViewPageable pageable);

	List<ResOrderOperateLogEntity> findListById(Long id);

	List<ResOrderOperateLogEntity> exportList(ReqOrderOperateLogExcel bean);

}
