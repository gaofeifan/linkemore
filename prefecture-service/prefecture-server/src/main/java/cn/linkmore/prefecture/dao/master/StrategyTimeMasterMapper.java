package cn.linkmore.prefecture.dao.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.StrategyTime;
@Mapper
public interface StrategyTimeMasterMapper {
	int save(StrategyTime strategyInterval);

	int update(StrategyTime strategyInterval);

	int delete(List<Long> ids);

	int updateStatus(Map<String, Object> map);

	int updatePublic(Map<String, Object> map);
	
}
