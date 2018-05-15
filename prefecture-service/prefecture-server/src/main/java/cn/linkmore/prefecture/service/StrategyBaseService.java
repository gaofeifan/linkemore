package cn.linkmore.prefecture.service;

import cn.linkmore.prefecture.entity.StrategyBase;

/**
 * Service接口 - 计费策略信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
public interface StrategyBaseService {
	/**
	 * 根据订单详情中计费策略id查询
	 * @param id
	 * @return
	 */
	StrategyBase findById(Long id);
	
}
