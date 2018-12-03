package cn.linkmore.account.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.UserClusterMapper;
import cn.linkmore.account.dao.cluster.UserStaffClusterMapper;
import cn.linkmore.account.dao.master.UserStaffMasterMapper;
import cn.linkmore.account.entity.UserStaff;
import cn.linkmore.account.request.ReqCheck;
import cn.linkmore.account.request.ReqUserStaff;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResUserStaff;
import cn.linkmore.account.service.UserStaffService;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.util.DomainUtil;
/**
 * @author   GFF
 * @Date     2018年5月17日
 * @Version  v2.0
 */
@Service
public class UserStaffServiceImpl implements UserStaffService {

	@Resource
	private UserStaffClusterMapper userStaffClusterMapper;
	@Resource
	private UserStaffMasterMapper userStaffMasterMapper;
	@Resource
	private UserClusterMapper userClusterMapper;

	@Override
	public ResUserStaff findById(Long userId) {
		return userStaffClusterMapper.findById(userId);
	}

	@Override
	public void save(ReqUserStaff record) {
		ResUser user = this.userClusterMapper.findByMobile(record.getUsername());
		if(user != null){
			record.setCreateTime(new Date());
			record.setId(user.getId());
			this.userStaffMasterMapper.saveReq(record);
		}else {
			throw new DataException("对应账号不存在");
		}
	}

	@Override
	public void update(ReqUserStaff record) {
		this.userStaffMasterMapper.updateReq(record);
	}

	@Override
	public Integer check(ReqCheck check) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", check.getProperty());
		param.put("value", check.getValue()); 
		param.put("id", check.getId());
		return this.userStaffClusterMapper.check(param);
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
		Integer count = this.userStaffClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<UserStaff> list = this.userStaffClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public ResUserStaff findByMobile(String mobile) {
		return this.userStaffClusterMapper.findByMobile(mobile);
	}
	
	
	
}
