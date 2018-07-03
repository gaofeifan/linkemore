package cn.linkmore.ops.biz.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqEnterprise;
import cn.linkmore.ops.biz.service.EnterpriseService;
import cn.linkmore.ops.security.request.ReqPerson;
/**
 * @author   GFF
 * @Date     2018年6月25日
 * @Version  v2.0
 */
import cn.linkmore.prefecture.client.EnterpriseClient;
@Service
public class EnterpriseServiceImpl implements EnterpriseService {

	@Resource
	private EnterpriseClient enterpriseClient;

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
	public Object selectAll() {
		Object object = this.enterpriseClient.selectAll();
		return object;
	}

	@Override
	public void setPassword(ReqPerson person) {
//		this.enterpriseClient.setPassword(person);
	}
	
	
}
