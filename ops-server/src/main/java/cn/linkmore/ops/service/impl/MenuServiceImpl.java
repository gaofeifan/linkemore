package cn.linkmore.ops.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.request.ReqCheck;
import cn.linkmore.ops.request.ReqMenu;
import cn.linkmore.security.response.ResMenu;
import cn.linkmore.ops.response.ResPerson;
import cn.linkmore.ops.service.MenuService;
import cn.linkmore.security.client.MenuClient;
import cn.linkmore.util.ObjectUtils;

/**
 * Service实现类 -权限模块 - 类信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class MenuServiceImpl implements MenuService {

	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MenuClient menuClient;
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.menuClient.list(pageable);
	}

	@Override
	public int save(ReqMenu reqMenu) {
		cn.linkmore.security.request.ReqMenu reqMenuSecurity = new cn.linkmore.security.request.ReqMenu();
		reqMenuSecurity = ObjectUtils.copyObject(reqMenu, reqMenuSecurity);
		return this.menuClient.save(reqMenuSecurity);
	}

	@Override
	public int update(ReqMenu reqMenu) {
		cn.linkmore.security.request.ReqMenu reqMenuSecurity = new cn.linkmore.security.request.ReqMenu();
		reqMenuSecurity = ObjectUtils.copyObject(reqMenu, reqMenuSecurity);
		return this.menuClient.update(reqMenuSecurity);
		
	}

	@Override
	public int delete(List<Long> ids) {
		return this.menuClient.delete(ids);
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		cn.linkmore.security.request.ReqCheck reqCheckSecurity = new cn.linkmore.security.request.ReqCheck();
		reqCheckSecurity = ObjectUtils.copyObject(reqCheck, reqCheckSecurity);
		return this.menuClient.check(reqCheckSecurity);
	}

	@Override
	public Tree findTree() {
		return this.menuClient.tree();
	}

	@Override
	public Map<String, Object> map() {
		return this.menuClient.map();
	}

	@Override
	public void cachePersonAuthList() {
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson)subject.getSession().getAttribute("person");
		/*if(person == null) {
			person = new ResPerson();
			person.setId(3L);
		}*/
		List<ResMenu> list = this.menuClient.findPersonAuthList(person.getId());
		List<ResMenu> tms = new ArrayList<ResMenu>(); 
		 
		Map<Long,ResMenu> fm = new HashMap<Long,ResMenu>();
		Map<Long,ResMenu> sm = new HashMap<Long,ResMenu>(); 
		Map<Long,ResMenu> pm = new HashMap<Long,ResMenu>();
		for(ResMenu menu:list) {
			if(menu.getLevel().intValue()==2&&menu.getPageId()!=null) {
				tms.add(menu);
			}else if(menu.getLevel().intValue()==1&&menu.getPageId()!=null) { 
				menu.setChildren(new ArrayList<ResMenu>()); 
				sm.put(menu.getId(), menu);
				pm.put(menu.getId(), menu);
			}else if(menu.getLevel().intValue()==1) {
				menu.setChildren(new ArrayList<ResMenu>()); 
				sm.put(menu.getId(), menu);
			}else if(menu.getLevel().intValue()==0) {
				menu.setChildren(new ArrayList<ResMenu>());
				fm.put(menu.getId(), menu); 
			} 
		}
		ResMenu parent = null;  
		for(ResMenu menu:tms) {
			parent =  sm.get(menu.getParentId());
			if(parent!=null) {
				parent.getChildren().add(menu);
				pm.put(parent.getId(), parent);
			}
		} 
		Set<Long> keys = pm.keySet();
		ResMenu top = null;
		Set<ResMenu> topSet = new HashSet<ResMenu>();
		Map<Long,ResMenu> topMap = new HashMap<Long,ResMenu>();
		for(Long key:keys) {
			parent = pm.get(key);
			top = fm.get(parent.getParentId());
			if(top!=null) {
				top.getChildren().add(parent);
				topSet.add(top);
				topMap.put(top.getId(), top);
			}
		}
		List<ResMenu> fs = new ArrayList<ResMenu>();
		fs.addAll(topSet);
		subject.getSession().setAttribute("top_menu_list", fs);
		subject.getSession().setAttribute("top_menu_map", topMap);
	}

	@Override
	public List<ResMenu> findPersonAuthList(Long id) {
		List<ResMenu> list = this.menuClient.findPersonAuthList(id);
		log.info("menu service impl list {}",list.size());
		return list;
	}
	
	
}
