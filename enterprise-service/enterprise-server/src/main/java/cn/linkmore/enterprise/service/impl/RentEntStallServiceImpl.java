package cn.linkmore.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.RentEntStallClusterMapper;
import cn.linkmore.enterprise.dao.master.RentEntStallMasterMapper;
import cn.linkmore.enterprise.entity.RentEntStall;
import cn.linkmore.enterprise.request.ReqRentEntStall;
import cn.linkmore.enterprise.service.RentEntStallService;
import cn.linkmore.util.DomainUtil;
@Service
public class RentEntStallServiceImpl implements RentEntStallService {

	@Resource
	private RentEntStallClusterMapper rentEntStallClusterMapper;
	@Resource
	private RentEntStallMasterMapper rentEntStallMasterMapper;
	
	@Override
	public RentEntStall selectByPrimaryKey(Long rentEntId) {
		return this.rentEntStallClusterMapper.findById(rentEntId);
	}

	@Override
	public void saveBatch(List<ReqRentEntStall> list) {
		this.rentEntStallMasterMapper.saveBatch(list);
	}

	@Override
	public ViewPage stallListCompany(ViewPageable pageable) {
		Map<String,Object> param = new HashMap<String,Object>(); 
		List<ViewFilter> filters = pageable.getFilters();
		if(StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if(filters!=null&&filters.size()>0) {
			for(ViewFilter filter:filters) {
				param.put(filter.getProperty(), filter.getValue());
			}		}
		if(StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.rentEntStallClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<RentEntStall> list = this.rentEntStallClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}
	@Override
	public List<RentEntStall> stallListCompany(Long companyid) {
		List<RentEntStall> list = this.rentEntStallClusterMapper.stallListCompany(companyid);
		return list; 
	}

	
}
