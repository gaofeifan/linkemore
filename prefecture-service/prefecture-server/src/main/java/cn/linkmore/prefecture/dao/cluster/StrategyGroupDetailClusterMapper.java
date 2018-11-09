package cn.linkmore.prefecture.dao.cluster;

import java.util.List;

import cn.linkmore.prefecture.entity.StrategyGroupDetail;

public interface StrategyGroupDetailClusterMapper {

    StrategyGroupDetail selectByPrimaryKey(Long id);
    
    List<StrategyGroupDetail> findList(Long strategyGroupId);
    
	List<StrategyGroupDetail> findPreGroupDetailList(Long preId);

}