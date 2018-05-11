package cn.linkmore.account.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.AdminUserAuth;
@Mapper
public interface AdminUserAuthClusterMapper {

    AdminUserAuth selectById(Long id);

}