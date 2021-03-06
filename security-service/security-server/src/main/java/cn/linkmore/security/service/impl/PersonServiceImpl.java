package cn.linkmore.security.service.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.dao.cluster.PersonClusterMapper;
import cn.linkmore.security.dao.cluster.PersonRoleClusterMapper;
import cn.linkmore.security.dao.cluster.RoleClusterMapper;
import cn.linkmore.security.dao.master.PersonMasterMapper;
import cn.linkmore.security.dao.master.PersonRoleMasterMapper;
import cn.linkmore.security.entity.Person;
import cn.linkmore.security.entity.PersonRole;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.request.ReqPerson;
import cn.linkmore.security.response.ResPerson;
import cn.linkmore.security.response.ResPersonRole;
import cn.linkmore.security.response.ResRole;
import cn.linkmore.security.service.PersonService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
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
	private RoleClusterMapper roleClusterMapper;
	
	@Autowired
	private PersonRoleMasterMapper personRoleMasterMapper;
	
	@Autowired
	private PersonRoleClusterMapper personRoleClusterMapper;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResPerson findByUsername(String username) {
		ResPerson person = null;
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
		List<ResPerson> list = this.personClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public Long save(ReqPerson reqPerson) {
		Person person = new Person();
		person = ObjectUtils.copyObject(reqPerson, person);
		if(person.getEntId()!= null && person.getEntId()!=0L) {
			ResPerson resPerson = personClusterMapper.findById(person.getEntId());
			if(resPerson != null) {
				person.setEntName(resPerson.getUsername());
			}
		}
		person.setCreateTime(new Date());
		person.setPassword(PasswordUtil.encode(person.getPassword()));
		person.setLockCount(0);
		person.setLockStatus(0); 
		person.setLockTime(new Date());
		person.setLoginIp("");
		if(reqPerson.getType() == 2) {
			person.setType(2); 
		}else {
			person.setType(1); 
		}
		person.setLoginTime(new Date());
		person.setPreId(reqPerson.getPreId());
		this.personMasterMapper.save(person);
		return person.getId();
	}
	
	@Override
	public int update(ReqPerson reqPerson) {
		Person person = new Person();
		person = ObjectUtils.copyObject(reqPerson, person);
		ResPerson db = this.personClusterMapper.findById(person.getId());
		db.setRealname(person.getRealname());
		db.setStatus(person.getStatus());
		if(StringUtils.isNotBlank(person.getPassword())) {
			db.setPassword(PasswordUtil.encode(person.getPassword()));
		}
		person = ObjectUtils.copyObject(db, person);
		return this.personMasterMapper.update(person);
	}
	
	@Override
	public void updateEntPassword(ReqPerson person){
		Person p = new Person();
		p = ObjectUtils.copyObject(person, p);
		this.personMasterMapper.updatePassword(p);
	}
	
	@Override
	public int loginUpdate(ReqPerson person) {
		ResPerson db = this.personClusterMapper.findById(person.getId());
		db.setLockCount(person.getLockCount());
		db.setLockStatus(person.getLockStatus());
		db.setLockTime(person.getLockTime());
		db.setLoginIp(person.getLoginIp());
		db.setLoginTime(person.getLoginTime()); 
		
		Person p = new Person();
		p = ObjectUtils.copyObject(db, p);
		return this.personMasterMapper.loginUpdate(p); 
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
		return this.personClusterMapper.check(param); 
	}
	
	@Override
	public List<ResPersonRole> personRoleList(Long id){ 
		return this.personRoleClusterMapper.findListById(id);
	}
	
	
	@Override
	public List<ResRole> roleList(Map<String,Object> param){
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

	@Override
	public ResPerson findById(Long id) {
		return this.personClusterMapper.findById(id);
	}

	@Override
	public int updateEntId(ReqPerson reqPerson) {
		return this.personMasterMapper.updateEntId(reqPerson);
		
	}
}
