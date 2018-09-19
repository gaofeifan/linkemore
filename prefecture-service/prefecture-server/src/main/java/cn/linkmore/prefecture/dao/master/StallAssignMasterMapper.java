package cn.linkmore.prefecture.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.StallAssign;
@Mapper
public interface StallAssignMasterMapper {
    int delete(Long id);

    int save(StallAssign record);

    int update(StallAssign record);

	void cancel(StallAssign sa);
}