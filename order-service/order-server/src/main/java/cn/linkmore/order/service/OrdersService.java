package cn.linkmore.order.service;

import cn.linkmore.order.request.ReqOrderCreate;
import cn.linkmore.order.request.ReqOrderDown;
import cn.linkmore.order.request.ReqOrderSwitch;
import cn.linkmore.order.response.ResUserOrder;

/**
 * Service - 订单
 * @author liwenlong
 * @version 2.0
 *
 */
public interface OrdersService {

	/**
	 * 预约
	 * @param roc
	 */
	void create(ReqOrderCreate roc);
	
	/**
	 * 最新订单
	 * @param userId
	 * @return
	 */
	ResUserOrder latest(Long userId);
	
	/**
	 * 订单详情
	 * @param id
	 * @return
	 */
	ResUserOrder detail(Long id);

	/**
	 * 降下地锁
	 * @param rod
	 */
	void down( ReqOrderDown rod);

	/**
	 * 切换车位
	 * @param ros
	 */
	void switchStall(ReqOrderSwitch ros);

}
