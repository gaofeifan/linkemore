package cn.linkmore.prefecture.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.dao.cluster.TargetMonthClusterMapper;
import cn.linkmore.prefecture.response.ResTargetMounth;
import cn.linkmore.prefecture.service.TargetMonthService;
import cn.linkmore.util.DomainUtil;

/**
 * Service实现类 - 车区目标
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class TargetMonthServiceImpl implements TargetMonthService {
	@Autowired
	private TargetMonthClusterMapper targetMounthClusterMapper;
	
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
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty().replaceAll(" ", "")));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.targetMounthClusterMapper.count(param);
		List<ResTargetMounth> list = new ArrayList<ResTargetMounth>();
		if(count>0){
			param.put("start", pageable.getStart());
			param.put("pageSize", pageable.getPageSize());
			list = this.targetMounthClusterMapper.findPage(param);
		}
		return new ViewPage(count,pageable.getPageSize(),list);
	}
}
