package cn.linkmore.prefecture.dao.cluster;

import java.util.List;

import cn.linkmore.prefecture.entity.PrefectureStrategyGroup;

public interface PrefectureStrategyGroupClusterMapper {

    PrefectureStrategyGroup selectByPrimaryKey(Long id);

    List<PrefectureStrategyGroup> findList(Long prefectureStrategyId);
}