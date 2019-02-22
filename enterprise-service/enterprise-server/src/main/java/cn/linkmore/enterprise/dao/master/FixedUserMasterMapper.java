package cn.linkmore.enterprise.dao.master;


import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.FixedUserPick;


@Mapper
public interface FixedUserMasterMapper {

	void delete(FixedUserPick  frx);
	
	void save(FixedUserPick  frx);
	
}
