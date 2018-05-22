package cn.linkmore.security.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.entity.PersonRole;
/**
 * 账户角色
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface PersonRoleMasterMapper {
    int delete(Long id);

    int save(PersonRole record);

    int update(PersonRole record);
}