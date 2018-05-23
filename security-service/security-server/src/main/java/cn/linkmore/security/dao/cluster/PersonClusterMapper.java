package cn.linkmore.security.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.entity.Page;
import cn.linkmore.security.entity.Person;
import cn.linkmore.security.request.ReqCheck;
/**
 * 账户
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface PersonClusterMapper {

    Person findById(Long id);

	Person findByUsername(String username);

	Integer count(Map<String, Object> param);

	List<Page> findPage(Map<String, Object> param);

	Integer check(ReqCheck reqCheck);

}