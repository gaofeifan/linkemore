package cn.linkmore.order.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.entity.OrdersDetail;
@Mapper
public interface OrdersDetailMasterMapper {
    int delete(Long id);

    int save(OrdersDetail record);

    int update(OrdersDetail record);
}