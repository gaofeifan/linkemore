package cn.linkmore.account.client.hystrix;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.account.client.UserGroupClient;
import cn.linkmore.account.request.ReqUserGroup;
import cn.linkmore.account.request.ReqUserGroupDetail;
import cn.linkmore.account.request.ReqUserGroupInput;
import cn.linkmore.account.response.ResUserGroup;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
@Component
public class UserGroupClientHystrix implements UserGroupClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public int save(ReqUserGroup reqUserGroup) {
		log.info("userGroup service save(ReqUserGroup reqUserGroup) hystrix");
		return 0;
	}

	@Override
	public int update(ReqUserGroup reqUserGroup) {
		log.info("userGroup service update(ReqUserGroup reqUserGroup) hystrix");
		return 0;
	}

	@Override
	public int deleteByIds(List<Long> ids) {
		log.info("userGroup service deleteByIds(List<Long> ids) hystrix");
		return 0;
	}

	@Override
	public int updateStatus(Map<String, Object> map) {
		log.info("userGroup service updateStatus(Map<String, Object> map) hystrix");
		return 0;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("userGroup service list(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public List<ResUserGroup> findList(Map<String, Object> map) {
		log.info("userGroup service findList(Map<String, Object> map) hystrix");
		return null;
	}

	@Override
	public ResUserGroup getByPrimarkey(Long id) {
		log.info("userGroup service getByPrimarkey(Long id) hystrix");
		return null;
	}

	@Override
	public ViewPage userList(ViewPageable pageable) {
		log.info("userGroup service userList(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public int userSave(ReqUserGroupDetail reqUserGroupDetail) {
		log.info("userGroup service userSave(ReqUserGroupDetail reqUserGroupDetail) hystrix");
		return 0;
	}

	@Override
	public int deleteUserByIds(List<Long> ids) {
		log.info("userGroup service deleteUserByIds(List<Long> ids) hystrix");
		return 0;
	}


	@Override
	public ViewPage inputList(ViewPageable pageable) {
		log.info("userGroup service inputList(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public int inputSave(ReqUserGroupInput reqUserGroupInput) {
		log.info("userGroup service inputSave(ReqUserGroupInput reqUserGroupInput hystrix");
		return 0;
	}

	@Override
	public int inputUpdate(ReqUserGroupInput reqUserGroupInput) {
		log.info("userGroup service inputuUpdate(ReqUserGroupInput reqUserGroupInput) hystrix");
		return 0;
	}

	@Override
	public int inputDeleteByIds(List<Long> ids) {
		log.info("userGroup service inputDeleteByIds(List<Long> ids) hystrix");
		return 0;
	}

	@Override
	public boolean inputExists(ReqUserGroupInput reqUserGroupInput) {
		log.info("userGroup service inputExists(ReqUserGroupInput reqUserGroupInput) hystrix");
		return false;
	}

	@Override
	public boolean syncByUserGroupId(Long userGroupId) {
		log.info("userGroup service syncByUserGroupId(Long userGroupId) hystrix");
		return false;
	}

	@Override
	public boolean inputSyncByUserId(Long UserId) {
		log.info("userGroup service inputSyncByUserId(Long UserId) hystrix");
		return false;
	}

	@Override
	public boolean inputSyncByPlate(String plate) {
		log.info("userGroup service inputSyncByPlate(String plate) hystrix");
		return false;
	}

	@Override
	public int inputSaveByUserIds(Map<String, Object> map) {
		log.info("userGroup service inputSaveByUserIds(Map<String, Object> map) hystrix");
		return 0;
	}

	@Override
	public int inputDeleteAndUserByIds(List<Long> ids) {
		log.info("userGroup service inputDeleteAndUserByIds(List<Long> ids) hystrix");
		return 0;
	}

	@Override
	public ViewPage pageUserByNotInUserGroup(ViewPageable pageable) {
		log.info("userGroup service pageUserByNotInUserGroup(ViewPageable pageable) hystrix");
		return null;
	}

}
