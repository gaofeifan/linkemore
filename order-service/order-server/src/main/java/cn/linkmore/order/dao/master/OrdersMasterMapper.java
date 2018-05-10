package cn.linkmore.order.dao.master;

import cn.linkmore.order.entity.Orders;

public interface OrdersMasterMapper {
    int delete(Long id);

    int save(Orders record);

    int update(Orders record);
}