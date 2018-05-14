package cn.linkmore.account.dao.master;

import cn.linkmore.account.entity.UserStaff;

public interface UserStaffMasterMapper {
    int delete(Long id);

    int save(UserStaff record);

    int update(UserStaff record);
}