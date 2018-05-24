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
import cn.linkmore.security.dao.cluster.PageClusterMapper;
import cn.linkmore.security.dao.master.PageMasterMapper;
import cn.linkmore.security.entity.Page;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.response.ResPage;
import cn.linkmore.security.service.PageService;
import cn.linkmore.util.DomainUtil;

/**
 * Service实现类 -权限模块 - 页面信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class PageServiceImpl implements PageService {

	@Autowired
	private PageClusterMapper pageClusterMapper;
	
	@Autowired
	private PageMasterMapper pageMasterMapper;
	
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
		Integer count = this.pageClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResPage> list = this.pageClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public void save(Page page) {
		page.setCreateTime(new Date());
		this.pageMasterMapper.save(page);
	}
	
	@Override
	public Page update(Page page) {
		this.pageMasterMapper.update(page);
		return page;
	}

	@Override
	public int delete(List<Long> ids) {
		return this.pageMasterMapper.delete(ids); 
	}
	
	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.pageClusterMapper.check(param); 
	}
	
}
