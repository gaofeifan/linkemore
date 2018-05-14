package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.Prefecture;
import cn.linkmore.prefecture.response.ResPrefectureDetail;

@Mapper
public interface PrefectureClusterMapper {
	/**
	 * 查询主键对应的车区信息
	 * @param id 主健
	 * @return ResPrefecture车区信息
	 */
    Prefecture findById(Long id);

	List<ResPrefectureDetail> findPreListByLoc(Map<String, Object> param);

	ResPrefectureDetail findPrefectureById(Long id);

	List<ResPrefectureDetail> findPreListByCityId(Long cityId);
}