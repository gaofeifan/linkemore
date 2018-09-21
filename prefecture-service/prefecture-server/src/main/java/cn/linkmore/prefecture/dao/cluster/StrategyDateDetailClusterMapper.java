package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

import cn.linkmore.prefecture.entity.StrategyDateDetail;

public interface StrategyDateDetailClusterMapper {

    StrategyDateDetail selectByPrimaryKey(Long id);

    List<StrategyDateDetail> findList(Long strategyDateId);

}