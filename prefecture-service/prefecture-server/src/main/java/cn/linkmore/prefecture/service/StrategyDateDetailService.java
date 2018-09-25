package cn.linkmore.prefecture.service;

import java.util.List;

import cn.linkmore.prefecture.entity.StrategyDateDetail;

public interface StrategyDateDetailService {

    int deleteByPrimaryKey(Long id);

	int insert(StrategyDateDetail record);

	int updateByPrimaryKey(StrategyDateDetail record);

	List<StrategyDateDetail> findList(Long strategyDateDetailId);

	StrategyDateDetail selectByPrimaryKey(Long id);
}
