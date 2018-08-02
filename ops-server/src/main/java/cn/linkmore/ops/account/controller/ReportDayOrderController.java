package cn.linkmore.ops.account.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.linkmore.bean.common.ResultMap;
import cn.linkmore.ops.account.service.ReportDayService;
import cn.linkmore.report.request.ReqReportDay;
import cn.linkmore.report.response.ResAveragePrice;
import cn.linkmore.report.response.ResCity;
import cn.linkmore.report.response.ResOrder;
import cn.linkmore.report.response.ResPre;
import cn.linkmore.report.response.ResRunTime;
import cn.linkmore.report.response.ResStallAverage;
import cn.linkmore.report.response.ResTitle;
import cn.linkmore.util.StringUtil;

/**
 * Controller 日报-订单分析
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Controller
@RequestMapping("/admin/account/report_day_order")
public class ReportDayOrderController {

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

	@RequestMapping(value = "/title", method = RequestMethod.POST)
	@ResponseBody
	public List<ResTitle> titleList(HttpServletRequest request, ReqReportDay reportDay) {
		List<ResOrder> orderList = this.reportDayService.orderList(reportDay);
		Map<String, String> map = new HashMap<String, String>();
		List<ResTitle> titleList = new ArrayList<ResTitle>();
		ResTitle title = new ResTitle();
		title.setField("day");
		title.setTitle("日期");
		titleList.add(title);
		ResTitle title2 = new ResTitle();
		title2.setField("total");
		title2.setTitle("合计");
		titleList.add(title2);
		if (CollectionUtils.isNotEmpty(orderList)) {
			for (ResOrder resOrder : orderList) {
				if (resOrder.getCityName().equals("北京")) {
					if (!map.containsKey("bjTotal")) {
						ResTitle title3 = new ResTitle();
						title3.setField("bjTotal");
						title3.setTitle(resOrder.getCityName() + "合计");
						titleList.add(title3);
						map.put("bjTotal", resOrder.getCityName() + "合计");
					}
				}
				if (resOrder.getCityName().equals("杭州")) {
					if (!map.containsKey("hzTotal")) {
						ResTitle title4 = new ResTitle();
						title4.setField("hzTotal");
						title4.setTitle(resOrder.getCityName() + "合计");
						titleList.add(title4);
						map.put("hzTotal", resOrder.getCityName() + "合计");
					}
				}
			}

			for (ResOrder resOrder : orderList) {
				if (!map.containsKey(resOrder.getPreName())) {
					ResTitle title5 = new ResTitle();
					title5.setField(resOrder.getPreName());
					title5.setTitle(resOrder.getPreName());
					titleList.add(title5);
					map.put(resOrder.getPreName(), resOrder.getPreName());
				}
			}
			int titleSize = titleList.size();
			String width = "130";
			if (titleSize < 10) {
				width = 100 / titleSize + "%";
			}
			for (ResTitle resTitle : titleList) {
				resTitle.setWidth(width);
			}
		} else {
			titleList = new ArrayList<ResTitle>();
		}

		return titleList;
	}

	@RequestMapping(value = "/yl_title", method = RequestMethod.POST)
	@ResponseBody
	public List<ResTitle> ylTitleList(HttpServletRequest request, ReqReportDay reportDay) {
		List<ResOrder> orderList = this.reportDayService.orderList(reportDay);
		Map<String, String> map = new HashMap<String, String>();
		List<ResTitle> titleList = new ArrayList<ResTitle>();
		ResTitle title = new ResTitle();
		title.setField("day");
		title.setTitle("日期");
		titleList.add(title);
		ResTitle title2 = new ResTitle();
		title2.setField("total");
		title2.setTitle("合计");
		titleList.add(title2);
		if (CollectionUtils.isNotEmpty(orderList)) {
			for (ResOrder resOrder : orderList) {
				if (resOrder.getCityName().equals("杭州")) {
					if (!map.containsKey("hzTotal")) {
						ResTitle title4 = new ResTitle();
						title4.setField("hzTotal");
						title4.setTitle(resOrder.getCityName() + "合计");
						titleList.add(title4);
						map.put("hzTotal", resOrder.getCityName() + "合计");
					}
				}
			}

			for (ResOrder resPull : orderList) {
				if (!map.containsKey(resPull.getPreName()) && resPull.getCityName().equals("杭州")) {
					ResTitle title5 = new ResTitle();
					title5.setField(resPull.getPreName());
					title5.setTitle(resPull.getPreName());
					titleList.add(title5);
					map.put(resPull.getPreName(), resPull.getPreName());
				}
			}
			int titleSize = titleList.size();
			String width = "130";
			if (titleSize < 10) {
				width = 100 / titleSize + "%";
			}
			for (ResTitle resTitle : titleList) {
				resTitle.setWidth(width);
			}
		} else {
			titleList = new ArrayList<ResTitle>();
		}

		return titleList;
	}

	@RequestMapping(value = "/stall_average", method = RequestMethod.POST)
	@ResponseBody
	public ResultMap<List<Map<String, Object>>> stallAverageList(HttpServletRequest request, ReqReportDay reportDay) {
		List<ResStallAverage> averageList = this.reportDayService.stallAverageList(reportDay);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		if(CollectionUtils.isNotEmpty(averageList)) {
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
					totalAverage = new BigDecimal((float) (bjTotal + hzTotal) / (bjStall + hzStall))
							.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
					map.put("bjTotal", bjTotalAverage);
					map.put("hzTotal", hzTotalAverage);
					map.put("total", totalAverage);
					list.add(map);
				}
			}
		}
		return new ResultMap<List<Map<String, Object>>>(0, "", list, list.size());
	}

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	@ResponseBody
	public ResultMap<List<Map<String, Object>>> orderList(HttpServletRequest request, ReqReportDay reportDay) {
		List<ResOrder> orderList = this.reportDayService.orderList(reportDay);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
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
				list.add(map);
			}
		}
		return new ResultMap<List<Map<String, Object>>>(0, "", list, list.size());
	}

	@RequestMapping(value = "/yl_order", method = RequestMethod.POST)
	@ResponseBody
	public ResultMap<List<Map<String, Object>>> ylOrderList(HttpServletRequest request, ReqReportDay reportDay) {
		List<ResOrder> orderList = this.reportDayService.ylOrderList(reportDay);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())
				&& orderList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(), reportDay.getEndTime());
			for (String date : dateList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				int hzTotal = 0;
				for (ResOrder resPull : orderList) {
					if (map.get(resPull.getPreName()) == null) {
						map.put(resPull.getPreName(), 0);
					}
					if (date.equals(resPull.getDay())) {
						map.put(resPull.getPreName(), resPull.getDayTotal());
						if (resPull.getCityName().equals("杭州")) {
							hzTotal += resPull.getDayTotal();
						}
					}
				}
				map.put("hzTotal", hzTotal);
				map.put("total", hzTotal);
				list.add(map);
			}
		}
		return new ResultMap<List<Map<String, Object>>>(0, "", list, list.size());
	}

	@RequestMapping(value = "/newuser_order", method = RequestMethod.POST)
	@ResponseBody
	public ResultMap<List<Map<String, Object>>> newUserOrderList(HttpServletRequest request, ReqReportDay reportDay) {
		List<ResOrder> orderList = this.reportDayService.newUserOrderList(reportDay);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
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
				list.add(map);
			}
		}
		return new ResultMap<List<Map<String, Object>>>(0, "", list, list.size());
	}

	@RequestMapping(value = "/olduser_order", method = RequestMethod.POST)
	@ResponseBody
	public ResultMap<List<Map<String, Object>>> oldUserOrderList(HttpServletRequest request, ReqReportDay reportDay) {
		List<ResOrder> orderList = this.reportDayService.oldUserOrderList(reportDay);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
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
				list.add(map);
			}
		}
		return new ResultMap<List<Map<String, Object>>>(0, "", list, list.size());
	}

	/**
	 * 运营时长
	 * 
	 * @param request
	 * @param reportDay
	 * @return
	 */
	@RequestMapping(value = "/runtime", method = RequestMethod.POST)
	@ResponseBody
	public ResultMap<List<Map<String, Object>>> runtimeList(HttpServletRequest request, ReqReportDay reportDay) {
		List<ResRunTime> runtimeList = this.reportDayService.runtimeList(reportDay);
		log.info("runtime list {}",JSON.toJSON(runtimeList));
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		if(CollectionUtils.isNotEmpty(runtimeList)) {
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

			if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())) {
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
					list.add(map);
				}
			}
		}
		return new ResultMap<List<Map<String, Object>>>(0, "", list, list.size());
	}
	
	/**
	 * 运营时长比例
	 * 
	 * @param request
	 * @param reportDay
	 * @return
	 */
	@RequestMapping(value = "/runtime_rate", method = RequestMethod.POST)
	@ResponseBody
	public ResultMap<List<Map<String, Object>>> runtimeRateList(HttpServletRequest request, ReqReportDay reportDay) {
		List<ResRunTime> runtimeList = this.reportDayService.runtimeList(reportDay);
		log.info("runtime_rate list {}",JSON.toJSON(runtimeList));
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		if(CollectionUtils.isNotEmpty(runtimeList)) {
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
					int bjShopRuntime = 0;
					int hzShopRuntime = 0;
					double bjRuntimeRate = 0d;
					double hzRuntimeRate = 0d;
					double totalRuntimeRate = 0d;
					for (ResRunTime resRunTime : runtimeList) {
						if (map.get(resRunTime.getPreName()) == null) {
							map.put(resRunTime.getPreName(), 0);
						}
						if (date.equals(resRunTime.getDay())) {
							map.put(resRunTime.getPreName(), resRunTime.getRuntimeRate());
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
						bjRuntimeRate = new BigDecimal((float) bjTotalTime / 60 / bjShopRuntime / bjStall)
								.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
					}
					if (hzStall != 0 && hzShopRuntime != 0) {
						hzRuntimeRate = new BigDecimal((float) hzTotalTime / 60/ hzShopRuntime / hzStall)
								.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
					}

					if ((bjStall + hzStall) != 0 && (bjShopRuntime + hzShopRuntime)!= 0) {
						totalRuntimeRate = new BigDecimal((float) (bjTotalTime + hzTotalTime) / (bjShopRuntime + hzShopRuntime) / 60 / (bjStall + hzStall))
								.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
					}

					map.put("bjTotal", bjRuntimeRate);
					map.put("hzTotal", hzRuntimeRate);
					map.put("total", totalRuntimeRate);
					list.add(map);
				}
			}
		}
		return new ResultMap<List<Map<String, Object>>>(0, "", list, list.size());
	}

	/**
	 * 日单量
	 * 
	 * @param request
	 * @param reportDay
	 * @return
	 */
	@RequestMapping(value = "/rdl", method = RequestMethod.POST)
	@ResponseBody
	public ResultMap<List<Map<String, Object>>> rdlList(HttpServletRequest request, ReqReportDay reportDay) {
		List<ResRunTime> runtimeList = this.reportDayService.runtimeList(reportDay);
		log.info("rdl list {}",JSON.toJSON(runtimeList));
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		if(CollectionUtils.isNotEmpty(runtimeList)) {
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

			if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())) {
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
					list.add(map);
				}
			}
		}
		return new ResultMap<List<Map<String, Object>>>(0, "", list, list.size());
	}

	/**
	 * 均停时长
	 * 
	 * @param request
	 * @param reportDay
	 * @return
	 */
	@RequestMapping(value = "/jtsc", method = RequestMethod.POST)
	@ResponseBody
	public ResultMap<List<Map<String, Object>>> jtscList(HttpServletRequest request, ReqReportDay reportDay) {
		List<ResRunTime> runtimeList = this.reportDayService.runtimeList(reportDay);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
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
				list.add(map);
			}
		}
		return new ResultMap<List<Map<String, Object>>>(0, "", list, list.size());
	}
	
	public double add(double v1, double v2) {
       BigDecimal b1=new BigDecimal(Double.toString(v1));
       BigDecimal b2 = new BigDecimal(Double.toString(v2));
       return b1.add(b2).doubleValue();
    }

	/**
	 * 客单价
	 * 
	 * @param request
	 * @param reportDay
	 * @return
	 */
	@RequestMapping(value = "/average_price", method = RequestMethod.POST)
	@ResponseBody
	public ResultMap<List<Map<String, Object>>> averagePriceList(HttpServletRequest request, ReqReportDay reportDay) {
		List<ResAveragePrice> averageList = this.reportDayService.averagePriceList(reportDay);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
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
				list.add(map);
			}
		}
		return new ResultMap<List<Map<String, Object>>>(0, "", list, list.size());
	}
}