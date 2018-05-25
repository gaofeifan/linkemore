package cn.linkmore.security.dao.master;

import java.util.List;

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

	int loginUpdate(Person db);

	int delete(List<Long> ids);

	int unlock(Long id);
	
}