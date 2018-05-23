package cn.linkmore.security.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.dao.cluster.DictGroupClusterMapper;
import cn.linkmore.security.dao.master.DictGroupMasterMapper;
import cn.linkmore.security.entity.DictGroup;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.service.DictGroupService;
import cn.linkmore.util.DomainUtil;

/**
 * Service实现类 -权限模块 - 字典分类信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class DictGroupServiceImpl implements DictGroupService {

	@Autowired
	private DictGroupClusterMapper dictGroupClusterMapper;
	@Autowired
	private DictGroupMasterMapper dictGroupMasterMapper;
	
	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.dictGroupClusterMapper.check(param); 
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
		Integer count = this.dictGroupClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<DictGroup> list = this.dictGroupClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public void save(DictGroup record) {
		record.setCreateTime(new Date());
		this.dictGroupMasterMapper.save(record);
	}
	
	@Override
	public DictGroup update(DictGroup record) {
		this.dictGroupMasterMapper.update(record);
		return record;
	}

	@Override
	public int delete(List<Long> ids) {
		return this.dictGroupMasterMapper.delete(ids); 
	}
	
}
