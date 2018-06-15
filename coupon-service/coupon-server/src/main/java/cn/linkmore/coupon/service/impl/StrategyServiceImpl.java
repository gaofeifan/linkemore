package cn.linkmore.coupon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.dao.cluster.StrategyClusterMapper;
import cn.linkmore.coupon.dao.master.StrategyMasterMapper;
import cn.linkmore.coupon.entity.Strategy;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqStrategy;
import cn.linkmore.coupon.response.ResStrategy;
import cn.linkmore.coupon.service.StrategyService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;

@Service
public class StrategyServiceImpl implements StrategyService {
	
	@Autowired
	private StrategyClusterMapper strategyClusterMapper; 
	
	@Autowired
	private StrategyMasterMapper strategyMasterMapper; 
	
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
		Integer count = this.strategyClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResStrategy> list = this.strategyClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public int save(ReqStrategy record) { 
		Strategy strategy = ObjectUtils.copyObject(record, new Strategy());
		return this.strategyMasterMapper.save(strategy);
	}
	
	@Override
	public int update(ReqStrategy record) {
		Strategy strategy = ObjectUtils.copyObject(record, new Strategy());
		return this.strategyMasterMapper.update(strategy);
	}

	@Override
	public int delete(Long id) {
		return this.strategyMasterMapper.delete(id); 
	}
	
	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.strategyClusterMapper.check(param); 
	}
}
