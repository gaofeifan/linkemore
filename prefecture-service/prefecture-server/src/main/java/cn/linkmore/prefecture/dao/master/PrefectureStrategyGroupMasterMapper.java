package cn.linkmore.prefecture.dao.master;

import java.util.List;

import cn.linkmore.prefecture.entity.PrefectureStrategyGroup;

public interface PrefectureStrategyGroupMasterMapper {
    int deleteByPrimaryKey(Long id);
    
    int delete(List<Long> ids);
    
    int insert(PrefectureStrategyGroup record);

    int insertSelective(PrefectureStrategyGroup record);

    int updateByPrimaryKeySelective(PrefectureStrategyGroup record);

    int updateByPrimaryKey(PrefectureStrategyGroup record);
}