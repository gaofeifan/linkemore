package cn.linkmore.coupon.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.Strategy;
@Mapper
public interface StrategyClusterMapper {

    Strategy findById(Long id);

}