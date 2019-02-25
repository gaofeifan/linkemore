package cn.linkmore.enterprise.dao.master;

import java.util.Map;

import cn.linkmore.enterprise.entity.FixedStall;

public interface FixedStallMasterMapper {
    int deleteByPrimaryKey(Long id);

    int deleteStall(Map<String, Object> map);
    
    int insert(FixedStall record);

    int insertMore(Map<String, Object> map);
    
    int insertByStallName(FixedStall record);
    
    int insertSelective(FixedStall record);

    int updateByPrimaryKeySelective(FixedStall record);

    int updateByPrimaryKey(FixedStall record);

	int updateStatus(Map<String, Object> map);
}