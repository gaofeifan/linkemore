package cn.linkmore.prefecture.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.dao.cluster.PrefectureGroupClusterMapper;
import cn.linkmore.prefecture.dao.master.PrefectureGroupMasterMapper;
import cn.linkmore.prefecture.entity.PrefectureGroup;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqPrefectureGroup;
import cn.linkmore.prefecture.response.ResPrefectureGroup;
import cn.linkmore.prefecture.service.PreGroupService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;

@Service
public class PreGroupServiceImpl implements PreGroupService {

	@Autowired
	private PrefectureGroupClusterMapper groupClusterMapper;
	
	@Autowired
	private PrefectureGroupMasterMapper groupMasterMapper;

	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.groupClusterMapper.check(param);
	}

	@Override
	public int save(ReqPrefectureGroup reqGroup) {
		reqGroup.setCreateTime(new Date());
		reqGroup.setUpdateTime(new Date());
		//操作人id
		//group.setOperatorId(person.getId());
		reqGroup.setStatus((short)1);
		PrefectureGroup group = new PrefectureGroup();
		group = ObjectUtils.copyObject(reqGroup, group);
		return this.groupMasterMapper.save(group);
	}

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
		Integer count = this.groupClusterMapper.count(param);
		List<ResPrefectureGroup> list = new ArrayList<ResPrefectureGroup>();
		if(count > 0){
			param.put("start", pageable.getStart());
			param.put("pageSize", pageable.getPageSize());
			list = this.groupClusterMapper.findPage(param);
		}
		for (ResPrefectureGroup group : list) {
			group.setPreCount(group.getPreIds().split(",").length);
		}
		return new ViewPage(count, pageable.getPageSize(), list);
	}

	@Override
	public int start(List<Long> ids) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ids", ids);
		param.put("status", 1);
		param.put("updateTime", new Date());
		return this.groupMasterMapper.startOrDown(param);
	}
	@Override
	public int down(List<Long> ids) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ids", ids);
		param.put("status", 0);
		param.put("updateTime", new Date());
		return this.groupMasterMapper.startOrDown(param);
	}

}
