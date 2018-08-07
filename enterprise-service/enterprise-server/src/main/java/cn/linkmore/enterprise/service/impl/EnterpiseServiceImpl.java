package cn.linkmore.enterprise.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.EnterpriseClusterMapper;
import cn.linkmore.enterprise.dao.master.EnterpriseMasterMapper;
import cn.linkmore.enterprise.entity.Enterprise;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqEnterprise;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.enterprise.service.EnterpriseService;
import cn.linkmore.security.client.PersonClient;
import cn.linkmore.security.client.RoleClient;
import cn.linkmore.security.request.ReqPerson;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.PasswordUtil;

@Service
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackForClassName = "RuntimeExcpeiton")
public class EnterpiseServiceImpl implements EnterpriseService {

	@Resource
	private EnterpriseMasterMapper enterpriseMasterMapper;
	
	@Resource
	private EnterpriseClusterMapper enterpriseClusterMapper;

	@Resource
	private RoleClient roleClient;

	/*@Resource
	private PersonRoleClient personRoleClient;*/

	@Resource
	private PersonClient personClient;
	
	@Override
	public ResEnterprise findById(Long id) {
		ResEnterprise resEnterprise = this.enterpriseClusterMapper.findById(id);
		return resEnterprise;
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<ViewFilter> filters = pageable.getFilters();
		if (StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if (filters != null && filters.size() > 0) {
			for (ViewFilter filter : filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		if (StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.enterpriseClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResEnterprise> list = this.enterpriseClusterMapper.findPage(param);
		return new ViewPage(count, pageable.getPageSize(), list);
	}

	@Override
	public List<ResEnterprise> findList(Map<String, Object> param) {
		List<ResEnterprise> list = this.enterpriseClusterMapper.findList(param);
		return list;
	}

	@Override
	public void setPassword(ReqPerson person) {
		person.setPassword(PasswordUtil.encode(person.getPassword()));
//		this.personClient.updatePassword(person);
	}

	@Override
	public int save(ReqEnterprise record) {
		ReqPerson person = new ReqPerson();
		person.setUsername(record.getAccount());
		person.setType(2);
		person.setStatus(1);
		person.setCreateTime(new Date());
		person.setPassword(PasswordUtil.encode(record.getPassword()));
		person.setLockCount(0);
		person.setLockStatus(0);
		person.setLockTime(new Date());
		person.setLoginIp("");
		person.setRealname(record.getName());
		person.setLoginTime(new Date());
		this.personClient.save(person);
		record.setId(person.getId());
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		Enterprise enter = ObjectUtils.copyObject(record, new Enterprise());
		this.enterpriseMasterMapper.save(enter);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("code", "enterprise-base-role");
		/*List<ResRole> list = this.roleClient.findList(param);
		if (list != null && list.size() > 0) {
			ResRole role = list.get(0);
			ReqPersonRole pr = new ReqPersonRole();
			pr.setPersonId(person.getId());
			pr.setRoleId(role.getId());
			this.personRoleMapper.save(pr);
		}*/
		return this.personClient.update(person);
	}

	@Override
	public int update(ReqEnterprise record) {
		record.setUpdateTime(new Date());
		Enterprise enter = ObjectUtils.copyObject(record, new Enterprise());
		return this.enterpriseMasterMapper.update(enter);
	}

	@Override
	public int delete(Long id) {
		int count = this.enterpriseMasterMapper.delete(id);
		/*this.personRoleMapper.delete(id);
		this.personClient.delete(id);*/
		return count;
	}

	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.enterpriseClusterMapper.check(param);
	}

	@Override
	public List<ResEnterprise> selectRegionByIndustry(Integer industry) {
		return this.enterpriseClusterMapper.selectRegionByIndustry(industry);
	}

	@Override
	public List<ResEnterprise> selectAll() {
		return this.enterpriseClusterMapper.findAll();
	}

	@Override
	public ResEnterprise findName(Map<String, Object> map) {
		return this.enterpriseClusterMapper.findName(map);
	}

}
