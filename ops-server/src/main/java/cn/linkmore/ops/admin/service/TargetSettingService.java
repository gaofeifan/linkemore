package cn.linkmore.ops.admin.service;

import java.util.List;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.admin.request.ReqCheck;
import cn.linkmore.ops.admin.request.ReqTargetSetting;
import cn.linkmore.ops.admin.response.ResPreList;

/**
 * Service接口 - 车区目标
 * @author liwenlong
 * @version 2.0
 *
 */
public interface TargetSettingService {
	/**
	 * 分页查询数据
	 * @param pageable 分页查询条件
	 * @return 查询结果集合
	 */
	ViewPage findPage(ViewPageable pageable);
	
	/**
	 * 保存
	 * @param record
	 */
	int save(ReqTargetSetting record); 
	
	/**
	 * 更新
	 * @param record
	 * @return
	 */
	int update(ReqTargetSetting record) ;

	 /**
	  * 删除
	  * @param ids
	  * @return
	  */
	int delete(List<Long> ids) ;
	
	/**
	 * 检查参数
	 * 
	 * @return
	 */
	Boolean check(ReqCheck reqCheck);

	/**
	 * 查询车区列表
	 * @return
	 */
	List<ResPreList> findPrefectureList(); 
	
	/**
	 * 执行任务
	 * 需要定时调度
	 */
	void dayTargetScheduled();
	
}
