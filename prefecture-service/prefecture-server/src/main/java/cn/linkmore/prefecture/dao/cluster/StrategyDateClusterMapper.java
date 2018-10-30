package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

import cn.linkmore.prefecture.entity.StrategyDate;
import cn.linkmore.prefecture.response.ResStrategyDate;

public interface StrategyDateClusterMapper {

    StrategyDate selectByPrimaryKey(Long id);
    
    List<StrategyDate> findPage(Map<String, Object> param);
    
    List<ResStrategyDate> findList(Map<String, Object> param);
    
    Integer count(Map<String, Object> param);
    
}