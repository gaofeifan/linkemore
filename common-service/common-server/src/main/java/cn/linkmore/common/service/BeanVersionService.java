package cn.linkmore.common.service;

import cn.linkmore.common.request.ReqVersion;
import cn.linkmore.common.response.ResVersionBean;

/**
 * 	版本管理接口类
 * @author   GFF
 * @Date     2018年5月17日
 * @Version  v2.0
 */
public interface BeanVersionService {

	/**
	 * @Description  当前版本
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResVersionBean currentAppVersion(Integer appType);

	/**
	 * @Description  上报用户版本
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void report(ReqVersion vrb);

}
