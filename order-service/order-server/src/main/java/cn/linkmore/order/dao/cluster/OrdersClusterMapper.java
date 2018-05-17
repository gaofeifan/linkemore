package cn.linkmore.order.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.entity.Orders;
@Mapper
public interface OrdersClusterMapper {
	/**
	 * 根据主键id查询订单信息
	 * @param id
	 * @return
	 */
    Orders findById(Long id);

}