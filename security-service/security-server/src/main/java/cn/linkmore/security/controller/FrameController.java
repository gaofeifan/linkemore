package cn.linkmore.security.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.security.entity.Person;
import cn.linkmore.security.response.ReqAuthElement;

/**
 * Controller - 权限模块 - 框架
 * 
 * @author liwenlong
 * @version 1.0
 *
 */

@RestController
@RequestMapping("/frame")
public class FrameController {
	 

	@RequestMapping(value = "/v2.0/success", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> success() {
		Subject subject = SecurityUtils.getSubject();
		Person person = (Person)subject.getSession().getAttribute("person");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("login", true);
		map.put("token", "hello_kitty");
		map.put("person", person);
		return map;
	}

	@RequestMapping(value = "/v2.0/top_menu", method = RequestMethod.GET)
	@ResponseBody
	public List<Menu> topMenu() throws IOException {
		Subject subject = SecurityUtils.getSubject();
		List<cn.linkmore.security.entity.Menu> menus = (List<cn.linkmore.security.entity.Menu>)subject.getSession().getAttribute("top_menu_list"); 
		Collections.sort(menus);
		List<Menu> list = new ArrayList<Menu>();
		Menu menu = null;
		for(cn.linkmore.security.entity.Menu m:menus) {
			menu = new Menu(m.getName(), m.getIcon(), m.getId().toString());
			list.add(menu);
		}  
		return list;
	}
	@RequestMapping(value = "/v2.0/auth_element", method = RequestMethod.GET)
	@ResponseBody
	public List<Dom> authElement() throws IOException {
		Subject subject = SecurityUtils.getSubject();
		List<ReqAuthElement> aes = (List<ReqAuthElement>)subject.getSession().getAttribute("authelement");  
		List<String> ids = (List<String>)subject.getSession().getAttribute("authorities"); 
		Dom dom = null;
		List<Dom> list = new ArrayList<Dom>();
		Map<Long,Dom> dmap = new HashMap<Long,Dom>(); 
		for(ReqAuthElement ae:aes){
			Element e = new Element();
			e.setLabelId(ae.getLabelId());
			e.setLabelName(ae.getLabelName()); 
			e.setStatus(ids.indexOf(ae.getInterfaceId().toString())>=0); 
			dom = dmap.get(ae.getId());
			if(dom==null){
				dom = new Dom();
				dom.setId(ae.getId().toString());
				dom.setPath(ae.getPath());
				dom.setChildren(new ArrayList<Element>());
				dmap.put(ae.getId(), dom); 
				list.add(dom);
			}
			dom.getChildren().add(e);
		}
		return list;
	}

	@RequestMapping(value = "/v2.0/left_menu", method = RequestMethod.GET)
	@ResponseBody
	public List<Menu> leftMenu(@RequestParam("pid") Long pid) throws IOException {
		List<Menu> list = new ArrayList<Menu>();
		Menu menu = null;
		Menu child = null;
		List<Menu> children = null; 
		Subject subject = SecurityUtils.getSubject();
		Map<Long,cn.linkmore.security.entity.Menu> topMap = (Map<Long,cn.linkmore.security.entity.Menu>)subject.getSession().getAttribute("top_menu_map"); 
		cn.linkmore.security.entity.Menu top = topMap.get(pid);
		Collections.sort(top.getChildren());
		for(cn.linkmore.security.entity.Menu m:top.getChildren()) {
			if(m.getChildren()==null||m.getChildren().size()<=0) {
				menu = new Menu(m.getName(), m.getIcon(), false, m.getPath()); 
			}else {
				menu = new Menu(m.getName(), m.getIcon(), true, null); 
				children = new ArrayList<Menu>(); 
				Collections.sort(m.getChildren());
				for(cn.linkmore.security.entity.Menu mm:m.getChildren()) {
					child = new Menu(mm.getName(), mm.getIcon(), false, mm.getPath());
					children.add(child);
				}
				menu.setChildren(children);
			}
			list.add(menu);
		}  
		return list;
	}
}

class Dom implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8584866119360103455L;
	private String id;
	private String path;
	List<Element> children;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public List<Element> getChildren() {
		return children;
	}
	public void setChildren(List<Element> children) {
		this.children = children;
	} 
}
class Element implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -169450576389198433L;
	private String labelId;
	private String labelName;
	private Boolean status;
	public String getLabelId() {
		return labelId;
	}
	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
}

class Menu implements Serializable {
	/**
	 * serila id
	 */
	private static final long serialVersionUID = 8689031724259233638L;
	private String title;
	private String icon;
	private String pid;
	private Boolean spread = false;
	private String href;
	List<Menu> children;

	public Menu(String title, String icon, String pid) {
		this.title = title;
		this.icon = icon;
		this.pid = pid;
	}

	public Menu(String title, String icon, Boolean spread, String href) {
		this.title = title;
		this.icon = icon;
		this.href = href;
		this.spread = spread;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Boolean getSpread() {
		return spread;
	}

	public void setSpread(Boolean spread) {
		this.spread = spread;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

}
