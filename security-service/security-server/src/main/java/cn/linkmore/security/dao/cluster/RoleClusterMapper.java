package cn.linkmore.security.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.entity.Role;
import cn.linkmore.security.request.ReqCheck;
/**
 * 角色
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface RoleClusterMapper {

    Role findById(Long id);

	List<Role> findList(Map<String, Object> param);

	Integer check(ReqCheck reqCheck);

	List<Role> findPage(Map<String, Object> param);

	Integer count(Map<String, Object> param);

}