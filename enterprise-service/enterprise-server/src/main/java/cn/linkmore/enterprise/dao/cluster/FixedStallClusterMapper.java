package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;

import cn.linkmore.enterprise.entity.FixedStall;
import cn.linkmore.enterprise.response.ResStall;

public interface FixedStallClusterMapper {

    FixedStall selectByPrimaryKey(Long id);

    List<ResStall> selectByFixedId(Long fixedId);
    
    ResStall selectByFixedAndStall(Map<String, Object> param);
    
    Integer existsStall(Map<String, Object> param);
    
    Integer existsFixedStall(Map<String, Object> param);
    
}