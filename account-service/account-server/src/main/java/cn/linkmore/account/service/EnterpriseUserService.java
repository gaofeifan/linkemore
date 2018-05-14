package cn.linkmore.account.service;

import java.util.List;

import cn.linkmore.account.entity.EnterpriseUser;

public interface EnterpriseUserService {

	List<EnterpriseUser> selectByUserId(Long userId);

}
