package cn.linkmore.common.service;

import cn.linkmore.common.request.ReqUnusualLog;

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
	void insert(ReqUnusualLog unusualLog);



}