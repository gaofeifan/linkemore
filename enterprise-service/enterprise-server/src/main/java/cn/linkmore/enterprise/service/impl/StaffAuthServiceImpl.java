package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.enterprise.dao.cluster.EntOperateAuthClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntStaffAuthClusterMapper;
import cn.linkmore.enterprise.dao.master.EntStaffAuthMasterMapper;
import cn.linkmore.enterprise.entity.EntOperateAuth;
import cn.linkmore.enterprise.entity.EntStaffAuth;
import cn.linkmore.enterprise.entity.Enterprise;
import cn.linkmore.enterprise.request.ReqStaffAuthBind;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.enterprise.service.EnterpriseService;
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
	private EnterpriseService enterpriseService;
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
	public List<Tree> tree() {
		List<Tree> roots = new ArrayList<>();
		Tree root = null;
		List<Tree> chiList = null;
		Tree chi = null;
		Map<String,Object> map = new HashMap<>();
		map.put("status", 1);
		List<EntOperateAuth> auths = this.entOperateAuthClusterMapper.findList(map);
		Set<Long> entIds = auths.stream().map(a -> a.getEntId()).collect(Collectors.toSet());
		List<Long> list = new ArrayList<>(entIds);
		List<ResEnterprise> ents = this.enterpriseService.findListByIds(list);
		for (ResEnterprise resEnterprise : ents) {
			root = new Tree();
			root.setName(resEnterprise.getName());
			root.setId(resEnterprise.getId().toString());
			root.setIsParent(false);
			root.setCode(resEnterprise.getId().toString());
			root.setOpen(true);
			root.setmId(resEnterprise.getId().toString());
			chiList = new ArrayList<>();
			for (EntOperateAuth auth : auths) {
				if(auth.getEntId().equals(resEnterprise.getId())) {
					chi = new Tree();
					chi.setName(auth.getName());
					chi.setId(auth.getId().toString());
					chi.setIsParent(false);
					chi.setCode(auth.getId().toString());
					chi.setOpen(true);
					chi.setmId(auth.getId().toString());
					chiList.add(chi);
				}
			}
			root.setChildren(chiList);
			roots.add(root);
			
		}
		return roots;
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
