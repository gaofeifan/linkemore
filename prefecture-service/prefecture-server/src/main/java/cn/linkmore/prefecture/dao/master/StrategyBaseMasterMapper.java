package cn.linkmore.prefecture.dao.master;
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
	 * 删除id对应的信息
	 * @param id 主键
	 * @return  删除条数
	 */
	int delete(Long id);

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
}