package cn.linkmore.common.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * app异常日志上报接口
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
public interface UnusualLogService {

	/**
	 * 新增
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void insert(cn.linkmore.common.controller.app.request.ReqUnusualLog unusualLog);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);
}
