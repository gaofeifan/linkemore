package cn.linkmore.prefecture.dao.master;

import java.util.List;

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
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids); 
    
}