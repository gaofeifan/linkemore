package cn.linkmore.prefecture.dao.master;

import cn.linkmore.prefecture.entity.StallRecharge;

public interface StallRechargeMasterMapper {
    int delete(Long id);

    int save(StallRecharge record);

    int update(StallRecharge record);
}