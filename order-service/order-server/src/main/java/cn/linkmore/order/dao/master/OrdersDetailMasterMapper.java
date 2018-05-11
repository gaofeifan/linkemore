package cn.linkmore.order.dao.master;

import cn.linkmore.order.entity.OrdersDetail;

public interface OrdersDetailMasterMapper {
    int delete(Long id);

    int save(OrdersDetail record);

    int update(OrdersDetail record);
}