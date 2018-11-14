package cn.linkmore.prefecture.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.prefecture.response.ResStrategyFee;

public interface StrategyFeeService {

	List<ResStrategyFee> findList();

	Map<String, Object> amount(Map<String, Object> param);

	String info(Map<String, Object> param);

	
}
