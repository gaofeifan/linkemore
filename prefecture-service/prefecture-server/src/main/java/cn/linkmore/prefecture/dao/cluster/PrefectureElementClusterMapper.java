package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.PrefectureElement;
@Mapper
public interface PrefectureElementClusterMapper {

    PrefectureElement findById(Long id);

    List<PrefectureElement> findByPreId(Long preId);

	Integer count(Map<String, Object> param);

	List<PrefectureElement> findPage(Map<String, Object> param);
}