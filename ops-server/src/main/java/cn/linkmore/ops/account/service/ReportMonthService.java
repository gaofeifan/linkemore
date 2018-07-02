package cn.linkmore.ops.account.service;

import java.util.List;
import java.util.Map;
import cn.linkmore.report.request.ReqReportMonth;
import cn.linkmore.report.response.ResAveragePrice;
import cn.linkmore.report.response.ResCity;
import cn.linkmore.report.response.ResCost;
import cn.linkmore.report.response.ResIncome;
import cn.linkmore.report.response.ResNewUser;
import cn.linkmore.report.response.ResOrder;
import cn.linkmore.report.response.ResPre;
import cn.linkmore.report.response.ResPull;
import cn.linkmore.report.response.ResPullCost;
import cn.linkmore.report.response.ResRunTime;
import cn.linkmore.report.response.ResStallAverage;
import cn.linkmore.report.response.ResUserNum;

/**
 * 月报service
 * @author jiaohanbin
 * @version 2.0
 *
 */
public interface ReportMonthService {
	/**
	 * 城市列表
	 * @return
	 */
	List<ResCity> cityList();
	/**
	 * 车区列表
	 * @param param
	 * @return
	 */
	List<ResPre> preList(Map<String,Object> param);
	
	/**
	 * 用户总数
	 * @return
	 */
	Integer totalCount();
	/**
	 * 用户数量
	 * @param param
	 * @return
	 */
	List<ResUserNum> userNumList(ReqReportMonth reportMonth);
	/**
	 * 新增用户列表
	 * @param param
	 * @return
	 */
	List<ResNewUser> newUserList(ReqReportMonth reportMonth);
	/**
	 * 拉新列表
	 * @param param
	 * @return
	 */
	List<ResPull> pullList(ReqReportMonth reportMonth);
	
	/**
	 * 单车位日均列表
	 * @param param
	 * @return
	 */
	List<ResStallAverage> stallAverageList(ReqReportMonth reportMonth);
	
	/**
	 * 订单列表
	 * @param param
	 * @return
	 */
	List<ResOrder> orderList(ReqReportMonth reportMonth);
	
	/**
	 * 银联订单列表
	 * @param param
	 * @return
	 */
	List<ResOrder> ylOrderList(ReqReportMonth reportMonth);
	
	
	/**
	 * 新用户订单列表
	 * @param param
	 * @return
	 */
	List<ResOrder> newUserOrderList(ReqReportMonth reportMonth);
	
	
	/**
	 * 老用户订单列表
	 * @param param
	 * @return
	 */
	List<ResOrder> oldUserOrderList(ReqReportMonth reportMonth);
	
	/**
	 * 运营时长列表
	 * @param param
	 * @return
	 */
	List<ResRunTime> runtimeList(ReqReportMonth reportMonth);
	
	/**
	 * 客单价列表
	 * @param param
	 * @return
	 */
	List<ResAveragePrice> averagePriceList(ReqReportMonth reportMonth);
	
	/**
	 * 成本列表
	 * @param param
	 * @return
	 */
	List<ResCost> costList(ReqReportMonth reportMonth);
	
	/**
	 * 收入列表
	 * @param param
	 * @return
	 */
	List<ResIncome> incomeList(ReqReportMonth reportMonth);
	/**
	 * 拉新成本列表
	 * @param param
	 * @return
	 */
	List<ResPullCost> pullCostList(ReqReportMonth reportMonth);
	
}
