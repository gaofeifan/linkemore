package cn.linkmore.prefecture.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.StallRecharge;
@Mapper
public interface StallRechargeMasterMapper {
    int delete(Long id);

    int save(StallRecharge record);

    int update(StallRecharge record);
}