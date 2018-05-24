package cn.linkmore.security.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.response.ResRoleElement;
/**
 * 角色元素
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface RoleElementClusterMapper {

    ResRoleElement findById(Long id);

	List<ResRoleElement> findList(Map<String, Object> param);

}