package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.enterprise.dao.cluster.EntOperateAuthClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntStaffAuthClusterMapper;
import cn.linkmore.enterprise.dao.master.EntStaffAuthMasterMapper;
import cn.linkmore.enterprise.entity.EntOperateAuth;
import cn.linkmore.enterprise.entity.EntStaffAuth;
import cn.linkmore.enterprise.request.ReqStaffAuthBind;
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
	private EntOperateAuthClusterMapper entOperateAuthClusterMapper;
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
	public Tree tree() {
		Map<String,Object> map = new HashMap<>();
		map.put("status", 1);
		List<EntOperateAuth> auths = this.entOperateAuthClusterMapper.findList(map);
		Tree root = new Tree();
		root.setName("权限树");
		root.setId("0");
		root.setIsParent(false);
		root.setCode("0");
		root.setOpen(true);
		root.setmId("0");
		List<Tree> chiList = new ArrayList<>();
		Tree chi = null;
		for (EntOperateAuth auth : auths) {
			chi = new Tree();
			chi.setName(auth.getName());
			chi.setId(auth.getId().toString());
			chi.setIsParent(false);
			chi.setCode(auth.getId().toString());
			chi.setOpen(true);
			chi.setmId(auth.getId().toString());
			chiList.add(chi);
		}
		root.setChildren(chiList);
		return root;
	}

	@Override
	public Map<String, Object> resouce(Long staffId) {
		Map<String ,Object> map = new HashMap<>();
		map.put("staffId", staffId);
		List<EntOperateAuth> list = this.entOperateAuthClusterMapper.findList(map);
		map.put("authIds", list);
		return map;
	}
}
