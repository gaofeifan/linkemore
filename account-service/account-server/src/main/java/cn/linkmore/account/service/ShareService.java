package cn.linkmore.account.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 分享记录接口
 * @author   GFF
 * @Date     2018年5月17日
 * @Version  v2.0
 */
public interface ShareService {

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

}
