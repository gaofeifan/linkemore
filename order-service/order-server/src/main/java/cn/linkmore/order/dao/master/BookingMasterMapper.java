package cn.linkmore.order.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.order.entity.Booking;
@Mapper
public interface BookingMasterMapper {
    int delete(Long id);

    int save(Booking record);

    int update(Booking record);
}