package cn.linkmore.ops.biz.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqPrefectureStrategy;
import cn.linkmore.prefecture.response.ResPrefectureStrategyNew;
import cn.linkmore.prefecture.response.ResStrategyFee;

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
	 * 获取计费策略列表
	 * @return
	 */
	List<ResStrategyFee> findList();
	/**
	 * 验证运营时段是否交叉
	 * @param pageable
	 * @return
	 */
	int validateTime(Map<String, String> map);
	
	/**
	 * 验证运营 分期策略是否交叉
	 * @param pageable
	 * @return
	 */
	int validateDate(Map<String, String> map);
}
