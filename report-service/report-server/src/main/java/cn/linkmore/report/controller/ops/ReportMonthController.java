package cn.linkmore.report.controller.ops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
import cn.linkmore.report.service.ReportMonthService;

/**
 * Controller - 月报Controller
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Controller
@RequestMapping("/ops/report_month")
public class ReportMonthController {
	@Resource
	private ReportMonthService reportMonthService;

	public Map<String, Object> convert(ReqReportMonth reportMonth) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<Long> preIds = new ArrayList<Long>();
		if (StringUtils.isNotBlank(reportMonth.getPreIds())) {
			String[] preIdStr = reportMonth.getPreIds().split(",");
			for(String preId : preIdStr) {
				preIds.add(Long.valueOf(preId));
			}
		}
		param.put("list", preIds);
		param.put("startTime", reportMonth.getStartTime());
		param.put("endTime", reportMonth.getEndTime());
		return param;
	}
	
	public Map<String, Object> orderConvert(ReqReportMonth reportMonth) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<Long> preIds = new ArrayList<Long>();
		List<Long> statuIds = new ArrayList<Long>();
		if (StringUtils.isNotBlank(reportMonth.getPreIds())) {
			String[] preIdStr = reportMonth.getPreIds().split(",");
			for(String preId : preIdStr) {
				preIds.add(Long.valueOf(preId));
			}
		}
		if (StringUtils.isBlank(reportMonth.getStatuIds())) {
			reportMonth.setStatuIds("1,3,4,6,7");
			String[] statuIdStr = reportMonth.getStatuIds().split(",");
			for(String statuId : statuIdStr) {
				statuIds.add(Long.valueOf(statuId));
			}
		} else {
			String[] statuIdStr = reportMonth.getStatuIds().split(",");
			for(String statuId : statuIdStr) {
				statuIds.add(Long.valueOf(statuId));
			}
		}
		param.put("list", preIds);
		param.put("startTime", reportMonth.getStartTime());
		param.put("endTime", reportMonth.getEndTime());
		param.put("statuList", statuIds);
		return param;
	}

	@RequestMapping(value = "/v2.0/city_list", method = RequestMethod.GET)
	@ResponseBody
	public List<ResCity> cityList() {
		return this.reportMonthService.cityList();
	}

	@RequestMapping(value = "/v2.0/pre_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPre> preList(@RequestBody Map<String, Object> param) {
		return this.reportMonthService.preList(param);
	}

	@RequestMapping(value = "/v2.0/total_count", method = RequestMethod.GET)
	@ResponseBody
	public Integer totalCount() {
		return this.reportMonthService.totalCount();
	}

	@RequestMapping(value = "/v2.0/user_num", method = RequestMethod.POST)
	@ResponseBody
	public List<ResUserNum> userNumList(@RequestBody ReqReportMonth reportMonth) {
		Map<String, Object> param = convert(reportMonth);
		return this.reportMonthService.userNumList(param);
	}

	@RequestMapping(value = "/v2.0/new_user", method = RequestMethod.POST)
	@ResponseBody
	public List<ResNewUser> newUserList(@RequestBody ReqReportMonth reportMonth) {
		Map<String, Object> param = convert(reportMonth);
		return this.reportMonthService.newUserList(param);
	}

	@RequestMapping(value = "/v2.0/pull", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPull> pullList(@RequestBody ReqReportMonth reportMonth) {
		Map<String, Object> param = convert(reportMonth);
		return this.reportMonthService.pullList(param);
	}

	@RequestMapping(value = "/v2.0/stall_average", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStallAverage> stallAverageList(@RequestBody ReqReportMonth reportMonth) {
		Map<String, Object> param = convert(reportMonth);
		return this.reportMonthService.stallAverageList(param);
	}

	@RequestMapping(value = "/v2.0/order", method = RequestMethod.POST)
	@ResponseBody
	public List<ResOrder> orderList(@RequestBody ReqReportMonth reportMonth) {
		Map<String, Object> param = orderConvert(reportMonth);
		return this.reportMonthService.orderList(param);
	}

	@RequestMapping(value = "/v2.0/yl_order", method = RequestMethod.POST)
	@ResponseBody
	public List<ResOrder> ylOrderList(@RequestBody ReqReportMonth reportMonth) {
		Map<String, Object> param = convert(reportMonth);
		return this.reportMonthService.ylOrderList(param);
	}

	@RequestMapping(value = "/v2.0/newuser_order", method = RequestMethod.POST)
	@ResponseBody
	public List<ResOrder> newUserOrderList(@RequestBody ReqReportMonth reportMonth) {
		Map<String, Object> param = convert(reportMonth);
		return this.reportMonthService.newUserOrderList(param);
	}

	@RequestMapping(value = "/v2.0/olduser_order", method = RequestMethod.POST)
	@ResponseBody
	public List<ResOrder> oldUserOrderList(@RequestBody ReqReportMonth reportMonth) {
		Map<String, Object> param = convert(reportMonth);
		return this.reportMonthService.oldUserOrderList(param);
	}

	@RequestMapping(value = "/v2.0/runtime", method = RequestMethod.POST)
	@ResponseBody
	public List<ResRunTime> runtimeList(@RequestBody ReqReportMonth reportMonth) {
		Map<String, Object> param = convert(reportMonth);
		return this.reportMonthService.runtimeList(param);
	}

	@RequestMapping(value = "/v2.0/average_price", method = RequestMethod.POST)
	@ResponseBody
	public List<ResAveragePrice> averagePriceList(@RequestBody ReqReportMonth reportMonth) {
		Map<String, Object> param = convert(reportMonth);
		return this.reportMonthService.averagePriceList(param);
	}
	
	@RequestMapping(value = "/v2.0/cost", method = RequestMethod.POST)
	@ResponseBody
	public List<ResCost> costList(@RequestBody ReqReportMonth reportMonth) {
		Map<String, Object> param = convert(reportMonth);
		return this.reportMonthService.costList(param);
	}
	
	@RequestMapping(value = "/v2.0/income", method = RequestMethod.POST)
	@ResponseBody
	public List<ResIncome> incomeList(@RequestBody ReqReportMonth reportMonth) {
		Map<String, Object> param = convert(reportMonth);
		return this.reportMonthService.incomeList(param);
	}
	
	@RequestMapping(value = "/v2.0/pull_cost", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPullCost> pullCostList(@RequestBody ReqReportMonth reportMonth) {
		Map<String, Object> param = convert(reportMonth);
		return this.reportMonthService.pullCostList(param);
	}

}
