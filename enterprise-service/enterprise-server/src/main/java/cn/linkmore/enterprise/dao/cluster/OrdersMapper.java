package cn.linkmore.enterprise.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.Orders;


/**
 * Mapper - 订单信息
 * @author liwl
 * @version 1.0
 *
 */
@Mapper
public interface OrdersMapper { 
	Orders findByNo(String orderNo);
	
    Orders find(Long id); 

    int updateStatus(Orders record);
    
    Orders findStallLatest(Long stallId);

    Orders findPlateOrder(String plate);
}