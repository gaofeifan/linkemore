package cn.linkmore.account.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 领券记录接口
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
public interface ReceiveService {
	/**
	 * 分页查询领券信息
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);
	/**
	 * 分页查询领券详情信息
	 * @param pageable
	 * @return
	 */
	ViewPage findDetailPage(ViewPageable pageable);
}
