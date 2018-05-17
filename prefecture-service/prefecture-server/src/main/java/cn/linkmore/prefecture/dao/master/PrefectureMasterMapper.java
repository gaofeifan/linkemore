package cn.linkmore.prefecture.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.Prefecture;
/**
 * dao 车区
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface PrefectureMasterMapper {
    /**
	 * 删除id对应的信息
	 * @param id 主键
	 * @return  删除条数
	 */
	int delete(Long id);

	/**
	 * 保存信息
	 * @param record 专区信息
	 * @return 保存条数
	 */
	int save(Prefecture record);  

	/**
	 * 更新信息
	 * @param record 专区信息
	 * @return 更新条数
	 */
	int update(Prefecture record); 
    
}