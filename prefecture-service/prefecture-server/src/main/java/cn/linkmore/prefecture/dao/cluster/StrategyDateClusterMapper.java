package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

import cn.linkmore.prefecture.entity.StrategyDate;

public interface StrategyDateClusterMapper {

    StrategyDate selectByPrimaryKey(Long id);
    
    List<StrategyDate> findPage(Map<String, Object> param);
    
    Integer count(Map<String, Object> param);
    
}