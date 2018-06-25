package cn.linkmore.account.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.UserAppfansClusterMapper;
import cn.linkmore.account.dao.master.AccountMasterMapper;
import cn.linkmore.account.dao.master.UserAppfansMasterMapper;
import cn.linkmore.account.entity.Account;
import cn.linkmore.account.entity.User;
import cn.linkmore.account.entity.UserAppfans;
import cn.linkmore.account.request.ReqUserAppfans;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResUserAppfans;
import cn.linkmore.account.response.ResUserLogin;
import cn.linkmore.account.service.UserAppfansService;
import cn.linkmore.account.service.UserService;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.util.JsonUtil;
@Service()
public class UserAppfansServiceImpl implements UserAppfansService {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource
	private AccountMasterMapper accountMasterMapper;
	@Resource
	private UserAppfansClusterMapper userAppfansClusterMapper;
	@Resource
	private UserAppfansMasterMapper userAppfansMasterMapper;
	@Resource
	@Lazy
	private UserService userService;
	@Override
	public UserAppfans findById(String id) {
		return this.userAppfansClusterMapper.findById(id);
	}

	@Override
	public ResUserAppfans findByUserId(Long userId) {
		ResUserAppfans userAppfans = this.userAppfansClusterMapper.findByUserId(userId);
		return userAppfans;
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

	

	@Override
	public void deleteByUserId(Long userId) {
		this.userAppfansMasterMapper.deleteByUserId(userId);
	}
	
	
	
	
}
