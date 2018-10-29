package cn.linkmore.prefecture.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.prefecture.dao.cluster.StrategyFeeClusterMapper;
import cn.linkmore.prefecture.response.ResStrategyFee;
import cn.linkmore.prefecture.service.StrategyFeeService;
@Service
public class StrategyFeeServiceImpl implements StrategyFeeService {


	@Autowired
	private StrategyFeeClusterMapper strategyFeeClusterMapper;
	
	
	@Override
	public List<ResStrategyFee> findList() {
		return this.strategyFeeClusterMapper.findList();

	}

}
