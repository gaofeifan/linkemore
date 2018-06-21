package cn.linkmore.order.dao.cluster;

import java.util.List;
import java.util.Map;

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

	/**
	 * 根据车牌查询最新订单状态
	 * @param carno
	 * @return
	 */
	Integer getPlateLastOrderStatus(String carno);
	/**
	 * 查询当前用户订单数
	 * @param userId
	 * @return
	 */
	Integer userCount(Long userId);

	/**
	 * 查询车位最新订单
	 * @param stallId
	 * @return
	 */
	ResUserOrder findStallLatest(Long stallId);
	
	/**
	 * 查询用户订单列表
	 * @param param
	 * @return
	 */
	List<ResUserOrder> findUserList(Map<String,Object> param);

}