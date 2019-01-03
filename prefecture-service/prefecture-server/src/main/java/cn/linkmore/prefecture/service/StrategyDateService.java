package cn.linkmore.prefecture.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqStrategyDate;
import cn.linkmore.prefecture.response.ResStrategyDate;

public interface StrategyDateService {

	int insert(ReqStrategyDate record);

    int deleteByPrimaryKey(Long id);

	int updateByPrimaryKey(ReqStrategyDate record);

	ResStrategyDate selectByPrimaryKey(Long id);
	
	ViewPage findPage(ViewPageable pageable);

	int delete(List<Long> ids);

	int updateStatus(Map<String, Object> map);

	List<ResStrategyDate> findList(Map<String, Object> param);

	int updatePublic(Map<String, Object> map);

}
