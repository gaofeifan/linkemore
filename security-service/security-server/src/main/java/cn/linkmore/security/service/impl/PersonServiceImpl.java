package cn.linkmore.security.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.dao.cluster.InterfaceClusterMapper;
import cn.linkmore.security.dao.cluster.PersonClusterMapper;
import cn.linkmore.security.dao.cluster.PersonRoleClusterMapper;
import cn.linkmore.security.dao.cluster.RoleClusterMapper;
import cn.linkmore.security.dao.master.PersonMasterMapper;
import cn.linkmore.security.dao.master.PersonRoleMasterMapper;
import cn.linkmore.security.entity.Interface;
import cn.linkmore.security.entity.Page;
import cn.linkmore.security.entity.Person;
import cn.linkmore.security.entity.PersonRole;
import cn.linkmore.security.entity.Role;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.service.PersonService;
import cn.linkmore.security.shiro.Principal;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.PasswordUtil;

/**
 * Service实现类 -权限模块 - 日志信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonClusterMapper personClusterMapper;
	@Autowired
	private PersonMasterMapper personMasterMapper;
	
	@Autowired
	private InterfaceClusterMapper interfaceClusterMapper;
	
	@Autowired
	private RoleClusterMapper roleClusterMapper;
	
	@Autowired
	private PersonRoleMasterMapper personRoleMasterMapper;
	
	@Autowired
	private PersonRoleClusterMapper personRoleClusterMapper;
	

	@Override
	public Person findByUsername(String username) {
		Person person = null;
		if(StringUtils.isNotBlank(username)) {
			person = this.personClusterMapper.findByUsername(username);
		}
		return person;
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
		Integer count = this.personClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<Page> list = this.personClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public void save(Person person) {
		person.setCreateTime(new Date());
		person.setPassword(PasswordUtil.encode(person.getPassword()));
		person.setLockCount(0);
		person.setLockStatus(0); 
		person.setLockTime(new Date());
		person.setLoginIp("");
		person.setType(1); 
		person.setLoginTime(new Date());
		this.personMasterMapper.save(person);
	}
	
	@Override
	public Person update(Person person) {
		Person db = this.personClusterMapper.findById(person.getId());
		db.setRealname(person.getRealname());
		db.setStatus(person.getStatus());
		if(StringUtils.isNotBlank(person.getPassword())) {
			db.setPassword(PasswordUtil.encode(person.getPassword()));
		}
		this.personMasterMapper.update(db);
		return person;
	}
	
	@Override
	public void updatePassword(Person person,String oldPassword,String newPassword){
		Person db = this.personClusterMapper.findById(person.getId());
		if(StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)){
			throw new RuntimeException("密码不能为空");
		}
		if(StringUtils.isNotBlank(db.getPassword())){
			if(PasswordUtil.checkPassword(oldPassword, db.getPassword())){
				db.setPassword(PasswordUtil.encode(newPassword));
				this.personMasterMapper.update(db);
				Subject subject = SecurityUtils.getSubject();
				subject.getSession().removeAttribute("person");
				subject.logout();
			}else{
				throw new RuntimeException("原始密码错误");
			}
		}
	}
	
	@Override
	public void loginUpdate(Person person) {
		Person db = this.personClusterMapper.findById(person.getId());
		db.setLockCount(person.getLockCount());
		db.setLockStatus(person.getLockStatus());
		db.setLockTime(person.getLockTime());
		db.setLoginIp(person.getLoginIp());
		db.setLoginTime(person.getLoginTime()); 
		this.personMasterMapper.loginUpdate(db); 
	}

	@Override
	public int delete(List<Long> ids) {
		return this.personMasterMapper.delete(ids); 
	}
	
	@Override
	public void unlock(Long id) {
		this.personMasterMapper.unlock(id); 
	}
	
	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.personClusterMapper.check(reqCheck); 
	}
	
	@Override
	public List<String> findAuthList(Principal principal) { 
		List<Interface> list = this.interfaceClusterMapper.findPersonAuthList(principal.getId());
		List<String> as = new ArrayList<String>();
		for(Interface i:list) {
			as.add(i.getId().toString()); 
		}
		return as;
	}
	
	
	@Override
	public List<PersonRole> personRoleList(Long id){ 
		return this.personRoleClusterMapper.findListById(id);
		
	}
	
	
	@Override
	public List<Role> roleList(){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("status", 1);
		return this.roleClusterMapper.findList(param);
	}
	
	
	@Override
	public void bind(Long id,String[] ids) {
		PersonRole pr = null;
		this.personRoleMasterMapper.delete(id);
		for(String rid:ids) {
			pr = new PersonRole();
			pr.setPersonId(id);
			pr.setRoleId(Long.valueOf(rid));
			this.personRoleMasterMapper.save(pr);
		}
	}
}
