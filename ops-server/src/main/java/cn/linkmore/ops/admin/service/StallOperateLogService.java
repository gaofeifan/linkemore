package cn.linkmore.ops.admin.service;
import java.util.List;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqStallOperateLog;
import cn.linkmore.prefecture.request.ReqStallOperateLogExcel;
import cn.linkmore.prefecture.response.ResStallOperateLog;

/**
 * Service - 车位上下线
 * @author jiaohanbin
 *
 */
public interface StallOperateLogService {

	//StallOperateLog find(Long id);
	
	//int delete(List<Long> ids);
	
	//int save(ReqStallOperateLog stallOperateLog);
	
	//int update(ReqStallOperateLog stallOperateLog);

	ViewPage findPage(ViewPageable pageable);

	List<ResStallOperateLog> findListById(Long id);

	List<ResStallOperateLog> exportList(ReqStallOperateLogExcel bean);
	

}
