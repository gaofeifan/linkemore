package cn.linkmore.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.dao.cluster.CityClusterMapper;
import cn.linkmore.common.dao.master.CityMasterMapper;
import cn.linkmore.common.entity.City;
import cn.linkmore.common.response.ResCity;
import cn.linkmore.common.service.CityService;
import cn.linkmore.third.client.LocateClient;
import cn.linkmore.util.EntityUtil;
/**
 * Service实现类 - 城市信息
 * @author liwenlong
 * @version 2.0
 *
 */
@Service
public class CityServiceImpl implements CityService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CityClusterMapper cityClusterMapper;
	
	@Autowired
	private CityMasterMapper cityMasterMapper;
	
	@Autowired
	private LocateClient locateClient;

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
	public List<ResCity> findList(Map<String, Object> param) {
		return this.cityClusterMapper.findList(param);
	}
	@Override
	public List<cn.linkmore.common.controller.app.response.ResCity> list(String longitude, String latitude) {
		List<cn.linkmore.common.response.ResCity> list = this.findList(0, 10);
		List<cn.linkmore.common.controller.app.response.ResCity> res = null;
		cn.linkmore.common.controller.app.response.ResCity rc = null;
		Map<String,cn.linkmore.common.controller.app.response.ResCity> rcMap = new HashMap<String,cn.linkmore.common.controller.app.response.ResCity>();
		if (CollectionUtils.isNotEmpty(list)) {
			res = new ArrayList<cn.linkmore.common.controller.app.response.ResCity>();
			for (cn.linkmore.common.response.ResCity re : list) {
				rc = new cn.linkmore.common.controller.app.response.ResCity();
				rc.setId(re.getId()); 
				rc.setName(re.getName());
				rc.setLongitude(re.getLongitude());
				rc.setLatitude(re.getLatitude());
				res.add(rc);
				rcMap.put(re.getCode().substring(0,4), rc);
			}
		}
		if (!rcMap.isEmpty()) {
			rc = null;
			cn.linkmore.third.request.ReqLocate req = new cn.linkmore.third.request.ReqLocate();
			req.setLongitude(longitude);
			req.setLatitude(latitude); 
			cn.linkmore.third.response.ResLocate info = this.locateClient.get(longitude,latitude);
			log.info("locate info = {}",JSON.toJSON(info));
			if(info!=null&&info.getAdcode()!=null) { 
				rc = rcMap.get(info.getAdcode().substring(0, 4));
				if(rc!=null) {
					rc.setStatus(cn.linkmore.common.controller.app.response.ResCity.STATUS_CHECKED);
					rc.setLongitude(longitude);
					rc.setLatitude(latitude);
				}
			}
			if(rc==null) {
				rc = res.get(0);
				rc.setStatus(cn.linkmore.common.controller.app.response.ResCity.STATUS_ASSIGN);
			}
		}
		return res;
	}
	
	
}
