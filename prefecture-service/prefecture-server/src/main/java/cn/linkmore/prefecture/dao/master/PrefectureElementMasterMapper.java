package cn.linkmore.prefecture.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.PrefectureElement;
@Mapper
public interface PrefectureElementMasterMapper {
    int delete(Long id);

    int save(PrefectureElement record);

    int update(PrefectureElement record);
}