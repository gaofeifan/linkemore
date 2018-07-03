package cn.linkmore.account.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.WhitelistClusterMapper;
import cn.linkmore.account.dao.master.WhitelistMasterMapper;
import cn.linkmore.account.entity.Whitelist;
import cn.linkmore.account.request.ReqWhitelist;
import cn.linkmore.account.service.WhitelistService;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.util.DomainUtil;

/**
 * 权限类接口实现
 * @author   GFF
 * @Date     2018年6月22日
 * @Version  v2.0
 */
@Service
public class WhitelistServiceImpl implements WhitelistService {

	@Resource
	private WhitelistClusterMapper clusterMapper;
	@Resource
	private WhitelistMasterMapper masterMapper;
	
	@Override
	public Integer check(String property, String value, Long id) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", property);
		param.put("value", value); 
		param.put("id", id);
		return this.clusterMapper.check(param);
	}

	@Override
	public void save(ReqWhitelist record) {
		record.setCreateTime( new Date());
		this.masterMapper.saveReq(record);
	}

	@Override
	public void update(ReqWhitelist record) {
		this.masterMapper.updateReqSelective(record);
	}

	@Override
	public void delete(List<Long> ids) {
		this.masterMapper.deleteIds(ids);
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
		Integer count = this.clusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<Whitelist> list = this.clusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	
}
