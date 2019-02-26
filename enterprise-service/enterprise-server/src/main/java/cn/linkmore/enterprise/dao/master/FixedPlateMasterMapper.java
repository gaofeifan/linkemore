package cn.linkmore.enterprise.dao.master;

import java.util.Map;

import cn.linkmore.enterprise.entity.FixedPlate;

public interface FixedPlateMasterMapper {
    int deleteByPrimaryKey(Long id);

    int deleteByPlate(Map<String, Object> map);
    
    int insert(FixedPlate record);
    
    int insertMore(Map<String, Object> map);
    
    int insertSelective(FixedPlate record);

    int updateByPrimaryKeySelective(FixedPlate record);

    int updateByPrimaryKey(FixedPlate record);
}