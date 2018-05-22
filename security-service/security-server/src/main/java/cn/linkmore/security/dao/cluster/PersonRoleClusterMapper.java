package cn.linkmore.security.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.entity.PersonRole;
/**
 * 账户角色
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface PersonRoleClusterMapper {

    PersonRole findById(Long id);

}