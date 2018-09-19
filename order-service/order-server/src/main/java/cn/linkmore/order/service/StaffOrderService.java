package cn.linkmore.order.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.linkmore.order.response.ResIncome;
import cn.linkmore.order.response.ResPreOrderCount;
import cn.linkmore.order.response.ResTrafficFlow;

/**
 * @author   GFF
 * @Date     2018年9月18日
 * @Version  v2.0
 */
public interface StaffOrderService {

	/**
	 * @Description  查询管理版车场列表统计
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResPreOrderCount> findPreListCount(Map<String, Object> param);

	/**
	 * @Description  查询今日收入
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	BigDecimal findDayIncome(Map<String, Object> map);

	/**
	 * @Description  查询收入报表金额
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	BigDecimal findAmountReportCount(Map<String, Object> map);

	/**
	 * @Description  查询收入报表列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Map<String, Object> findAmountReportList(Map<String, Object> map);

	/**
	 * @Description  查询车流量报表总数
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer findCarReportCount(Map<String, Object> map);

	/**
	 * @Description  查询车流量报表列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Map<String, Object> findCarReportList(Map<String, Object> map);

	/**
	 * @Description  查询收入月数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResIncome> findAmountMonthList(Map<String, Object> map);

	/**
	 * @Description  查询车流程月数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResTrafficFlow> findCarMonthList(Map<String, Object> map);

}
