package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.PrefectureElement;
@Mapper
public interface PrefectureElementClusterMapper {

    PrefectureElement findById(Long id);

    List<PrefectureElement> findByPreId(Long preId);
}