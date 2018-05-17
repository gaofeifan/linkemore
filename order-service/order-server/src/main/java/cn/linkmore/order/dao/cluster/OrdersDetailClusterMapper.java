package cn.linkmore.order.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.entity.OrdersDetail;
@Mapper
public interface OrdersDetailClusterMapper {

    OrdersDetail findById(Long id);

}