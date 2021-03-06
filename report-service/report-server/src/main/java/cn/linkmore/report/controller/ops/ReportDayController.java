package cn.linkmore.report.controller.ops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

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
import cn.linkmore.report.service.ReportDayService;

/**
 * Controller - 车区每日目标
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Controller
@RequestMapping("/ops/report_day")
public class ReportDayController {
	@Resource
	private ReportDayService reportDayService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());


	public Map<String, Object> convert(ReqReportDay reportDay) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<Long> preIds = new ArrayList<Long>();
		if (StringUtils.isNotBlank(reportDay.getPreIds())) {
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
	
	public Map<String, Object> orderConvert(ReqReportDay reportDay) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<Long> preIds = new ArrayList<Long>();
		List<Long> statuIds = new ArrayList<Long>();
		if (StringUtils.isNotBlank(reportDay.getPreIds())) {
			String[] preIdStr = reportDay.getPreIds().split(",");
			for(String preId : preIdStr) {
				preIds.add(Long.valueOf(preId));
			}
		}
		if (StringUtils.isBlank(reportDay.getStatuIds())) {
			reportDay.setStatuIds("1,3,4,6,7");
			String[] statuIdStr = reportDay.getStatuIds().split(",");
			for(String statuId : statuIdStr) {
				statuIds.add(Long.valueOf(statuId));
			}
		} else {
			String[] statuIdStr = reportDay.getStatuIds().split(",");
			for(String statuId : statuIdStr) {
				statuIds.add(Long.valueOf(statuId));
			}
		}
		param.put("list", preIds);
		param.put("startTime", reportDay.getStartTime());
		param.put("endTime", reportDay.getEndTime());
		param.put("statuList", statuIds);
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
		List<ResPull> pullList = this.reportDayService.pullList(param);
		log.info("pull list = {}",JSON.toJSON(pullList));
		return pullList;
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
		Map<String, Object> param = orderConvert(reportDay);
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
		
		long startTime = System.currentTimeMillis();    //获取开始时间

		List<ResOrder> orders = this.reportDayService.newUserOrderList(param);
		log.info("new user orders = {}" ,JSON.toJSON(orders));
		long endTime = System.currentTimeMillis();    //获取结束时间

		log.info("new user orders runtime ={}", (endTime - startTime) + "ms");    //输出程序运行时间
		
		return orders;
	}

	@RequestMapping(value = "/v2.0/olduser_order", method = RequestMethod.POST)
	@ResponseBody
	public List<ResOrder> oldUserOrderList(@RequestBody ReqReportDay reportDay) {
		Map<String, Object> param = convert(reportDay);
		long startTime = System.currentTimeMillis();    //获取开始时间

		List<ResOrder> orders = this.reportDayService.oldUserOrderList(param);
		log.info("old user orders = {}" ,JSON.toJSON(orders));

		long endTime = System.currentTimeMillis();    //获取结束时间

		log.info("old user orders runtime ={}", (endTime - startTime) + "ms");    //输出程序运行时间
		
		return orders;
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
	
	@RequestMapping(value = "/v2.0/cost", method = RequestMethod.POST)
	@ResponseBody
	public List<ResCost> costList(@RequestBody ReqReportDay reportDay) {
		Map<String, Object> param = convert(reportDay);
		return this.reportDayService.costList(param);
	}
	
	@RequestMapping(value = "/v2.0/income", method = RequestMethod.POST)
	@ResponseBody
	public List<ResIncome> incomeList(@RequestBody ReqReportDay reportDay) {
		Map<String, Object> param = convert(reportDay);
		return this.reportDayService.incomeList(param);
	}
	
	@RequestMapping(value = "/v2.0/pull_cost", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPullCost> pullCostList(@RequestBody ReqReportDay reportDay) {
		Map<String, Object> param = convert(reportDay);
		return this.reportDayService.pullCostList(param);
	}

}
