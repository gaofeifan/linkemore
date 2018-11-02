package cn.linkmore.prefecture.dao.master;

import java.util.List;
import java.util.Map;

import cn.linkmore.prefecture.entity.StrategyGroup;

public interface StrategyGroupMasterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StrategyGroup record);

    int insertSelective(StrategyGroup record);

    int updateByPrimaryKeySelective(StrategyGroup record);

    int updateByPrimaryKey(StrategyGroup record);
    
    int updateStallCount (Long id);
    
    int delete(List<Long> ids);
    
	int updateStatus(Map<String, Object> map);
}