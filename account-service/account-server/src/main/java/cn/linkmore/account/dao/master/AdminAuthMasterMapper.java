package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.AdminAuth;
@Mapper
public interface AdminAuthMasterMapper {
    int deleteById(Long id);

    int insert(AdminAuth record);

    int insertSelective(AdminAuth record);

    int updateByIdSelective(AdminAuth record);

    int updateByPrimaryKey(AdminAuth record);
}