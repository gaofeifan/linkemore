package cn.linkmore.ops.security.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.security.request.ReqCheck;
import cn.linkmore.ops.security.response.ResPersonRole;
import cn.linkmore.ops.security.response.ResRole;
import cn.linkmore.ops.security.service.PersonService;
import cn.linkmore.ops.shiro.Principal;
import cn.linkmore.security.client.InterfaceClient;
import cn.linkmore.security.client.PersonClient;
import cn.linkmore.security.request.ReqPerson;
import cn.linkmore.security.response.ResInterface;
import cn.linkmore.security.response.ResPerson;
import cn.linkmore.util.ObjectUtils;

/**
 * Service实现类 -权限模块 - 类信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonClient personClient;
	
	@Autowired
	private InterfaceClient interClient;

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.personClient.list(pageable);
	}

	@Override
	public Long save(ReqPerson reqPerson) {
		return this.personClient.save(reqPerson);
	}

	@Override
	public int update(ReqPerson reqPerson) {
		return this.personClient.update(reqPerson);
		
	}

	@Override
	public int delete(List<Long> ids) {
		return this.personClient.delete(ids);
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		cn.linkmore.security.request.ReqCheck reqCheckSecurity = new cn.linkmore.security.request.ReqCheck();
		reqCheckSecurity = ObjectUtils.copyObject(reqCheck, reqCheckSecurity);
		return this.personClient.check(reqCheckSecurity);
	}

	@Override
	public ResPerson findByUsername(String username) {
		ResPerson person = this.personClient.findByUsername(username);
		return person;
	}

	@Override
	public int loginUpdate(ReqPerson person) {
		return 0;
	}

	@Override
	public List<String> findAuthList(Principal principal) {
		List<ResInterface> list = this.interClient.findPersonAuthList(principal.getId());
		List<String> as = new ArrayList<String>();
		for(ResInterface i:list) {
			as.add(i.getId().toString()); 
		}
		return as;
	}

	@Override
	public void unlock(Long id) {
		this.personClient.unlock(id);
	}

	@Override
	public List<ResPersonRole> personRoleList(Long id) {
		List<ResPersonRole> resPersonRoleList = new ArrayList<ResPersonRole>();
		ResPersonRole resPersonRole = null;
		List<cn.linkmore.security.response.ResPersonRole> resPersonList = this.personClient.personRolList(id);
		for(cn.linkmore.security.response.ResPersonRole rolePerson :resPersonList) {
			resPersonRole = new ResPersonRole();
			resPersonRole = ObjectUtils.copyObject(rolePerson, resPersonRole);
			resPersonRoleList.add(resPersonRole);
		}
		return resPersonRoleList;
	}

	@Override
	public List<ResRole> roleList() {
		List<ResRole> resRoleList = new ArrayList<ResRole>();
		ResRole resRole = null;
		List<cn.linkmore.security.response.ResRole> resList = this.personClient.roleList();
		for(cn.linkmore.security.response.ResRole role :resList) {
			resRole = new ResRole();
			resRole = ObjectUtils.copyObject(role, resRole);
			resRoleList.add(resRole);
		}
		return resRoleList;
	}

	@Override
	public void bind(Long id, String ids) {
		this.personClient.bind(id, ids);
	}

}
