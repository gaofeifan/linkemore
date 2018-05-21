package cn.linkmore.order.dao.cluster;

import cn.linkmore.order.entity.Booking;

public interface BookingClusterMapper {

    Booking findById(Long id);
}