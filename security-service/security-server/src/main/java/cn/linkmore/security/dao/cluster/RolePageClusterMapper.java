package cn.linkmore.security.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.entity.RolePage;
/**
 * 角色页面
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface RolePageClusterMapper {
	
    RolePage findById(Long id);

	List<RolePage> findList(Map<String, Object> param);
    
}