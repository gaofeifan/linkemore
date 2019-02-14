package cn.linkmore.enterprise.dao.master;

import cn.linkmore.enterprise.entity.FixedDisable;

public interface FixedDisableMasterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FixedDisable record);

    int insertSelective(FixedDisable record);

    int updateByPrimaryKeySelective(FixedDisable record);

    int updateByPrimaryKey(FixedDisable record);
}