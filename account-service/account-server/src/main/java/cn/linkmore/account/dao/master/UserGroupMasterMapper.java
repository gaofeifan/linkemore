package cn.linkmore.account.dao.master;

import java.util.List;
import java.util.Map;

import cn.linkmore.account.entity.UserGroup;

public interface UserGroupMasterMapper {
    int deleteByPrimaryKey(Long id);

    int deleteByIds(List<Long> ids);
    
    int updateStatus(Map<String, Object> map);
    
    int insert(UserGroup record);

    int insertSelective(UserGroup record);

    int updateByPrimaryKeySelective(UserGroup record);

    int updateByPrimaryKey(UserGroup record);
}