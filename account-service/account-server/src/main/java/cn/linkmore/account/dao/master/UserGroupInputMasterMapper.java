package cn.linkmore.account.dao.master;

import java.util.List;
import java.util.Map;

import cn.linkmore.account.entity.UserGroupInput;

public interface UserGroupInputMasterMapper {
    int deleteByPrimaryKey(Long id);

    int deleteByIds(List<Long> ids);
    
    int deleteByGroupIds(List<Long> ids);
    
    int insertByUserIds(Map<String, Object> map);
    
    int insert(UserGroupInput record);

    int insertSelective(UserGroupInput record); 

    int updateByPrimaryKeySelective(UserGroupInput record);

    int updateByPrimaryKey(UserGroupInput record);
}