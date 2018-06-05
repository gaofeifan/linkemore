package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.StrategyBase;
import cn.linkmore.prefecture.response.ResStrategyBase;
/**
 * dao 计费详情
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface StrategyBaseClusterMapper {
	/**
	 * 查询主键对应的计费策略信息
	 * @param id 主健
	 * @return ResStrategyBase 计费策略信息
	 */
	StrategyBase findById(Long id);

	Integer count(Map<String, Object> param);

	List<ResStrategyBase> findPage(Map<String, Object> param);

	Integer check(Map<String, Object> param);
}