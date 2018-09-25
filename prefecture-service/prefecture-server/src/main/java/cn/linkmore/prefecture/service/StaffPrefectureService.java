package cn.linkmore.prefecture.service;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.order.response.ResIncome;
import cn.linkmore.order.response.ResTrafficFlow;
import cn.linkmore.prefecture.controller.staff.request.ReqPreType;
import cn.linkmore.prefecture.controller.staff.request.ReqPreTypePage;
import cn.linkmore.prefecture.controller.staff.response.ResAmountDetail;
import cn.linkmore.prefecture.controller.staff.response.ResAmountReport;
import cn.linkmore.prefecture.controller.staff.response.ResCarReport;
import cn.linkmore.prefecture.controller.staff.response.ResDayIncome;
import cn.linkmore.prefecture.controller.staff.response.ResDayTrafficFlow;
import cn.linkmore.prefecture.controller.staff.response.ResStaffPreListCount;

/**
 * @author   GFF
 * @Date     2018年9月18日
 * @Version  v2.0
 */
public interface StaffPrefectureService {

	/**
	 * @Description  查询运营车区统计
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResStaffPreListCount> findPreList(HttpServletRequest request, Long cityId);

	/**
	 * @Description  查询管理版车场运营今日收入
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	BigDecimal findDayIncome(HttpServletRequest request, Long preId);

	/**
	 * @Description  查询报表收入统计
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	BigDecimal findAmountReportCount(HttpServletRequest request, ReqPreType type);


	/**
	 * @Description  查询车流量报表统计
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer findCarReportCount(HttpServletRequest request, ReqPreType type);

	/**
	 * @Description  查询报表车区收入列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResAmountReport findAmountReportList(HttpServletRequest request, ReqPreType type);

	/**
	 * @Description  查询车区车流量列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResCarReport findCarReportList(HttpServletRequest request, ReqPreType type);

	/**
	 * @Description  查询车流量月列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResDayTrafficFlow> findCarMonthList(HttpServletRequest request,ReqPreTypePage page);

	/**
	 * @Description  查询收入月数据列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResDayIncome> findAmountMonthList(HttpServletRequest request,ReqPreTypePage page);

	/**
	 * @Description  查询车场实时收费明细
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResAmountDetail> findAmountDetail(Integer pageNo, Long preId, HttpServletRequest request);

}
