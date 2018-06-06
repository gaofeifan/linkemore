package cn.linkmore.user.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.user.request.ReqBooking;
import cn.linkmore.user.request.ReqOrderStall;
import cn.linkmore.user.request.ReqSwitch;
import cn.linkmore.user.response.ResCheckedOrder;
import cn.linkmore.user.response.ResOrder;
import cn.linkmore.user.response.ResOrderDetail;

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

	/**
	 * 切换车位
	 * @param rs
	 * @param request
	 */
	void switchStall(ReqSwitch rs, HttpServletRequest request);

	/**
	 * 分页显示 
	 * @param start 起始以0为始
	 * @param request
	 * @return
	 */
	List<ResCheckedOrder> list(Long start, HttpServletRequest request);

	/**
	 * 订单详情
	 * @param orderId 订单ID
	 * @param request 请求
	 * @return
	 */
	ResOrderDetail detail(Long orderId, HttpServletRequest request);

}
