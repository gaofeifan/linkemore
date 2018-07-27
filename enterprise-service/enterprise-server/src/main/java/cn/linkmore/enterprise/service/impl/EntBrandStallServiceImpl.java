package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.EntBrandStallClusterMapper;
import cn.linkmore.enterprise.dao.master.EntBrandStallMasterMapper;
import cn.linkmore.enterprise.entity.EntBrandStall;
import cn.linkmore.enterprise.request.ReqEntBrandStall;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.response.ResBrandStall;
import cn.linkmore.enterprise.service.EntBrandStallService;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
/**
 * 品牌车位
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class EntBrandStallServiceImpl implements EntBrandStallService {
	@Resource
	private EntBrandStallMasterMapper entBrandStallMasterMapper;
	
	@Resource
	private EntBrandStallClusterMapper entBrandStallClusterMapper;
	
	@Resource
	private StallClient stallClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<ViewFilter> filters = pageable.getFilters();
		if (StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if (filters != null && filters.size() > 0) {
			for (ViewFilter filter : filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		if (StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.entBrandStallClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResBrandStall> list = this.entBrandStallClusterMapper.findPage(param);
		return new ViewPage(count, pageable.getPageSize(), list);
	}

	@Override
	public List<ResBrandStall> findList(Map<String, Object> param) {
		return null;
	}

	@Override
	public int save(ReqEntBrandStall record) {
		if(StringUtils.isNotBlank(record.getStallIdJson())) {
			String[] stallIds = record.getStallIdJson().split(",");
			List<Long> ids = new ArrayList<Long>();
			for(String id: stallIds) {
				ids.add(Long.valueOf(id));
				EntBrandStall brandStall = new EntBrandStall();
				brandStall = ObjectUtils.copyObject(record, brandStall);
				brandStall.setStallId(Long.valueOf(id));
				brandStall.setCreateTime(new Date());
				brandStall.setUpdateTime(new Date());
				brandStall.setStatus((short)1);
				entBrandStallMasterMapper.save(brandStall);
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("brand", "1");
			map.put("list", ids);
			this.stallClient.updateBrand(map);
		}
		return 0;
	}

	@Override
	public int update(ReqEntBrandStall record) {
		EntBrandStall brandStall = new EntBrandStall();
		brandStall = ObjectUtils.copyObject(record, brandStall);
		return entBrandStallMasterMapper.update(brandStall);
	}

	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.entBrandStallClusterMapper.check(param);
	}

	@Override
	public int delete(List<Long> ids) {
		List<ResBrandStall> stallList = this.entBrandStallClusterMapper.findByIds(ids);
		List<Long> stallIds = new ArrayList<Long>();
		if(CollectionUtils.isNotEmpty(stallList)) {
			for(ResBrandStall stall: stallList) {
				stallIds.add(stall.getStallId());
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("brand", "0");
			map.put("list", stallIds);
			this.stallClient.updateBrand(map);
		}
		return entBrandStallMasterMapper.delete(ids);
	}
	
}
