package cn.linkmore.security.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.entity.Role;
/**
 * 角色
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface RoleClusterMapper {

    Role findById(Long id);

}