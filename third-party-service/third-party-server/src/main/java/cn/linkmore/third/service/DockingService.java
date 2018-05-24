package cn.linkmore.third.service;

import cn.linkmore.third.request.ReqOrder;

/**
 * Service接口 - 停车场对接
 * @author liwenlong
 * @version 2.0
 *
 */
public interface DockingService {

	/**
	 * 发通知
	 * @param ro
	 */
	void order(ReqOrder ro);

}
