package cn.linkmore.prefecture.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.PrefectureElement;
@Mapper
public interface PrefectureElementMasterMapper {
    int delete(Long id);

    int save(PrefectureElement record);

    int update(PrefectureElement record);
    
    int deleteBatch(List<Long> ids);
}