package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;

import cn.linkmore.enterprise.entity.ReqFixedUser;


public interface FixedUserClusterMapper {
	
	Integer count(Map<String, Object> param);

	List<ReqFixedUser> findPage(Map<String, Object> param);
}
