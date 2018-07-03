package cn.linkmore.prefecture.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.dao.cluster.ApplicationGroupClusterMapper;
import cn.linkmore.prefecture.dao.master.ApplicationGroupMasterMapper;
import cn.linkmore.prefecture.entity.ApplicationGroup;
import cn.linkmore.prefecture.request.ReqApplicationGroup;
import cn.linkmore.prefecture.response.ResApplicationGroup;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;

/**
 * 应用程序接口实现
 * @author   GFF
 * @Date     2018年6月23日
 * @Version  v2.0
 */
@Service
public class ApplicationGroupServiceImpl implements ApplicationGroupService {

	@Resource
	private ApplicationGroupClusterMapper applicationGroupClusterMapper;
	@Resource
	private ApplicationGroupMasterMapper applicationGroupMasterMapper;
	
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
		Integer count = this.applicationGroupClusterMapper.count(param);
		List<ResApplicationGroup> list = new ArrayList<>();
		if(count > 0){
			param.put("start", pageable.getStart());
			param.put("pageSize", pageable.getPageSize());
			list = this.applicationGroupClusterMapper.findPage(param);
		}
		/*for (ResApplicationGroup group : list) {
			//根据user_group_id 获取userGroup的 user_ids,如果为空 则状态为  结束
			UserGroup userGroup = userGroupMapper.selectById(group.getUserGroupId());
			if(null != userGroup){
				group.setUserGroup(userGroup.getName());
				String 	userIds = redisTemplate.opsForValue().get(RedisKey.USER_GROUP_IDS+userGroup.getId());
				if(!StringUtils.isNotBlank(userIds)){
					group.setApplicationStatus("已结束");
					continue;
				}
				if(group.getStatus()==1){
					group.setApplicationStatus("已启动");
				}else if(group.getStatus()==0){
					group.setApplicationStatus("已关闭");
				}
			}else{
				group.setApplicationStatus("已结束");
			}
		}*/
		return new ViewPage(count, pageable.getPageSize(), list);
	}

	@Override
	public void add(ReqApplicationGroup requestBean) {
		ApplicationGroup group = ObjectUtils.copyObject(requestBean, new ApplicationGroup());
		group.setCreateTime(new Date());
		group.setUpdateTime(new Date());
		group.setStatus((short) 0);
		this.applicationGroupMasterMapper.save(group);
	}

	@Override
	public void start(List<Long> ids) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ids", ids);
		param.put("status", 1);
		param.put("updateTime", new Date());
		this.applicationGroupMasterMapper.startOrDown(param);
		
	}

	@Override
	public void down(List<Long> ids) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ids", ids);
		param.put("status", 0);
		param.put("updateTime", new Date());
		this.applicationGroupMasterMapper.startOrDown(param);
	}

	@Override
	public Integer check(String property, String value, Long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("property", property);
		param.put("value", value);
		param.put("id", id);
		return this.applicationGroupClusterMapper.check(param);
	}

}
