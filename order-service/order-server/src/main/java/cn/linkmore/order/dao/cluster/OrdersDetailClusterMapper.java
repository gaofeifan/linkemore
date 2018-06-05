package cn.linkmore.order.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.entity.OrdersDetail;
@Mapper
public interface OrdersDetailClusterMapper {

    OrdersDetail findById(Long id);

    /**
     * 根据魔兽ID获取订单详情
     * @param orderId
     * @return
     */
	OrdersDetail findByOrderId(Long orderId);

}