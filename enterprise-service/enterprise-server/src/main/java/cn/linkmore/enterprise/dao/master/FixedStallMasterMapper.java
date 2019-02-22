package cn.linkmore.enterprise.dao.master;

import cn.linkmore.enterprise.entity.FixedStall;

public interface FixedStallMasterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FixedStall record);

    int insertSelective(FixedStall record);

    int updateByPrimaryKeySelective(FixedStall record);

    int updateByPrimaryKey(FixedStall record);
}