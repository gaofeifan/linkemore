package cn.linkmore.user.service;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.user.request.ReqBooking;
import cn.linkmore.user.request.ReqOrderStall;
import cn.linkmore.user.response.ResOrder;

/**
 * Service接口 - 预约
 * @author liwenlong
 * @version 2.0
 *
 */
public interface OrderService {

	/**
	 * 创建订单
	 * @param prefectureId 专区ID
	 * @param request
	 */
	void create(ReqBooking rb, HttpServletRequest request);

	/**
	 * 查询用户订单
	 * @param request
	 * @return
	 */
	ResOrder current(HttpServletRequest request);
	
	/**
	 * 降下地锁
	 * @param ros
	 * @param request
	 */
	void down(ReqOrderStall ros,HttpServletRequest request);

}
