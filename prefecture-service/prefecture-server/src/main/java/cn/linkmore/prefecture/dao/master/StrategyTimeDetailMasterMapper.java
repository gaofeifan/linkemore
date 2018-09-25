package cn.linkmore.prefecture.dao.master;

import java.util.List;

import cn.linkmore.prefecture.entity.StrategyTimeDetail;

public interface StrategyTimeDetailMasterMapper {
    int deleteByPrimaryKey(Long id);
    
    int delete(List<Long> ids);
    
    int insert(StrategyTimeDetail record);

    int insertSelective(StrategyTimeDetail record);

    int updateByPrimaryKeySelective(StrategyTimeDetail record);

    int updateByPrimaryKey(StrategyTimeDetail record);
}