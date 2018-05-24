package cn.linkmore.security.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.response.ResRolePage;
/**
 * 角色页面
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface RolePageClusterMapper {
	
    ResRolePage findById(Long id);

	List<ResRolePage> findList(Map<String, Object> param);
    
}