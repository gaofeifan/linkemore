package cn.linkmore.enterprise.dao.master;

import cn.linkmore.enterprise.entity.FixedPlate;

public interface FixedPlateMasterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FixedPlate record);

    int insertSelective(FixedPlate record);

    int updateByPrimaryKeySelective(FixedPlate record);

    int updateByPrimaryKey(FixedPlate record);
}