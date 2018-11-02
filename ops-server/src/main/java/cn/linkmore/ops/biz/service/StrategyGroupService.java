package cn.linkmore.ops.biz.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqStrategyGroup;
import cn.linkmore.prefecture.request.ReqStrategyGroupDetail;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStrategyGroup;
import cn.linkmore.prefecture.response.ResStrategyGroupArea;

/**
 * 
 * @Description - 分组策略
 * @Author lilinhai
 * @version 
 */
public interface StrategyGroupService {

	int save(ReqStrategyGroup record);

	int update(ReqStrategyGroup record);
	
	int updateStatus(Map<String, Object> map);
	
	int delete(List<Long> ids);

	ResStrategyGroup selectByPrimaryKey(Long id);
	
	ViewPage findPage(ViewPageable pageable);

	Tree findTree(Map<String, Object> param);

	List<ResStall> findAreaStall(Map<String, Object> param);

	int deleteStall(Map<String, Object> map);

	int addStall(ReqStrategyGroupDetail reqStrategyGroupDetail);

	List<ResStrategyGroupArea> selectStallByPrimaryKey(Long id);

	Long existsStall(Map<String, Object> map);

	List<ResStrategyGroup> findList(Map<String, Object> param);
	
}
