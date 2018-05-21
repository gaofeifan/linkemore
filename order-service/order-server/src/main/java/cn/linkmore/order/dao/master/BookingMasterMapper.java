package cn.linkmore.order.dao.master;

import cn.linkmore.order.entity.Booking;

public interface BookingMasterMapper {
    int delete(Long id);

    int save(Booking record);

    int update(Booking record);
}