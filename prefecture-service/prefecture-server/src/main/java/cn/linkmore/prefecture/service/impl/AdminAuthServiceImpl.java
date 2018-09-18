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
import cn.linkmore.common.client.CityClient;
import cn.linkmore.common.response.ResCity;
import cn.linkmore.prefecture.dao.cluster.AdminAuthCityClusterMapper;
import cn.linkmore.prefecture.dao.cluster.AdminAuthClusterMapper;
import cn.linkmore.prefecture.dao.cluster.AdminAuthStallClusterMapper;
import cn.linkmore.prefecture.dao.cluster.PrefectureClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StallClusterMapper;
import cn.linkmore.prefecture.dao.master.AdminAuthCityMasterMapper;
import cn.linkmore.prefecture.dao.master.AdminAuthMasterMapper;
import cn.linkmore.prefecture.dao.master.AdminAuthPreMasterMapper;
import cn.linkmore.prefecture.dao.master.AdminAuthStallMasterMapper;
import cn.linkmore.prefecture.dao.master.AdminUserAuthMasterMapper;
import cn.linkmore.prefecture.entity.AdminAuth;
import cn.linkmore.prefecture.entity.AdminAuthCity;
import cn.linkmore.prefecture.entity.AdminAuthPre;
import cn.linkmore.prefecture.entity.AdminAuthStall;
import cn.linkmore.prefecture.entity.Prefecture;
import cn.linkmore.prefecture.entity.Stall;
import cn.linkmore.prefecture.request.ReqAdminAuth;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.response.ResAdminAuth;
import cn.linkmore.prefecture.response.ResAdminAuthStall;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResStaffCity;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.service.AdminAuthService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
import net.sf.json.JSONArray;

/**
 * 
 * Service实现类 - 线下管理员 授权
 * @author jiaohanbin
 * @version 2.0
 */
@Service
public class AdminAuthServiceImpl implements AdminAuthService {
	@Autowired
	private AdminAuthClusterMapper adminAuthClusterMapper;
	@Autowired
	private StallClusterMapper stallClusterMapper;
	@Autowired
	private PrefectureClusterMapper prefectureClusterMapper;
	@Autowired
	private AdminAuthStallClusterMapper adminAuthStallClusterMapper;
	@Autowired
	private AdminAuthMasterMapper adminAuthMasterMapper;
	@Autowired
	private AdminUserAuthMasterMapper adminUserAuthMasterMapper;
	@Autowired
	private AdminAuthCityMasterMapper adminAuthCityMasterMapper;
	@Autowired
	private AdminAuthCityClusterMapper adminAuthCityClusterMapper;
	@Autowired
	private AdminAuthPreMasterMapper adminAuthPreMasterMapper;
	@Autowired
	private AdminAuthStallMasterMapper adminAuthStallMasterMapper;
	@Autowired
	private CityClient cityClient;
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
		Integer count = this.adminAuthClusterMapper.count(param);
		List<ResAdminAuth> list = new ArrayList<ResAdminAuth>();
		if(count>0){
			param.put("start", pageable.getStart());
			param.put("pageSize", pageable.getPageSize());
			list = this.adminAuthClusterMapper.findPage(param);
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
		return this.adminAuthClusterMapper.check(param); 
	}
	/*
	 * 保存
	 */
	@Override
	public int save(ReqAdminAuth admin) {
		admin.setCreateTime(new Date());
		admin.setUpdateTime(new Date());
		
		AdminAuth adminAuth = new AdminAuth();
		adminAuth = ObjectUtils.copyObject(admin, adminAuth);
		return this.adminAuthMasterMapper.save(adminAuth);
	}
	
	@Override
	public int update(ReqAdminAuth admin) {
		admin.setUpdateTime(new Date());
		AdminAuth adminAuth = new AdminAuth();
		adminAuth = ObjectUtils.copyObject(admin, adminAuth);
		return this.adminAuthMasterMapper.update(adminAuth);
	}
	/*
	 * 资源树
	 */
	@Override
	public Tree findTree() {
		List<ResCity> citys = this.cityClient.list(0, 50);
		List<ResPrefectureDetail> pres = prefectureClusterMapper.findAll();
		List<Stall> stalls = this.stallClusterMapper.findAll();
		
		Tree root = new Tree();
		root.setName("车位授权树");
		root.setId("root");
		root.setOpen(true);
		root.setpId(null);
		root.setChildren(new ArrayList<Tree>());
		Tree tree = null;
		List<Tree> children = new ArrayList<>();
		Map<Long,Tree> treeMap = new HashMap<Long,Tree>();
		for (ResCity city : citys) {
			tree = new Tree();
			tree.setId("city"+city.getId().toString());
			tree.setName(city.getName());
			tree.setCode(city.getId().toString());
			tree.setmId(city.getId().toString());
			tree.setOpen(true);
			tree.setChildren(new ArrayList<Tree>());
			tree.setIsParent(true);
			children.add(tree);
			treeMap.put(city.getId(), tree);
		}
		Tree child = null;
		List<Tree> children2 = new ArrayList<>();
		Map<Long,Tree> treeMap2 = new HashMap<Long,Tree>();
		for (ResPrefectureDetail pre : pres) {
			tree = treeMap.get(pre.getCityId());
			if(tree==null) {
				continue;
			}
			child = new Tree();
			child.setIsParent(false);
			child.setId("pre"+pre.getId().toString());
			child.setpId(tree.getId());
			child.setName(pre.getName());
			child.setCode(pre.getId().toString());
			child.setmId(pre.getId().toString()); 
			child.setChildren(new ArrayList<Tree>());
			tree.getChildren().add(child);
			children2.add(child);
			treeMap2.put(pre.getId(), child);
		}
		Tree child3 = null;
		for(Stall stall:stalls) {
			child = treeMap2.get(stall.getPreId());
			if(tree==null) {
				continue;
			}
			child3 = new Tree();
			child3.setId("stall"+stall.getId().toString());
			child3.setpId(tree.getId());
			child3.setName(stall.getStallName());
			child3.setIsParent(false);
			child3.setCode(stall.getId().toString());
			child3.setmId(stall.getId().toString()); 
			child.getChildren().add(child3);
		}
		root.setChildren(children);
		return root;
	}

	@Override
	public ResAdminAuth find(Long id) {
		return this.adminAuthClusterMapper.findById(id);
	}
	
	@Override
	public int delete(List<Long> ids) {
		for (Long authId : ids) {
			//删除授权车位表
			this.adminAuthStallMasterMapper.delete(authId);
		}
		//删除授权区表
		this.adminUserAuthMasterMapper.deleteByAuthId(ids);
		return this.adminAuthMasterMapper.delete(ids);
	}

	/*
	 * 绑定
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void bind(Long id, String json,String citys,String pres) {
		//绑定城市
		List<String> cityList = new ArrayList<String>(); 
		JSONArray citysArray = JSONArray.fromObject(citys);
		cityList = citysArray.subList(0, citysArray.size());
		adminAuthCityMasterMapper.deleteByAuthId(id);
		AdminAuthCity cc = new AdminAuthCity();
		if(cityList!=null&&cityList.size()>0){
			for (String oid : cityList) {
				cc.setCityId(Long.valueOf(oid));
				cc.setAuthId(id);
				adminAuthCityMasterMapper.save(cc);
			}
		}
		//绑定专区
		List<String> presList = new ArrayList<String>(); 
		JSONArray presArray = JSONArray.fromObject(pres);
		presList = presArray.subList(0, presArray.size());
		adminAuthPreMasterMapper.deleteByAuthId(id);
		AdminAuthPre pp = new AdminAuthPre();
		if(presList!=null&&presList.size()>0){
			for (String oid : presList) {
				pp.setPreId(Long.valueOf(oid));
				pp.setAuthId(id);
				adminAuthPreMasterMapper.save(pp);
			}
		}
		//绑定车位
		List<String> list = new ArrayList<String>(); 
		JSONArray jsonArray = JSONArray.fromObject(json);
		list = jsonArray.subList(0, jsonArray.size());  
		adminAuthStallMasterMapper.deleteByAuthId(id);
		AdminAuthStall aa = new AdminAuthStall();
		if(list!=null&&list.size()>0){
			for(String oid:list){
				aa.setStallId(Long.valueOf(oid));
				aa.setAuthId(id);
				adminAuthStallMasterMapper.save(aa);
			}
		}
	}
	/*
	 * 权限回显
	 */
	@Override
	public Map<String, Object> resource(Long id) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("authId", id);
		List<ResAdminAuthStall> adminAuthStalls = this.adminAuthStallClusterMapper.findList(param);
		List<Long> stallIds = new ArrayList<Long>();
		for (ResAdminAuthStall aas : adminAuthStalls) {
			stallIds.add(aas.getStallId());
		}
		if(stallIds.size() > 0){
			List<ResStall> stalls = this.stallClusterMapper.findTreeList(stallIds);
			
			param.put("adminAuthStalls", adminAuthStalls);
			param.put("stalls", stalls);
		}
		return param;
	}

	@Override
	public List<ResStaffCity> findStaffCitysByAdminId(Long id) {
		return this.adminAuthCityClusterMapper.findStaffCitysByAdminId(id);
	}

	
}
