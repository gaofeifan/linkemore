package cn.linkmore.account.service;

import cn.linkmore.account.response.ResEnterpriseUser;

public interface EnterpriseUserService {

	ResEnterpriseUser selectByUserId(Long userId);

}
