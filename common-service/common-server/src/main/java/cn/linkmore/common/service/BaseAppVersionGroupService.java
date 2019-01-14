package cn.linkmore.common.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.request.ReqBaseAppVersionGroup;
import cn.linkmore.common.response.ResBaseAppVersionGroup;

/**
 * 版本用户组管理
 * 
 * @author llh
 *
 */
public interface BaseAppVersionGroupService {

	int insert(ReqBaseAppVersionGroup record);

	int deleteByIds(List<Long> ids);
	
	ViewPage findPage(ViewPageable pageable);
	
	List<ResBaseAppVersionGroup> findList(Map<String, Object> param);
}
