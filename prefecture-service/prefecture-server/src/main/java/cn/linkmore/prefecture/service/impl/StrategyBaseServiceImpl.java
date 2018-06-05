package cn.linkmore.prefecture.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.dao.cluster.StrategyBaseClusterMapper;
import cn.linkmore.prefecture.dao.master.StrategyBaseMasterMapper;
import cn.linkmore.prefecture.entity.StrategyBase;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqStrategyBase;
import cn.linkmore.prefecture.response.ResStrategyBase;
import cn.linkmore.prefecture.service.StrategyBaseService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
/**
 * Service实现类 - 计费策略信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class StrategyBaseServiceImpl implements StrategyBaseService {
	
	@Autowired
	private StrategyBaseClusterMapper strategyBaseClusterMapper;
	
	@Autowired
	private StrategyBaseMasterMapper strategyBaseMasterMapper;

	@Override
	public StrategyBase findById(Long id) {
		return strategyBaseClusterMapper.findById(id);
	}

	@Override
	public int save(ReqStrategyBase reqStrategyBase) {
		StrategyBase strategyBase = new StrategyBase();
		strategyBase = ObjectUtils.copyObject(reqStrategyBase, strategyBase);
		return this.strategyBaseMasterMapper.update(strategyBase);
	}

	@Override
	public int update(ReqStrategyBase reqStrategyBase) {
		StrategyBase strategyBase = new StrategyBase();
		strategyBase = ObjectUtils.copyObject(reqStrategyBase, strategyBase);
		return this.strategyBaseMasterMapper.save(strategyBase);
	}

	@Override
	public int delete(List<Long> ids) {
		return this.strategyBaseMasterMapper.delete(ids);
	}

	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.strategyBaseClusterMapper.check(param); 
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
		Integer count = this.strategyBaseClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResStrategyBase> list = this.strategyBaseClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

}
