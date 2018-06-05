package cn.linkmore.ops.biz.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 分享领券
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
public interface ReceiveService {

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * @Description  详情分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findDetailPage(ViewPageable pageable);

}
