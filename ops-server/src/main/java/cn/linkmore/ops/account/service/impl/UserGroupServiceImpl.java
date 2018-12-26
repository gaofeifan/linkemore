package cn.linkmore.ops.account.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.account.client.UserGroupClient;
import cn.linkmore.account.request.ReqUserGroup;
import cn.linkmore.account.request.ReqUserGroupDetail;
import cn.linkmore.account.request.ReqUserGroupInput;
import cn.linkmore.account.response.ResUserGroup;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.account.service.UserGroupService;

@Service
public class UserGroupServiceImpl implements UserGroupService {

	@Resource
	private UserGroupClient userGroupClient;
	
	@Override
	public int insert(ReqUserGroup record) {
		return this.userGroupClient.save(record);
	}

	@Override
	public int deleteByIds(List<Long> ids) {
		return this.userGroupClient.deleteByIds(ids);
	}

	@Override
	public int updateByPrimaryKey(ReqUserGroup record) {
		return this.userGroupClient.update(record);
	}

	@Override
	public int updateStatus(Map<String, Object> map) {
		return this.userGroupClient.updateStatus(map);
	}

	@Override
	public ResUserGroup getByPrimarkey(Long id) {
		return this.userGroupClient.getByPrimarkey(id);
	}
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.userGroupClient.list(pageable);
	}
	
	@Override
	public List<ResUserGroup> findList(Map<String, Object> map) {
		return this.userGroupClient.findList(map);
	}
	
	
	@Override
	public int userInsert(ReqUserGroupDetail record) {
		return this.userGroupClient.userSave(record);
	}

	@Override
	public int userDeleteByIds(List<Long> ids) {
		return this.userGroupClient.deleteUserByIds(ids);
	}

	@Override
	public ViewPage userFindPage(ViewPageable pageable) {
		return this.userGroupClient.userList(pageable);
	}


	@Override
	public int inputInsert(ReqUserGroupInput record) {
		return this.userGroupClient.inputSave(record);
	}
	
	@Override
	public int inputUpdateByPrimaryKey(ReqUserGroupInput record) {
		return this.userGroupClient.inputUpdate(record);
	};
	
	@Override
	public int inputDeleteByIds(List<Long> ids) {
		return this.userGroupClient.inputDeleteByIds(ids);
	}

	@Override
	public ViewPage inputFindPage(ViewPageable pageable) {
		return this.userGroupClient.inputList(pageable);
	}

	@Override
	public boolean inputExists(ReqUserGroupInput reqUserGroupInput) {
		return this.userGroupClient.inputExists(reqUserGroupInput);
	}

	@Override
	public boolean syncByUserGroupId(Long userGroupId) {
		return this.userGroupClient.syncByUserGroupId(userGroupId);
	}

	@Override
	public int inputSaveByUserIds(Map<String, Object> map) {
		return this.userGroupClient.inputSaveByUserIds(map);
	}

	@Override
	public int inputDeleteAndUserByIds(List<Long> ids) {
		return this.userGroupClient.inputDeleteAndUserByIds(ids);
	}

	@Override
	public ViewPage pageUserByNotInUserGroup(ViewPageable pageable) {
		return this.userGroupClient.pageUserByNotInUserGroup(pageable);
	}

}
