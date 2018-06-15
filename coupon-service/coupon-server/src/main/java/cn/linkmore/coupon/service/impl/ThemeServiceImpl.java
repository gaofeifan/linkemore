package cn.linkmore.coupon.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.dao.cluster.ThemeClusterMapper;
import cn.linkmore.coupon.dao.master.ThemeMasterMapper;
import cn.linkmore.coupon.entity.Theme;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTheme;
import cn.linkmore.coupon.response.ResEnterpriseBean;
import cn.linkmore.coupon.response.ResTheme;
import cn.linkmore.coupon.service.ThemeService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;

@Service
public class ThemeServiceImpl implements ThemeService {
	@Autowired
	private ThemeClusterMapper themeClusterMapper;
	@Autowired
	private ThemeMasterMapper themeMasterMapper;
	
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
		Integer count = this.themeClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResTheme> list = this.themeClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public int save(ReqTheme record) { 
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", "enterprise_id");
		param.put("value", record.getEnterpriseId()); 
		Integer count = this.themeClusterMapper.check(param); 
		if(count>0){
			throw new DataException("请勿重复定制企业停车券主题");
		}
		record.setCreateTime(new Date());
		Theme theme = ObjectUtils.copyObject(record, new Theme());
		return this.themeMasterMapper.save(theme);
	}
	
	@Override
	public int update(ReqTheme record) {
		Theme theme = ObjectUtils.copyObject(record, new Theme());
		return this.themeMasterMapper.update(theme);
	}

	@Override
	public int delete(Long id) {
		return this.themeMasterMapper.delete(id); 
	}
	
	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.themeClusterMapper.check(param); 
	}

	@Override
	public List<ResEnterpriseBean> findEnterpriseList() {
		return this.themeClusterMapper.findEnterpriseList();
	}

	
}
