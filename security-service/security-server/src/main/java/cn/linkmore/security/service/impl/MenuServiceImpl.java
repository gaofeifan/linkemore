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
import cn.linkmore.security.dao.cluster.MenuClusterMapper;
import cn.linkmore.security.dao.cluster.PageClusterMapper;
import cn.linkmore.security.dao.master.MenuMasterMapper;
import cn.linkmore.security.entity.Menu;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.request.ReqMenu;
import cn.linkmore.security.response.ResDict;
import cn.linkmore.security.response.ResMenu;
import cn.linkmore.security.response.ResPage;
import cn.linkmore.security.service.MenuService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;


/**
 * Service实现类 -权限模块 - 菜单信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuClusterMapper menuClusterMapper;
	
	@Autowired
	private MenuMasterMapper menuMasterMapper;
	
	@Autowired
	private DictClusterMapper dictClusterMapper; 
	
	@Autowired
	private PageClusterMapper pageClusterMapper; 
	
	@Override
	public Tree findTree(){
		List<ResMenu> list =  this.menuClusterMapper.findTree();
		Tree tree = null; 
		List<ResMenu> fs = new ArrayList<ResMenu>();
		List<ResMenu> ss = null; 
		Map<Long,List<ResMenu>> map = new HashMap<Long,List<ResMenu>>();
		for(ResMenu menu:list) {
			if(menu.getParentId()==0) {
				fs.add(menu);
			}else {
				ss = map.get(menu.getParentId());
				if(ss==null) {
					ss = new ArrayList<ResMenu>();
					map.put(menu.getParentId(), ss);
				}
				ss.add(menu);
			}
		}
		Tree child = null;
		List<Tree> children = null;
		List<Tree> pchildren = new ArrayList<Tree>();
		for(ResMenu menu:fs) {
			tree = new Tree();
			tree.setName(menu.getName());
			tree.setId(menu.getId().toString());
			tree.setCode(menu.getId().toString());
			tree.setOpen(true);
			tree.setpId("0");
			ss = map.get(menu.getId());
			if(ss!=null) {
				tree.setIsParent(true);
				children = new ArrayList<Tree>();
				for(ResMenu s:ss) {
					child = new Tree();
					child.setName(s.getName());
					child.setId(s.getId().toString());
					child.setCode(s.getId().toString());
					child.setpId(menu.getId().toString());
					child.setIsParent(false);
					children.add(child);
				} 
				tree.setChildren(children);
			}else {
				tree.setIsParent(false);
			}
			pchildren.add(tree);
		}
		Tree root = new Tree();
		root.setName("系统菜单树");
		root.setId("0");
		root.setIsParent(false);
		root.setCode("0");
		root.setOpen(true);
		root.setmId("0");
		root.setChildren(pchildren);
		return root;
	}
	
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
		Integer count = this.menuClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResMenu> list = this.menuClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public int save(ReqMenu reqMenu) {
		Menu menu = new Menu();
		menu = ObjectUtils.copyObject(reqMenu, menu);
		menu.setCreateTime(new Date());
		return this.menuMasterMapper.save(menu);
	}
	
	@Override
	public int update(ReqMenu reqMenu) {
		Menu menu = new Menu();
		menu = ObjectUtils.copyObject(reqMenu, menu);
		return this.menuMasterMapper.update(menu);
	}

	@Override
	public int delete(List<Long> ids) {
		return this.menuMasterMapper.delete(ids); 
	}
	
	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.menuClusterMapper.check(param); 
	}
	
	@Override
	public Map<String,Object> map() {
		Map<String,Object> param = new HashMap<String,Object>(); 
		List<ResDict> dicts = this.dictClusterMapper.findByGroupCode("security-page-category");
		param.put("category", dicts);
		List<ResPage> pages = this.pageClusterMapper.findAll();
		param.put("page", pages);
		return param;
	}

	@Override
	public List<ResMenu> findPersonAuthList(Long id) {
		List<ResMenu> list = this.menuClusterMapper.findPersonAuthList(id);
		return list;
	}
}
