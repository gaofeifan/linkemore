package cn.linkmore.prefecture.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqPrefectureStrategy;
import cn.linkmore.prefecture.response.ResPrefectureStrategyNew;

public interface PrefectureStrategyService {
	/**
	  * 保存
	  * @param reqStrategyInterval
	  * @return
	  */
	int save(ReqPrefectureStrategy reqPrefectureStrategy);
	/**
	 * 更新
	 * @param reqStrategyInterval
	 * @return
	 */
	int update (ReqPrefectureStrategy reqPrefectureStrategy);
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
	 * 获取单个对象
	 * @param id
	 * @return
	 */
	ResPrefectureStrategyNew selectByPrimaryKey(Long id);
	
	/**
	 * 验证车位锁时段是否存在交叉
	 * @param map
	 * @return
	 */
	int validateTime(Map<String, String> map);
	
	/**
	 * 验证运营 分期策略是否交叉
	 * @param map
	 * @return
	 */
	int validateDate(Map<String, String> map);

}
