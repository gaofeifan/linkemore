package cn.linkmore.prefecture.service;

import java.util.List;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqTargetSetting;
import cn.linkmore.prefecture.response.ResPre;

/**
 * Service接口 - 车区目标
 * @author liwenlong
 * @version 1.0
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
	 * @param property 属性
	 * @param value 值
	 * @param id 主健
	 * @return
	 */
	Integer check(ReqCheck reqCheck);
	
	/**
	 * 执行任务
	 * 需要定时调度
	 */
	void dayTargetScheduled();
	
}
