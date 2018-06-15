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
import cn.linkmore.coupon.dao.cluster.RollbackClusterMapper;
import cn.linkmore.coupon.response.ResRollbackBean;
import cn.linkmore.coupon.service.RollbackService;
import cn.linkmore.util.DomainUtil;

@Service
public class RollbackServiceImpl implements RollbackService {
	
	@Autowired
	private RollbackClusterMapper rollbackClusterMapper;
	
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
		
		Integer count = this.rollbackClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResRollbackBean> list = this.rollbackClusterMapper.findPage(param);
		for(ResRollbackBean bean:list){
			bean.setAfterDealPayAmount(bean.getUsedDealPayAmount() - bean.getContractAmount());
			bean.setAfterDealGiftAmount(bean.getUserDealGiftAmount() - bean.getGivenAmount());
		}
		return new ViewPage(count,pageable.getPageSize(),list); 
	}
}
