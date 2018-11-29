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
import cn.linkmore.enterprise.dao.cluster.RentEntUserClusterMapper;
import cn.linkmore.enterprise.dao.master.RentEntUserMasterMapper;
import cn.linkmore.enterprise.entity.RentEntUser;
import cn.linkmore.enterprise.request.ReqRentEntUser;
import cn.linkmore.enterprise.service.RentEntUserService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
@Service
public class RentEntUserServiceImpl implements RentEntUserService {

	@Resource
	private RentEntUserClusterMapper rentEntUserClusterMapper;
	@Resource
	private RentEntUserMasterMapper rentEntUserMasterMapper;
	
	@Override
	public RentEntUser selectByPrimaryKey(Long rentEntId) {
		return this.rentEntUserClusterMapper.selectByPrimaryKey(rentEntId);
	}
	
	@Override
	public void save(ReqRentEntUser user) {
		this.rentEntUserMasterMapper.insert(ObjectUtils.copyObject(user, new RentEntUser()));
	}
	
	@Override
	public void update(ReqRentEntUser user) {
		this.rentEntUserMasterMapper.updateByPrimaryKey(ObjectUtils.copyObject(user, new RentEntUser()));
	}
	@Override
	public void deleteIds(List<Long> ids) {
		this.rentEntUserMasterMapper.deleteIds(ids);
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
			}		}
		if(StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.rentEntUserClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<RentEntUser> list = this.rentEntUserClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}
	
	

}
