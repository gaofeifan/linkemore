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
import cn.linkmore.common.dao.cluster.DistrictClusterMapper;
import cn.linkmore.common.dao.master.DistrictMasterMapper;
import cn.linkmore.common.entity.District;
import cn.linkmore.common.response.ResDistrict;
import cn.linkmore.common.service.DistrictService;
import cn.linkmore.util.EntityUtil;
/**
 * Service实现类 - 区域信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class DistrictServiceImpl implements DistrictService {
	
	@Autowired
	private DistrictClusterMapper districtClusterMapper;
	
	@Autowired
	private DistrictMasterMapper districtMasterMapper;

	public ResDistrict find(Long id) {
		return this.districtClusterMapper.findById(id);
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
		Integer count = this.districtClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResDistrict> list = this.districtClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}
	@Override
	public int save(District record) {
		return this.districtMasterMapper.save(record);
	}
	
	public int update(District record) {
		return this.districtMasterMapper.update(record);
	}
	public int delete(Long id) {
		return this.districtMasterMapper.delete(id); 
	}
	
	public Integer check(String property, String value, Long id) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", property.replaceAll(" ", ""));
		param.put("value", value);
		param.put("id", id);
		return this.districtClusterMapper.exists(param);
	}
	
	@Override
	public int delete(List<Long> ids) {
		return 0; 
	}
	
}
