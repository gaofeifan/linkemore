package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.PrefectureGroup;
import cn.linkmore.prefecture.response.ResPrefectureGroup;
@Mapper
public interface PrefectureGroupClusterMapper {

    PrefectureGroup findById(Long id);

	Integer check(Map<String, Object> param);

	Integer count(Map<String, Object> param);

	List<ResPrefectureGroup> findPage(Map<String, Object> param);

}