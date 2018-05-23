package cn.linkmore.security.controller;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.entity.Role;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.service.RoleService;

/**
 * Controller - 角色操作
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/role")
public class RoleController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(@RequestBody Role role){
		ViewMsg msg = null;
		try {
			this.roleService.save(role);
			msg = new ViewMsg("保存成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			msg = new ViewMsg("保存失败",false);
		}
		return msg;
		 
	}
	
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(@RequestBody Role role){
		ViewMsg msg = null;
		try {
			this.roleService.update(role);
			msg = new ViewMsg("保存成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			msg = new ViewMsg("保存失败",false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody List<Long> ids){ 
		ViewMsg msg = null;
		try {
			this.roleService.delete(ids);
			msg = new ViewMsg("删除成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败",false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck){
		Boolean flag = true ;
		Integer count = this.roleService.check(reqCheck); 
		if(count>0){
            flag = false;
        }
        return flag;
	}
	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){
		return this.roleService.findPage(pageable); 
	}  
	
	@RequestMapping(value = "/v2.0/tree", method = RequestMethod.GET)
	@ResponseBody
	public Tree tree(){ 
		return this.roleService.findTree();
	}
	
	@RequestMapping(value = "/v2.0/resource", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> resource(@RequestParam("id") Long id){ 
		return this.roleService.resource(id);
	}
	
	@RequestMapping(value = "/v2.0/bind", method = RequestMethod.GET)
	@ResponseBody
	public ViewMsg bind(@RequestParam("id") Long id,@RequestParam("pids") String pids,@RequestParam("eids") String eids){ 
		ViewMsg msg = null;
		try {
			this.roleService.bind(id,pids,eids);
			msg = new ViewMsg("绑定成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("绑定失败",false);
		}
		return msg;
	}
}
