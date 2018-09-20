package cn.linkmore.ops.biz.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * @author   GFF
 * @Date     2018年9月20日
 * @Version  v2.0
 */
public interface UnusualLogService {

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

}
