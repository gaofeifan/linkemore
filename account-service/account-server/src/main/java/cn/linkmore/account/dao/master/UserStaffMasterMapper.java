package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.UserStaff;
@Mapper
public interface UserStaffMasterMapper {
    int delete(Long id);

    int save(UserStaff record);

    int update(UserStaff record);
}