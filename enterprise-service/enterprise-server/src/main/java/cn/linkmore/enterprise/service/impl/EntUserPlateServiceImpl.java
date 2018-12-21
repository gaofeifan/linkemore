/**
 * 
 */
package cn.linkmore.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.EntUserPlateClusterMapper;
import cn.linkmore.enterprise.dao.master.EntUserPlateMasterMapper;
import cn.linkmore.enterprise.entity.EntUserPlate;
import cn.linkmore.enterprise.request.ReqEntUserPlate;
import cn.linkmore.enterprise.service.EntUserPlateService;
import cn.linkmore.util.DomainUtil;

/**
 * 企业免费车牌
 * @author jiaohanbin
 * @Date 2018年12月20日
 * @Version v2.0
 */
@Service
public class EntUserPlateServiceImpl implements EntUserPlateService {
	@Resource
	private EntUserPlateClusterMapper entUserPlateClusterMapper;
	@Resource
	private EntUserPlateMasterMapper entUserPlateMasterMapper;
	
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
		Integer count = this.entUserPlateClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<EntUserPlate> list = this.entUserPlateClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public void save(EntUserPlate entUserPlate) {
		this.entUserPlateMasterMapper.save(entUserPlate);
		
	}

	@Override
	public void update(EntUserPlate entUserPlate) {
		this.entUserPlateMasterMapper.update(entUserPlate);
	}

	@Override
	public void delete(List<Long> ids) {
		this.entUserPlateMasterMapper.delete(ids);
	}

	@Override
	public int exists(String plateNo) {
		return this.entUserPlateClusterMapper.exists(plateNo);
	}

	@Override
	public int saveBatch(List<ReqEntUserPlate> plateList) {
		return this.entUserPlateMasterMapper.saveBatch(plateList);
	}
	
}
