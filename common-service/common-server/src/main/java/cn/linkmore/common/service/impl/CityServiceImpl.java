package cn.linkmore.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.dao.cluster.CityClusterMapper;
import cn.linkmore.common.dao.master.CityMasterMapper;
import cn.linkmore.common.entity.City;
import cn.linkmore.common.response.ResCity;
import cn.linkmore.common.service.CityService;
import cn.linkmore.util.EntityUtil;
/**
 * Service实现类 - 城市信息
 * @author liwenlong
 * @version 2.0
 *
 */
@Service
public class CityServiceImpl implements CityService {
	
	@Autowired
	private CityClusterMapper cityClusterMapper;
	
	@Autowired
	private CityMasterMapper cityMasterMapper;

	@Override
	public ResCity find(Long id) {
		return this.cityClusterMapper.findById(id);
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
			param.put("property", EntityUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.cityClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResCity> list = this.cityClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}
	@Override
	public City save(City record) {
		this.cityMasterMapper.save(record);
		return record;
	}
	@Override
	public ResCity find(String code) {
		return this.cityClusterMapper.findByCode(code); 
	}
	@Override
	public void update(City record) {
		this.cityMasterMapper.update(record);
	}
	@Override
	public int delete(Long id) {
		return this.cityMasterMapper.delete(id); 
	}
	@Override
	public List<ResCity> findList(Integer start,Integer size) {
		Map<String,Object> param = new HashMap<String,Object>();
		if(start==null) {
			start = 0;
		}
		param.put("start", start);
		if(size!=null) {
			param.put("size", size);
		}
		return this.cityClusterMapper.findList(param);
	}
	@Override
	public Integer exists(String property, String value, Long id) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", property.replaceAll(" ", ""));
		param.put("value", value);
		param.put("id", id);
		return this.cityClusterMapper.exists(param);
	}
	
	@Override
	public Integer check(String property, String value, Long id) {
		Integer count = this.cityClusterMapper.check(property, value, id); 
        return count;
	}
	
	@Override
	public void deleteIds(List<Long> ids) {
		this.cityMasterMapper.deleteIds(ids); 
	}
	
	@Override
	public List<City> findList(Map<String, Object> param) {
		return null;
	}
	
	
	
	
}
