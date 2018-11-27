package cn.linkmore.enterprise.dao.master;

import cn.linkmore.enterprise.entity.ReqEntUserPlate;

public interface EntUserPlateMasterMapper {
    int delete(Long id);

    int save(ReqEntUserPlate record);

    int update(ReqEntUserPlate record);
}