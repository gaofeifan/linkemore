package cn.linkmore.prefecture.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.StrategyBase;

@Mapper
public interface StrategyBaseClusterMapper {
	/**
	 * 查询主键对应的计费策略信息
	 * @param id 主健
	 * @return ResStrategyBase 计费策略信息
	 */
	StrategyBase findById(Long id);
}