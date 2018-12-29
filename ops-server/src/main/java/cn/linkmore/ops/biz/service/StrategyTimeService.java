package cn.linkmore.ops.biz.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqStrategyTime;
import cn.linkmore.prefecture.response.ResStrategyTime;

/**
 * 
 * @Description - 时段策略
 * @Author lilinhai
 * @version 
 */
public interface StrategyTimeService {

	ViewPage findPage(ViewPageable pageable);

	List<ResStrategyTime> findList(Map<String, Object> map);

	int save(ReqStrategyTime record);

	int update(ReqStrategyTime record);
	
	int updateStatus(Map<String, Object> map);
	
	int updatePublic(Map<String, Object> map);
	
	int delete(List<Long> ids);

	ResStrategyTime selectByPrimaryKey(Long id);

	

	//Boolean check(ReqCheck reqCheck);

}
