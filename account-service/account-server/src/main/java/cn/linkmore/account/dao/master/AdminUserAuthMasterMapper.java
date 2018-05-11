package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.AdminUserAuth;
@Mapper
public interface AdminUserAuthMasterMapper {
    int deleteById(Long id);

    int insert(AdminUserAuth record);

    int insertSelective(AdminUserAuth record);

    int updateByIdSelective(AdminUserAuth record);

    int updateById(AdminUserAuth record);
}