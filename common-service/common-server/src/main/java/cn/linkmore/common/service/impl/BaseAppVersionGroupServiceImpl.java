package cn.linkmore.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.linkmore.account.response.ResUserGroup;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.dao.cluster.BaseAppVersionGroupClusterMapper;
import cn.linkmore.common.dao.master.BaseAppVersionGroupMasterMapper;
import cn.linkmore.common.entity.BaseAppVersionGroup;
import cn.linkmore.common.request.ReqBaseAppVersionGroup;
import cn.linkmore.common.response.ResBaseAppVersionGroup;
import cn.linkmore.common.service.BaseAppVersionGroupService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
@Service
public class BaseAppVersionGroupServiceImpl implements BaseAppVersionGroupService {

	@Resource
	private BaseAppVersionGroupMasterMapper baseAppVersionGroupMasterMapper;
	
	@Resource
	private BaseAppVersionGroupClusterMapper baseAppVersionGroupClusterMapper;
		
	@Override
	public int insert(ReqBaseAppVersionGroup record) {
		BaseAppVersionGroup baseAppVersionGroup =new BaseAppVersionGroup();
		baseAppVersionGroup = ObjectUtils.copyObject(record, baseAppVersionGroup);
		return baseAppVersionGroupMasterMapper.insert(baseAppVersionGroup);
	}

	@Override
	public int deleteByIds(List<Long> ids) {
		return baseAppVersionGroupMasterMapper.deleteByIds(ids);
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
		Integer count = this.baseAppVersionGroupClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResBaseAppVersionGroup> list = this.baseAppVersionGroupClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list);
	}

	@Override
	public List<ResBaseAppVersionGroup> findList(Map<String, Object> param) {
		return baseAppVersionGroupClusterMapper.findList(param);
	}

}
