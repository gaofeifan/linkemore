package cn.linkmore.prefecture.service;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.prefecture.dao.cluster.AdminUserPackClusterMapper;
import cn.linkmore.prefecture.dao.master.AdminUserPackMasterMapper;

/**
 * @author   GFF
 * @Date     2018年9月17日
 * @Version  v2.0
 */
@Service
public class StaffAdminUserPackServiceImpl implements StaffAdminUserPackService {

	@Resource
	private AdminUserPackClusterMapper AdminUserPackClusterMapper;
	@Resource
	private AdminUserPackMasterMapper AdminUserPackMasterMapper;
	
	@Override
	public HashMap<String, Object> findByAdminId(Long adminId) {
		return this.AdminUserPackClusterMapper.findByAdminId(adminId);
	}
	
	
}
