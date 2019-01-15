package cn.linkmore.ops.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.account.client.UserGroupClient;
import cn.linkmore.account.response.ResUserGroup;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.BaseVersionGroupClient;
import cn.linkmore.common.request.ReqBaseAppVersionGroup;
import cn.linkmore.common.response.ResBaseAppVersionGroup;
import cn.linkmore.ops.biz.service.BaseUserVersionGroupService;

@Service
public class BaseUserVersionGroupServiceImpl implements BaseUserVersionGroupService {

	@Resource
	private BaseVersionGroupClient baseVersionGroupClient;
	
	@Resource
	private UserGroupClient userGroupClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return baseVersionGroupClient.findPage(pageable);
	}

	@Override
	public int save(ReqBaseAppVersionGroup reqBaseAppVersionGroup) {
		return baseVersionGroupClient.insert(reqBaseAppVersionGroup);

	}

	@Override
	public int delete(List<Long> ids) {
		return baseVersionGroupClient.deleteByIds(ids);

	}

	@Override
	public List<ResUserGroup> userGroupList(Map<String, Object> map) {
		List<ResUserGroup> allUserGroup = userGroupClient.findList(map);
		List<ResBaseAppVersionGroup> versionUserGroup = baseVersionGroupClient.findList(map);
		
		List<ResUserGroup> groupList=new ArrayList<ResUserGroup>();
		for(ResUserGroup resUserGroup:allUserGroup) {
			boolean exist=false;
			for(ResBaseAppVersionGroup resBaseAppVersionGroup:versionUserGroup) {
				if(resUserGroup.getId()==resBaseAppVersionGroup.getUserGroupId()) {
					exist=true;
					break;
				}
			}
			if(!exist) {
				groupList.add(resUserGroup);
			}
		}

		return groupList;
		
	}

}
