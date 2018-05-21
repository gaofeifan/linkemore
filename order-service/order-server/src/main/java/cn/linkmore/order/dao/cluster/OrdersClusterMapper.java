package cn.linkmore.order.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.entity.Orders;
import cn.linkmore.order.response.ResUserOrder;
@Mapper
public interface OrdersClusterMapper {
	/**
	 * 根据主键id查询订单信息
	 * @param id
	 * @return
	 */
    Orders findById(Long id);

    /**
     * 订单详情
     * @param orderId 订单ID
     * @return
     */
	ResUserOrder findDetail(Long id);

	/**
	 * 用户最新订单
	 * @param userId 用户ID
	 * @return
	 */
	ResUserOrder findUserLatest(Long userId);

}