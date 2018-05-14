package cn.linkmore.account.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.EnterpriseUserClusterMapper;
import cn.linkmore.account.dao.master.EnterpriseUserMasterMapper;
import cn.linkmore.account.entity.EnterpriseUser;
import cn.linkmore.account.service.EnterpriseUserService;
@Service
public class EnterpriseUserServiceImpl implements EnterpriseUserService {

	@Resource
	private EnterpriseUserClusterMapper enterpriseUserClusterMapper;
	@Resource
	private EnterpriseUserMasterMapper enterpriseUserMasterMapper;
	
	@Override
	public List<EnterpriseUser> selectByUserId(Long userId){
		return this.enterpriseUserClusterMapper.selectByUserId(userId);
	}
}
