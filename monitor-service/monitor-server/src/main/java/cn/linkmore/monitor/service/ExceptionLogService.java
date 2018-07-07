package cn.linkmore.monitor.service;

import java.util.List;

import cn.linkmore.monitor.entity.ExceptionLog;

/**
 * 异常日志记录
 * @author   GFF
 * @Date     2018年7月4日
 * @Version  v2.0
 */
public interface ExceptionLogService {

	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void save(ExceptionLog log);

	/**
	 * @Description  查询所有异常日志
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ExceptionLog> list();

}
