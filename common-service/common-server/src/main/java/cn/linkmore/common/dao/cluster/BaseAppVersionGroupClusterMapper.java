package cn.linkmore.common.dao.cluster;

import java.util.List;
import java.util.Map;

import cn.linkmore.common.response.ResBaseAppVersionGroup;

public interface BaseAppVersionGroupClusterMapper {
   
	List<ResBaseAppVersionGroup> findPage(Map<String, Object> param);

	Integer count(Map<String, Object> param);
	
	List<ResBaseAppVersionGroup> findList(Map<String, Object> param);
}