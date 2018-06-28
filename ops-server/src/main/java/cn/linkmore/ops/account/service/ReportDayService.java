package cn.linkmore.ops.account.service;

import java.util.List;
import java.util.Map;
import cn.linkmore.report.request.ReqReportDay;
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
 * 日报service
 * @author jiaohanbin
 * @version 2.0
 *
 */
public interface ReportDayService {
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
	List<ResUserNum> userNumList(ReqReportDay reportDay);
	/**
	 * 新增用户列表
	 * @param param
	 * @return
	 */
	List<ResNewUser> newUserList(ReqReportDay reportDay);
	/**
	 * 拉新列表
	 * @param param
	 * @return
	 */
	List<ResPull> pullList(ReqReportDay reportDay);
	
	/**
	 * 单车位日均列表
	 * @param param
	 * @return
	 */
	List<ResStallAverage> stallAverageList(ReqReportDay reportDay);
	
	/**
	 * 订单列表
	 * @param param
	 * @return
	 */
	List<ResOrder> orderList(ReqReportDay reportDay);
	
	/**
	 * 银联订单列表
	 * @param param
	 * @return
	 */
	List<ResOrder> ylOrderList(ReqReportDay reportDay);
	
	
	/**
	 * 新用户订单列表
	 * @param param
	 * @return
	 */
	List<ResOrder> newUserOrderList(ReqReportDay reportDay);
	
	
	/**
	 * 老用户订单列表
	 * @param param
	 * @return
	 */
	List<ResOrder> oldUserOrderList(ReqReportDay reportDay);
	
	/**
	 * 运营时长列表
	 * @param param
	 * @return
	 */
	List<ResRunTime> runtimeList(ReqReportDay reportDay);
	
	/**
	 * 客单价列表
	 * @param param
	 * @return
	 */
	List<ResAveragePrice> averagePriceList(ReqReportDay reportDay);
	
	/**
	 * 成本列表
	 * @param param
	 * @return
	 */
	List<ResCost> costList(ReqReportDay reportDay);
	
	/**
	 * 收入列表
	 * @param param
	 * @return
	 */
	List<ResIncome> incomeList(ReqReportDay reportDay);
	/**
	 * 拉新成本列表
	 * @param param
	 * @return
	 */
	List<ResPullCost> pullCostList(ReqReportDay reportDay);
	
}
