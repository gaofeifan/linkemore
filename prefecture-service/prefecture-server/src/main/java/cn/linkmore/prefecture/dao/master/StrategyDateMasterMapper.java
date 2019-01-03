package cn.linkmore.prefecture.dao.master;

import java.util.List;
import java.util.Map;

import cn.linkmore.prefecture.entity.StrategyDate;

public interface StrategyDateMasterMapper {
    int deleteByPrimaryKey(Long id);
    
    int delete(List<Long> ids);
    
    int insert(StrategyDate record);

    int insertSelective(StrategyDate record);

    int updateByPrimaryKeySelective(StrategyDate record);

    int updateByPrimaryKey(StrategyDate record);
    
    int updateStatus(Map<String, Object> map);
    
    int updatePublic(Map<String, Object> map);
}