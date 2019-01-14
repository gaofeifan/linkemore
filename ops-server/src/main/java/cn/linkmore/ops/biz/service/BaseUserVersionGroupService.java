package cn.linkmore.ops.biz.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.account.response.ResUserGroup;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.request.ReqBaseAppVersionGroup;

public interface BaseUserVersionGroupService {

	ViewPage findPage(ViewPageable pageable);

	int save(ReqBaseAppVersionGroup reqBaseAppVersionGroup);

	int delete(List<Long> ids);

	List<ResUserGroup> userGroupList(Map<String, Object> map);

}
