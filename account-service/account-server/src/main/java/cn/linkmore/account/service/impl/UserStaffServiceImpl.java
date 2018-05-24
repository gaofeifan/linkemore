package cn.linkmore.account.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.UserStaffClusterMapper;
import cn.linkmore.account.response.ResUserStaff;
import cn.linkmore.account.service.UserStaffService;
/**
 * @author   GFF
 * @Date     2018年5月17日
 * @Version  v2.0
 */
@Service
public class UserStaffServiceImpl implements UserStaffService {

	@Resource
	private UserStaffClusterMapper userStaffClusterMapper;

	@Override
	public ResUserStaff findById(Long userId) {
		return userStaffClusterMapper.findById(userId);
	}
	
}
