package cn.linkmore.ops.biz.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqEnterprise;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.ops.biz.service.EnterpriseService;
import cn.linkmore.prefecture.client.EnterpriseClient;
import cn.linkmore.security.client.PersonClient;
import cn.linkmore.security.request.ReqPerson;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.PasswordUtil;
@Service
public class EnterpriseServiceImpl implements EnterpriseService {

	@Resource
	private EnterpriseClient enterpriseClient;
	@Resource
	private PersonClient personClient;
	@Override
	public int save(ReqEnterprise record) {
		this.enterpriseClient.save(record);
		return 0;
	}

	@Override
	public int update(ReqEnterprise record) {
		this.enterpriseClient.update(record);
		return 0;
	}

	@Override
	public int delete(Long id) {
		this.enterpriseClient.delete(id);
		return 0;
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		Boolean check = this.enterpriseClient.check(reqCheck);
		return check;
	}

	@Override
	public Boolean check(String property, String value, Long id) {
		ReqCheck reqCheck = new ReqCheck();
		reqCheck.setId(id);
		reqCheck.setProperty(property);
		reqCheck.setValue(value);
		Boolean check = this.enterpriseClient.check(reqCheck);
		return check;
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		ViewPage page = this.enterpriseClient.list(pageable);
		return page;
	}

	@Override
	public List<ResEnterprise> selectAll() {
		List<ResEnterprise> enterList = this.enterpriseClient.selectAll();
		return enterList;
	}

	@Override
	public void setPassword(ReqPerson person) {
		person.setPassword(PasswordUtil.encode(person.getPassword()));
		this.personClient.updateEntPassword(person);
	}

	@Override
	public ResEnterprise find(Map<String,Object> param) {
		return this.enterpriseClient.findName(param);
	}
	
	
}
