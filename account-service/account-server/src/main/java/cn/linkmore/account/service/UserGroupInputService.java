package cn.linkmore.account.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.account.request.ReqUserGroupDetail;
import cn.linkmore.account.request.ReqUserGroupInput;
import cn.linkmore.account.response.ResUserGroupInput;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

public interface UserGroupInputService {
	
	int deleteByPrimaryKey(Long id);

	int deleteByIds(List<Long> ids);
	
	int deleteAndUserByIds(List<Long> ids);
	
	int deleteByGroupIds(List<Long> ids);

	int insertByUserIds(Map<String, Object> map);
	
	int insert(ReqUserGroupInput record);

	int insertSelective(ReqUserGroupInput record);

	int updateByPrimaryKeySelective(ReqUserGroupInput record);

	int updateByPrimaryKey(ReqUserGroupInput record);

	ResUserGroupInput selectByPrimaryKey(Long id);

	ViewPage findPage(ViewPageable pageable);

	boolean exists(ReqUserGroupInput reqUserGroupInput);

	boolean syncByUserGroupId(Long userGroupId);
	boolean syncByPlate(String plant);
	boolean syncByUserId(Long userId);

	ViewPage pageUserByNotInUserGroup(ViewPageable pageable);

	

	
}
