package cn.linkmore.report.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.report.response.ResAveragePrice;
import cn.linkmore.report.response.ResCity;
import cn.linkmore.report.response.ResNewUser;
import cn.linkmore.report.response.ResOrder;
import cn.linkmore.report.response.ResPre;
import cn.linkmore.report.response.ResPull;
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
	List<ResUserNum> userNumList(Map<String, Object> param);
	/**
	 * 新增用户列表
	 * @param param
	 * @return
	 */
	List<ResNewUser> newUserList(Map<String, Object> param);
	/**
	 * 拉新列表
	 * @param param
	 * @return
	 */
	List<ResPull> pullList(Map<String, Object> param);
	
	/**
	 * 单车位日均列表
	 * @param param
	 * @return
	 */
	List<ResStallAverage> stallAverageList(Map<String, Object> param);
	
	/**
	 * 订单列表
	 * @param param
	 * @return
	 */
	List<ResOrder> orderList(Map<String, Object> param);
	
	/**
	 * 银联订单列表
	 * @param param
	 * @return
	 */
	List<ResOrder> ylOrderList(Map<String, Object> param);
	
	
	/**
	 * 新用户订单列表
	 * @param param
	 * @return
	 */
	List<ResOrder> newUserOrderList(Map<String, Object> param);
	
	
	/**
	 * 老用户订单列表
	 * @param param
	 * @return
	 */
	List<ResOrder> oldUserOrderList(Map<String, Object> param);
	
	/**
	 * 运营时长列表
	 * @param param
	 * @return
	 */
	List<ResRunTime> runtimeList(Map<String, Object> param);
	
	/**
	 * 客单价列表
	 * @param param
	 * @return
	 */
	List<ResAveragePrice> averagePriceList(Map<String, Object> param);
	
}
