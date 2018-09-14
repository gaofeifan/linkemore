package cn.linkmore.order.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.order.response.ResUnusualOrder;

/**
 * Service接口 －异常订单
 * @author jiaohanbin
 * @version 1.0
 *
 */
public interface UnusualOrderService {
	
	void updateAppointmentTimeoutList();
	
	void updateLockDownTimeoutList();
		
	void updateUnreleaseCloseOrders();
	
	void updateUnreleaseHangOrders();
	
	void updateUnreleaseCompleteOrders();

	List<ResUnusualOrder> findList(Map<String, Object> map);

}
