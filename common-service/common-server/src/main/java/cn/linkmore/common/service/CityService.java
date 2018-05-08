package cn.linkmore.common.service;

import cn.linkmore.bean.ViewPage;
import cn.linkmore.bean.ViewPageable;
import cn.linkmore.common.entity.City;
import cn.linkmore.common.response.ResCity;

/**
 * Service接口 - 城市信息
 * @author liwenlong
 * @version 2.0
 *
 */
public interface CityService {

	/**
	 * 根据ID查询对应的城市信息
	 * @param id 主键
	 * @return
	 */
	ResCity find(Long id);
	
	/**
	 * 根据行政编号查询对应的城市信息
	 * @param code 行政编号
	 * @return
	 */
	ResCity find(String code);

	/**
	 * 保存信息
	 * @param record 城市信息
	 * @return City
	 */
	City save(City record);
	
	/**
	 * 更新信息
	 * @param record 城市信息
	 */
	void update(City record);
	
	/**
	 * 删除主键对应信息
	 * @param id 主键
	 * @return 执行条数
	 */
	int delete(Long id);

	/**
	 * 分页查询
	 * @param pageable  参数
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);

}
