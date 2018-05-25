package cn.linkmore.ops.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * Service接口 - 日志信息
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
public interface LogService {
	/**
	 * 分页查询
	 * @param Pageable
	 * @return ViewPage 
	 */
	ViewPage findPage(ViewPageable pageable);
}
