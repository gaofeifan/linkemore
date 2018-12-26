package cn.linkmore.account.dao.cluster;

import java.util.List;
import java.util.Map;

import cn.linkmore.account.entity.UserGroupDetail;
import cn.linkmore.account.request.ReqUserGroupDetail;
import cn.linkmore.account.response.ResGroupUser;

public interface UserGroupDetailClusterMapper {
   
    UserGroupDetail selectByPrimaryKey(Long id);
    
    List<ResGroupUser> findPage(Map<String, Object> param);
    
    Integer count(Map<String, Object> param);
    
    List<ResGroupUser> pageUserByNotInUserGroup(Map<String, Object> param);
    
    Integer pageUserByNotInUserGroupCount(Map<String, Object> param);
    
    
    List<UserGroupDetail> findList(Map<String, Object> param);
    
    List<UserGroupDetail> findSyncList(Map<String, Object> param);

}