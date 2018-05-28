package cn.linkmore.common.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
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
	
	/**
	 * 获取数据列表
	 * @param start
	 * @param size
	 * @return
	 */
	List<ResCity> findList(Integer start,Integer size);
	
	/**
	 * 是否存在实体
	 * @param property
	 * @param value
	 * @param id
	 * @return
	 */
	Integer exists(String property,String value,Long id);

	/**
	 * @Description  校验
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer check(String property, String value, Long id);

	/**
	 * @Description  批量删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void deleteIds(List<Long> ids);

}
