package cn.linkmore.coupon.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.OrderReceive;
@Mapper
public interface OrderReceiveMasterMapper {
    int delete(Long id);

    int save(OrderReceive record);

    int update(OrderReceive record);
}