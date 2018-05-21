package cn.linkmore.order.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.entity.Booking;
@Mapper
public interface BookingClusterMapper {

    Booking findById(Long id);
}