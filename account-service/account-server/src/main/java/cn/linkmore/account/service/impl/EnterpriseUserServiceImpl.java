package cn.linkmore.account.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.EnterpriseUserClusterMapper;
import cn.linkmore.account.dao.master.EnterpriseUserMasterMapper;
import cn.linkmore.account.entity.EnterpriseUser;
import cn.linkmore.account.response.ResEnterpriseUser;
import cn.linkmore.account.service.EnterpriseUserService;
import cn.linkmore.util.ObjectUtils;
@Service
public class EnterpriseUserServiceImpl implements EnterpriseUserService {

	@Resource
	private EnterpriseUserClusterMapper enterpriseUserClusterMapper;
	@Resource
	private EnterpriseUserMasterMapper enterpriseUserMasterMapper;
	
	@Override
	public ResEnterpriseUser selectByUserId(Long userId){
		 EnterpriseUser user = this.enterpriseUserClusterMapper.selectByUserId(userId);
		 return ObjectUtils.copyObject(user, new ResEnterpriseUser());
	}
}
