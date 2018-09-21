package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.StrategyTime;
import cn.linkmore.prefecture.response.ResStrategyTime;
@Mapper
public interface StrategyTimeClusterMapper {

	StrategyTime selectByPrimaryKey(Long id);
	
	List<ResStrategyTime> findList(Map<String, Object> param);

	Integer count(Map<String, Object> param);

	List<ResStrategyTime> findPage(Map<String, Object> param);

}
