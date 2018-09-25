package cn.linkmore.prefecture.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqStrategyTime;
import cn.linkmore.prefecture.response.ResStrategyTime;

public interface StrategyTimeService {
	 /**
	  * 保存
	  * @param reqStrategyInterval
	  * @return
	  */
	int save(ReqStrategyTime reqStrategyInterval);
	/**
	 * 更新
	 * @param reqStrategyInterval
	 * @return
	 */
	int update (ReqStrategyTime reqStrategyInterval);
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids);
	
	
	/**
	 * 更改状态-开启/关闭
	 * @param ids
	 * @return
	 */
	int updateStatus(Map<String, Object> map);

	/**
	 * 分页列表
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);
	/**
	 * 列表
	 * @param pageable
	 * @return
	 */
	List<ResStrategyTime> findList(Map<String, Object> param);
	/**
	 * 获取单个对象
	 * @param id
	 * @return
	 */
	ResStrategyTime selectByPrimaryKey(Long id);

}
