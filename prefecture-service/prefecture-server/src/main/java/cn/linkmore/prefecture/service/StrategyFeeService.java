package cn.linkmore.prefecture.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.prefecture.response.ResStrategyFee;

public interface StrategyFeeService {

	List<ResStrategyFee> findList();

	Map<String, Object> amount(Map<String, Object> param);

	String info(Map<String, Object> param);
	/**
	 * 查看当前车区分组的免费时长
	 * @param param
	 * @return
	 */
	int freeMins(Map<String, Object> param);
}
