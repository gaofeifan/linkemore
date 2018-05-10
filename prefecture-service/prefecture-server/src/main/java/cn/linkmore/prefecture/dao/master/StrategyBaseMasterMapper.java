package cn.linkmore.prefecture.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.StrategyBase;

@Mapper
public interface StrategyBaseMasterMapper {
	
	int deleteByPrimaryKey(Long id);

    int insert(StrategyBase record);

    int updateByPrimaryKey(StrategyBase record);
}