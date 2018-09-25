package cn.linkmore.prefecture.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.prefecture.dao.cluster.StrategyDateDetailClusterMapper;
import cn.linkmore.prefecture.dao.master.StrategyDateDetailMasterMapper;
import cn.linkmore.prefecture.entity.StrategyDateDetail;
import cn.linkmore.prefecture.service.StrategyDateDetailService;

@Service
public class StrategyDateDetailServiceImpl implements StrategyDateDetailService {
	
	@Autowired
	private StrategyDateDetailMasterMapper strategyDateDetailMasterMapper;
	
	@Autowired
	private StrategyDateDetailClusterMapper strategyDateDetailClusterMapper;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return strategyDateDetailMasterMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(StrategyDateDetail record) {
		return strategyDateDetailMasterMapper.insert(record);
	}

	@Override
	public int updateByPrimaryKey(StrategyDateDetail record) {
		return strategyDateDetailMasterMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<StrategyDateDetail> findList(Long strategyDateDetailId) {
		return strategyDateDetailClusterMapper.findList(strategyDateDetailId);
	}

	@Override
	public StrategyDateDetail selectByPrimaryKey(Long id) {
		return strategyDateDetailClusterMapper.selectByPrimaryKey(id);
	}

}
