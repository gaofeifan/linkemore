package cn.linkmore.ops.ent.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqRentEntUser;
import cn.linkmore.enterprise.request.ReqRentUser;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.ops.ent.service.RentEntUserService;
import cn.linkmore.ops.security.response.ResPerson;
import cn.linkmore.prefecture.client.EnterpriseClient;
import cn.linkmore.prefecture.client.OpsRentEntUserClient;
@Service
public class RentEntUserServiceImpl implements RentEntUserService{

	@Resource
	private EnterpriseClient enterpriseClient;
	@Resource
	private OpsRentEntUserClient opsRentEntUserClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.opsRentEntUserClient.list(pageable);
	}

	@Override
	public void save(ReqRentEntUser ent) {
		this.opsRentEntUserClient.save(ent);
	}

	@Override
	public void update(ReqRentEntUser ent) {
		this.opsRentEntUserClient.update(ent);
		
	}

	@Override
	public void deleteIds(List<Long> ids) {
		this.opsRentEntUserClient.delete(ids);
	}

	@Override
	public ViewPage findPageI(ViewPageable pageable) {
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson)subject.getSession().getAttribute("person"); 
		List<ViewFilter> filters = pageable.getFilters();
		 ViewFilter vf = new ViewFilter();
		 vf.setProperty("entId");
		 vf.setValue(person.getId());
		 filters.add(vf);
		return this.opsRentEntUserClient.listI(pageable);
	}

	@Override
	public void saveI(ReqRentUser ent) {
		Map<String, Object> param = new HashMap<String, Object>();
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson)subject.getSession().getAttribute("person"); 
		param.put("id", person.getId());
		ResEnterprise enterprise = this.enterpriseClient.findName(param );
		ent.setEntId(person.getId());
		ent.setEntName(enterprise.getName());
		this.opsRentEntUserClient.saveI(ent);
	}

	@Override
	public void updateI(ReqRentUser ent) {
		this.opsRentEntUserClient.updateI(ent);
	}

	@Override
	public void deleteIdsI(List<Long> ids) {
		this.opsRentEntUserClient.deleteI(ids);
	}
	
	@Override
	public boolean exists(ReqRentEntUser reqRentEntUser) {
		return this.opsRentEntUserClient.exists(reqRentEntUser);
	}
}
