package cn.linkmore.ops.security.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.security.request.ReqCheck;
import cn.linkmore.ops.security.response.ResPersonRole;
import cn.linkmore.ops.security.response.ResRole;
import cn.linkmore.ops.security.service.PersonService;
import cn.linkmore.security.request.ReqPerson;

/**
 * Controller - 权限模块  - 用户
 * @author liwenlong
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/admin/security/person")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqPerson person){
		ViewMsg msg = null;
		try {
			this.personService.save(person);
			msg = new ViewMsg("保存成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			msg = new ViewMsg("保存失败",false);
		}
		return msg;
		 
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqPerson person){
		ViewMsg msg = null;
		try {
			this.personService.update(person);
			msg = new ViewMsg("保存成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			msg = new ViewMsg("保存失败",false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody List<Long> ids){ 
		ViewMsg msg = null;
		try {
			this.personService.delete(ids);
			msg = new ViewMsg("删除成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败",false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(ReqCheck reqCheck){
		Boolean flag = this.personService.check(reqCheck); 
        return flag;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request,ViewPageable pageable){
		return this.personService.findPage(pageable); 
	}  
	
	@RequestMapping(value = "/unlock", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg unlock(Long id){
		ViewMsg msg = null;
		try {
			this.personService.unlock(id);
			msg = new ViewMsg("解锁成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) { 
			msg = new ViewMsg("解锁失败",false);
		}
		return msg;
		 
	}
	
	@RequestMapping(value = "/role_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResRole> roleList(HttpServletRequest request){
		return this.personService.roleList();
	}  
	
	
	@RequestMapping(value = "/person_role_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPersonRole> personRolList(Long id){
		return this.personService.personRoleList(id);
	}  
	
	
	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg bind(Long id,String ids){
		ViewMsg msg = null;
		try {
			this.personService.bind(id,ids);
			msg = new ViewMsg("授权成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) { 
			msg = new ViewMsg("授权失败",false);
		}
		return msg;
		 
	}
}
