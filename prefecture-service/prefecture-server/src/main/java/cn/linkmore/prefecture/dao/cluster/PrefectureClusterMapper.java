package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.Prefecture;
import cn.linkmore.prefecture.response.ResPrefecture;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResPrefectureList;
/**
 * dao 车区
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface PrefectureClusterMapper {
	/**
	 * 查询主键对应的车区信息
	 * @param id 主健
	 * @return ResPrefecture车区信息
	 */
    Prefecture findById(Long id);

	ResPrefectureDetail findPrefectureById(Long id);

	List<ResPrefecture> findPreByStatusAndGPS(Map<String, Object> paramMap);

	List<ResPrefecture> findPreByStatusAndGPS1(Map<String, Object> paramMap);
	
	List<ResPrefectureList> findPreListByCityId(Map<String, Object> paramMap);
	
	List<ResPrefectureList> findPreListByCityId1(Map<String, Object> paramMap);
}