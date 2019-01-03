package cn.linkmore.ops.account.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.account.request.ReqUserGroup;
import cn.linkmore.account.request.ReqUserGroupDetail;
import cn.linkmore.account.request.ReqUserGroupInput;
import cn.linkmore.account.response.ResUserGroup;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

public interface UserGroupService {
	
	int insert(ReqUserGroup record);
	
	int deleteByIds(List<Long> ids);
	
	int updateByPrimaryKey(ReqUserGroup record);
	
	int updateStatus(Map<String, Object> map);
	
	ResUserGroup getByPrimarkey(Long id);
	
	ViewPage findPage(ViewPageable pageable);
	
	List<ResUserGroup> findList(Map<String, Object> map);
	
	
	int inputSaveByUserIds(Map<String, Object> map);
	
	int userInsert(ReqUserGroupDetail record);
	
	int userDeleteByIds(List<Long> ids);
	
	int inputDeleteAndUserByIds(List<Long> ids);
	
	ViewPage userFindPage(ViewPageable pageable);
	
	int inputInsert(ReqUserGroupInput record);
	
	int inputUpdateByPrimaryKey(ReqUserGroupInput record);
	
	int inputDeleteByIds(List<Long> ids);
	
	ViewPage inputFindPage(ViewPageable pageable);
	
	boolean inputExists(ReqUserGroupInput reqUserGroupInput);

	boolean syncByUserGroupId(Long userGroupId);

	ViewPage pageUserByNotInUserGroup(ViewPageable pageable);

	

	
}
