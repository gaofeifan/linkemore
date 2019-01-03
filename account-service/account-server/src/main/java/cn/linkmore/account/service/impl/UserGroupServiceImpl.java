package cn.linkmore.account.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.UserGroupClusterMapper;
import cn.linkmore.account.dao.master.UserGroupDetailMasterMapper;
import cn.linkmore.account.dao.master.UserGroupInputMasterMapper;
import cn.linkmore.account.dao.master.UserGroupMasterMapper;
import cn.linkmore.account.entity.UserGroup;
import cn.linkmore.account.request.ReqUserGroup;
import cn.linkmore.account.response.ResUserGroup;
import cn.linkmore.account.service.UserGroupService;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;

@Service
public class UserGroupServiceImpl implements UserGroupService{

	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserGroupMasterMapper userGroupMasterMapper;
	
	@Autowired
	private UserGroupClusterMapper userGroupClusterMapper;

	@Override
	public int insert(ReqUserGroup record) {
		UserGroup userGroup=new UserGroup();
		userGroup = ObjectUtils.copyObject(record, userGroup);
		int count=userGroupMasterMapper.insert(userGroup);
		record.setId(userGroup.getId());
		return count;
	}

	@Override
	public int insertSelective(ReqUserGroup record) {
		UserGroup userGroup=new UserGroup();
		userGroup = ObjectUtils.copyObject(record, userGroup);
		return userGroupMasterMapper.insertSelective(userGroup);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return userGroupMasterMapper.deleteByPrimaryKey(id);		
	}

	@Override
	public int deleteByIds(List<Long> ids) {
		return userGroupMasterMapper.deleteByIds(ids);
	}

	@Override
	public int updateByPrimaryKey(ReqUserGroup record) {
		UserGroup userGroup=new UserGroup();
		userGroup = ObjectUtils.copyObject(record, userGroup);
		return userGroupMasterMapper.updateByPrimaryKey(userGroup);
	}

	@Override
	public int updateByPrimaryKeySelective(ReqUserGroup record) {
		UserGroup userGroup=new UserGroup();
		userGroup = ObjectUtils.copyObject(record, userGroup);
		return userGroupMasterMapper.updateByPrimaryKeySelective(userGroup);
	}

	@Override
	public int updateStatus(Map<String, Object> map) {
		return userGroupMasterMapper.updateStatus(map);
	}

	@Override
	public ResUserGroup selectByPrimaryKey(Long id) {
		UserGroup userGroup=userGroupClusterMapper.selectByPrimaryKey(id);
		ResUserGroup resUserGroup=new ResUserGroup();
		resUserGroup = ObjectUtils.copyObject(userGroup, resUserGroup);
		return resUserGroup;
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
		Integer count = this.userGroupClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResUserGroup> list = this.userGroupClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list);
	}

	@Override
	public List<ResUserGroup> findList(Map<String, Object> param) {
		return userGroupClusterMapper.findList(param);
	}

}
