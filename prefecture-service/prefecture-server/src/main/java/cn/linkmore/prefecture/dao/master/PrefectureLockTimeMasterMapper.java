package cn.linkmore.prefecture.dao.master;

import java.util.List;

import cn.linkmore.prefecture.entity.PrefectureLockTime;

public interface PrefectureLockTimeMasterMapper {
    int deleteByPrimaryKey(Long id);

    int delete(List<Long> ids);
    
    int insert(PrefectureLockTime record);

    int insertSelective(PrefectureLockTime record);

    int updateByPrimaryKeySelective(PrefectureLockTime record);

    int updateByPrimaryKey(PrefectureLockTime record);
}