package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

import cn.linkmore.prefecture.entity.PrefectureEntrance;

public interface PrefectureEntranceClusterMapper {

	PrefectureEntrance findById(Long id);

	Integer count(Map<String, Object> param);

	List<PrefectureEntrance> findPage(Map<String, Object> param);

	List<PrefectureEntrance> findByPreId(Long preId);

}