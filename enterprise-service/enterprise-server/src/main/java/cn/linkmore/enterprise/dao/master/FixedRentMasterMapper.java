package cn.linkmore.enterprise.dao.master;

import cn.linkmore.enterprise.entity.FixedRent;

public interface FixedRentMasterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FixedRent record);

    int insertSelective(FixedRent record);

    int updateByPrimaryKeySelective(FixedRent record);

    int updateByPrimaryKey(FixedRent record);
}