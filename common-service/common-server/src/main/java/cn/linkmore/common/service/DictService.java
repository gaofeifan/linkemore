package cn.linkmore.common.service;

import java.util.List;

import cn.linkmore.common.response.ResOldDict;

/**
 * Service接口 - 字典
 * @author liwenlong
 * @version 2.0
 *
 */
public interface DictService {

	ResOldDict find(Long id);
	/**
	 * 查询计费系统下拉列表
	 * @return
	 */
	List<ResOldDict> findBillSystemList();
}
