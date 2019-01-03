package cn.linkmore.account.dao.cluster;

import java.util.List;
import java.util.Map;

import cn.linkmore.account.entity.UserGroupInput;
import cn.linkmore.account.request.ReqUserGroupInput;
import cn.linkmore.account.response.ResUserGroupInput;

public interface UserGroupInputClusterMapper {
   
	UserGroupInput selectByPrimaryKey(Long id);
    
    List<ResUserGroupInput> findPage(Map<String, Object> param);

    List<UserGroupInput> findList(Map<String, Object> param);
    
	Integer count(Map<String, Object> param);

	UserGroupInput findByPlate(ReqUserGroupInput reqUserGroupInput);

}