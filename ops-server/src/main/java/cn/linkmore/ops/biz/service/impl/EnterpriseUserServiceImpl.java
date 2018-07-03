package cn.linkmore.ops.biz.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEnterpriseUser;
import cn.linkmore.enterprise.response.ResEnterpriseUser;
import cn.linkmore.ops.biz.service.EnterpriseUserService;
import cn.linkmore.prefecture.client.EnterpriseUserClient;
import cn.linkmore.security.response.ResPerson;
import cn.linkmore.util.ObjectUtils;
/**
 * @author   GFF
 * @Date     2018年6月25日
 * @Version  v2.0
 */
@Service
public class EnterpriseUserServiceImpl implements EnterpriseUserService {

	@Resource
	private EnterpriseUserClient enterpriseUserClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable, ResPerson person) {
		ViewPage page = this.enterpriseUserClient.list(pageable);
		return page;
	}

	@Override
	public void update(ReqEnterpriseUser enterpriseUser) {
		int update = this.enterpriseUserClient.update(enterpriseUser);
	}

	@Override
	public ResEnterpriseUser getEnterpriseUser(Long id) {
		ResEnterpriseUser res = this.enterpriseUserClient.getEnterpriseUser(id);
		return res;
	}


	@Override
	public void saveAll(List<ReqEnterpriseUser> entAll) {
		this.enterpriseUserClient.saveAll(entAll);
	}

	@Override
	public List<ResEnterpriseUser> findExcel(Map<String, Object> param) {
		return this.enterpriseUserClient.findExcel(param);
	}

	public void delete(ResEnterpriseUser enterpriseUser) {
		enterpriseUser.setStatus((short)0);
		enterpriseUser.setUpdateTime(new Date());
		ReqEnterpriseUser object = ObjectUtils.copyObject(enterpriseUser, new ReqEnterpriseUser());
		this.enterpriseUserClient.update(object);
	}

	
	
	
}
