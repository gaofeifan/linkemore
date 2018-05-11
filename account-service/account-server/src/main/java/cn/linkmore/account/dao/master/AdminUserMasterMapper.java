package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.AdminUser;
@Mapper
public interface AdminUserMasterMapper {
    int deleteById(Long id);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    int updateByIdSelective(AdminUser record);

    int updateById(AdminUser record);
}