package cn.linkmore.account.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.UserStaff;
@Mapper
public interface UserStaffClusterMapper {
    UserStaff findById(Long id);
}