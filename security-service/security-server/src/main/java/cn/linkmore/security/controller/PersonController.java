package cn.linkmore.security.controller;

import java.util.List;
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
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.entity.Person;
import cn.linkmore.security.entity.PersonRole;
import cn.linkmore.security.entity.Role;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.service.PersonService;

/**
 * Controller - 账户信息
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/person")
public class PersonController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PersonService personService;
	
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(@RequestBody Person person){
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
	
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(@RequestBody Person person){
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
	
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
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
	
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck){
		Boolean flag = true ;
		Integer count = this.personService.check(reqCheck); 
		if(count>0){
            flag = false;
        }
        return flag;
	}
	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){
		return this.personService.findPage(pageable); 
	}  
	
	@RequestMapping(value = "/v2.0/unlock", method = RequestMethod.GET)
	@ResponseBody
	public ViewMsg unlock(@RequestParam("id") Long id){
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
	
	@RequestMapping(value = "/v2.0/role_list", method = RequestMethod.GET)
	@ResponseBody
	public List<Role> roleList(){
		return this.personService.roleList();
	}  
	
	
	@RequestMapping(value = "/v2.0/person_role_list", method = RequestMethod.POST)
	@ResponseBody
	public List<PersonRole> personRolList(@RequestParam("id") Long id){
		return this.personService.personRoleList(id);
	}  
	
	
	@RequestMapping(value = "/v2.0/bind", method = RequestMethod.GET)
	@ResponseBody
	public ViewMsg bind(@RequestParam("id") Long id,@RequestParam("ids") String ids){
		ViewMsg msg = null;
		try {
			String[] array = ids.split(",");
			this.personService.bind(id,array);
			msg = new ViewMsg("授权成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) { 
			msg = new ViewMsg("授权失败",false);
		}
		return msg;
		 
	}
}
