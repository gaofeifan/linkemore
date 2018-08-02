package cn.linkmore.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.EntRentedRecordClusterMapper;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.enterprise.service.RentedRecordService;
import cn.linkmore.util.DomainUtil;
/**
 * 接口实现
 * @author   GFF
 * @Date     2018年7月30日
 * @Version  v2.0
 */
@Service
public class RentedRecordServiceImpl implements RentedRecordService {

	@Resource
	private EntRentedRecordClusterMapper entRentedRecordClusterMapper;
	
	@Override
	public ViewPage findList(ViewPageable pageable) {
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
		Integer count = this.entRentedRecordClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResEnterprise> list = this.entRentedRecordClusterMapper.findPage(param);
		return new ViewPage(count, pageable.getPageSize(), list);
	}

	
	
}
