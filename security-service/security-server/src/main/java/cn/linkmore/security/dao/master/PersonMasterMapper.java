package cn.linkmore.security.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.entity.Person;
/**
 * 账户
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface PersonMasterMapper {
    int delete(Long id);

    int save(Person record);

    int update(Person record);
}