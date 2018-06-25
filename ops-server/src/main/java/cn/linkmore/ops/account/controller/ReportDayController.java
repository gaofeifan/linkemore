package cn.linkmore.ops.account.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.linkmore.ops.account.service.ReportDayService;
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
/**
 * Controller - 车区每日目标
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Controller
@RequestMapping("/admin/account/report_day")
public class ReportDayController { 
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private ReportDayService reportDayService;
	
	@RequestMapping(value = "/city_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResCity> cityList(){
		return this.reportDayService.cityList(); 
	} 
	
	@RequestMapping(value = "/pre_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPre> preList(HttpServletRequest request, Long cityId){
		Map<String,Object> param = new HashMap<String,Object>();
		if(cityId != null && cityId != 0) {
			param.put("cityId", cityId);
		}
		return this.reportDayService.preList(param);
	}
	
	@RequestMapping(value = "/total_count", method = RequestMethod.GET)
	@ResponseBody
	public Integer totalCount(){
		return this.reportDayService.totalCount(); 
	} 
	
	@RequestMapping(value = "/user_num", method = RequestMethod.POST)
	@ResponseBody
	public String userNumList(HttpServletRequest request,ReqReportDay reportDay){
		List<ResUserNum> list = this.reportDayService.userNumList(reportDay);
		if(CollectionUtils.isNotEmpty(list)) {
			for(ResUserNum resUserNum :list) {
				double average = new BigDecimal((float)resUserNum.getDayTotal()/resUserNum.getStallTotal()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
				resUserNum.setAverage(average);
			}
		}else {
			list = new ArrayList<ResUserNum>();
		}
		log.info("userNumList = "+ JSON.toJSON(list));
		JSONObject obj=new JSONObject();    
        //前台通过key值获得对应的value值 
        obj.put("code", 0);    
        obj.put("msg", "");    
        obj.put("count",list.size());    
        obj.put("data",list);    
        return obj.toString();  
	}
	
	@RequestMapping(value = "/new_user", method = RequestMethod.POST)
	@ResponseBody
	public String newUserList(HttpServletRequest request, ReqReportDay reportDay){
		List<ResNewUser> newUserList = this.reportDayService.newUserList(reportDay);
		log.info("newUserList = "+ JSON.toJSON(newUserList));
		JSONObject obj=new JSONObject();
        obj.put("code", 0);    
        obj.put("msg", "");    
        obj.put("count",newUserList.size());    
        obj.put("data",newUserList);
        return obj.toString();  
	}
	
	@RequestMapping(value = "/pull", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPull> pullList(HttpServletRequest request, ReqReportDay reportDay){
		
		return this.reportDayService.pullList(reportDay);
	}
	
	@RequestMapping(value = "/stall_average", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStallAverage> stallAverageList(HttpServletRequest request, ReqReportDay reportDay){
		return this.reportDayService.stallAverageList(reportDay);
	}
	
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	@ResponseBody
	public List<ResOrder> orderList(HttpServletRequest request, ReqReportDay reportDay){
		
		return this.reportDayService.orderList(reportDay);
	}
	
	@RequestMapping(value = "/yl_order", method = RequestMethod.POST)
	@ResponseBody
	public List<ResOrder> ylOrderList(HttpServletRequest request, ReqReportDay reportDay){
		return this.reportDayService.ylOrderList(reportDay);
	}
	
	@RequestMapping(value = "/newuser_order", method = RequestMethod.POST)
	@ResponseBody
	public List<ResOrder> newUserOrderList(HttpServletRequest request, ReqReportDay reportDay){
		return this.reportDayService.newUserOrderList(reportDay);
	}
	
	@RequestMapping(value = "/olduser_order", method = RequestMethod.POST)
	@ResponseBody
	public List<ResOrder> oldUserOrderList(HttpServletRequest request, ReqReportDay reportDay){
		
		return this.reportDayService.oldUserOrderList(reportDay);
	}
	
	@RequestMapping(value = "/runtime", method = RequestMethod.POST)
	@ResponseBody
	public List<ResRunTime> runtimeList(HttpServletRequest request, ReqReportDay reportDay){
		return this.reportDayService.runtimeList(reportDay);
	}
	
	@RequestMapping(value = "/average_price", method = RequestMethod.POST)
	@ResponseBody
	public List<ResAveragePrice> averagePriceList(HttpServletRequest request, ReqReportDay reportDay){
		return this.reportDayService.averagePriceList(reportDay);
	}
	
	
}
