package cn.linkmore.order.service;

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

}
