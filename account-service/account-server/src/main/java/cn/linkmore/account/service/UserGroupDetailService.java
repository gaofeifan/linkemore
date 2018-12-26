package cn.linkmore.account.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.account.request.ReqUserGroupDetail;
import cn.linkmore.account.response.ResUserGroupDetail;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

public interface UserGroupDetailService {
	
	int deleteByPrimaryKey(Long id);

	int deleteByIds(List<Long> ids);

	int deleteByGroupIds(List<Long> ids);

	int insertByUserIds(Map<String, Object> map);
	
	int insert(ReqUserGroupDetail record);

	int insertSelective(ReqUserGroupDetail record);

	int updateByPrimaryKeySelective(ReqUserGroupDetail record);

	int updateByPrimaryKey(ReqUserGroupDetail record);

	ResUserGroupDetail selectByPrimaryKey(Long id);

	ViewPage findPage(ViewPageable pageable);

}
