package cn.linkmore.security.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.entity.Person;
/**
 * 账户
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface PersonClusterMapper {

    Person findById(Long id);

}