package cn.linkmore.account.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.UserGroupDetailClusterMapper;
import cn.linkmore.account.dao.master.UserGroupDetailMasterMapper;
import cn.linkmore.account.entity.UserGroupDetail;
import cn.linkmore.account.request.ReqUserGroupDetail;
import cn.linkmore.account.response.ResGroupUser;
import cn.linkmore.account.response.ResUserGroupDetail;
import cn.linkmore.account.service.UserGroupDetailService;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;

@Service
public class UserGroupDetailServiceImpl implements UserGroupDetailService {

	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserGroupDetailMasterMapper userGroupDetailMasterMapper;
	
	@Autowired
	private UserGroupDetailClusterMapper userGroupDetailClusterMapper;	
	
	
	private UserGroupDetail reqToEntity(ReqUserGroupDetail record) {
		UserGroupDetail userGroupDetail=new UserGroupDetail();
		userGroupDetail = ObjectUtils.copyObject(record, userGroupDetail);
		return userGroupDetail;
	}
	
	private ResUserGroupDetail entityToRes(UserGroupDetail record) {
		ResUserGroupDetail resUserGroupDetail=new ResUserGroupDetail();
		resUserGroupDetail = ObjectUtils.copyObject(record, resUserGroupDetail);
		return resUserGroupDetail;
	}
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return userGroupDetailMasterMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteByIds(List<Long> ids) {
		return userGroupDetailMasterMapper.deleteByIds(ids);
	}

	@Override
	public int deleteByGroupIds(List<Long> ids) {
		return userGroupDetailMasterMapper.deleteByGroupIds(ids);
	}
	
	@Override
	public int insertByUserIds(Map<String, Object> map) {
		return userGroupDetailMasterMapper.insertByUserIds(map);
	}
	
	@Override
	public int insert(ReqUserGroupDetail record) {
		return userGroupDetailMasterMapper.insert(reqToEntity(record));
	}

	@Override
	public int insertSelective(ReqUserGroupDetail record) {
		return userGroupDetailMasterMapper.insertSelective(reqToEntity(record));
	}

	@Override
	public int updateByPrimaryKeySelective(ReqUserGroupDetail record) {
		return userGroupDetailMasterMapper.updateByPrimaryKeySelective(reqToEntity(record));
	}

	@Override
	public int updateByPrimaryKey(ReqUserGroupDetail record) {
		return userGroupDetailMasterMapper.updateByPrimaryKey(reqToEntity(record));
	}

	@Override
	public ResUserGroupDetail selectByPrimaryKey(Long id) {
		return entityToRes(userGroupDetailClusterMapper.selectByPrimaryKey(id));
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
		Integer count = this.userGroupDetailClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResGroupUser> list = this.userGroupDetailClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list);
	}




}
