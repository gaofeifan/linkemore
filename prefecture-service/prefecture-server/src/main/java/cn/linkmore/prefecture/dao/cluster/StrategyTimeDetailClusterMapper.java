package cn.linkmore.prefecture.dao.cluster;

import java.util.List;

import cn.linkmore.prefecture.entity.StrategyTimeDetail;

public interface StrategyTimeDetailClusterMapper {

    StrategyTimeDetail selectByPrimaryKey(Long id);

    List<StrategyTimeDetail> findList(Long strategyTimeDetail);
    
    List<StrategyTimeDetail> findListByIds(List<Long> ids);
    
}