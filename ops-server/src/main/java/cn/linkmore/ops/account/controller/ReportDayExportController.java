package cn.linkmore.ops.account.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
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

import cn.linkmore.ops.account.service.ReportDayService;
import cn.linkmore.ops.utils.ExcelUtil;
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
import cn.linkmore.util.StringUtil;

/**
 * Controller - 日报-用户分析
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Controller
@RequestMapping("/admin/account/report_day_export")
public class ReportDayExportController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private ReportDayService reportDayService;

	@RequestMapping(value = "/city_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResCity> cityList() {
		return this.reportDayService.cityList();
	}

	@RequestMapping(value = "/pre_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPre> preList(HttpServletRequest request, Long cityId) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (cityId != 0) {
			param.put("cityId", cityId);
		}
		return this.reportDayService.preList(param);
	}

	/**
	 * 用户数量初始化导出数据
	 * 
	 * @param reportDay
	 * @return
	 */
	public Map<String, Object> userNumMap(ReqReportDay reportDay) {
		List<ResUserNum> list = this.reportDayService.userNumList(reportDay);
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
	 * @param reportDay
	 * @return
	 */
	public Map<String, Object> newUserMap(ReqReportDay reportDay) {
		List<ResNewUser> newUserList = this.reportDayService.newUserList(reportDay);
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
			sNewUser.put("bjPull", newUser.getHzPull());
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

	public Map<String, Object> pullMap(ReqReportDay reportDay) {
		List<ResPull> pullList = this.reportDayService.pullList(reportDay);
		JSONArray jaPull = new JSONArray();
		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())
				&& pullList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(), reportDay.getEndTime());
			Map<String, Object> mapPull = null;
			for (String date : dateList) {
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
		Map<String, String> headMapPull = userTitleMap(reportDay);
		Map<String, Object> pullMap = new HashMap<String, Object>();
		pullMap.put("head", headMapPull);
		pullMap.put("value", jaPull);
		pullMap.put("title", "现场拉新");
		return pullMap;
	}

	public Map<String, Object> outlinePullMap(ReqReportDay reportDay) {
		// List<ResPull> pullList = this.reportDayService.pullList(reportDay);
		List<ResPull> pullList = new ArrayList<ResPull>();
		JSONArray jaPull = new JSONArray();
		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(), reportDay.getEndTime());
			Map<String, Object> mapPull = null;
			for (String date : dateList) {
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
		Map<String, String> headMapPull = userTitleMap(reportDay);
		Map<String, Object> outlinePullMap = new HashMap<String, Object>();
		outlinePullMap.put("head", headMapPull);
		outlinePullMap.put("value", jaPull);
		outlinePullMap.put("title", "线下转化");
		return outlinePullMap;
	}

	/**
	 * 单车位日均
	 * 
	 * @param reportDay
	 * @return
	 */
	public Map<String, Object> stallAverageMap(ReqReportDay reportDay) {
		List<ResStallAverage> averageList = this.reportDayService.stallAverageList(reportDay);
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

		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())
				&& averageList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(), reportDay.getEndTime());
			for (String date : dateList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				int bjTotal = 0;
				int hzTotal = 0;
				double bjTotalAverage = 0d;
				double hzTotalAverage = 0d;
				double totalAverage = 0d;
				for (ResStallAverage resPull : averageList) {
					if (map.get(resPull.getPreName()) == null) {
						map.put(resPull.getPreName(), 0);
					}
					if (date.equals(resPull.getDay())) {
						map.put(resPull.getPreName(), resPull.getAverage());
						if (resPull.getCityName().equals("北京")) {
							bjTotal += resPull.getDayTotal();
						} else if (resPull.getCityName().equals("杭州")) {
							hzTotal += resPull.getDayTotal();
						}
					}
				}
				if (bjStall != 0) {
					bjTotalAverage = new BigDecimal((float) bjTotal / bjStall).setScale(1, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
				}
				if (hzStall != 0) {
					hzTotalAverage = new BigDecimal((float) hzTotal / hzStall).setScale(1, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
				}
				if(bjStall + hzStall != 0) {
					totalAverage = new BigDecimal((float) (bjTotal + hzTotal) / (bjStall + hzStall))
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				map.put("bjTotal", bjTotalAverage);
				map.put("hzTotal", hzTotalAverage);
				map.put("total", totalAverage);
				ja.add(map);
			}
		}
		Map<String, String> headMap = userTitleMap(reportDay);
		Map<String, Object> averageMap = new HashMap<String, Object>();
		averageMap.put("head", headMap);
		averageMap.put("value", ja);
		averageMap.put("title", "单车位日均");
		return averageMap;
	}

	/*********************** 订单 **********************/

	public Map<String, Object> orderMap(ReqReportDay reportDay) {
		List<ResOrder> orderList = this.reportDayService.orderList(reportDay);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())
				&& orderList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(), reportDay.getEndTime());
			for (String date : dateList) {
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
		Map<String, String> headMap = orderTitleMap(reportDay);
		Map<String, Object> orderMap = new HashMap<String, Object>();
		orderMap.put("head", headMap);
		orderMap.put("value", ja);
		orderMap.put("title", "订单数量");
		return orderMap;
	}

	public Map<String, Object> ylOrderMap(ReqReportDay reportDay) {
		List<ResOrder> orderList = this.reportDayService.ylOrderList(reportDay);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())
				&& orderList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(), reportDay.getEndTime());
			for (String date : dateList) {
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
		Map<String, String> headMap = ylOrderTitleMap(reportDay);
		Map<String, Object> orderMap = new HashMap<String, Object>();
		orderMap.put("head", headMap);
		orderMap.put("value", ja);
		orderMap.put("title", "银联订单");
		return orderMap;
	}

	public Map<String, Object> newUserOrderMap(ReqReportDay reportDay) {
		List<ResOrder> orderList = this.reportDayService.newUserOrderList(reportDay);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())
				&& orderList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(), reportDay.getEndTime());
			for (String date : dateList) {
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
		Map<String, String> headMap = orderTitleMap(reportDay);
		Map<String, Object> orderMap = new HashMap<String, Object>();
		orderMap.put("head", headMap);
		orderMap.put("value", ja);
		orderMap.put("title", "新用户订单");
		return orderMap;
	}

	public Map<String, Object> oldUserOrderMap(ReqReportDay reportDay) {
		List<ResOrder> orderList = this.reportDayService.oldUserOrderList(reportDay);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())
				&& orderList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(), reportDay.getEndTime());
			for (String date : dateList) {
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
		Map<String, String> headMap = orderTitleMap(reportDay);
		Map<String, Object> orderMap = new HashMap<String, Object>();
		orderMap.put("head", headMap);
		orderMap.put("value", ja);
		orderMap.put("title", "老用户订单");
		return orderMap;
	}

	public Map<String, Object> runtimeMap(ReqReportDay reportDay) {
		List<ResRunTime> runtimeList = this.reportDayService.runtimeList(reportDay);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;

		int bjStall = 0;
		int hzStall = 0;
		Map<String, Object> stallMap = new HashMap<String, Object>();
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

		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())
				&& runtimeList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(), reportDay.getEndTime());
			for (String date : dateList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				int bjTotalTime = 0;
				int hzTotalTime = 0;
				double bjTotalAverage = 0d;
				double hzTotalAverage = 0d;
				double totalAverage = 0d;
				for (ResRunTime resRunTime : runtimeList) {
					if (map.get(resRunTime.getPreName()) == null) {
						map.put(resRunTime.getPreName(), 0);
					}
					if (date.equals(resRunTime.getDay())) {
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
					bjTotalAverage = new BigDecimal((float) bjTotalTime / 60 / bjStall)
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				if (hzStall != 0) {
					hzTotalAverage = new BigDecimal((float) hzTotalTime / 60 / hzStall)
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}

				if ((bjStall + hzStall) != 0) {
					totalAverage = new BigDecimal((float) (bjTotalTime + hzTotalTime) / 60 / (bjStall + hzStall))
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}

				map.put("bjTotal", bjTotalAverage);
				map.put("hzTotal", hzTotalAverage);
				map.put("total", totalAverage);
				ja.add(map);
			}
		}
		Map<String, String> headMap = orderTitleMap(reportDay);
		Map<String, Object> runtimeMap = new HashMap<String, Object>();
		runtimeMap.put("head", headMap);
		runtimeMap.put("value", ja);
		runtimeMap.put("title", "运营时长");
		return runtimeMap;
	}

	public Map<String, Object> rdlMap(ReqReportDay reportDay) {
		List<ResRunTime> runtimeList = this.reportDayService.runtimeList(reportDay);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;

		int bjStall = 0;
		int hzStall = 0;
		Map<String, Object> stallMap = new HashMap<String, Object>();
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

		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())
				&& runtimeList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(), reportDay.getEndTime());
			for (String date : dateList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				int bjOrderTotal = 0;
				int hzOrderTotal = 0;
				double bjTotalAverage = 0d;
				double hzTotalAverage = 0d;
				double totalAverage = 0d;
				for (ResRunTime resRunTime : runtimeList) {
					if (map.get(resRunTime.getPreName()) == null) {
						map.put(resRunTime.getPreName(), 0);
					}
					if (date.equals(resRunTime.getDay())) {
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
					bjTotalAverage = new BigDecimal((float) bjOrderTotal / bjStall)
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				if (hzStall != 0) {
					hzTotalAverage = new BigDecimal((float) hzOrderTotal / hzStall)
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				if ((bjStall + hzStall) != 0) {
					totalAverage = new BigDecimal((float) (bjOrderTotal + hzOrderTotal) / (bjStall + hzStall))
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}

				map.put("bjTotal", bjTotalAverage);
				map.put("hzTotal", hzTotalAverage);
				map.put("total", totalAverage);
				ja.add(map);
			}
		}
		Map<String, String> headMap = orderTitleMap(reportDay);
		Map<String, Object> rdlMap = new HashMap<String, Object>();
		rdlMap.put("head", headMap);
		rdlMap.put("value", ja);
		rdlMap.put("title", "日单量");
		return rdlMap;
	}

	public Map<String, Object> jtscMap(ReqReportDay reportDay) {
		List<ResRunTime> runtimeList = this.reportDayService.runtimeList(reportDay);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;

		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())
				&& runtimeList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(), reportDay.getEndTime());
			for (String date : dateList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				int bjTotalTime = 0;
				int hzTotalTime = 0;
				int bjOrderCount = 0;
				int hzOrderCount = 0;
				double bjTotalAverage = 0d;
				double hzTotalAverage = 0d;
				double totalAverage = 0d;
				for (ResRunTime resRunTime : runtimeList) {
					if (map.get(resRunTime.getPreName()) == null) {
						map.put(resRunTime.getPreName(), 0);
					}
					if (date.equals(resRunTime.getDay())) {
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
					bjTotalAverage = new BigDecimal((float) bjTotalTime / 60 / bjOrderCount)
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				if (hzOrderCount != 0) {
					hzTotalAverage = new BigDecimal((float) hzTotalTime / 60 / hzOrderCount)
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				if ((bjTotalTime + hzTotalTime) != 0) {
					totalAverage = new BigDecimal(
							(float) (bjTotalTime + hzTotalTime) / 60 / (bjOrderCount + hzOrderCount))
									.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}

				map.put("bjTotal", bjTotalAverage);
				map.put("hzTotal", hzTotalAverage);
				map.put("total", totalAverage);
				ja.add(map);
			}
		}
		Map<String, String> headMap = orderTitleMap(reportDay);
		Map<String, Object> jtscMap = new HashMap<String, Object>();
		jtscMap.put("head", headMap);
		jtscMap.put("value", ja);
		jtscMap.put("title", "均停时长");
		return jtscMap;
	}

	public double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	public Map<String, Object> averagePriceMap(ReqReportDay reportDay) {
		List<ResAveragePrice> averageList = this.reportDayService.averagePriceList(reportDay);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())
				&& averageList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(), reportDay.getEndTime());
			for (String date : dateList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				// 此处需要重新计算
				double bjAmountTotal = 0d;// 北京总金额
				double hzAmountTotal = 0d;// 杭州总金额
				int bjDayTotal = 0;// 北京总订单量
				int hzDayTotal = 0;// 杭州总订单量
				double bjTotalAverage = 0d;
				double hzTotalAverage = 0d;
				double totalAverage = 0d;
				for (ResAveragePrice resPrice : averageList) {
					if (map.get(resPrice.getPreName()) == null) {
						map.put(resPrice.getPreName(), 0);
					}
					if (date.equals(resPrice.getDay())) {
						map.put(resPrice.getPreName(), resPrice.getAveragePrice());
						if (resPrice.getCityName().equals("北京")) {
							bjAmountTotal = add(bjAmountTotal, resPrice.getAmountTotal());
							bjDayTotal += resPrice.getDayTotal();
						} else if (resPrice.getCityName().equals("杭州")) {
							hzAmountTotal = add(hzAmountTotal, resPrice.getAmountTotal());
							hzDayTotal += resPrice.getDayTotal();
						}
					} else {

					}
				}
				if (bjDayTotal != 0) {
					bjTotalAverage = new BigDecimal((float) bjAmountTotal / bjDayTotal)
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				if (hzDayTotal != 0) {
					hzTotalAverage = new BigDecimal((float) hzAmountTotal / hzDayTotal)
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				if ((bjDayTotal + hzDayTotal) != 0) {
					totalAverage = new BigDecimal((float) (bjAmountTotal + hzAmountTotal) / (bjDayTotal + hzDayTotal))
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
		Map<String, String> headMap = orderTitleMap(reportDay);
		Map<String, Object> averagePriceMap = new HashMap<String, Object>();
		averagePriceMap.put("head", headMap);
		averagePriceMap.put("value", ja);
		averagePriceMap.put("title", "客单价");
		return averagePriceMap;
	}

	public Map<String, Object> costMap(ReqReportDay reportDay) {
		List<ResCost> costList = this.reportDayService.costList(reportDay);
		Map<String, Object> map = null;
		JSONArray ja = new JSONArray();
		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())
				&& costList != null) {
			List<String> dateList = new ArrayList<String>();
			dateList.add("单车位天成本");
			dateList.add("车区天成本");
			dateList.add("单车位月成本");
			dateList.add("车区月成本");
			for (String column : dateList) {
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

					if (column.equals("车区天成本")) {
						map.put(resCost.getPreName(), resCost.getMonthRent() / 30);
						if (resCost.getCityName().equals("北京")) {
							bjTotal += resCost.getMonthRent() / 30;
						} else if (resCost.getCityName().equals("杭州")) {
							hzTotal += resCost.getMonthRent() / 30;
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

					if (column.equals("单车位天成本")) {
						map.put(resCost.getPreName(), resCost.getMonthRent() / resCost.getStallTotal() / 30);
						if (resCost.getCityName().equals("北京")) {
							bjTotal += resCost.getMonthRent() / resCost.getStallTotal() / 30;
						} else if (resCost.getCityName().equals("杭州")) {
							hzTotal += resCost.getMonthRent() / resCost.getStallTotal() / 30;
						}
					}
				}
				map.put("bjTotal", bjTotal);
				map.put("hzTotal", hzTotal);
				map.put("total", bjTotal + hzTotal);
				ja.add(map);
			}
		}
		Map<String, String> headMap = costTitleMap(reportDay);
		Map<String, Object> costMap = new HashMap<String, Object>();
		costMap.put("head", headMap);
		costMap.put("value", ja);
		costMap.put("title", "成本");
		return costMap;
	}

	public Map<String, Object> dealMap(ReqReportDay reportDay) {
		List<ResIncome> incomeList = this.reportDayService.incomeList(reportDay);
		Map<String, Object> map = null;
		JSONArray ja = new JSONArray();
		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())
				&& incomeList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(), reportDay.getEndTime());
			for (String date : dateList) {
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
		Map<String, String> headMap = incomeTitleMap(reportDay);
		Map<String, Object> dealMap = new HashMap<String, Object>();
		dealMap.put("head", headMap);
		dealMap.put("value", ja);
		dealMap.put("title", "交易额");
		return dealMap;
	}

	public Map<String, Object> dealCostMap(ReqReportDay reportDay) {
		List<ResIncome> incomeList = this.reportDayService.incomeList(reportDay);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())
				&& incomeList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(), reportDay.getEndTime());
			for (String date : dateList) {
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
		Map<String, String> headMap = incomeTitleMap(reportDay);
		Map<String, Object> dealCostMap = new HashMap<String, Object>();
		dealCostMap.put("head", headMap);
		dealCostMap.put("value", ja);
		dealCostMap.put("title", "交易额占成本");
		return dealCostMap;
	}

	public Map<String, Object> cashMap(ReqReportDay reportDay) {
		List<ResIncome> incomeList = this.reportDayService.incomeList(reportDay);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())
				&& incomeList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(), reportDay.getEndTime());
			for (String date : dateList) {
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
		Map<String, String> headMap = incomeTitleMap(reportDay);
		Map<String, Object> cashMap = new HashMap<String, Object>();
		cashMap.put("head", headMap);
		cashMap.put("value", ja);
		cashMap.put("title", "现金收入");
		return cashMap;
	}

	public Map<String, Object> cashCostMap(ReqReportDay reportDay) {
		List<ResIncome> incomeList = this.reportDayService.incomeList(reportDay);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())
				&& incomeList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(), reportDay.getEndTime());
			for (String date : dateList) {
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
		Map<String, String> headMap = incomeTitleMap(reportDay);
		Map<String, Object> cashCostMap = new HashMap<String, Object>();
		cashCostMap.put("head", headMap);
		cashCostMap.put("value", ja);
		cashCostMap.put("title", "现金收入占成本");
		return cashCostMap;
	}

	public Map<String, Object> cashDealMap(ReqReportDay reportDay) {
		List<ResIncome> incomeList = this.reportDayService.incomeList(reportDay);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())
				&& incomeList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(), reportDay.getEndTime());
			for (String date : dateList) {
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
		Map<String, String> headMap = incomeTitleMap(reportDay);
		Map<String, Object> cashDealMap = new HashMap<String, Object>();
		cashDealMap.put("head", headMap);
		cashDealMap.put("value", ja);
		cashDealMap.put("title", "现金收入占交易额");
		return cashDealMap;
	}

	public Map<String, Object> feeMap(ReqReportDay reportDay) {
		List<ResIncome> incomeList = this.reportDayService.incomeList(reportDay);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())
				&& incomeList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(), reportDay.getEndTime());
			for (String date : dateList) {
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
		Map<String, String> headMap = incomeTitleMap(reportDay);
		Map<String, Object> feeMap = new HashMap<String, Object>();
		feeMap.put("head", headMap);
		feeMap.put("value", ja);
		feeMap.put("title", "费用");
		return feeMap;
	}

	public Map<String, Object> pullCostMap(ReqReportDay reportDay) {
		List<ResPullCost> pullCostList = this.reportDayService.pullCostList(reportDay);
		JSONArray ja = new JSONArray();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())
				&& pullCostList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(), reportDay.getEndTime());
			for (String date : dateList) {
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
		Map<String, String> headMap = incomeTitleMap(reportDay);
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
	public void export(ReqReportDay reportDay, HttpServletResponse response, HttpServletRequest request) {
		try {
			String title = "日报";
			// 用户分析
			Map<Integer, Object> exportList = new TreeMap<Integer, Object>(new Comparator<Integer>() {
				public int compare(Integer obj1, Integer obj2) {
					// 降序排序
					return obj1.compareTo(obj2);
				}
			});
			// 用户分析
			Map<String, Object> userNumMap = userNumMap(reportDay);
			Map<String, Object> newUserMap = newUserMap(reportDay);
			Map<String, Object> pullMap = pullMap(reportDay);
			Map<String, Object> outlinePullMap = outlinePullMap(reportDay);
			Map<String, Object> stallAverageMap = stallAverageMap(reportDay);
			// 订单分析
			Map<String, Object> orderMap = orderMap(reportDay);
			Map<String, Object> ylOrderMap = ylOrderMap(reportDay);
			Map<String, Object> newUserOrderMap = newUserOrderMap(reportDay);
			Map<String, Object> oldUserOrderMap = oldUserOrderMap(reportDay);
			Map<String, Object> runtimeMap = runtimeMap(reportDay);
			Map<String, Object> rdlMap = rdlMap(reportDay);
			Map<String, Object> jtscMap = jtscMap(reportDay);
			Map<String, Object> averagePriceMap = averagePriceMap(reportDay);

			// 收入分析
			Map<String, Object> costMap = costMap(reportDay);
			Map<String, Object> dealMap = dealMap(reportDay);
			Map<String, Object> dealCostMap = dealCostMap(reportDay);
			Map<String, Object> cashMap = cashMap(reportDay);
			Map<String, Object> cashDealMap = cashDealMap(reportDay);
			Map<String, Object> cashCostMap = cashCostMap(reportDay);
			Map<String, Object> feeMap = feeMap(reportDay);
			Map<String, Object> pullCostMap = pullCostMap(reportDay);
			// 用户
			exportList.put(1, userNumMap);
			exportList.put(2, newUserMap);
			exportList.put(3, pullMap);
			exportList.put(4, outlinePullMap);
			exportList.put(5, stallAverageMap);
			// 订单
			exportList.put(6, orderMap);
			exportList.put(7, ylOrderMap);
			exportList.put(8, newUserOrderMap);
			exportList.put(9, oldUserOrderMap);
			exportList.put(10, runtimeMap);
			exportList.put(11, rdlMap);
			exportList.put(12, jtscMap);
			exportList.put(13, averagePriceMap);

			// 收入
			exportList.put(14, costMap);
			exportList.put(15, dealMap);
			exportList.put(16, dealCostMap);
			exportList.put(17, cashMap);
			exportList.put(18, cashDealMap);
			exportList.put(19, cashCostMap);
			exportList.put(20, feeMap);
			exportList.put(21, pullCostMap);

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

	public Map<String, String> userTitleMap(ReqReportDay reportDay) {
		List<ResPull> pullList = this.reportDayService.pullList(reportDay);
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

	public Map<String, String> orderTitleMap(ReqReportDay reportDay) {
		List<ResOrder> orderList = this.reportDayService.orderList(reportDay);
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

	public Map<String, String> ylOrderTitleMap(ReqReportDay reportDay) {
		List<ResOrder> orderList = this.reportDayService.orderList(reportDay);
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

	public Map<String, String> costTitleMap(ReqReportDay reportDay) {
		List<ResCost> costList = this.reportDayService.costList(reportDay);
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

	public Map<String, String> incomeTitleMap(ReqReportDay reportDay) {
		List<ResCost> costList = this.reportDayService.costList(reportDay);
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
