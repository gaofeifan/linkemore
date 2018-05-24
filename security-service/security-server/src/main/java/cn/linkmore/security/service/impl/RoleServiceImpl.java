package cn.linkmore.security.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.dao.cluster.DictClusterMapper;
import cn.linkmore.security.dao.cluster.PageClusterMapper;
import cn.linkmore.security.dao.cluster.PageElementClusterMapper;
import cn.linkmore.security.dao.cluster.RoleClusterMapper;
import cn.linkmore.security.dao.cluster.RoleElementClusterMapper;
import cn.linkmore.security.dao.cluster.RolePageClusterMapper;
import cn.linkmore.security.dao.master.PageElementMasterMapper;
import cn.linkmore.security.dao.master.RoleElementMasterMapper;
import cn.linkmore.security.dao.master.RoleMasterMapper;
import cn.linkmore.security.dao.master.RolePageMasterMapper;
import cn.linkmore.security.entity.Role;
import cn.linkmore.security.entity.RoleElement;
import cn.linkmore.security.entity.RolePage;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.response.ResDict;
import cn.linkmore.security.response.ResPage;
import cn.linkmore.security.response.ResPageElement;
import cn.linkmore.security.response.ResRoleElement;
import cn.linkmore.security.response.ResRolePage;
import cn.linkmore.security.service.RoleService;
import cn.linkmore.util.DomainUtil;


/**
 * Service实现类 -权限模块 - 日志信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleClusterMapper roleClusterMapper;
	
	@Autowired
	private RoleMasterMapper roleMasterMapper;
	
	@Autowired
	private DictClusterMapper dictClusterMapper;
	
	@Autowired
	private PageClusterMapper  pageClusterMapper; 
	
	@Autowired
	private RolePageClusterMapper rolePageClusterMapper;
	
	@Autowired
	private RolePageMasterMapper rolePageMasterMapper;
	
	@Autowired
	private RoleElementClusterMapper roleElementClusterMapper;
	
	@Autowired
	private PageElementClusterMapper  pageElementClusterMapper;
	
	@Autowired
	private RoleElementMasterMapper roleElementMasterMapper;
	
	@Autowired
	private PageElementMasterMapper  pageElementMasterMapper;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) { 
		Map<String,Object> param = new HashMap<String,Object>(); 
		List<ViewFilter> filters = pageable.getFilters();
		if(StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if(filters!=null&&filters.size()>0) {
			for(ViewFilter filter:filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		if(StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.roleClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<Role> list = this.roleClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public void save(Role role) {
		role.setCreateTime(new Date());
		this.roleMasterMapper.save(role);
	}
	
	@Override
	public Role update(Role role) {
		this.roleMasterMapper.update(role);
		return role;
	}

	@Override
	public int delete(List<Long> ids) {
		return this.roleMasterMapper.delete(ids); 
	}
	
	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.roleClusterMapper.check(reqCheck); 
	}

	@Override
	public Tree findTree() {
		List<ResPage> list =  this.pageClusterMapper.findAll();
		List<ResPageElement> pes= this.pageElementClusterMapper.findAll(); 
		List<ResDict> dicts = this.dictClusterMapper.findByGroupCode("security-page-category");
		List<Tree> trees = new ArrayList<Tree>();
		Map<Long,Tree> treeMap = new HashMap<Long,Tree>();
		Tree tree  =null;
		for(ResDict di:dicts) { 
			tree = new Tree(); 
			tree.setId("d"+di.getId().toString());
			tree.setName(di.getName());
			tree.setIsParent(true);
			tree.setCode(di.getCode());
			tree.setmId(di.getId().toString());
			tree.setOpen(true);
			tree.setChildren(new ArrayList<Tree>());
			trees.add(tree); 
			treeMap.put(di.getId(), tree);
		} 
		Tree child = null;
		Map<Long,Tree> childMap = new HashMap<Long,Tree>();
		for(ResPage page:list) {
			tree = treeMap.get(page.getCategoryId());
			if(tree==null) {
				continue;
			}
			child = new Tree();
			child.setId("p"+page.getId().toString());
			child.setpId(tree.getId());
			child.setName(page.getName());
			child.setIsParent(false);
			child.setCode(page.getId().toString());
			child.setmId(page.getId().toString()); 
			child.setChildren(new ArrayList<Tree>());
			tree.getChildren().add(child);
			childMap.put(page.getId(), child);
		}
		for(ResPageElement pe:pes) {
			tree = childMap.get(pe.getPageId());
			if(tree==null) {
				continue;
			}
			child = new Tree();
			child.setId("e"+pe.getId().toString());
			child.setpId(tree.getId());
			child.setName(pe.getName()); 
			child.setIsParent(false);
			child.setCode(pe.getId().toString());
			child.setmId(pe.getId().toString()); 
			child.setChildren(new ArrayList<Tree>());
			tree.getChildren().add(child); 
			tree.setIsParent(true);
		}
		Tree root = new Tree();
		root.setName("系统页面资源树");
		root.setId("0");
		root.setIsParent(false);
		root.setCode("0");
		root.setOpen(true);
		root.setmId("0"); 
		root.setChildren(trees);
		return root;
	}

	@Override
	public Map<String, Object> resource(Long id) { 
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("roleId", id);
		List<ResRoleElement> res = this.roleElementClusterMapper.findList(param);
		List<ResRolePage> rps = this.rolePageClusterMapper.findList(param);
		param.put("res", res);
		param.put("rps", rps);
		return param;
	}

	@Override
	public void bind(Long id, String pids, String eids) {
		String[] pageIds = pids.split(",");
		String[] elementIds = eids.split(",");
		RolePage rp = null;
		this.roleElementMasterMapper.delete(id);
		this.rolePageMasterMapper.delete(id);
		List<RolePage> rps = new ArrayList<RolePage>();
		for(String sid:pageIds) {
			rp = new RolePage();
			rp.setRoleId(id);
			rp.setPageId(Long.valueOf(sid));
			rps.add(rp);
		}
		if(rps.size()>0){
			this.rolePageMasterMapper.batchSave(rps);
		} 
		RoleElement re = null;
		List<RoleElement> res = new ArrayList<RoleElement>();
		for(String sid:elementIds) {
			re = new RoleElement();
			re.setRoleId(id);
			re.setElementId(Long.valueOf(sid));
			res.add(re); 
		}
		if(res.size()>0){
			this.roleElementMasterMapper.batchSave(res);
		} 
	}
}
