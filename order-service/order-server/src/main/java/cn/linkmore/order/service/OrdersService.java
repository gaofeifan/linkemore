package cn.linkmore.order.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.order.controller.app.request.ReqBooking;
import cn.linkmore.order.controller.app.request.ReqOrderStall;
import cn.linkmore.order.controller.app.request.ReqSwitch;
import cn.linkmore.order.controller.app.response.ResCheckedOrder;
import cn.linkmore.order.controller.app.response.ResOrder;
import cn.linkmore.order.controller.app.response.ResOrderDetail;
import cn.linkmore.order.request.ReqOrderDown;
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
	 * @param request
	 */
	void create(ReqBooking rb,HttpServletRequest request);
	
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
	ResOrderDetail detail(Long id,HttpServletRequest request);

	/**
	 * 降下地锁
	 * @param rod
	 */
	void down(ReqOrderStall ros,HttpServletRequest request);

	/**
	 * 切换车位
	 * @param ros
	 */
	void switchStall(ReqSwitch rs,HttpServletRequest request);

	/**
	 * 订单列表
	 * @param start 
	 * @param request
	 * @return
	 */
	List<ResCheckedOrder> list(Long start, HttpServletRequest request);

	/**
	 * 当前订单
	 * @param request
	 * @return
	 */
	ResOrder current(HttpServletRequest request);

	/**
	 * 落锁结果查询
	 * @param ros
	 * @param request
	 */
	void downResult(HttpServletRequest request);

	/**
	 * @Description  降锁消息推送
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void downMsgPush(Long orderId, Long stallId); 
}
