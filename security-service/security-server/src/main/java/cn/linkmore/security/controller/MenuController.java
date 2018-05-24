package cn.linkmore.security.controller;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.entity.Menu;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.request.ReqMenu;
import cn.linkmore.security.service.MenuService;
import cn.linkmore.util.ObjectUtils;

/**
 * Controller - 菜单操作
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqMenu reqMenu){
		Menu menu = new Menu();
		menu = ObjectUtils.copyObject(reqMenu, menu);
		this.menuService.save(menu);
	}
	
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody ReqMenu reqMenu){
		Menu menu = new Menu();
		menu = ObjectUtils.copyObject(reqMenu, menu);
		this.menuService.update(menu);
	}
	
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public void delete(@RequestBody List<Long> ids){ 
		this.menuService.delete(ids);
	}
	
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck){
		Boolean flag = true ;
		Integer count = this.menuService.check(reqCheck); 
		if(count>0){
            flag = false;
        }
        return flag;
	}
	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){ 
		return this.menuService.findPage(pageable); 
	} 
	
	@RequestMapping(value = "/v2.0/tree", method = RequestMethod.GET)
	@ResponseBody
	public Tree tree(){ 
		return this.menuService.findTree();
	}
	
	@RequestMapping(value = "/v2.0/map", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> map(){ 
		return this.menuService.map();
	}
}
