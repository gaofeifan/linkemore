package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.enterprise.controller.ent.request.ReqStaffAuthBind;
import cn.linkmore.enterprise.dao.cluster.EntStaffAuthClusterMapper;
import cn.linkmore.enterprise.dao.master.EntStaffAuthMasterMapper;
import cn.linkmore.enterprise.entity.EntStaffAuth;
import cn.linkmore.enterprise.service.StaffAuthService;
/**
 * 员工接口实现
 * @author   GFF
 * @Date     2018年7月20日
 * @Version  v2.0
 */
@Service
public class StaffAuthServiceImpl implements StaffAuthService {

	@Resource
	private EntStaffAuthClusterMapper entStaffAuthClusterMapper;
	@Resource
	private EntStaffAuthMasterMapper entStaffAuthMasterMapper;
	
	@Override
	public void bind(ReqStaffAuthBind staff) {
		List<Long> ids = staff.getAuthIds();
		List<EntStaffAuth> list = new ArrayList<>();
		EntStaffAuth staffAuth = null;
		this.entStaffAuthMasterMapper.deleteByStaffId(staff.getStaffId());
		for (Long id : ids) {
			staffAuth = new EntStaffAuth();
			staffAuth.setAuthId(id);
			staffAuth.setStaffId(staff.getStaffId());
			list.add(staffAuth);
		}
		this.entStaffAuthMasterMapper.saveBatch(list);
	}

	@Override
	public void tree() {
		
	}

	
	
	
}
