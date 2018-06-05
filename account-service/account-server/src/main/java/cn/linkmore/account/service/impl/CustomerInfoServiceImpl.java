package cn.linkmore.account.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.CustomerInfoClusterMapper;
import cn.linkmore.account.dao.master.CustomerInfoMasterMapper;
import cn.linkmore.account.request.ReqCustomerInfoExport;
import cn.linkmore.account.response.ResCustomerInfoExport;
import cn.linkmore.account.service.CustomerInfoService;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.util.DomainUtil;
/**
 * 用户数据录入--实现
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@Service
public class CustomerInfoServiceImpl implements CustomerInfoService {

	@Resource
	private CustomerInfoClusterMapper customerInfoClusterMapper;
	@Resource
	private CustomerInfoMasterMapper customerInfoMasterMapper;
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
	
		if(StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty().replaceAll(" ", "")));
			param.put("direction", pageable.getOrderDirection());
		}
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResCustomerInfoExport>list = this.customerInfoClusterMapper.findPage(param);
		Integer count = this.customerInfoClusterMapper.count(param);
		return new ViewPage(count, pageable.getPageSize(), list);
	}

	@Override
	public List<ResCustomerInfoExport> getExportList(ReqCustomerInfoExport pageable) {
		return this.customerInfoClusterMapper.getExportList(pageable);
	}

}
