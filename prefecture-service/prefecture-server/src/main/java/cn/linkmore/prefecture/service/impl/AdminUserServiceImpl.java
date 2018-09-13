package cn.linkmore.prefecture.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.dao.cluster.AdminAuthClusterMapper;
import cn.linkmore.prefecture.dao.cluster.AdminUserAuthClusterMapper;
import cn.linkmore.prefecture.dao.cluster.AdminUserClusterMapper;
import cn.linkmore.prefecture.dao.master.AdminUserAuthMasterMapper;
import cn.linkmore.prefecture.dao.master.AdminUserMasterMapper;
import cn.linkmore.prefecture.entity.AdminUser;
import cn.linkmore.prefecture.entity.AdminUserAuth;
import cn.linkmore.prefecture.request.ReqAdminUser;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.response.ResAdmin;
import cn.linkmore.prefecture.response.ResAdminAuth;
import cn.linkmore.prefecture.response.ResAdminUser;
import cn.linkmore.prefecture.response.ResAdminUserAuth;
import cn.linkmore.prefecture.service.AdminUserService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
/**
 * 
 * Service实现类 - 线下管理员
 * @author jiaohanbin
 * @version 2.0
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {
	@Autowired
	private AdminUserClusterMapper adminUserClusterMapper;
	@Autowired
	private AdminAuthClusterMapper adminAuthClusterMapper;
	@Autowired
	private AdminUserAuthClusterMapper adminUserAuthClusterMapper;
	
	@Autowired
	private AdminUserMasterMapper adminUserMasterMapper;
	@Autowired
	private AdminUserAuthMasterMapper adminUserAuthMasterMapper;
	
	

	private static final String ADMIN = "admin-";
	/*
	 * 管理员列表
	 */
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
		Integer count = this.adminUserClusterMapper.count(param);
		List<ResAdminUser> list = new ArrayList<ResAdminUser>();
		if(count>0){
			param.put("start", pageable.getStart());
			param.put("pageSize", pageable.getPageSize());
			list = this.adminUserClusterMapper.findPage(param);
		}
		return new ViewPage(count,pageable.getPageSize(),list);
	}
	
	/*
	 * 检查手机号重复
	 */
	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.adminUserClusterMapper.check(param); 
	}
	/*
	 * 保存
	 */
	@Override
	public int save(ReqAdminUser admin) {
		admin.setCreateTime(new Date());
		admin.setUpdateTime(new Date());
		AdminUser adminUser = new AdminUser();
		adminUser = ObjectUtils.copyObject(admin, adminUser);
		return this.adminUserMasterMapper.save(adminUser);
	}
	
	@Override
	public int update(ReqAdminUser admin) {
		admin.setUpdateTime(new Date());
		AdminUser adminUser = new AdminUser();
		adminUser = ObjectUtils.copyObject(admin, adminUser);
		return this.adminUserMasterMapper.update(adminUser);
	}
	/*
	 * 资源树
	 */
	@Override
	public Tree findTree() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", "create_time");
		param.put("direction", "asc");
		List<ResAdminAuth> list = this.adminAuthClusterMapper.findList(param);
		List<Tree> trees = new ArrayList<Tree>();
		Tree root = new Tree();
		root.setName("权限区授权树");
		root.setId("0");
		root.setIsParent(false);
		root.setCode("0");
		root.setOpen(true);
		root.setmId("0"); 
		root.setChildren(trees);
		Tree tree = null;
		List<Tree> children = new ArrayList<Tree>();
		for (ResAdminAuth auth : list) {
			tree = new Tree();
			tree.setName(auth.getName());
			tree.setCode(auth.getId().toString());
			tree.setmId(auth.getId().toString());
			tree.setId(auth.getId().toString());
			tree.setIsParent(false); 
			children.add(tree);
		}
		root.setChildren(children);
		return root;
	}

	@Override
	public ResAdminUser find(Long id) {
		return this.adminUserClusterMapper.findById(id);
	}
	
	@Override
	public int delete(List<Long> ids) {
		for (Long id : ids) {
			this.adminUserAuthMasterMapper.deleteByUserId(id);
		}
		return this.adminUserMasterMapper.delete(ids);
	}

	/*
	 * 绑定
	 */
	@Override
	public void bind(Long id, String authids) {
		String[] aIds = authids.split(",");
		AdminUserAuth userAuth = null;
		//根据userId删除旧的信息
		this.adminUserAuthMasterMapper.deleteByUserId(id);
		if(StringUtils.isNotBlank(authids)){
			for (String authid : aIds) {
				userAuth = new AdminUserAuth();
				userAuth.setUserId(id);
				userAuth.setAuthId(Long.valueOf(authid));
				this.adminUserAuthMasterMapper.save(userAuth);
			}
		}
	}
	/*
	 * 权限回显
	 */
	@Override
	public Map<String, Object> resource(Long id) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", id);
		List<ResAdminUserAuth> userAuths = this.adminUserAuthClusterMapper.findList(param);
		param.put("userAuths", userAuths);
		return param;
	}

	@Override
	public List<ResAdminUser> findAll() {
		return this.adminUserClusterMapper.findAll();
	}

	@Override
	public ResAdminUser findMobile(String mobile) {
		return this.adminUserClusterMapper.findByMobile(mobile);
	}

	@Override
	public void updateLoginTime(Long id) {
		Map<String,Object> map = new HashMap<>();
		map.put("id", id);
		map.put("loginTime", new Date());
		this.adminUserMasterMapper.updateLoginTime(map);
	}

	@Override
	public ResAdmin authLogin(String mobile) {
		ResAdminUser user = this.findMobile(mobile);
		ResAdmin admin = new ResAdmin();
		admin.setId(user.getId());
		admin.setCellphone(user.getCellphone());
		admin.setCreateTime(user.getCreateTime());
		admin.setLoginTime(user.getLoginTime());
		admin.setRealname(user.getRealname());
		admin.setUpdateTime(user.getUpdateTime());
		admin.setStatus(user.getStatus());
		Map<String, Object> map = new HashMap<>();
		List<ResAdminAuth> list = this.adminAuthClusterMapper.findList(map);
		this.adminUserAuthClusterMapper.findList(map);
		for (ResAdminAuth resAdminAuth : list) {
			if(resAdminAuth.getCode().contains(ADMIN)) {
				admin.setIsOperate(true);
				if(resAdminAuth.getCode().equals(ResAdmin.ADMIN_ALL)) {
					admin.setType(ResAdmin.ADMIN_ALL);
					admin.setCode(ResAdmin.ADMIN_ALL_CODE);
				}
			}
		}
		return admin;
	}             	

	
	
}
