package cn.linkmore.prefecture.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.Stall;

@Mapper
public interface StallMasterMapper {
	int deleteByPrimaryKey(Long id);

	int insert(Stall record);

	int updateByPrimaryKey(Stall stall);

	void order(Stall stall);
	
	void cancel(Stall stall);
	
	void lockdown(Stall stall);

	void pay(Stall stall);

}