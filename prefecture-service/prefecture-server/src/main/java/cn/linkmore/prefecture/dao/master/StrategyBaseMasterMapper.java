package cn.linkmore.prefecture.dao.master;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.StrategyBase;
/**
 * dao 计费详情
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface StrategyBaseMasterMapper {

	/**
	 * 保存信息
	 * @param record 车位信息
	 * @return 保存条数
	 */
	int save(StrategyBase record);  

	/**
	 * 更新信息
	 * @param record 车位信息
	 * @return 更新条数
	 */
	int update(StrategyBase record);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids); 
}