package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.TargetDay;
import cn.linkmore.prefecture.response.ResPreMounthAmount;
import cn.linkmore.prefecture.response.ResPreOrderAmount;
import cn.linkmore.prefecture.response.ResPreUserAmount;
import cn.linkmore.prefecture.response.ResTargetDay;
@Mapper
public interface TargetDayClusterMapper {

    TargetDay findById(Long id);

	Integer count(Map<String, Object> param);

	List<ResTargetDay> findPage(Map<String, Object> param);

	List<ResPreUserAmount> findPrefectureUserAmount(Map<String, Object> param);

	List<ResPreOrderAmount> findPrefectureOrderAmount(Map<String, Object> param);

	List<ResPreMounthAmount> findMounthAmount(List<Long> mounthIds);
	
	List<TargetDay> findDayList(String format);

}