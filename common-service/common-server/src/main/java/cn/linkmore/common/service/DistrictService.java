package cn.linkmore.common.service;

import java.util.List;

import cn.linkmore.bean.ViewPage;
import cn.linkmore.bean.ViewPageable;
import cn.linkmore.common.entity.District;

/**
 * Service接口 - 区域信息
 * @author liwenlong
 * @version 1.0
 *
 */
public interface DistrictService {

	/**
	 * 分页查询
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * 保存
	 * @param record
	 */
	int save(District record);

	/**
	 * 更新
	 * @param record
	 * @return
	 */
	int update(District record);

	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids);

	/**
	 * 检查属性存在与否
	 * @param property
	 * @param value
	 * @param id
	 * @return
	 */
	Integer check(String property, String value, Long id);

}
