package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

import cn.linkmore.prefecture.entity.StrategyStall;
import cn.linkmore.prefecture.response.ResStrategyFee;

public interface StrategyFeeClusterMapper {
    List<ResStrategyFee> findList();
    
    List<StrategyStall> findStrategyStallList(Long id);
    
    List<StrategyStall> findStrategyFeeList(Map<String,Object> map);
    
    
}