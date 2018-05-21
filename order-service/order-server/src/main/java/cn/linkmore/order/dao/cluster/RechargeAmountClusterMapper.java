package cn.linkmore.order.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.entity.RechargeAmount;
@Mapper
public interface RechargeAmountClusterMapper {

    RechargeAmount findById(Long id);
}