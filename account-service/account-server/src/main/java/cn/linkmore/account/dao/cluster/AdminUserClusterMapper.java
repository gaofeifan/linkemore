package cn.linkmore.account.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.AdminUser;
@Mapper
public interface AdminUserClusterMapper {


    AdminUser selectById(Long id);

}