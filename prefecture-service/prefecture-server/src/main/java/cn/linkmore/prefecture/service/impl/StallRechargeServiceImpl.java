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
import cn.linkmore.prefecture.dao.cluster.StallRechargeClusterMapper;
import cn.linkmore.prefecture.request.ReqStallRechargeExcel;
import cn.linkmore.prefecture.response.ResStallRecharge;
import cn.linkmore.prefecture.service.StallRechargeService;
import cn.linkmore.util.DomainUtil;

/**
 * Service实现类 - 电池更换记录
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class StallRechargeServiceImpl implements StallRechargeService {
	
	@Autowired
	private StallRechargeClusterMapper stallRechargeClusterMapper;
	
	/**
	 * 列表
	 */
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
		Integer count = this.stallRechargeClusterMapper.count(param);
		List<ResStallRecharge> list = new ArrayList<ResStallRecharge>();
		if(count > 0){
			param.put("start", pageable.getStart());
			param.put("pageSize", pageable.getPageSize());
			list = this.stallRechargeClusterMapper.findPage(param);
		}
		return new ViewPage(count,pageable.getPageSize(),list);
	}
	
	 
	/**
	 * 导出
	 */
	@Override
	public List<ResStallRecharge> exportList(
			ReqStallRechargeExcel bean) {
		return this.stallRechargeClusterMapper.findExcelList(bean);
	}

}
