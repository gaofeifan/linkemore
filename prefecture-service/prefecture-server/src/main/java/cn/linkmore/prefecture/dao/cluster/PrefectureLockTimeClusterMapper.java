package cn.linkmore.prefecture.dao.cluster;

import java.util.List;

import cn.linkmore.prefecture.entity.PrefectureLockTime;

public interface PrefectureLockTimeClusterMapper {

    PrefectureLockTime selectByPrimaryKey(Long id);

    List<PrefectureLockTime> findList(Long prefectureStrategyId);
}
