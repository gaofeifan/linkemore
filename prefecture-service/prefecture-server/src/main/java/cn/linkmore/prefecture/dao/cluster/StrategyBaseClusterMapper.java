package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.StrategyBase;

@Mapper
public interface StrategyBaseClusterMapper {
	
	StrategyBase getFreeMinsById(Long strategyId);
	
    StrategyBase selectByPrimaryKey(Long id);

    List<StrategyBase> selectAll();
    
}