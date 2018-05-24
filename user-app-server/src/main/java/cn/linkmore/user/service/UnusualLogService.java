package cn.linkmore.user.service;

import cn.linkmore.common.request.ReqUnusualLog;

/**
 * app异常日志上报接口
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
public interface UnusualLogService {

	/**
	 * @Description  app异常日志上报
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void insert(cn.linkmore.user.request.ReqUnusualLog unusualLog);

}
