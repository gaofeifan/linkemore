package cn.linkmore.security.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.entity.RolePage;
/**
 * 角色页面
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface RolePageMasterMapper {
    int delete(Long id);

    int save(RolePage record);

    int update(RolePage record);
}