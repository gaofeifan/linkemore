package cn.linkmore.security.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.security.entity.Role;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.response.ResRole;
/**
 * 角色
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface RoleClusterMapper {

    ResRole findById(Long id);

	List<ResRole> findList(Map<String, Object> param);

	Integer check(Map<String, Object> param);

	List<Role> findPage(Map<String, Object> param);

	Integer count(Map<String, Object> param);

}