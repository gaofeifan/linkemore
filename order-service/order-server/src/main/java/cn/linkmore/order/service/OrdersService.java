package cn.linkmore.order.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.order.controller.app.request.ReqBooking;
import cn.linkmore.order.controller.app.request.ReqBrandBooking;
import cn.linkmore.order.controller.app.request.ReqOrderStall;
import cn.linkmore.order.controller.app.request.ReqStallBooking;
import cn.linkmore.order.controller.app.request.ReqSwitch;
import cn.linkmore.order.controller.app.response.ResCheckedOrder;
import cn.linkmore.order.controller.app.response.ResOrder;
import cn.linkmore.order.controller.app.response.ResOrderDetail;
import cn.linkmore.order.request.ReqOrderExcel;
import cn.linkmore.order.response.ResCharge;
import cn.linkmore.order.response.ResChargeDetail;
import cn.linkmore.order.response.ResChargeList;
import cn.linkmore.order.response.ResEntOrder;
import cn.linkmore.order.response.ResIncome;
import cn.linkmore.order.response.ResOrderExcel;
import cn.linkmore.order.response.ResOrderPlate;
import cn.linkmore.order.response.ResPreOrderCount;
import cn.linkmore.order.response.ResTrafficFlow;
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
	Integer downResult(HttpServletRequest request);

	/**
	 * @Description  降锁消息推送
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void downMsgPush(Long orderId, Long stallId);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * @Description  文件导出
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResOrderExcel> exportList(ReqOrderExcel bean);

	/**
	 * @Description  查询车区当日统计
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResPreOrderCount> findPreCountByIds(List<Long> preId);

	/**
	 * @Description  根据车区id查询车牌号
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResOrderPlate> findPlateByPreId(Long preId); 
	/**
	 * 品牌预约车位
	 * @param rbb
	 * @param request
	 */
	void brandCreate(ReqBrandBooking rbb, HttpServletRequest request);

	/**
	 * @param type 
	 * @Description  查询车区今日收入
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	BigDecimal findPreDayIncome( Long authStall);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Map<String, Object> findTrafficFlow(Map<String, Object> map);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Map<String, Object> findProceeds(Map<String, Object> map);

	/**
	 * @Description  查询收费明细
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResChargeDetail> findChargeDetail(Map<String, Object> param);

	/**
	 * @Description  查询车流量list
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResTrafficFlow findTrafficFlowList(Map<String, Object> param);

	/**
	 * @Description  查询收入列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResIncome findIncomeList(Map<String, Object> param);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
/*	List<ResCharge> findChargeDetailNew(Map<String, Object> param);*/

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	BigDecimal findProceedsAmount(Map<String, Object> param);

	/**
	 * @Description  根据条件查询总流量
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer findTrafficFlowCount(Map<String, Object> param);

	/**
	 * @Description  查询长租固定车位
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResEntOrder findOrderByStallId(Long stallId);
	/**
	 * 选择车位预约车位功能
	 * @param rsb
	 * @param request
	 */
	void appoint(ReqStallBooking rsb, HttpServletRequest request);

	/**
	 * @Description  根据车位id查询最后订单
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResUserOrder findStallLatest(Long stallId);


	
}
