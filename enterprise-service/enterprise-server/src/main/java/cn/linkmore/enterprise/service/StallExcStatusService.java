package cn.linkmore.enterprise.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.enterprise.entity.StallExcStatus;

/**
 * 车位异常原因状态
 * @author   GFF
 * @Date     2018年8月6日
 * @Version  v2.0
 */
public interface StallExcStatusService {

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	StallExcStatus findExcStatus(Long stallId);

	/**
	 * @Description  批量查询车位状态
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<StallExcStatus> findExcStatusList(List<Long> stallId);

	/**
	 * @Description  更新车位异常状态
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateExcStatus(Map<String, Object> map);

	/**
	 * @Description  批量新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void saveBatch(List<StallExcStatus> excs);

	/**
	 * @Description  查询车位异常原因
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<StallExcStatus> findAll();

}
