package cn.linkmore.account.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.AdminAuth;
@Mapper
public interface AdminAuthClusterMapper {
    AdminAuth selectById(Long id);
}