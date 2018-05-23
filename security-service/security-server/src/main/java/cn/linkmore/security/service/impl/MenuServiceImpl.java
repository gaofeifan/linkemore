package cn.linkmore.security.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
import cn.linkmore.security.entity.Dict;
import cn.linkmore.security.entity.Menu;
import cn.linkmore.security.entity.Page;
import cn.linkmore.security.entity.Person;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.service.MenuService;
import cn.linkmore.util.DomainUtil;


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
		List<Menu> list =  this.menuClusterMapper.findTree();
		Tree tree = null; 
		List<Menu> fs = new ArrayList<Menu>();
		List<Menu> ss = null; 
		Map<Long,List<Menu>> map = new HashMap<Long,List<Menu>>();
		for(Menu menu:list) {
			if(menu.getParentId()==0) {
				fs.add(menu);
			}else {
				ss = map.get(menu.getParentId());
				if(ss==null) {
					ss = new ArrayList<Menu>();
					map.put(menu.getParentId(), ss);
				}
				ss.add(menu);
			}
		}
		Tree child = null;
		List<Tree> children = null;
		List<Tree> pchildren = new ArrayList<Tree>();
		for(Menu menu:fs) {
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
				for(Menu s:ss) {
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
		List<Menu> list = this.menuClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public void save(Menu menu) {
		menu.setCreateTime(new Date());
		this.menuMasterMapper.save(menu);
		
	}
	
	@Override
	public Menu update(Menu menu) {
		this.menuMasterMapper.update(menu);
		return menu;
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
		List<Dict> dicts = this.dictClusterMapper.findByGroupCode("security-page-category");
		param.put("category", dicts);
		List<Page> pages = this.pageClusterMapper.findAll();
		param.put("page", pages);
		return param;
	}

	@Override
	public void cachePersonAuthList() {
		Subject subject = SecurityUtils.getSubject();
		Person person = (Person)subject.getSession().getAttribute("person");  
		
		List<Menu> list = this.menuClusterMapper.findPersonAuthList(person.getId());
		List<Menu> tms = new ArrayList<Menu>(); 
		 
		Map<Long,Menu> fm = new HashMap<Long,Menu>();
		Map<Long,Menu> sm = new HashMap<Long,Menu>(); 
		Map<Long,Menu> pm = new HashMap<Long,Menu>();
		for(Menu menu:list) {
			if(menu.getLevel().intValue()==2&&menu.getPageId()!=null) {
				tms.add(menu);
			}else if(menu.getLevel().intValue()==1&&menu.getPageId()!=null) { 
				menu.setChildren(new ArrayList<Menu>()); 
				sm.put(menu.getId(), menu);
				pm.put(menu.getId(), menu);
			}else if(menu.getLevel().intValue()==1) {
				menu.setChildren(new ArrayList<Menu>()); 
				sm.put(menu.getId(), menu);
			}else if(menu.getLevel().intValue()==0) {
				menu.setChildren(new ArrayList<Menu>());
				fm.put(menu.getId(), menu); 
			} 
		}
		Menu parent = null;  
		for(Menu menu:tms) {
			parent =  sm.get(menu.getParentId());
			if(parent!=null) {
				parent.getChildren().add(menu);
				pm.put(parent.getId(), parent);
			}
		} 
		Set<Long> keys = pm.keySet();
		Menu top = null;
		Set<Menu> topSet = new HashSet<Menu>();
		Map<Long,Menu> topMap = new HashMap<Long,Menu>();
		for(Long key:keys) {
			parent = pm.get(key);
			top = fm.get(parent.getParentId());
			if(top!=null) {
				top.getChildren().add(parent);
				topSet.add(top);
				topMap.put(top.getId(), top);
			}
		}
		List<Menu> fs = new ArrayList<Menu>();
		fs.addAll(topSet);
		subject.getSession().setAttribute("top_menu_list", fs);
		subject.getSession().setAttribute("top_menu_map", topMap);
	}
}
