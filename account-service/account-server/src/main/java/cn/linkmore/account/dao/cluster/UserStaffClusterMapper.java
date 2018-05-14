package cn.linkmore.account.dao.cluster;

import cn.linkmore.account.entity.UserStaff;

public interface UserStaffClusterMapper {
    UserStaff findById(Long id);
}