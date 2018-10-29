package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

import cn.linkmore.prefecture.entity.StrategyDateDetail;
import cn.linkmore.prefecture.entity.StrategyTimeDetail;

public interface StrategyDateDetailClusterMapper {

    StrategyDateDetail selectByPrimaryKey(Long id);

    List<StrategyDateDetail> findList(Long strategyDateId);

    List<StrategyDateDetail> findListByIds(List<Long> ids);
}