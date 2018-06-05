package cn.linkmore.prefecture.service;

import java.util.List;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqStallOperateLog;
import cn.linkmore.prefecture.request.ReqStallOperateLogExcel;
import cn.linkmore.prefecture.response.ResStallOperateLog;


/**
 * Service - 车位上下线
 * @author jiaohanbin
 * @version 2.0
 */
public interface StallOperateLogService {
	
	int delete(List<Long> ids);
	
	int save(ReqStallOperateLog stallOperateLog);
	
	int update(ReqStallOperateLog stallOperateLog);

	ViewPage findPage(ViewPageable pageable);

	List<ResStallOperateLog> findListById(Long id);

	List<ResStallOperateLog> exportList(ReqStallOperateLogExcel bean);
}
