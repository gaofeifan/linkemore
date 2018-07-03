package cn.linkmore.ops.biz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqEnterpriseDeal;
import cn.linkmore.enterprise.response.ResEnterpriseDeal;
import cn.linkmore.ops.biz.service.EnterpriseDealService;
/**
 * 
 * @author   GFF
 * @Date     2018年6月25日
 * @Version  v2.0
 */
import cn.linkmore.prefecture.client.EnterpriseDealClient;
@Service
public class EnterpriseDealServiceImpl implements EnterpriseDealService {

	@Resource
	private EnterpriseDealClient enterpriseDealClient;

	@Override
	public void save(ReqEnterpriseDeal deal) {
		this.enterpriseDealClient.save(deal);
	}

	@Override
	public void update(ReqEnterpriseDeal deal) {
		this.enterpriseDealClient.update(deal);
	}

	@Override
	public void delete(List<Long> ids) {
		this.enterpriseDealClient.delete(ids);
	}
	
	@Override
	public Boolean check(String property, String value, Long id) {
		ReqCheck reqCheck = new ReqCheck();
		reqCheck.setId(id);
		reqCheck.setValue(value);
		reqCheck.setProperty(property);
		Boolean check = this.enterpriseDealClient.check(reqCheck );
		return check;
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		ViewPage page = this.enterpriseDealClient.list(pageable);
		return page;
	}

	@Override
	public List<Tree> findTree() {
		List<Tree> tree = this.enterpriseDealClient.tree();
		return tree;
	}

	@Override
	public Map<String, Object> map() {
		Map<String, Object> map = this.enterpriseDealClient.map();
		return map;
	}

	@Override
	public List<ResEnterpriseDeal> listByEnterpriseId(Integer enterpriseId, Integer isCreate) {
		Map<String, Object> map = new HashMap<>();
		map.put("enterpriseId", enterpriseId);
		map.put("isCreate", isCreate);
		List<ResEnterpriseDeal> list = this.enterpriseDealClient.listByEnterpriseId(map);
		return list;
	}
	
	
}
