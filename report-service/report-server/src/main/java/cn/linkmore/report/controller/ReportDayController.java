package cn.linkmore.report.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.report.request.ReqReportDay;
import cn.linkmore.report.response.ResAveragePrice;
import cn.linkmore.report.response.ResCity;
import cn.linkmore.report.response.ResNewUser;
import cn.linkmore.report.response.ResOrder;
import cn.linkmore.report.response.ResPre;
import cn.linkmore.report.response.ResPull;
import cn.linkmore.report.response.ResRunTime;
import cn.linkmore.report.response.ResStallAverage;
import cn.linkmore.report.response.ResUserNum;
import cn.linkmore.report.service.ReportDayService;

/**
 * Controller - 车区每日目标
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Controller
@RequestMapping("/report_day")
public class ReportDayController {
	@Resource
	private ReportDayService reportDayService;

	public Map<String, Object> convert(ReqReportDay reportDay) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<Long> preIds = new ArrayList<Long>();
		if (reportDay.getPreIds()=="" || reportDay.getPreIds() ==null) {
			if (reportDay.getCityId() != null) {
				param.put("cityId", reportDay.getCityId());
			}
			List<ResPre> preList = reportDayService.preList(param);
			for (ResPre pre : preList) {
				preIds.add(pre.getId());
			}
		} else {
			String[] preIdStr = reportDay.getPreIds().split(",");
			for(String preId : preIdStr) {
				preIds.add(Long.valueOf(preId));
			}
		}
		param.put("list", preIds);
		param.put("startTime", reportDay.getStartTime());
		param.put("endTime", reportDay.getEndTime());
		return param;
	}

	@RequestMapping(value = "/v2.0/city_list", method = RequestMethod.GET)
	@ResponseBody
	public List<ResCity> cityList() {
		return this.reportDayService.cityList();
	}

	@RequestMapping(value = "/v2.0/pre_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPre> preList(@RequestBody Map<String, Object> param) {
		return this.reportDayService.preList(param);
	}

	@RequestMapping(value = "/v2.0/total_count", method = RequestMethod.GET)
	@ResponseBody
	public Integer totalCount() {
		return this.reportDayService.totalCount();
	}

	@RequestMapping(value = "/v2.0/user_num", method = RequestMethod.POST)
	@ResponseBody
	public List<ResUserNum> userNumList(@RequestBody ReqReportDay reportDay) {
		Map<String, Object> param = convert(reportDay);
		return this.reportDayService.userNumList(param);
	}

	@RequestMapping(value = "/v2.0/new_user", method = RequestMethod.POST)
	@ResponseBody
	public List<ResNewUser> newUserList(@RequestBody ReqReportDay reportDay) {
		Map<String, Object> param = convert(reportDay);
		return this.reportDayService.newUserList(param);
	}

	@RequestMapping(value = "/v2.0/pull", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPull> pullList(@RequestBody ReqReportDay reportDay) {
		Map<String, Object> param = convert(reportDay);
		return this.reportDayService.pullList(param);
	}

	@RequestMapping(value = "/v2.0/stall_average", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStallAverage> stallAverageList(@RequestBody ReqReportDay reportDay) {
		Map<String, Object> param = convert(reportDay);
		return this.reportDayService.stallAverageList(param);
	}

	@RequestMapping(value = "/v2.0/order", method = RequestMethod.POST)
	@ResponseBody
	public List<ResOrder> orderList(@RequestBody ReqReportDay reportDay) {
		Map<String, Object> param = convert(reportDay);
		return this.reportDayService.orderList(param);
	}

	@RequestMapping(value = "/v2.0/yl_order", method = RequestMethod.POST)
	@ResponseBody
	public List<ResOrder> ylOrderList(@RequestBody ReqReportDay reportDay) {
		Map<String, Object> param = convert(reportDay);
		return this.reportDayService.ylOrderList(param);
	}

	@RequestMapping(value = "/v2.0/newuser_order", method = RequestMethod.POST)
	@ResponseBody
	public List<ResOrder> newUserOrderList(@RequestBody ReqReportDay reportDay) {
		Map<String, Object> param = convert(reportDay);
		return this.reportDayService.newUserOrderList(param);
	}

	@RequestMapping(value = "/v2.0/olduser_order", method = RequestMethod.POST)
	@ResponseBody
	public List<ResOrder> oldUserOrderList(@RequestBody ReqReportDay reportDay) {
		Map<String, Object> param = convert(reportDay);
		return this.reportDayService.oldUserOrderList(param);
	}

	@RequestMapping(value = "/v2.0/runtime", method = RequestMethod.POST)
	@ResponseBody
	public List<ResRunTime> runtimeList(@RequestBody ReqReportDay reportDay) {
		Map<String, Object> param = convert(reportDay);
		return this.reportDayService.runtimeList(param);
	}

	@RequestMapping(value = "/v2.0/average_price", method = RequestMethod.POST)
	@ResponseBody
	public List<ResAveragePrice> averagePriceList(@RequestBody ReqReportDay reportDay) {
		Map<String, Object> param = convert(reportDay);
		return this.reportDayService.averagePriceList(param);
	}

}
