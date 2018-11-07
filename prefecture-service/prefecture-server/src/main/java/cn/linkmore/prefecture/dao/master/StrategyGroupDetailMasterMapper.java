package cn.linkmore.prefecture.dao.master;

import java.util.List;

import cn.linkmore.prefecture.entity.StrategyGroupDetail;

public interface StrategyGroupDetailMasterMapper {
    int deleteByPrimaryKey(List<Long> ids);
    
    int deleteByStallId(List<Long> ids);
    
    int delete(List<Long> ids);
    
    int insert(StrategyGroupDetail record);

    int insertSelective(StrategyGroupDetail record);

    int updateByPrimaryKeySelective(StrategyGroupDetail record);

    int updateByPrimaryKey(StrategyGroupDetail record);
}