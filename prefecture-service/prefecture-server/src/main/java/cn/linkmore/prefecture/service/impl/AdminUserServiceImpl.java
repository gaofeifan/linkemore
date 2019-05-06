package cn.linkmore.prefecture.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
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
	private static final String ADMIN_STALL = "admin-stall";
	private static final String ADMIN_ALL = "admin-all";
	private static final String ADMIN_ORDER = "admin-order";
	private static final String ADMIN_ENT = "admin-ent";
	private static final String ADMIN_MANAGE = "admin-manage";
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
		adminUser.setPassword(Md5PW.md5(null, admin.getPassword()));
		return this.adminUserMasterMapper.save(adminUser);
	}
	
	@Override
	public int update(ReqAdminUser admin) {
		admin.setUpdateTime(new Date());
		AdminUser adminUser = new AdminUser();
		adminUser = ObjectUtils.copyObject(admin, adminUser);
		ResAdminUser user = this.find(admin.getId());
		if(org.apache.commons.lang3.StringUtils.isNoneBlank(adminUser.getPassword())) {
			if(org.apache.commons.lang3.StringUtils.isBlank(user.getPassword())) {
				adminUser.setPassword(Md5PW.md5(null, adminUser.getPassword()));
			}else if(!user.getPassword().equals(adminUser.getPassword())) {
				adminUser.setPassword(Md5PW.md5(null, adminUser.getPassword()));
			}
		}
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
		if(user != null) {
			ResAdmin admin = new ResAdmin();
			admin.setId(user.getId());
			admin.setCellphone(user.getCellphone());
			admin.setCreateTime(user.getCreateTime());
			admin.setLoginTime(user.getLoginTime());
			admin.setRealname(user.getRealname());
			admin.setAccountName(user.getAccountName());
			admin.setPassword(user.getPassword());
			admin.setGatewayDelete(user.getGatewayDelete());
			admin.setUpdateTime(user.getUpdateTime());
			admin.setStatus(user.getStatus());
			List<ResAdminAuth> list = this.adminAuthClusterMapper.findUserAuthByUserId(user.getId());
			admin.setType("2");
			admin.setCode(ADMIN_STALL);
			if(list != null && list.size() != 0) {
				 ResAdminAuth adminAuth = list.get(list.size()-1);
				 if( adminAuth.getCode()!= null) {
					 if( adminAuth.getCode().trim().equals(ADMIN_ALL) ) {
						 admin.setIsOperate(true);
						 admin.setCode(ADMIN_ALL);
						 admin.setType("1");
					 }else if( adminAuth.getCode().trim().equals(ADMIN_ORDER)) {
						 admin.setIsOperate(true);
						 admin.setCode(ADMIN_ORDER);
					 }else if(adminAuth.getCode().trim().equals(ADMIN_MANAGE)) {
						 admin.setType("1"); 
						 admin.setCode(ADMIN_MANAGE);
					 }else {
						 
					 }
				 }
			}
			return admin;
		}
		return null;
	}

	@Override
	public ResAdmin findAccountName(String accountName) {
		ResAdminUser user = this.adminUserClusterMapper.findAccountName(accountName);
		if(user == null) {
			return null;
		}
		ResAdmin admin = new ResAdmin();
		admin.setId(user.getId());
		admin.setCellphone(user.getCellphone());
		admin.setCreateTime(user.getCreateTime());
		admin.setLoginTime(user.getLoginTime());
		admin.setRealname(user.getRealname());
		admin.setAccountName(user.getAccountName());
		admin.setPassword(user.getPassword());
		admin.setGatewayDelete(user.getGatewayDelete());
		admin.setUpdateTime(user.getUpdateTime());
		admin.setStatus(user.getStatus());
		List<ResAdminAuth> list = this.adminAuthClusterMapper.findUserAuthByUserId(user.getId());
		admin.setType("2");
		admin.setCode(ADMIN_STALL);
//		this.adminUserAuthClusterMapper.findList(map);
		if(list != null && list.size() != 0) {
			 ResAdminAuth adminAuth = list.get(list.size()-1);
			 if( adminAuth.getCode()!= null) {
				 if( adminAuth.getCode().trim().equals(ADMIN_ALL) ) {
					 admin.setIsOperate(true);
					 admin.setCode(ADMIN_ALL);
					 admin.setType("1");
				 }else if( adminAuth.getCode().trim().equals(ADMIN_ORDER)) {
					 admin.setIsOperate(true);
					 admin.setCode(ADMIN_ORDER);
				 }else if(adminAuth.getCode().trim().equals(ADMIN_MANAGE)) {
					 admin.setType("1"); 
					 admin.setCode(ADMIN_MANAGE);
				 }else {
					 
				 }
			 }
		}
		return admin;
	}

	@Override
	public void updateMobile(Long id, String mobile) {
		this.adminUserMasterMapper.updateMobile(id,mobile);
	}

	@Override
	public void updatePw(Long id, String pw) {
		this.adminUserMasterMapper.updatePw(id,pw);
	}     
	
	
	public static void main(String[] args) {
		System.out.println(new Date().getTime());
		
	}
	
}
class Md5PW{
	private static final String LINKEMORE = "LINKEMORE";
	public static String md5(String mobile ,String password) {
		if(mobile == null) {
			mobile = "";
		}
		String hex = DigestUtils.md5Hex(LINKEMORE+mobile+password);
		return hex;
	}
}
