package cn.linkmore.security.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.response.ResPersonRole;
/**
 * 账户角色
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface PersonRoleClusterMapper {

	ResPersonRole findById(Long id);

	List<ResPersonRole> findListById(Long id);

}