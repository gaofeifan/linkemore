package cn.linkmore.enterprise.service;

import cn.linkmore.common.request.ReqUnusualLog;

/**
 * @author   GFF
 * @Date     2018年9月1日
 * @Version  v2.0
 */
public interface UnusualLogService {

	/**
	 * @Description  新增异常日志上报
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void insert(ReqUnusualLog unusualLog);

}
