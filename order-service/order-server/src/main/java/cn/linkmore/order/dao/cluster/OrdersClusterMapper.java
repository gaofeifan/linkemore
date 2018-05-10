package cn.linkmore.order.dao.cluster;

import cn.linkmore.order.entity.Orders;

public interface OrdersClusterMapper {
	/**
	 * 根据主键id查询订单信息
	 * @param id
	 * @return
	 */
    Orders findById(Long id);

}