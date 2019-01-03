package cn.linkmore.account.dao.cluster;

import java.util.List;
import java.util.Map;

import cn.linkmore.account.entity.UserGroup;
import cn.linkmore.account.response.ResUserGroup;

public interface UserGroupClusterMapper {

	UserGroup selectByPrimaryKey(Long id);

	List<ResUserGroup> findPage(Map<String, Object> param);

	Integer count(Map<String, Object> param);

	List<ResUserGroup> findList(Map<String, Object> param);

}