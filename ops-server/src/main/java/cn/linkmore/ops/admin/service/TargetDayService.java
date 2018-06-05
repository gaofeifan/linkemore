package cn.linkmore.ops.admin.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * Service接口 - 车区每日情况
 * @author jiaohanbin
 * @version 2.0
 *
 */
public interface TargetDayService {
	/**
	 * 分页查询数据
	 * @param pageable 分页查询条件
	 * @return 查询结果集合
	 */
	ViewPage findPage(ViewPageable pageable);
	
}
