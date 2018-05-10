package cn.linkmore.common.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.AdminAuth;

@Mapper
public interface AdminAuthMapper {
    AdminAuth selectByPrimaryKey(Long id);
}