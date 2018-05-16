package cn.linkmore.account.service;

import java.util.List;

import cn.linkmore.account.entity.EnterpriseUser;

public interface EnterpriseUserService {

	EnterpriseUser selectByUserId(Long userId);

}
