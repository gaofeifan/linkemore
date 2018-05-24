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
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.entity.Person;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.request.ReqPerson;
import cn.linkmore.security.response.ResPersonRole;
import cn.linkmore.security.response.ResRole;
import cn.linkmore.security.service.PersonService;
import cn.linkmore.util.ObjectUtils;

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
	public void save(@RequestBody ReqPerson reqPerson){
		Person person = new Person();
		person = ObjectUtils.copyObject(reqPerson, person);
		this.personService.save(person);
		 
	}
	
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public void update(@RequestBody ReqPerson reqPerson){
		Person person = new Person();
		person = ObjectUtils.copyObject(reqPerson, person);
		this.personService.update(person);
	}
	
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public void delete(@RequestBody List<Long> ids){ 
		this.personService.delete(ids);
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
	public void unlock(@RequestParam("id") Long id){
		this.personService.unlock(id);
	}
	
	@RequestMapping(value = "/v2.0/role_list", method = RequestMethod.GET)
	@ResponseBody
	public List<ResRole> roleList(){
		return this.personService.roleList();
	}  
	
	
	@RequestMapping(value = "/v2.0/person_role_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPersonRole> personRolList(@RequestParam("id") Long id){
		return this.personService.personRoleList(id);
	}  
	
	
	@RequestMapping(value = "/v2.0/bind", method = RequestMethod.GET)
	@ResponseBody
	public void bind(@RequestParam("id") Long id,@RequestParam("ids") String ids){
		String[] array = ids.split(",");
		this.personService.bind(id,array);
	}
}