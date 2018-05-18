package cn.linkmore.coupon.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.Strategy;
@Mapper
public interface StrategyMasterMapper {
	int delete(Long id);

    int save(Strategy record);

    int update(Strategy record);
}