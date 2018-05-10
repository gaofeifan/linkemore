package cn.linkmore.common.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.District;

/**
 * Mapper - 区域信息[只写操作]
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface DistrictMasterMapper {

	/**
	 * 删除id对应的信息
	 * @param id 主键
	 * @return  删除条数
	 */
	int delete(Long id);

	/**
	 * 保存信息
	 * @param record 区域信息
	 * @return 保存条数
	 */
	int save(District record);  

	/**
	 * 更新信息
	 * @param record 区域信息
	 * @return 更新条数
	 */
	int update(District record); 
}