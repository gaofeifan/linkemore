package cn.linkmore.security.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.entity.Role;
/**
 * 角色
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface RoleMasterMapper {

    int save(Role record);

    int update(Role record);

	int delete(List<Long> ids);
}