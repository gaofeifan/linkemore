package cn.linkmore.account.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.StaffAppfansClusterMapper;
import cn.linkmore.account.dao.master.AccountMasterMapper;
import cn.linkmore.account.dao.master.StaffAppfansMasterMapper;
import cn.linkmore.account.entity.StaffAppfans;
import cn.linkmore.account.service.StaffAppfansService;
import cn.linkmore.account.service.UserService;
@Service
public class StaffAppfansServiceImpl implements StaffAppfansService {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource
	private AccountMasterMapper accountMasterMapper;
	@Resource
	private StaffAppfansClusterMapper staffAppfansClusterMapper;
	@Resource
	private StaffAppfansMasterMapper staffAppfansMasterMapper;
	@Resource
	@Lazy
	private UserService userService;
	@Override
	public StaffAppfans findById(String id) {
		return this.staffAppfansClusterMapper.findById(id);
	}

//	@Override
//	public ResUserAppfans findByUserId(Long userId) {
//		ResUserAppfans userAppfans = this.userAppfansClusterMapper.findByUserId(userId);
//		return userAppfans;
//	}

	@Override
	public void insertSelective(StaffAppfans record) {
		this.staffAppfansMasterMapper.insertSelective(record);
	}
	
	@Override
	public void updateByIdSelective(StaffAppfans record) {
		this.staffAppfansMasterMapper.updateByIdSelective(record);
	}

	@Override
	public void updateStatusByUserId(Long userId, int status) {
		this.staffAppfansMasterMapper.updateStatusByUserId(userId, status);
	}

	

	@Override
	public void deleteByUserId(Long userId) {
		this.staffAppfansMasterMapper.deleteByUserId(userId);
	}
	
	
	
	
}
