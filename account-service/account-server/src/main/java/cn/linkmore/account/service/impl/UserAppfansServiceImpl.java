package cn.linkmore.account.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.UserAppfansClusterMapper;
import cn.linkmore.account.dao.master.AccountMasterMapper;
import cn.linkmore.account.dao.master.UserAppfansMasterMapper;
import cn.linkmore.account.entity.Account;
import cn.linkmore.account.entity.User;
import cn.linkmore.account.entity.UserAppfans;
import cn.linkmore.account.request.ReqUserAppfans;
import cn.linkmore.account.response.ResUserAppfans;
import cn.linkmore.account.response.ResUserLogin;
import cn.linkmore.account.service.UserAppfansService;
import cn.linkmore.account.service.UserService;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.util.ObjectUtils;
@Service
public class UserAppfansServiceImpl implements UserAppfansService {

	@Resource
	private AccountMasterMapper accountMasterMapper;
	@Resource
	private UserAppfansClusterMapper userAppfansClusterMapper;
	@Resource
	private UserAppfansMasterMapper userAppfansMasterMapper;
	@Resource
	private UserService userService;
	@Override
	public UserAppfans selectById(String id) {
		return this.selectById(id);
	}

	@Override
	public ResUserAppfans selectByUserId(Long userId) {
		UserAppfans userAppfans = this.userAppfansClusterMapper.selectByUserId(userId);
		return ObjectUtils.copyObject(userAppfans, new ResUserAppfans());
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
	public ResUserLogin wxLogin(ReqUserAppfans userAppfans) {
		UserAppfans fans = this.userAppfansClusterMapper.selectById(userAppfans.getId());
		if(fans==null){
			fans = new UserAppfans();
			fans.setId(userAppfans.getId());
			fans.setHeadurl(userAppfans.getHeadurl());
			fans.setNickname(userAppfans.getNickname());
			fans.setUnionid(userAppfans.getUnionid());
			fans.setCreateTime(new Date());
			fans.setStatus((short)1);
			fans.setRegisterStatus((short)0);
			this.userAppfansMasterMapper.insertSelective(fans);  
		}else{
			fans.setStatus((short)1); 
			this.userAppfansMasterMapper.updateByIdSelective(fans);  
		}  
		User user = null;
		if(fans.getUserId()!=null){
			user = this.userService.selectById(fans.getUserId());
		}
		if(user==null){
			user = this.userService.selectByMobile(fans.getId());
			if(user!=null){
				fans.setUserId(user.getId()); 
				this.userAppfansMasterMapper.updateByIdSelective(fans);
			}
		}
		if(user==null){
			user = new User();
			user.setMobile(fans.getId());
			user.setUsername(fans.getId());
			user.setNickname(userAppfans.getNickname());
			user.setPassword("");
			user.setUserType("1");
			user.setStatus("1"); 
			user.setLastLoginTime(new Date());
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			user.setIsAppRegister((short)1);
			user.setAppRegisterTime(new Date());
			user.setIsWechatBind((short)0);
			this.userService.insertSelective(user);
			Account account = new Account();
			account.setId(user.getId());
			account.setAmount(0.00d);
			account.setUsableAmount(0.00d);
			account.setFrozenAmount(0.00d);
			account.setRechagePaymentAmount(0.00d);
			account.setRechargeAmount(0.00d);
			account.setAccType(1);
			account.setStatus((short) 1);
			account.setOrderAmount(0.00d);
			account.setOrderPaymentAmount(0.00d);
			account.setCreateTime(new Date());
			accountMasterMapper.insertSelective(account);
			fans.setUserId(user.getId());
			this.userAppfansMasterMapper.updateByIdSelective(fans);
		}  else if(user.getStatus().equals("2")){
			throw new BusinessException();
		} else {
			user.setLastLoginTime(new Date());
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("id", user.getId());
			param.put("lastLoginTime", new Date());
			param.put("updateTime", new Date());
			this.userService.updateLoginTime(param);
		}
		ResUserLogin urb = new ResUserLogin();
		urb.setId(user.getId());
		urb.setMobile(user.getUsername());
		return urb;
	}
	
	
	
	
}
