package cn.linkmore.common.dao.cluster;
import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.AdminUserAuth;

@Mapper
public interface AdminUserAuthMapper {
    AdminUserAuth selectByPrimaryKey(Long id);
}