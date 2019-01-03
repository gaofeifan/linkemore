package cn.linkmore.account.dao.master;

import java.util.List;
import java.util.Map;

import cn.linkmore.account.entity.UserGroupDetail;

public interface UserGroupDetailMasterMapper {
    int deleteByPrimaryKey(Long id);

    int deleteByIds(List<Long> ids);
    
    int deleteByInputIds(List<Long> ids);
    
    int deleteByGroupIds(List<Long> ids);
    
    int insertByUserIds(Map<String, Object> map);
    
    int insert(UserGroupDetail record);

    int insertSelective(UserGroupDetail record); 

    int updateByPrimaryKeySelective(UserGroupDetail record);

    int updateByPrimaryKey(UserGroupDetail record);

	

}