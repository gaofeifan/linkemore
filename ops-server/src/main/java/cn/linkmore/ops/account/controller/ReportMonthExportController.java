package cn.linkmore.ops.account.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import cn.linkmore.ops.account.service.ReportMonthService;
import cn.linkmore.ops.utils.ExcelUtil;
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
import cn.linkmore.util.StringUtil;
/**
 * Controller - 月报-导出
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Controller
@RequestMapping("/admin/account/report_month_export")
public class ReportMonthExportController { 
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private ReportMonthService reportMonthService;
	
	@RequestMapping(value = "/city_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResCity> cityList(){
		return this.reportMonthService.cityList(); 
	} 
	
	@RequestMapping(value = "/pre_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPre> preList(HttpServletRequest request, Long cityId){
		Map<String,Object> param = new HashMap<String,Object>();
		if(cityId != 0) {
			param.put("cityId", cityId);
		}
		return this.reportMonthService.preList(param);
	}
	
	/**
	 * 用户数量初始化导出数据
	 * 
	 * @param reportMonth
	 * @return
	 */
	public Map<String, Object> userNumMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResUserNum> list = this.reportMonthService.userNumList(reportMonth);
		if (CollectionUtils.isNotEmpty(list)) {
			for (ResUserNum resUserNum : list) {
				double average = new BigDecimal((float) resUserNum.getDayTotal() / resUserNum.getStallTotal())
						.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				resUserNum.setAverage(average);
			}
		} else {
			list = new ArrayList<ResUserNum>();
		}
		Map<String, Object> userNumMap = new HashMap<String, Object>();
		JSONArray ja = new JSONArray();
		Map<String, Object> s = null;
		for (ResUserNum userNum : list) {
			s = new HashMap<String, Object>();
			s.put("day", userNum.getDay());
			s.put("sumTotal", userNum.getSumTotal());
			s.put("dayTotal", userNum.getDayTotal());
			s.put("average", userNum.getAverage());
			ja.add(s);
		}
		Map<String, String> headMap = new LinkedHashMap<String, String>();
		headMap.put("day", "日期");
		headMap.put("sumTotal", "累计用户数");
		headMap.put("dayTotal", "新增用户数");
		headMap.put("average", "单车位日均");
		userNumMap.put("head", headMap);
		userNumMap.put("value", ja);
		userNumMap.put("title", "用户数量");
		return userNumMap;
	}

	/**
	 * 新增用户
	 * 
	 * @param reportMonth
	 * @return
	 */
	public Map<String, Object> newUserMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResNewUser> newUserList = this.reportMonthService.newUserList(reportMonth);
		log.info("newUserList = " + JSON.toJSON(newUserList));
		if (newUserList == null) {
			newUserList = new ArrayList<ResNewUser>();
		}
		Map<String, Object> newUserMap = new HashMap<String, Object>();
		JSONArray jaNewUser = new JSONArray();
		Map<String, Object> sNewUser = null;
		for (ResNewUser newUser : newUserList) {
			sNewUser = new HashMap<String, Object>();
			sNewUser.put("day", newUser.getDay());
			sNewUser.put("hzPull", newUser.getHzPull());
			sNewUser.put("bjPull", newUser.getBjPull());
			sNewUser.put("hzCooperation", newUser.getHzCooperation());
			sNewUser.put("bjCooperation", newUser.getBjCooperation());
			sNewUser.put("hzUnderTran", newUser.getHzUnderTran());
			sNewUser.put("bjUnderTran", newUser.getBjUnderTran());
			sNewUser.put("natureTran", newUser.getNatureTran());
			jaNewUser.add(sNewUser);
		}
		Map<String, String> headMapNewUser = new LinkedHashMap<String, String>();
		headMapNewUser.put("day", "日期");
		headMapNewUser.put("hzPull", "杭州现场拉新");
		headMapNewUser.put("bjPull", "北京现场拉新");
		headMapNewUser.put("hzCooperation", "杭州合作转化");
		headMapNewUser.put("bjCooperation", "北京合作转化");
		headMapNewUser.put("hzUnderTran", "杭州线下转化");
		headMapNewUser.put("bjUnderTran", "北京线下转化");
		headMapNewUser.put("natureTran", "自然转化");
		newUserMap.put("head", headMapNewUser);
		newUserMap.put("value", jaNewUser);
		newUserMap.put("title", "新增用户");
		return newUserMap;
	}

	public Map<String, Object> pullMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResPull> pullList = this.reportMonthService.pullList(reportMonth);
		JSONArray jaPull = new JSONArray();
		if (StringUtils.isNotBlank(reportMonth.getStartTime()) && StringUtils.isNotBlank(reportMonth.getEndTime())
				&& pullList != null) {
			List<String> monthList = StringUtil.getBetweenMonths(reportMonth.getStartTime(), reportMonth.getEndTime());
			Map<String, Object> mapPull = null;
			for (String date : monthList) {
				mapPull = new HashMap<String, Object>();
				mapPull.put("day", date);
				int bjTotal = 0;
				int hzTotal = 0;
				for (ResPull resPull : pullList) {
					if (mapPull.get(resPull.getPreName()) == null) {
						mapPull.put(resPull.getPreName(), 0);
					}
					if (date.equals(resPull.getDay())) {
						mapPull.put(resPull.getPreName(), resPull.getDayTotal());
						if (resPull.getCityName().equals("北京")) {
							bjTotal += resPull.getDayTotal();
						} else if (resPull.getCityName().equals("杭州")) {
							hzTotal += resPull.getDayTotal();
						}
					} else {

					}
				}
				mapPull.put("bjTotal", bjTotal);
				mapPull.put("hzTotal", hzTotal);
				mapPull.put("total", bjTotal + hzTotal);
				jaPull.add(mapPull);
			}
		}
		Map<String, String> headMapPull = userTitleMap(reportMonth);
		Map<String, Object> pullMap = new HashMap<String, Object>();
		pullMap.put("head", headMapPull);
		pullMap.put("value", jaPull);
		pullMap.put("title", "现场拉新");
		return pullMap;
	}

	public Map<String, Object> outlinePullMap(ReqReportMonth reportMonth) throws ParseException {
		// List<ResPull> pullList = this.reportMonthService.pullList(reportMonth);
		List<ResPull> pullList = new ArrayList<ResPull>();
		JSONArray jaPull = new JSONArray();
		if (StringUtils.isNotBlank(reportMonth.getStartTime()) && StringUtils.isNotBlank(reportMonth.getEndTime())) {
			List<String> monthList = StringUtil.getBetweenMonths(reportMonth.getStartTime(), reportMonth.getEndTime());
			Map<String, Object> mapPull = null;
			for (String date : monthList) {
				mapPull = new HashMap<String, Object>();
				mapPull.put("day", date);
				int bjTotal = 0;
				int hzTotal = 0;
				for (ResPull resPull : pullList) {
					if (mapPull.get(resPull.getPreName()) == null) {
						mapPull.put(resPull.getPreName(), 0);
					}
					if (date.equals(resPull.getDay())) {
						mapPull.put(resPull.getPreName(), resPull.getDayTotal());
						if (resPull.getCityName().equals("北京")) {
							bjTotal += resPull.getDayTotal();
						} else if (resPull.getCityName().equals("杭州")) {
							hzTotal += resPull.getDayTotal();
						}
					} else {

					}
				}
				mapPull.put("bjTotal", bjTotal);
				mapPull.put("hzTotal", hzTotal);
				mapPull.put("total", bjTotal + hzTotal);
				jaPull.add(mapPull);
			}
		}
		Map<String, String> headMapPull = userTitleMap(reportMonth);
		Map<String, Object> outlinePullMap = new HashMap<String, Object>();
		outlinePullMap.put("head", headMapPull);
		outlinePullMap.put("value", jaPull);
		outlinePullMap.put("title", "线下转化");
		return outlinePullMap;
	}

	/**
	 * 单车位日均
	 * 
	 * @param reportMonth
	 * @return
	 */
	public Map<String, Object> stallAverageMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResStallAverage> averageList = this.reportMonthService.stallAverageList(reportMonth);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		int bjStall = 0;
		int hzStall = 0;
		Map<String, Object> stallMap = new HashMap<String, Object>();
		for (ResStallAverage resPull : averageList) {
			if (stallMap.get(resPull.getPreName()) == null) {
				stallMap.put(resPull.getPreName(), resPull.getStallTotal());
				if (resPull.getCityName().equals("北京")) {
					bjStall += resPull.getStallTotal();
				} else if (resPull.getCityName().equals("杭州")) {
					hzStall += resPull.getStallTotal();
				}
			}
		}

		if (StringUtils.isNotBlank(reportMonth.getStartTime()) && StringUtils.isNotBlank(reportMonth.getEndTime())
				&& averageList != null) {
			List<String> monthList = StringUtil.getBetweenMonths(reportMonth.getStartTime(), reportMonth.getEndTime());
			for (String date : monthList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				int bjTotal = 0;
				int hzTotal = 0;
				int dayNum = 0;
				double bjTotalAverage = 0d;
				double hzTotalAverage = 0d;
				double totalAverage = 0d;
				for (ResStallAverage resPull : averageList) {
					if (map.get(resPull.getPreName()) == null) {
						map.put(resPull.getPreName(), 0);
					}
					if (date.equals(resPull.getDay())) {
						dayNum = resPull.getDayNum();
						map.put(resPull.getPreName(), resPull.getAverage());
						if (resPull.getCityName().equals("北京")) {
							bjTotal += resPull.getDayTotal();
						} else if (resPull.getCityName().equals("杭州")) {
							hzTotal += resPull.getDayTotal();
						}
					}
				}
				if (bjStall != 0) {
					bjTotalAverage = new BigDecimal((float) bjTotal/dayNum/ bjStall).setScale(1, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
				}
				if (hzStall != 0) {
					hzTotalAverage = new BigDecimal((float) hzTotal/dayNum/ hzStall).setScale(1, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
				}
				if(bjStall + hzStall != 0) {
					totalAverage = new BigDecimal((float) (bjTotal + hzTotal) /dayNum/(bjStall + hzStall))
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				map.put("bjTotal", bjTotalAverage);
				map.put("hzTotal", hzTotalAverage);
				map.put("total", totalAverage);
				ja.add(map);
			}
		}
		Map<String, String> headMap = userTitleMap(reportMonth);
		Map<String, Object> averageMap = new HashMap<String, Object>();
		averageMap.put("head", headMap);
		averageMap.put("value", ja);
		averageMap.put("title", "单车位日均");
		return averageMap;
	}

	/*********************** 订单  **********************/

	public Map<String, Object> orderMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResOrder> orderList = this.reportMonthService.orderList(reportMonth);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportMonth.getStartTime()) && StringUtils.isNotBlank(reportMonth.getEndTime())
				&& orderList != null) {
			List<String> monthList = StringUtil.getBetweenMonths(reportMonth.getStartTime(), reportMonth.getEndTime());
			for (String date : monthList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				int bjTotal = 0;
				int hzTotal = 0;
				for (ResOrder resPull : orderList) {
					if (map.get(resPull.getPreName()) == null) {
						map.put(resPull.getPreName(), 0);
					}
					if (date.equals(resPull.getDay())) {
						map.put(resPull.getPreName(), resPull.getDayTotal());
						if (resPull.getCityName().equals("北京")) {
							bjTotal += resPull.getDayTotal();
						} else if (resPull.getCityName().equals("杭州")) {
							hzTotal += resPull.getDayTotal();
						}
					} else {

					}
				}
				map.put("bjTotal", bjTotal);
				map.put("hzTotal", hzTotal);
				map.put("total", bjTotal + hzTotal);
				ja.add(map);
			}
		}
		Map<String, String> headMap = orderTitleMap(reportMonth);
		Map<String, Object> orderMap = new HashMap<String, Object>();
		orderMap.put("head", headMap);
		orderMap.put("value", ja);
		orderMap.put("title", "订单数量");
		return orderMap;
	}

	public Map<String, Object> ylOrderMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResOrder> orderList = this.reportMonthService.ylOrderList(reportMonth);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportMonth.getStartTime()) && StringUtils.isNotBlank(reportMonth.getEndTime())
				&& orderList != null) {
			List<String> monthList = StringUtil.getBetweenMonths(reportMonth.getStartTime(), reportMonth.getEndTime());
			for (String date : monthList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				int bjTotal = 0;
				int hzTotal = 0;
				for (ResOrder resPull : orderList) {
					if (map.get(resPull.getPreName()) == null) {
						map.put(resPull.getPreName(), 0);
					}
					if (date.equals(resPull.getDay())) {
						map.put(resPull.getPreName(), resPull.getDayTotal());
						if (resPull.getCityName().equals("北京")) {
							bjTotal += resPull.getDayTotal();
						} else if (resPull.getCityName().equals("杭州")) {
							hzTotal += resPull.getDayTotal();
						}
					} else {

					}
				}
				map.put("bjTotal", bjTotal);
				map.put("hzTotal", hzTotal);
				map.put("total", bjTotal + hzTotal);
				ja.add(map);
			}
		}
		Map<String, String> headMap = ylOrderTitleMap(reportMonth);
		Map<String, Object> orderMap = new HashMap<String, Object>();
		orderMap.put("head", headMap);
		orderMap.put("value", ja);
		orderMap.put("title", "银联订单");
		return orderMap;
	}

	public Map<String, Object> newUserOrderMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResOrder> orderList = this.reportMonthService.newUserOrderList(reportMonth);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportMonth.getStartTime()) && StringUtils.isNotBlank(reportMonth.getEndTime())
				&& orderList != null) {
			List<String> monthList = StringUtil.getBetweenMonths(reportMonth.getStartTime(), reportMonth.getEndTime());
			for (String date : monthList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				int bjTotal = 0;
				int hzTotal = 0;
				for (ResOrder resPull : orderList) {
					if (map.get(resPull.getPreName()) == null) {
						map.put(resPull.getPreName(), 0);
					}
					if (date.equals(resPull.getDay())) {
						map.put(resPull.getPreName(), resPull.getDayTotal());
						if (resPull.getCityName().equals("北京")) {
							bjTotal += resPull.getDayTotal();
						} else if (resPull.getCityName().equals("杭州")) {
							hzTotal += resPull.getDayTotal();
						}
					} else {

					}
				}
				map.put("bjTotal", bjTotal);
				map.put("hzTotal", hzTotal);
				map.put("total", bjTotal + hzTotal);
				ja.add(map);
			}
		}
		Map<String, String> headMap = orderTitleMap(reportMonth);
		Map<String, Object> orderMap = new HashMap<String, Object>();
		orderMap.put("head", headMap);
		orderMap.put("value", ja);
		orderMap.put("title", "新用户订单");
		return orderMap;
	}

	public Map<String, Object> oldUserOrderMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResOrder> orderList = this.reportMonthService.oldUserOrderList(reportMonth);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportMonth.getStartTime()) && StringUtils.isNotBlank(reportMonth.getEndTime())
				&& orderList != null) {
			List<String> monthList = StringUtil.getBetweenMonths(reportMonth.getStartTime(), reportMonth.getEndTime());
			for (String date : monthList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				int bjTotal = 0;
				int hzTotal = 0;
				for (ResOrder resPull : orderList) {
					if (map.get(resPull.getPreName()) == null) {
						map.put(resPull.getPreName(), 0);
					}
					if (date.equals(resPull.getDay())) {
						map.put(resPull.getPreName(), resPull.getDayTotal());
						if (resPull.getCityName().equals("北京")) {
							bjTotal += resPull.getDayTotal();
						} else if (resPull.getCityName().equals("杭州")) {
							hzTotal += resPull.getDayTotal();
						}
					} else {

					}
				}
				map.put("bjTotal", bjTotal);
				map.put("hzTotal", hzTotal);
				map.put("total", bjTotal + hzTotal);
				ja.add(map);
			}
		}
		Map<String, String> headMap = orderTitleMap(reportMonth);
		Map<String, Object> orderMap = new HashMap<String, Object>();
		orderMap.put("head", headMap);
		orderMap.put("value", ja);
		orderMap.put("title", "老用户订单");
		return orderMap;
	}

	public Map<String, Object> runtimeMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResRunTime> runtimeList = this.reportMonthService.runtimeList(reportMonth);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;

		int bjStall = 0;
		int hzStall = 0;
		Map<String, Object> stallMap = new HashMap<String, Object>();
		
		if(CollectionUtils.isNotEmpty(runtimeList) ) {
			for (ResRunTime resPull : runtimeList) {
				if (stallMap.get(resPull.getPreName()) == null) {
					stallMap.put(resPull.getPreName(), resPull.getStallTotal());
					if (resPull.getCityName().equals("北京")) {
						bjStall += resPull.getStallTotal();
					} else if (resPull.getCityName().equals("杭州")) {
						hzStall += resPull.getStallTotal();
					}
				}
			}
		}

		if (StringUtils.isNotBlank(reportMonth.getStartTime()) && StringUtils.isNotBlank(reportMonth.getEndTime())
				&& runtimeList != null) {
			List<String> monthList = StringUtil.getBetweenMonths(reportMonth.getStartTime(), reportMonth.getEndTime());
			for (String date : monthList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				int bjTotalTime = 0;
				int hzTotalTime = 0;
				int dayNum = 0;
				double bjTotalAverage = 0d;
				double hzTotalAverage = 0d;
				double totalAverage = 0d;
				for (ResRunTime resRunTime : runtimeList) {
					if (map.get(resRunTime.getPreName()) == null) {
						map.put(resRunTime.getPreName(), 0);
					}
					if (date.equals(resRunTime.getDay())) {
						dayNum = resRunTime.getDayNum();
						map.put(resRunTime.getPreName(), resRunTime.getRuntime());
						if (resRunTime.getCityName().equals("北京")) {
							bjTotalTime += resRunTime.getTotalTime();
						} else if (resRunTime.getCityName().equals("杭州")) {
							hzTotalTime += resRunTime.getTotalTime();
						}
					}
				}
				log.info("bj_total_time ,{} hz_total_time ,{} bj_stall,{} hz_stall_count,{}", bjTotalTime, hzTotalTime,
						bjStall, hzStall);
				if (bjStall != 0) {
					bjTotalAverage = new BigDecimal((float) bjTotalTime / 60 /dayNum / bjStall)
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				if (hzStall != 0) {
					hzTotalAverage = new BigDecimal((float) hzTotalTime / 60 /dayNum / hzStall)
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}

				if ((bjStall + hzStall) != 0) {
					totalAverage = new BigDecimal((float) (bjTotalTime + hzTotalTime) / 60 /dayNum / (bjStall + hzStall))
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}

				map.put("bjTotal", bjTotalAverage);
				map.put("hzTotal", hzTotalAverage);
				map.put("total", totalAverage);
				ja.add(map);
			}
		}
		Map<String, String> headMap = orderTitleMap(reportMonth);
		Map<String, Object> runtimeMap = new HashMap<String, Object>();
		runtimeMap.put("head", headMap);
		runtimeMap.put("value", ja);
		runtimeMap.put("title", "运营时长");
		return runtimeMap;
	}
	
	public Map<String, Object> runtimeRateMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResRunTime> runtimeList = this.reportMonthService.runtimeList(reportMonth);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;

		int bjStall = 0;
		int hzStall = 0;
		Map<String, Object> stallMap = new HashMap<String, Object>();
		
		if(CollectionUtils.isNotEmpty(runtimeList) ) {
			for (ResRunTime resPull : runtimeList) {
				if (stallMap.get(resPull.getPreName()) == null) {
					stallMap.put(resPull.getPreName(), resPull.getStallTotal());
					if (resPull.getCityName().equals("北京")) {
						bjStall += resPull.getStallTotal();
					} else if (resPull.getCityName().equals("杭州")) {
						hzStall += resPull.getStallTotal();
					}
				}
			}
		}

		if (StringUtils.isNotBlank(reportMonth.getStartTime()) && StringUtils.isNotBlank(reportMonth.getEndTime())
				&& runtimeList != null) {
			List<String> monthList = StringUtil.getBetweenMonths(reportMonth.getStartTime(), reportMonth.getEndTime());
			for (String date : monthList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				int bjTotalTime = 0;
				int hzTotalTime = 0;
				int bjShopRuntime = 0;
				int hzShopRuntime = 0;
				double bjRuntimeRate = 0d;
				double hzRuntimeRate = 0d;
				double totalRuntimeRate = 0d;
				int dayNum = 0;
				for (ResRunTime resRunTime : runtimeList) {
					if (map.get(resRunTime.getPreName()) == null) {
						map.put(resRunTime.getPreName(), 0);
					}
					if (date.equals(resRunTime.getDay())) {
						map.put(resRunTime.getPreName(), resRunTime.getRuntimeRate());
						dayNum = resRunTime.getDayNum();
						if (resRunTime.getCityName().equals("北京")) {
							bjTotalTime += resRunTime.getTotalTime();
							bjShopRuntime += resRunTime.getShopRuntime();
						} else if (resRunTime.getCityName().equals("杭州")) {
							hzTotalTime += resRunTime.getTotalTime();
							hzShopRuntime += resRunTime.getShopRuntime();
						}
					}
				}
				log.info("bj_total_time ,{} hz_total_time ,{} bj_stall,{} hz_stall_count,{}", bjTotalTime, hzTotalTime,
						bjStall, hzStall);
				if (bjStall != 0 && bjShopRuntime != 0) {
					bjRuntimeRate = new BigDecimal((float) bjTotalTime / bjShopRuntime / bjStall / dayNum)
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				if (hzStall != 0 && hzShopRuntime != 0) {
					hzRuntimeRate = new BigDecimal((float) hzTotalTime / hzShopRuntime / hzStall / dayNum)
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}

				if ((bjStall + hzStall) != 0 && (bjShopRuntime + hzShopRuntime)!= 0) {
					totalRuntimeRate = new BigDecimal((float) (bjTotalTime + hzTotalTime) / (bjShopRuntime + hzShopRuntime) / (bjStall + hzStall) / dayNum)
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}

				map.put("bjTotal", bjRuntimeRate);
				map.put("hzTotal", hzRuntimeRate);
				map.put("total", totalRuntimeRate);
				ja.add(map);
			}
		}
		Map<String, String> headMap = orderTitleMap(reportMonth);
		Map<String, Object> runtimeMap = new HashMap<String, Object>();
		runtimeMap.put("head", headMap);
		runtimeMap.put("value", ja);
		runtimeMap.put("title", "运营时长比例");
		return runtimeMap;
	}
	
	
	public Map<String, Object> rdlMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResRunTime> runtimeList = this.reportMonthService.runtimeList(reportMonth);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;

		int bjStall = 0;
		int hzStall = 0;
		Map<String, Object> stallMap = new HashMap<String, Object>();
		if(CollectionUtils.isNotEmpty(runtimeList) ) {
			for (ResRunTime resPull : runtimeList) {
				if (stallMap.get(resPull.getPreName()) == null) {
					stallMap.put(resPull.getPreName(), resPull.getStallTotal());
					if (resPull.getCityName().equals("北京")) {
						bjStall += resPull.getStallTotal();
					} else if (resPull.getCityName().equals("杭州")) {
						hzStall += resPull.getStallTotal();
					}
				}
			}
		}

		if (StringUtils.isNotBlank(reportMonth.getStartTime()) && StringUtils.isNotBlank(reportMonth.getEndTime())
				&& runtimeList != null) {
			List<String> monthList = StringUtil.getBetweenMonths(reportMonth.getStartTime(), reportMonth.getEndTime());
			for (String date : monthList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				int bjOrderTotal = 0;
				int hzOrderTotal = 0;
				int dayNum = 0;
				double bjTotalAverage = 0d;
				double hzTotalAverage = 0d;
				double totalAverage = 0d;
				for (ResRunTime resRunTime : runtimeList) {
					if (map.get(resRunTime.getPreName()) == null) {
						map.put(resRunTime.getPreName(), 0);
					}
					if (date.equals(resRunTime.getDay())) {
						dayNum = resRunTime.getDayNum();
						map.put(resRunTime.getPreName(), resRunTime.getRdl());
						if (resRunTime.getCityName().equals("北京")) {
							bjOrderTotal += resRunTime.getDayTotal();
						} else if (resRunTime.getCityName().equals("杭州")) {
							hzOrderTotal += resRunTime.getDayTotal();
						}
					}
				}
				log.info("bj_order_total ,{} hz_order_total ,{} bj_stall ,{} hz_stall_count,{}", bjOrderTotal,
						hzOrderTotal, bjStall, hzStall);
				if (bjStall != 0) {
					bjTotalAverage = new BigDecimal((float) bjOrderTotal /dayNum/ bjStall)
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				if (hzStall != 0) {
					hzTotalAverage = new BigDecimal((float) hzOrderTotal /dayNum/ hzStall)
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				if ((bjStall + hzStall) != 0) {
					totalAverage = new BigDecimal((float) (bjOrderTotal + hzOrderTotal) /dayNum/ (bjStall + hzStall))
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}

				map.put("bjTotal", bjTotalAverage);
				map.put("hzTotal", hzTotalAverage);
				map.put("total", totalAverage);
				ja.add(map);
			}
		}
		Map<String, String> headMap = orderTitleMap(reportMonth);
		Map<String, Object> rdlMap = new HashMap<String, Object>();
		rdlMap.put("head", headMap);
		rdlMap.put("value", ja);
		rdlMap.put("title", "日单量");
		return rdlMap;
	}
	
	
	public Map<String, Object> jtscMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResRunTime> runtimeList = this.reportMonthService.runtimeList(reportMonth);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;

		if (StringUtils.isNotBlank(reportMonth.getStartTime()) && StringUtils.isNotBlank(reportMonth.getEndTime())
				&& runtimeList != null) {
			List<String> monthList = StringUtil.getBetweenMonths(reportMonth.getStartTime(), reportMonth.getEndTime());
			for (String date : monthList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				int bjTotalTime = 0;
				int hzTotalTime = 0;
				int bjOrderCount = 0;
				int hzOrderCount = 0;
				int dayNum = 0;
				double bjTotalAverage = 0d;
				double hzTotalAverage = 0d;
				double totalAverage = 0d;
				for (ResRunTime resRunTime : runtimeList) {
					if (map.get(resRunTime.getPreName()) == null) {
						map.put(resRunTime.getPreName(), 0);
					}
					if (date.equals(resRunTime.getDay())) {
						dayNum = resRunTime.getDayNum();
						map.put(resRunTime.getPreName(), resRunTime.getJtsc());
						if (resRunTime.getCityName().equals("北京")) {
							bjTotalTime += resRunTime.getTotalTime();
							bjOrderCount += resRunTime.getDayTotal();
						} else if (resRunTime.getCityName().equals("杭州")) {
							hzTotalTime += resRunTime.getTotalTime();
							hzOrderCount += resRunTime.getDayTotal();
						}
					}
				}
				log.info("bj_total_time ,{} hz_total_time ,{} bj_order_count ,{} hz_order_count ,{}", bjTotalTime,
						hzTotalTime, bjOrderCount, hzOrderCount);
				if (bjOrderCount != 0) {
					bjTotalAverage = new BigDecimal((float) bjTotalTime / 60 /dayNum / bjOrderCount)
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				if (hzOrderCount != 0) {
					hzTotalAverage = new BigDecimal((float) hzTotalTime / 60 /dayNum / hzOrderCount)
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				if ((bjTotalTime + hzTotalTime) != 0) {
					totalAverage = new BigDecimal(
							(float) (bjTotalTime + hzTotalTime) / 60 /dayNum / (bjOrderCount + hzOrderCount))
									.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}

				map.put("bjTotal", bjTotalAverage);
				map.put("hzTotal", hzTotalAverage);
				map.put("total", totalAverage);
				ja.add(map);
			}
		}
		Map<String, String> headMap = orderTitleMap(reportMonth);
		Map<String, Object> jtscMap = new HashMap<String, Object>();
		jtscMap.put("head", headMap);
		jtscMap.put("value", ja);
		jtscMap.put("title", "均停时长");
		return jtscMap;
	}
	
	public double add(double v1, double v2) {
       BigDecimal b1=new BigDecimal(Double.toString(v1));
       BigDecimal b2 = new BigDecimal(Double.toString(v2));
       return b1.add(b2).doubleValue();
    }
	
	public Map<String, Object> averagePriceMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResAveragePrice> averageList = this.reportMonthService.averagePriceList(reportMonth);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportMonth.getStartTime()) && StringUtils.isNotBlank(reportMonth.getEndTime())
				&& averageList != null) {
			List<String> monthList = StringUtil.getBetweenMonths(reportMonth.getStartTime(), reportMonth.getEndTime());
			for (String date : monthList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				// 此处需要重新计算
				double bjAmountTotal = 0d;// 北京总金额
				double hzAmountTotal = 0d;// 杭州总金额
				int bjDayTotal = 0;// 北京总订单量
				int hzDayTotal = 0;// 杭州总订单量
				int dayNum = 0;
				double bjTotalAverage = 0d;
				double hzTotalAverage = 0d;
				double totalAverage = 0d;
				for (ResAveragePrice resPrice : averageList) {
					if (map.get(resPrice.getPreName()) == null) {
						map.put(resPrice.getPreName(), 0);
					}
					if (date.equals(resPrice.getDay())) {
						dayNum = resPrice.getDayNum();
						map.put(resPrice.getPreName(), resPrice.getAveragePrice());
						if (resPrice.getCityName().equals("北京")) {
							bjAmountTotal = add(bjAmountTotal,resPrice.getAmountTotal());
							bjDayTotal += resPrice.getDayTotal();
						} else if (resPrice.getCityName().equals("杭州")) {
							hzAmountTotal = add(hzAmountTotal,resPrice.getAmountTotal());
							hzDayTotal += resPrice.getDayTotal();
						}
					} else {

					}
				}
				if (bjDayTotal != 0) {
					bjTotalAverage = new BigDecimal((float) bjAmountTotal /dayNum/ bjDayTotal)
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				if (hzDayTotal != 0) {
					hzTotalAverage = new BigDecimal((float) hzAmountTotal /dayNum/ hzDayTotal)
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				if ((bjDayTotal + hzDayTotal) != 0) {
					totalAverage = new BigDecimal((float) (bjAmountTotal + hzAmountTotal) /dayNum/ (bjDayTotal + hzDayTotal))
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				log.info("bj_amount_total ,{} hz_amount_total ,{} bj_day_total ,{} hz_day_total,{}", bjAmountTotal,
						hzAmountTotal, bjDayTotal, hzDayTotal);

				map.put("bjTotal", bjTotalAverage);
				map.put("hzTotal", hzTotalAverage);
				map.put("total", totalAverage);
				ja.add(map);
			}
		}
		Map<String, String> headMap = orderTitleMap(reportMonth);
		Map<String, Object> averagePriceMap = new HashMap<String, Object>();
		averagePriceMap.put("head", headMap);
		averagePriceMap.put("value", ja);
		averagePriceMap.put("title", "客单价");
		return averagePriceMap;
	}
	
	public Map<String, Object> costMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResCost> costList = this.reportMonthService.costList(reportMonth);
		Map<String, Object> map = null;
		JSONArray ja = new JSONArray();
		if (StringUtils.isNotBlank(reportMonth.getStartTime()) && StringUtils.isNotBlank(reportMonth.getEndTime())
				&& costList != null) {
			List<String> monthList = new ArrayList<String>();
			monthList.add("单车位月成本");
			monthList.add("车区月成本");
			for (String column : monthList) {
				map = new HashMap<String, Object>();
				map.put("column", column);
				int bjTotal = 0;
				int hzTotal = 0;
				for (ResCost resCost : costList) {

					if (column.equals("车区月成本")) {
						map.put(resCost.getPreName(), resCost.getMonthRent());
						if (resCost.getCityName().equals("北京")) {
							bjTotal += resCost.getMonthRent();
						} else if (resCost.getCityName().equals("杭州")) {
							hzTotal += resCost.getMonthRent();
						}
					}

					if (column.equals("单车位月成本")) {
						map.put(resCost.getPreName(), resCost.getMonthRent() / resCost.getStallTotal());
						if (resCost.getCityName().equals("北京")) {
							bjTotal += resCost.getMonthRent() / resCost.getStallTotal();
						} else if (resCost.getCityName().equals("杭州")) {
							hzTotal += resCost.getMonthRent() / resCost.getStallTotal();
						}
					}
				}
				map.put("bjTotal", bjTotal);
				map.put("hzTotal", hzTotal);
				map.put("total", bjTotal + hzTotal);
				ja.add(map);
			}
		}
		Map<String, String> headMap = costTitleMap(reportMonth);
		Map<String, Object> costMap = new HashMap<String, Object>();
		costMap.put("head", headMap);
		costMap.put("value", ja);
		costMap.put("title", "成本");
		return costMap;
	}
	
	public Map<String, Object> dealMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResIncome> incomeList = this.reportMonthService.incomeList(reportMonth);
		Map<String, Object> map = null;
		JSONArray ja = new JSONArray();
		if (StringUtils.isNotBlank(reportMonth.getStartTime()) && StringUtils.isNotBlank(reportMonth.getEndTime())
				&& incomeList != null) {
			List<String> monthList = StringUtil.getBetweenMonths(reportMonth.getStartTime(), reportMonth.getEndTime());
			for (String date : monthList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				double bjTotal = 0d;// 北京总金额
				double hzTotal = 0d;// 杭州总金额
				for (ResIncome resIncome : incomeList) {
					if (map.get(resIncome.getPreName()) == null) {
						map.put(resIncome.getPreName(), 0);
					}
					if (date.equals(resIncome.getDay())) {
						map.put(resIncome.getPreName(), resIncome.getTotalAmount());
						if (resIncome.getCityName().equals("北京")) {
							bjTotal = add(bjTotal, resIncome.getTotalAmount());
						} else if (resIncome.getCityName().equals("杭州")) {
							hzTotal = add(hzTotal, resIncome.getTotalAmount());
						}
					}
				}
				map.put("bjTotal", bjTotal);
				map.put("hzTotal", hzTotal);
				map.put("total", add(bjTotal, hzTotal));
				ja.add(map);
			}
		}
		Map<String, String> headMap = incomeTitleMap(reportMonth);
		Map<String, Object> dealMap = new HashMap<String, Object>();
		dealMap.put("head", headMap);
		dealMap.put("value", ja);
		dealMap.put("title", "交易额");
		return dealMap;
	}
	
	public Map<String, Object> dealCostMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResIncome> incomeList = this.reportMonthService.incomeList(reportMonth);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportMonth.getStartTime()) && StringUtils.isNotBlank(reportMonth.getEndTime())
				&& incomeList != null) {
			List<String> monthList = StringUtil.getBetweenMonths(reportMonth.getStartTime(), reportMonth.getEndTime());
			for (String date : monthList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				double bjTotal = 0d;// 北京总金额
				double hzTotal = 0d;// 杭州总金额
				int bjCost = 0;
				int hzCost = 0;
				double bjTotalAverage = 0d;
				double hzTotalAverage = 0d;
				double totalAverage = 0d;

				for (ResIncome resIncome : incomeList) {
					if (map.get(resIncome.getPreName()) == null) {
						map.put(resIncome.getPreName(), 0);
					}
					if (date.equals(resIncome.getDay())) {
						map.put(resIncome.getPreName(), resIncome.getDealCostRate());
						if (resIncome.getCityName().equals("北京")) {
							bjTotal = add(bjTotal, resIncome.getTotalAmount());
							bjCost += resIncome.getCost();
						} else if (resIncome.getCityName().equals("杭州")) {
							hzTotal = add(hzTotal, resIncome.getTotalAmount());
							hzCost += resIncome.getCost();
						}
					}
				}

				if (bjCost != 0) {
					bjTotalAverage = new BigDecimal((float) bjTotal / bjCost).setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
				}
				if (hzCost != 0) {
					hzTotalAverage = new BigDecimal((float) hzTotal / hzCost).setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
				}
				if ((bjCost + hzCost) != 0) {
					totalAverage = new BigDecimal((float) (bjTotal + hzTotal) / (bjCost + hzCost))
							.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				log.info("bj_total ,{} hz_total ,{} bj_cost ,{} hz_cost,{}", bjTotal, hzTotal, bjCost, hzCost);

				map.put("bjTotal", bjTotalAverage);
				map.put("hzTotal", hzTotalAverage);
				map.put("total", totalAverage);
				ja.add(map);
			}
		}
		Map<String, String> headMap = incomeTitleMap(reportMonth);
		Map<String, Object> dealCostMap = new HashMap<String, Object>();
		dealCostMap.put("head", headMap);
		dealCostMap.put("value", ja);
		dealCostMap.put("title", "交易额占成本");
		return dealCostMap;
	}
	
	public Map<String, Object> cashMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResIncome> incomeList = this.reportMonthService.incomeList(reportMonth);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportMonth.getStartTime()) && StringUtils.isNotBlank(reportMonth.getEndTime())
				&& incomeList != null) {
			List<String> monthList = StringUtil.getBetweenMonths(reportMonth.getStartTime(), reportMonth.getEndTime());
			for (String date : monthList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				double bjTotal = 0d;// 北京总金额
				double hzTotal = 0d;// 杭州总金额
				for (ResIncome resIncome : incomeList) {
					if (map.get(resIncome.getPreName()) == null) {
						map.put(resIncome.getPreName(), 0);
					}
					if (date.equals(resIncome.getDay())) {
						map.put(resIncome.getPreName(), resIncome.getActualAmount());
						if (resIncome.getCityName().equals("北京")) {
							bjTotal = add(bjTotal, resIncome.getActualAmount());
						} else if (resIncome.getCityName().equals("杭州")) {
							hzTotal = add(hzTotal, resIncome.getActualAmount());
						}
					}
				}
				map.put("bjTotal", bjTotal);
				map.put("hzTotal", hzTotal);
				map.put("total", add(bjTotal, hzTotal));
				ja.add(map);
			}
		}
		Map<String, String> headMap = incomeTitleMap(reportMonth);
		Map<String, Object> cashMap = new HashMap<String, Object>();
		cashMap.put("head", headMap);
		cashMap.put("value", ja);
		cashMap.put("title", "现金收入");
		return cashMap;
	}
	
	public Map<String, Object> cashCostMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResIncome> incomeList = this.reportMonthService.incomeList(reportMonth);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportMonth.getStartTime()) && StringUtils.isNotBlank(reportMonth.getEndTime())
				&& incomeList != null) {
			List<String> monthList = StringUtil.getBetweenMonths(reportMonth.getStartTime(), reportMonth.getEndTime());
			for (String date : monthList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				double bjTotal = 0d;// 北京总金额
				double hzTotal = 0d;// 杭州总金额
				int bjCost = 0;
				int hzCost = 0;
				double bjTotalAverage = 0d;
				double hzTotalAverage = 0d;
				double totalAverage = 0d;

				for (ResIncome resIncome : incomeList) {
					if (map.get(resIncome.getPreName()) == null) {
						map.put(resIncome.getPreName(), 0);
					}
					if (date.equals(resIncome.getDay())) {
						map.put(resIncome.getPreName(), resIncome.getCashCostRate());
						if (resIncome.getCityName().equals("北京")) {
							bjTotal = add(bjTotal, resIncome.getActualAmount());
							bjCost += resIncome.getCost();
						} else if (resIncome.getCityName().equals("杭州")) {
							hzTotal = add(hzTotal, resIncome.getActualAmount());
							hzCost += resIncome.getCost();
						}
					}
				}

				if (bjCost != 0) {
					bjTotalAverage = new BigDecimal((float) bjTotal / bjCost).setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
				}
				if (hzCost != 0) {
					hzTotalAverage = new BigDecimal((float) hzTotal / hzCost).setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
				}
				if ((bjCost + hzCost) != 0) {
					totalAverage = new BigDecimal((float) (bjTotal + hzTotal) / (bjCost + hzCost))
							.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				log.info("bj_total ,{} hz_total ,{} bj_cost ,{} hz_cost,{}", bjTotal, hzTotal, bjCost, hzCost);

				map.put("bjTotal", bjTotalAverage);
				map.put("hzTotal", hzTotalAverage);
				map.put("total", totalAverage);
				ja.add(map);
			}
		}
		Map<String, String> headMap = incomeTitleMap(reportMonth);
		Map<String, Object> cashCostMap = new HashMap<String, Object>();
		cashCostMap.put("head", headMap);
		cashCostMap.put("value", ja);
		cashCostMap.put("title", "现金收入占成本");
		return cashCostMap;
	}
	
	public Map<String, Object> cashDealMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResIncome> incomeList = this.reportMonthService.incomeList(reportMonth);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportMonth.getStartTime()) && StringUtils.isNotBlank(reportMonth.getEndTime())
				&& incomeList != null) {
			List<String> monthList = StringUtil.getBetweenMonths(reportMonth.getStartTime(), reportMonth.getEndTime());
			for (String date : monthList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				double bjTotal = 0d;// 北京总金额
				double hzTotal = 0d;// 杭州总金额
				double bjCost = 0;
				double hzCost = 0;
				double bjTotalAverage = 0d;
				double hzTotalAverage = 0d;
				double totalAverage = 0d;

				for (ResIncome resIncome : incomeList) {
					if (map.get(resIncome.getPreName()) == null) {
						map.put(resIncome.getPreName(), 0);
					}
					if (date.equals(resIncome.getDay())) {
						map.put(resIncome.getPreName(), resIncome.getCashDealRate());
						if (resIncome.getCityName().equals("北京")) {
							bjTotal = add(bjTotal, resIncome.getActualAmount());
							bjCost = add(bjCost, resIncome.getTotalAmount());
						} else if (resIncome.getCityName().equals("杭州")) {
							hzTotal = add(hzTotal, resIncome.getActualAmount());
							hzCost = add(hzCost, resIncome.getTotalAmount());
						}
					}
				}

				if (bjCost != 0) {
					bjTotalAverage = new BigDecimal((float) bjTotal / bjCost).setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
				}
				if (hzCost != 0) {
					hzTotalAverage = new BigDecimal((float) hzTotal / hzCost).setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
				}
				if ((bjCost + hzCost) != 0) {
					totalAverage = new BigDecimal((float) add(bjTotal, hzTotal) / add(hzCost, bjCost))
							.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				log.info("bj_total ,{} hz_total ,{} bj_cost ,{} hz_cost,{}", bjTotal, hzTotal, bjCost, hzCost);

				map.put("bjTotal", bjTotalAverage);
				map.put("hzTotal", hzTotalAverage);
				map.put("total", totalAverage);
				ja.add(map);
			}
		}
		Map<String, String> headMap = incomeTitleMap(reportMonth);
		Map<String, Object> cashDealMap = new HashMap<String, Object>();
		cashDealMap.put("head", headMap);
		cashDealMap.put("value", ja);
		cashDealMap.put("title", "现金收入占交易额");
		return cashDealMap;
	}
	
	public Map<String, Object> feeMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResIncome> incomeList = this.reportMonthService.incomeList(reportMonth);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportMonth.getStartTime()) && StringUtils.isNotBlank(reportMonth.getEndTime())
				&& incomeList != null) {
			List<String> monthList = StringUtil.getBetweenMonths(reportMonth.getStartTime(), reportMonth.getEndTime());
			for (String date : monthList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				double bjTotal = 0d;// 北京总金额
				double hzTotal = 0d;// 杭州总金额
				for (ResIncome resIncome : incomeList) {
					if (map.get(resIncome.getPreName()) == null) {
						map.put(resIncome.getPreName(), 0);
					}
					if (date.equals(resIncome.getDay())) {
						map.put(resIncome.getPreName(), resIncome.getFee());
						if (resIncome.getCityName().equals("北京")) {
							bjTotal += resIncome.getFee();
						} else if (resIncome.getCityName().equals("杭州")) {
							hzTotal += resIncome.getFee();
						}
					}
				}
				map.put("bjTotal", bjTotal);
				map.put("hzTotal", hzTotal);
				map.put("total", add(bjTotal, hzTotal));
				ja.add(map);
			}
		}
		Map<String, String> headMap = incomeTitleMap(reportMonth);
		Map<String, Object> feeMap = new HashMap<String, Object>();
		feeMap.put("head", headMap);
		feeMap.put("value", ja);
		feeMap.put("title", "费用");
		return feeMap;
	}

	public Map<String, Object> pullCostMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResPullCost> pullCostList = this.reportMonthService.pullCostList(reportMonth);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportMonth.getStartTime()) && StringUtils.isNotBlank(reportMonth.getEndTime())
				&& pullCostList != null) {
			List<String> monthList = StringUtil.getBetweenMonths(reportMonth.getStartTime(), reportMonth.getEndTime());
			for (String date : monthList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				int bjFeeTotal = 0;// 北京费用总金额
				int hzFeeTotal = 0;// 杭州费用总金额
				int bjPullCount = 0;
				int hzPullCount = 0;
				double bjTotalAverage = 0d;
				double hzTotalAverage = 0d;
				double totalAverage = 0d;

				for (ResPullCost resPullCost : pullCostList) {
					if (map.get(resPullCost.getPreName()) == null) {
						map.put(resPullCost.getPreName(), 0);
					}
					if (date.equals(resPullCost.getDay())) {
						map.put(resPullCost.getPreName(), resPullCost.getPullCost());
						if (resPullCost.getCityName().equals("北京")) {
							bjFeeTotal += resPullCost.getFee();
							bjPullCount += resPullCost.getDayTotal();
						} else if (resPullCost.getCityName().equals("杭州")) {
							hzFeeTotal += resPullCost.getFee();
							hzPullCount += resPullCost.getDayTotal();
						}
					}
				}

				if (bjPullCount != 0) {
					bjTotalAverage = new BigDecimal((float) bjFeeTotal / bjPullCount)
							.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				if (hzPullCount != 0) {
					hzTotalAverage = new BigDecimal((float) hzFeeTotal / hzPullCount)
							.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				if ((bjPullCount + hzPullCount) != 0) {
					totalAverage = new BigDecimal((float) (bjFeeTotal + hzFeeTotal) / (bjPullCount + hzPullCount))
							.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				log.info("bj_fee_total ,{} hz_fee_total ,{} bj_pull_count ,{} hz_pull_count,{}", bjFeeTotal, hzFeeTotal,
						bjPullCount, hzPullCount);

				map.put("bjTotal", bjTotalAverage);
				map.put("hzTotal", hzTotalAverage);
				map.put("total", totalAverage);
				ja.add(map);
			}
		}
		Map<String, String> headMap = incomeTitleMap(reportMonth);
		Map<String, Object> pullCostMap = new HashMap<String, Object>();
		pullCostMap.put("head", headMap);
		pullCostMap.put("value", ja);
		pullCostMap.put("title", "拉新成本");
		return pullCostMap;
	}
	

	/*
	 * 导出
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public void export(ReqReportMonth reportMonth, HttpServletResponse response, HttpServletRequest request) {
		try {
			String title = "月报";
			// 用户分析
			Map<Integer, Object> exportList = new TreeMap<Integer, Object>(new Comparator<Integer>() {
				public int compare(Integer obj1, Integer obj2) {
					// 降序排序
					return obj1.compareTo(obj2);
				}
			});
			// 用户分析
			Map<String, Object> userNumMap = userNumMap(reportMonth);
			Map<String, Object> newUserMap = newUserMap(reportMonth);
			Map<String, Object> pullMap = pullMap(reportMonth);
			Map<String, Object> outlinePullMap = outlinePullMap(reportMonth);
			Map<String, Object> stallAverageMap = stallAverageMap(reportMonth);
			// 订单分析
			Map<String, Object> orderMap = orderMap(reportMonth);
			Map<String, Object> ylOrderMap = ylOrderMap(reportMonth);
			Map<String, Object> newUserOrderMap = newUserOrderMap(reportMonth);
			Map<String, Object> oldUserOrderMap = oldUserOrderMap(reportMonth);
			Map<String, Object> runtimeMap = runtimeMap(reportMonth);
			Map<String, Object> runtimeRateMap = runtimeRateMap(reportMonth);
			Map<String, Object> rdlMap = rdlMap(reportMonth);
			Map<String, Object> jtscMap = jtscMap(reportMonth);
			Map<String, Object> averagePriceMap = averagePriceMap(reportMonth);
			
			// 收入分析
			Map<String, Object> costMap = costMap(reportMonth);
			Map<String, Object> dealMap = dealMap(reportMonth);
			Map<String, Object> dealCostMap = dealCostMap(reportMonth);
			Map<String, Object> cashMap = cashMap(reportMonth);
			Map<String, Object> cashDealMap = cashDealMap(reportMonth);
			Map<String, Object> cashCostMap = cashCostMap(reportMonth);
			Map<String, Object> feeMap = feeMap(reportMonth);
			Map<String, Object> pullCostMap = pullCostMap(reportMonth);
			//用户
			exportList.put(1, userNumMap);
			exportList.put(2, newUserMap);
			exportList.put(3, pullMap);
			exportList.put(4, outlinePullMap);
			exportList.put(5, stallAverageMap);
			//订单
			exportList.put(6, orderMap);
			exportList.put(7, ylOrderMap);
			exportList.put(8, newUserOrderMap);
			exportList.put(9, oldUserOrderMap);
			exportList.put(10, runtimeMap);
			exportList.put(11, runtimeMap);
			exportList.put(12, rdlMap);
			exportList.put(13, jtscMap);
			exportList.put(14, averagePriceMap);
			
			//收入
			exportList.put(15, costMap);
			exportList.put(16, dealMap);
			exportList.put(17, dealCostMap);
			exportList.put(18, cashMap);
			exportList.put(19, cashDealMap);
			exportList.put(20, cashCostMap);
			exportList.put(21, feeMap);
			exportList.put(22, pullCostMap);

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ExcelUtil.exportReportExcel(exportList, title, os);
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			response.reset();
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String((title + ".xlsx").getBytes(), "iso-8859-1"));
			response.setContentLength(content.length);
			ServletOutputStream outputStream = response.getOutputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			BufferedOutputStream bos = new BufferedOutputStream(outputStream);
			byte[] buff = new byte[8192];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bis.close();
			bos.close();
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, String> userTitleMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResPull> pullList = this.reportMonthService.pullList(reportMonth);
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("day", "日期");
		map.put("total", "合计");
		if (CollectionUtils.isNotEmpty(pullList)) {
			for (ResPull resPull : pullList) {
				if (resPull.getCityName().equals("北京")) {
					if (!map.containsKey("bjTotal")) {
						map.put("bjTotal", resPull.getCityName() + "合计");
					}
				}
				if (resPull.getCityName().equals("杭州")) {
					if (!map.containsKey("hzTotal")) {
						map.put("hzTotal", resPull.getCityName() + "合计");
					}
				}
			}

			for (ResPull resPull : pullList) {
				if (!map.containsKey(resPull.getPreName())) {
					map.put(resPull.getPreName(), resPull.getPreName());
				}
			}
		}
		return map;
	}

	public Map<String, String> orderTitleMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResOrder> orderList = this.reportMonthService.orderList(reportMonth);
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("day", "日期");
		map.put("total", "合计");
		if (CollectionUtils.isNotEmpty(orderList)) {
			for (ResOrder resOrder : orderList) {
				if (resOrder.getCityName().equals("北京")) {
					if (!map.containsKey("bjTotal")) {
						map.put("bjTotal", resOrder.getCityName() + "合计");
					}
				}
				if (resOrder.getCityName().equals("杭州")) {
					if (!map.containsKey("hzTotal")) {
						map.put("hzTotal", resOrder.getCityName() + "合计");
					}
				}
			}

			for (ResOrder resOrder : orderList) {
				if (!map.containsKey(resOrder.getPreName())) {
					map.put(resOrder.getPreName(), resOrder.getPreName());
				}
			}
		}
		return map;
	}

	public Map<String, String> ylOrderTitleMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResOrder> orderList = this.reportMonthService.orderList(reportMonth);
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("day", "日期");
		map.put("total", "合计");
		if (CollectionUtils.isNotEmpty(orderList)) {
			for (ResOrder resOrder : orderList) {
				if (resOrder.getCityName().equals("杭州")) {
					if (!map.containsKey("hzTotal")) {
						map.put("hzTotal", resOrder.getCityName() + "合计");
					}
				}
			}

			for (ResOrder resPull : orderList) {
				if (!map.containsKey(resPull.getPreName()) && resPull.getCityName().equals("杭州")) {
					map.put(resPull.getPreName(), resPull.getPreName());
				}
			}
		}
		return map;
	}
	
	public Map<String, String> costTitleMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResCost> costList = this.reportMonthService.costList(reportMonth);
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("column", "列项");
		map.put("total", "合计");
		if (CollectionUtils.isNotEmpty(costList)) {
			for (ResCost resCost : costList) {
				if (resCost.getCityName().equals("北京")) {
					if (!map.containsKey("bjTotal")) {
						map.put("bjTotal", resCost.getCityName() + "合计");
					}
				}
				if (resCost.getCityName().equals("杭州")) {
					if (!map.containsKey("hzTotal")) {
						map.put("hzTotal", resCost.getCityName() + "合计");
					}
				}
			}

			for (ResCost resPull : costList) {
				if (!map.containsKey(resPull.getPreName())) {
					map.put(resPull.getPreName(), resPull.getPreName());
				}
			}
		} 
		return map;
	}

	
	public Map<String, String> incomeTitleMap(ReqReportMonth reportMonth) throws ParseException {
		List<ResCost> costList = this.reportMonthService.costList(reportMonth);
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("day", "日期");
		map.put("total", "合计");
		if (CollectionUtils.isNotEmpty(costList)) {
			for (ResCost resCost : costList) {
				if (resCost.getCityName().equals("北京")) {
					if (!map.containsKey("bjTotal")) {
						map.put("bjTotal", resCost.getCityName() + "合计");
					}
				}
				if (resCost.getCityName().equals("杭州")) {
					if (!map.containsKey("hzTotal")) {
						map.put("hzTotal", resCost.getCityName() + "合计");
					}
				}
			}

			for (ResCost resPull : costList) {
				if (!map.containsKey(resPull.getPreName())) {
					map.put(resPull.getPreName(), resPull.getPreName());
				}
			}
		}	
		return map;
	}
	
	
}
