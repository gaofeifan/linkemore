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
import cn.linkmore.bean.common.ResultMap;
import cn.linkmore.ops.account.service.ReportDayService;
import cn.linkmore.report.request.ReqReportDay;
import cn.linkmore.report.response.ResCity;
import cn.linkmore.report.response.ResCost;
import cn.linkmore.report.response.ResIncome;
import cn.linkmore.report.response.ResPre;
import cn.linkmore.report.response.ResPullCost;
import cn.linkmore.report.response.ResTitle;
import cn.linkmore.util.StringUtil;

/**
 * Controller 日报- 收入分析
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Controller
@RequestMapping("/admin/account/report_day_income")
public class ReportDayIncomeController {

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
		List<ResCost> costList = this.reportDayService.costList(reportDay);
		Map<String, String> map = new HashMap<String, String>();
		List<ResTitle> titleList = new ArrayList<ResTitle>();
		ResTitle title = new ResTitle();
		title.setField("column");
		title.setTitle("列项");
		titleList.add(title);
		ResTitle title2 = new ResTitle();
		title2.setField("total");
		title2.setTitle("合计");
		titleList.add(title2);
		if (CollectionUtils.isNotEmpty(costList)) {
			for (ResCost resCost : costList) {
				if (resCost.getCityName().equals("北京")) {
					if (!map.containsKey("bjTotal")) {
						ResTitle title3 = new ResTitle();
						title3.setField("bjTotal");
						title3.setTitle(resCost.getCityName() + "合计");
						titleList.add(title3);
						map.put("bjTotal", resCost.getCityName() + "合计");
					}
				}
				if (resCost.getCityName().equals("杭州")) {
					if (!map.containsKey("hzTotal")) {
						ResTitle title4 = new ResTitle();
						title4.setField("hzTotal");
						title4.setTitle(resCost.getCityName() + "合计");
						titleList.add(title4);
						map.put("hzTotal", resCost.getCityName() + "合计");
					}
				}
			}

			for (ResCost resPull : costList) {
				if (!map.containsKey(resPull.getPreName())) {
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

	@RequestMapping(value = "/income_title", method = RequestMethod.POST)
	@ResponseBody
	public List<ResTitle> incomeTitleList(HttpServletRequest request, ReqReportDay reportDay) {
		List<ResCost> costList = this.reportDayService.costList(reportDay);
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
		if (CollectionUtils.isNotEmpty(costList)) {
			for (ResCost resCost : costList) {
				if (resCost.getCityName().equals("北京")) {
					if (!map.containsKey("bjTotal")) {
						ResTitle title3 = new ResTitle();
						title3.setField("bjTotal");
						title3.setTitle(resCost.getCityName() + "合计");
						titleList.add(title3);
						map.put("bjTotal", resCost.getCityName() + "合计");
					}
				}
				if (resCost.getCityName().equals("杭州")) {
					if (!map.containsKey("hzTotal")) {
						ResTitle title4 = new ResTitle();
						title4.setField("hzTotal");
						title4.setTitle(resCost.getCityName() + "合计");
						titleList.add(title4);
						map.put("hzTotal", resCost.getCityName() + "合计");
					}
				}
			}

			for (ResCost resPull : costList) {
				if (!map.containsKey(resPull.getPreName())) {
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

	@RequestMapping(value = "/cost", method = RequestMethod.POST)
	@ResponseBody
	public ResultMap<List<Map<String, Object>>> costList(HttpServletRequest request, ReqReportDay reportDay) {
		List<ResCost> costList = this.reportDayService.costList(reportDay);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
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
				double bjTotal = 0;
				double hzTotal = 0;
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
						double dayRent =  new BigDecimal((float) resCost.getMonthRent() / 30).setScale(0, BigDecimal.ROUND_HALF_UP)
								.doubleValue();
						map.put(resCost.getPreName(), dayRent);
						if (resCost.getCityName().equals("北京")) {
							bjTotal = add(bjTotal, dayRent);
						} else if (resCost.getCityName().equals("杭州")) {
							hzTotal = add(hzTotal, dayRent);
						}
					}

					if (column.equals("单车位月成本")) {
						double monthStallRent = new BigDecimal((float) resCost.getMonthRent() / resCost.getStallTotal()).setScale(0, BigDecimal.ROUND_HALF_UP)
								.doubleValue();
						map.put(resCost.getPreName(), monthStallRent);
						if (resCost.getCityName().equals("北京")) {
							bjTotal = add(bjTotal, monthStallRent);
							//bjTotal += resCost.getMonthRent() / resCost.getStallTotal();
						} else if (resCost.getCityName().equals("杭州")) {
							hzTotal = add(hzTotal, monthStallRent);
							//hzTotal += resCost.getMonthRent() / resCost.getStallTotal();
						}
					}

					if (column.equals("单车位天成本")) {
						double dayStallRent = new BigDecimal((float) resCost.getMonthRent() / resCost.getStallTotal() / 30).setScale(0, BigDecimal.ROUND_HALF_UP)
								.doubleValue();
						map.put(resCost.getPreName(), dayStallRent);
						if (resCost.getCityName().equals("北京")) {
							bjTotal = add(bjTotal ,dayStallRent);
							//bjTotal += resCost.getMonthRent() / resCost.getStallTotal() / 30;
						} else if (resCost.getCityName().equals("杭州")) {
							hzTotal = add(hzTotal ,dayStallRent);
							//hzTotal += resCost.getMonthRent() / resCost.getStallTotal() / 30;
						}
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

	public double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	@RequestMapping(value = "/deal", method = RequestMethod.POST)
	@ResponseBody
	public ResultMap<List<Map<String, Object>>> dealList(HttpServletRequest request, ReqReportDay reportDay) {
		List<ResIncome> incomeList = this.reportDayService.incomeList(reportDay);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
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
				list.add(map);
			}
		}
		return new ResultMap<List<Map<String, Object>>>(0, "", list, list.size());
	}

	@RequestMapping(value = "/deal_cost", method = RequestMethod.POST)
	@ResponseBody
	public ResultMap<List<Map<String, Object>>> dealCostList(HttpServletRequest request, ReqReportDay reportDay) {
		List<ResIncome> incomeList = this.reportDayService.incomeList(reportDay);
		List<ResCost> costList = this.reportDayService.costList(reportDay);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())
				&& costList != null && incomeList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(), reportDay.getEndTime());
			//车区成本
			double bjCost = 0;
			double hzCost = 0;
			for (ResCost resCost : costList) {
				double dayRent =  new BigDecimal((float) resCost.getMonthRent() / 30).setScale(0, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				if (resCost.getCityName().equals("北京")) {
					bjCost = add(bjCost, dayRent);
				} else if (resCost.getCityName().equals("杭州")) {
					hzCost = add(hzCost, dayRent);
				}
			}
			
			for (String date : dateList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				double bjTotal = 0d;// 北京总金额
				double hzTotal = 0d;// 杭州总金额
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
							//bjCost += resIncome.getCost();
						} else if (resIncome.getCityName().equals("杭州")) {
							hzTotal = add(hzTotal, resIncome.getTotalAmount());
							//hzCost += resIncome.getCost();
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
				list.add(map);
			}
		}
		return new ResultMap<List<Map<String, Object>>>(0, "", list, list.size());
	}

	@RequestMapping(value = "/cash", method = RequestMethod.POST)
	@ResponseBody
	public ResultMap<List<Map<String, Object>>> cashList(HttpServletRequest request, ReqReportDay reportDay) {
		List<ResIncome> incomeList = this.reportDayService.incomeList(reportDay);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
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
				list.add(map);
			}
		}
		return new ResultMap<List<Map<String, Object>>>(0, "", list, list.size());
	}

	@RequestMapping(value = "/cash_cost", method = RequestMethod.POST)
	@ResponseBody
	public ResultMap<List<Map<String, Object>>> cashCostList(HttpServletRequest request, ReqReportDay reportDay) {
		List<ResIncome> incomeList = this.reportDayService.incomeList(reportDay);
		List<ResCost> costList = this.reportDayService.costList(reportDay);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())
				&& costList != null	&& incomeList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(), reportDay.getEndTime());
			
			double bjCost = 0;
			double hzCost = 0;
			for (ResCost resCost : costList) {
				double dayRent =  new BigDecimal((float) resCost.getMonthRent() / 30).setScale(0, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				if (resCost.getCityName().equals("北京")) {
					bjCost = add(bjCost, dayRent);
				} else if (resCost.getCityName().equals("杭州")) {
					hzCost = add(hzCost, dayRent);
				}
			}
			
			for (String date : dateList) {
				map = new HashMap<String, Object>();
				map.put("day", date);
				double bjTotal = 0d;// 北京总金额
				double hzTotal = 0d;// 杭州总金额
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
						} else if (resIncome.getCityName().equals("杭州")) {
							hzTotal = add(hzTotal, resIncome.getActualAmount());
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
				list.add(map);
			}
		}
		return new ResultMap<List<Map<String, Object>>>(0, "", list, list.size());
	}

	@RequestMapping(value = "/cash_deal", method = RequestMethod.POST)
	@ResponseBody
	public ResultMap<List<Map<String, Object>>> cashDealList(HttpServletRequest request, ReqReportDay reportDay) {
		List<ResIncome> incomeList = this.reportDayService.incomeList(reportDay);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
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
				list.add(map);
			}
		}
		return new ResultMap<List<Map<String, Object>>>(0, "", list, list.size());
	}

	@RequestMapping(value = "/fee", method = RequestMethod.POST)
	@ResponseBody
	public ResultMap<List<Map<String, Object>>> feeList(HttpServletRequest request, ReqReportDay reportDay) {
		List<ResIncome> incomeList = this.reportDayService.incomeList(reportDay);
		/*List<ResCost> costList = this.reportDayService.costList(reportDay);
		Map<String, Object> costMap = new HashMap<String, Object>();*/
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(reportDay.getStartTime()) && StringUtils.isNotBlank(reportDay.getEndTime())
			&& incomeList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(), reportDay.getEndTime());
			
			/*for (ResCost resCost : costList) {
				double dayRent =  new BigDecimal((float) resCost.getMonthRent() / 30).setScale(0, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				costMap.put(resCost.getPreName(), dayRent);
			}*/
			
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
				list.add(map);
			}
		}
		return new ResultMap<List<Map<String, Object>>>(0, "", list, list.size());
	}

	@RequestMapping(value = "/pull_cost", method = RequestMethod.POST)
	@ResponseBody
	public ResultMap<List<Map<String, Object>>> pullCostList(HttpServletRequest request, ReqReportDay reportDay) {
		List<ResPullCost> pullCostList = this.reportDayService.pullCostList(reportDay);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
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
				list.add(map);
			}
		}
		return new ResultMap<List<Map<String, Object>>>(0, "", list, list.size());
	}

}