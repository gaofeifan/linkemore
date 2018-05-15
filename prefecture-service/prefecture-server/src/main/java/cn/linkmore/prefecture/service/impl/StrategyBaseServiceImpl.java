package cn.linkmore.prefecture.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.prefecture.dao.cluster.StrategyBaseClusterMapper;
import cn.linkmore.prefecture.entity.StrategyBase;
import cn.linkmore.prefecture.service.StrategyBaseService;
/**
 * Service实现类 - 计费策略信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class StrategyBaseServiceImpl implements StrategyBaseService {
	
	@Autowired
	private StrategyBaseClusterMapper strategyBaseClusterMapper;

	@Override
	public StrategyBase findById(Long id) {
		return strategyBaseClusterMapper.findById(id);
	}

	
	
}
