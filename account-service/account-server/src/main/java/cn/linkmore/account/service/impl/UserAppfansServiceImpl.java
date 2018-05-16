package cn.linkmore.account.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.UserAppfansClusterMapper;
import cn.linkmore.account.dao.master.UserAppfansMasterMapper;
import cn.linkmore.account.entity.UserAppfans;
import cn.linkmore.account.service.UserAppfansService;
@Service
public class UserAppfansServiceImpl implements UserAppfansService {

	@Resource
	private UserAppfansClusterMapper userAppfansClusterMapper;
	@Resource
	private UserAppfansMasterMapper userAppfansMasterMapper;
	
	@Override
	public UserAppfans selectById(String id) {
		return this.selectById(id);
	}

	@Override
	public UserAppfans selectByUserId(Long userId) {
		return this.selectByUserId(userId);
	}

	@Override
	public void insertSelective(UserAppfans record) {
		this.userAppfansMasterMapper.insertSelective(record);
	}
	
	@Override
	public void updateByIdSelective(UserAppfans record) {
		this.userAppfansMasterMapper.updateByIdSelective(record);
	}

	@Override
	public void updateStatusByUserId(Long userId, int status) {
		this.userAppfansMasterMapper.updateStatusByUserId(userId, status);
	}
	
	
	
}
