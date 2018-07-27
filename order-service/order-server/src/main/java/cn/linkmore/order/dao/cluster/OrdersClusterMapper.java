package cn.linkmore.order.dao.cluster;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.controller.app.response.ResMonthCount;
import cn.linkmore.order.controller.app.response.ResOrder;
import cn.linkmore.order.entity.Orders;
import cn.linkmore.order.request.ReqOrderExcel;
import cn.linkmore.order.response.ResChargeDetail;
import cn.linkmore.order.response.ResIncomeList;
import cn.linkmore.order.response.ResOrderExcel;
import cn.linkmore.order.response.ResOrderOps;
import cn.linkmore.order.response.ResOrderPlate;
import cn.linkmore.order.response.ResPreDataList;
import cn.linkmore.order.response.ResPreOrderCount;
import cn.linkmore.order.response.ResTrafficFlow;
import cn.linkmore.order.response.ResTrafficFlowList;
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

	/**
	 * @Description  查询总数
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer count(Map<String, Object> param);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResOrder> findPage(Map<String, Object> param);

	/**
	 * @Description  文件导出
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResOrderExcel> exportList(ReqOrderExcel bean);

	/**
	 * 定时任务
	 * @return
	 */
	List<ResOrderOps> appointmentTimeoutList();

	List<ResOrderOps> lockDownTimeoutList();

	List<ResOrderOps> unreleaseCloseOrders();

	List<ResOrderOps> unreleaseHangOrders();

	List<ResOrderOps> unreleaseCompleteOrders();

	/**
	 * @Description  查询订单当日统计
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResPreOrderCount> findPreCountByIds(List<Long> ids);

	/**
	 * @Description  根据车区查询车牌号
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResOrderPlate> findPlateByPreId(Long preId);

	/**
	 * @Description  查询车区今日收入
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	BigDecimal findPreDayIncome(List<Long> authStall);

	/**
	 * @Description  查询车流量
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer findTrafficFlow(Map<String, Object> map);

	/**
	 * @Description  根据条件查询实收入
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	BigDecimal findProceeds(Map<String, Object> map);

	/**
	 * @Description  查询列表明细
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResChargeDetail> findChargeDetail(Map<String, Object> param);

	/**
	 * @Description  查询车流量列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResTrafficFlowList> findTrafficFlowList(Map<String, Object> param);

	/**
	 * @Description  查询收入列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResIncomeList> findIncomeList(Map<String, Object> param);

	/**
	 * @Description 查询月统计
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResMonthCount> findMonthCount(Map<String, Object> param);

	/**
	 * @Description  查询车场数据列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResPreDataList> findPreDataList(Map<String, Object> map);

}