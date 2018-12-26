package cn.linkmore.account.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.account.request.ReqUserGroup;
import cn.linkmore.account.response.ResUserGroup;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

public interface UserGroupService {

	int insert(ReqUserGroup record);
	
	int insertSelective(ReqUserGroup record);
	 
    int deleteByPrimaryKey(Long id);

    int deleteByIds(List<Long> ids);
    
	int updateByPrimaryKey(ReqUserGroup record);
	
	int updateByPrimaryKeySelective(ReqUserGroup record);
	
	int updateStatus(Map<String, Object> map);
	
	ResUserGroup selectByPrimaryKey(Long id);
	
	ViewPage findPage(ViewPageable pageable);

	List<ResUserGroup> findList(Map<String, Object> param);
	
}
