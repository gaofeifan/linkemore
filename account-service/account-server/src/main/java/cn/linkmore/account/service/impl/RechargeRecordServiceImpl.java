package cn.linkmore.account.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.RechargeRecordClusterMapper;
import cn.linkmore.account.request.ReqRechargeRecordExcel;
import cn.linkmore.account.response.ResRechargeRecordExcel;
import cn.linkmore.account.service.RechargeRecordService;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.util.DomainUtil;
/**
 * Service实现类 - 储值记录
 * @author   GFF
 * @Date     2018年5月31日
 * @Version  v2.0
 */
@Service
public class RechargeRecordServiceImpl implements RechargeRecordService {
	@Autowired
	private RechargeRecordClusterMapper rechargeRecordMapper; 
	
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
		Integer count = this.rechargeRecordMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResRechargeRecordExcel> list = this.rechargeRecordMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public List< ResRechargeRecordExcel> exportList(ReqRechargeRecordExcel bean) {
		return this.rechargeRecordMapper.findExportList(bean);
		 
	} 
}
