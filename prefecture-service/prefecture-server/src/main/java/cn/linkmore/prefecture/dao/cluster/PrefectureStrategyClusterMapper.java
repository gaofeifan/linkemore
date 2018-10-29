package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

import cn.linkmore.prefecture.entity.PrefectureStrategy;

public interface PrefectureStrategyClusterMapper {

    PrefectureStrategy selectByPrimaryKey(Long id);

	List<PrefectureStrategy> findPage(Map<String, Object> param);
    
    Integer count(Map<String, Object> param);
}