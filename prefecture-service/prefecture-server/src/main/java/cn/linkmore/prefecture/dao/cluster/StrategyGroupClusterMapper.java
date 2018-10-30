package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

import cn.linkmore.prefecture.entity.StrategyGroup;
import cn.linkmore.prefecture.response.ResStrategyGroup;

public interface StrategyGroupClusterMapper {

    StrategyGroup selectByPrimaryKey(Long id);
    
    Integer count(Map<String, Object> param);
    
    List<ResStrategyGroup> findPage(Map<String, Object> param);
    
    List<ResStrategyGroup> findList(Map<String, Object> param);
}