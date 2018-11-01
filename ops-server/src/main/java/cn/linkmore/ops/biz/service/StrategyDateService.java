package cn.linkmore.ops.biz.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqStrategyDate;
import cn.linkmore.prefecture.response.ResStrategyDate;

/**
 * 
 * @Description - 分期策略
 * @Author lilinhai
 * @version 
 */
public interface StrategyDateService {

	ViewPage findPage(ViewPageable pageable);

	List<ResStrategyDate> findList(Map<String, Object> map);

	int save(ReqStrategyDate record);

	int update(ReqStrategyDate record);
	
	int updateStatus(Map<String, Object> map);
	
	int delete(List<Long> ids);

	ResStrategyDate selectByPrimaryKey(Long id);

	//Boolean check(ReqCheck reqCheck);

}
