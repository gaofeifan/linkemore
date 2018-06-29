package cn.linkmore.ops.account.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import cn.linkmore.bean.common.ResultMap;
import cn.linkmore.ops.account.service.ReportDayService;
import cn.linkmore.ops.utils.ExcelUtil;
import cn.linkmore.report.request.ReqReportDay;
import cn.linkmore.report.response.ResCity;
import cn.linkmore.report.response.ResNewUser;
import cn.linkmore.report.response.ResPre;
import cn.linkmore.report.response.ResPull;
import cn.linkmore.report.response.ResStallAverage;
import cn.linkmore.report.response.ResTitle;
import cn.linkmore.report.response.ResUserNum;
import cn.linkmore.util.StringUtil;
/**
 * Controller - 日报-用户分析
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Controller
@RequestMapping("/admin/account/report_day")
public class ReportDayUserController { 
	
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
		if(cityId != 0) {
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
	public ResultMap<List<ResUserNum>> userNumList(HttpServletRequest request,ReqReportDay reportDay){
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
        return new ResultMap<List<ResUserNum>>(0,"",list,list.size()); 
	}
	
	@RequestMapping(value = "/new_user", method = RequestMethod.POST)
	@ResponseBody
	public ResultMap<List<ResNewUser>> newUserList(HttpServletRequest request, ReqReportDay reportDay){
		List<ResNewUser> newUserList = this.reportDayService.newUserList(reportDay);
		log.info("newUserList = "+ JSON.toJSON(newUserList));
		if(newUserList == null) {
			newUserList = new ArrayList<ResNewUser>();
		}
        return new ResultMap<List<ResNewUser>>(0,"",newUserList,newUserList.size()); 
	}
	
	@RequestMapping(value = "/title", method = RequestMethod.POST)
	@ResponseBody
	public List<ResTitle> titleList(HttpServletRequest request, ReqReportDay reportDay){
		List<ResPull> pullList = this.reportDayService.pullList(reportDay);
		Map<String,String> map = new HashMap<String,String>();
		List<ResTitle> titleList = new ArrayList<ResTitle>();
		ResTitle title = new ResTitle();
		title.setField("day");
		title.setTitle("日期");
		titleList.add(title);
		ResTitle title2 = new ResTitle();
		title2.setField("total");
		title2.setTitle("合计");
		titleList.add(title2);
		if(CollectionUtils.isNotEmpty(pullList)) {
			for(ResPull resPull :pullList) {
				if(resPull.getCityName().equals("北京")) {
					if(!map.containsKey("bjTotal")) {
						ResTitle title3 = new ResTitle();
						title3.setField("bjTotal");
						title3.setTitle(resPull.getCityName()+"合计");
						titleList.add(title3);
						map.put("bjTotal", resPull.getCityName()+"合计");
					}
				}
				if(resPull.getCityName().equals("杭州")) {
					if(!map.containsKey("hzTotal")) {
						ResTitle title4 = new ResTitle();
						title4.setField("hzTotal");
						title4.setTitle(resPull.getCityName()+"合计");
						titleList.add(title4);
						map.put("hzTotal", resPull.getCityName()+"合计");
					}
				}
			}
			
			for(ResPull resPull :pullList) {
				if(!map.containsKey(resPull.getPreName())) {
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
		}else {
			titleList = new ArrayList<ResTitle>();
		}
		
		return titleList;
	}
	
	@RequestMapping(value = "/pull", method = RequestMethod.POST)
	@ResponseBody
	public ResultMap<List<Map<String,Object>>> pullList(HttpServletRequest request, ReqReportDay reportDay){
		List<ResPull> pullList = this.reportDayService.pullList(reportDay);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = null;
		if(StringUtils.isNotBlank(reportDay.getStartTime()) && 
				StringUtils.isNotBlank(reportDay.getEndTime()) && pullList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(),reportDay.getEndTime());
			for(String date : dateList) {
				map = new HashMap<String,Object>();
				map.put("day", date);
				int bjTotal = 0;
				int hzTotal = 0;
				for(ResPull resPull: pullList) {
					if(map.get(resPull.getPreName())==null) {
						map.put(resPull.getPreName(), 0);
					}
					if(date.equals(resPull.getDay())) {
						map.put(resPull.getPreName(), resPull.getDayTotal());
						if(resPull.getCityName().equals("北京")) {
							bjTotal += resPull.getDayTotal();
						}else if(resPull.getCityName().equals("杭州")) {
							hzTotal += resPull.getDayTotal();
						}
					}else {
						
					}
				}
				map.put("bjTotal", bjTotal);
				map.put("hzTotal", hzTotal);
				map.put("total", bjTotal + hzTotal);
				list.add(map);
			}
			System.out.println("list = "+ list);
			System.out.println("pull_list "+ JSON.toJSON(pullList));
		}
		return new ResultMap<List<Map<String,Object>>>(0,"", list, list.size()); 
	}
	
	@RequestMapping(value = "/stall_average", method = RequestMethod.POST)
	@ResponseBody
	public ResultMap<List<Map<String,Object>>> stallAverageList(HttpServletRequest request, ReqReportDay reportDay){
		List<ResStallAverage> averageList = this.reportDayService.stallAverageList(reportDay);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = null;
		int bjStall = 0;
		int hzStall = 0;
		Map<String,Object> stallMap = new HashMap<String,Object>();
		for(ResStallAverage resPull: averageList) {
			if(stallMap.get(resPull.getPreName())==null) {
				stallMap.put(resPull.getPreName(), resPull.getStallTotal());
				if(resPull.getCityName().equals("北京")) {
					bjStall += resPull.getStallTotal();
				}else if(resPull.getCityName().equals("杭州")) {
					hzStall += resPull.getStallTotal();
				}
			}
		}
		
		if(StringUtils.isNotBlank(reportDay.getStartTime()) && 
				StringUtils.isNotBlank(reportDay.getEndTime()) && averageList != null) {
			List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(),reportDay.getEndTime());
			for(String date : dateList) {
				map = new HashMap<String,Object>();
				map.put("day", date);
				int bjTotal = 0;
				int hzTotal = 0;
				double bjTotalAverage = 0d;
				double hzTotalAverage = 0d;
				double totalAverage = 0d;
				for(ResStallAverage resPull: averageList) {
					if(map.get(resPull.getPreName())==null) {
						map.put(resPull.getPreName(), 0);
					}
					if(date.equals(resPull.getDay())) {
						map.put(resPull.getPreName(), resPull.getAverage());
						if(resPull.getCityName().equals("北京")) {
							bjTotal += resPull.getDayTotal();
						}else if(resPull.getCityName().equals("杭州")) {
							hzTotal += resPull.getDayTotal();
						}
					}
				}
				if(bjStall != 0) {
					 bjTotalAverage = new BigDecimal((float)bjTotal/bjStall).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue(); 
				}
				if(hzStall != 0) {
					 hzTotalAverage = new BigDecimal((float)hzTotal/hzStall).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();  
				}
				totalAverage = new BigDecimal((float)(bjTotal+hzTotal)/(bjStall+hzStall)).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();  
				map.put("bjTotal", bjTotalAverage);
				map.put("hzTotal", hzTotalAverage);
				map.put("total", totalAverage);
				list.add(map);
			}
		}
		return new ResultMap<List<Map<String,Object>>>(0,"", list, list.size()); 
	}
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * 导出
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public void export(ReqReportDay reportDay, HttpServletResponse response,HttpServletRequest request) {
		String title = "日报";
		List<ResUserNum> list = this.reportDayService.userNumList(reportDay);
		if(CollectionUtils.isNotEmpty(list)) {
			for(ResUserNum resUserNum :list) {
				double average = new BigDecimal((float)resUserNum.getDayTotal()/resUserNum.getStallTotal()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
				resUserNum.setAverage(average);
			}
		}else {
			list = new ArrayList<ResUserNum>();
		}
		
		List<ResNewUser> newUserList = this.reportDayService.newUserList(reportDay);
		log.info("newUserList = "+ JSON.toJSON(newUserList));
		if(newUserList == null) {
			newUserList = new ArrayList<ResNewUser>();
		}
		try {
			/*JSONArray ja = new JSONArray();
			Map<String, Object> s = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			
			ExcelUtil.exportExcel("用户数量", headMap, ja, null, 0, os);
			
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
			ExcelUtil.exportExcel("新增用户", headMapNewUser, jaNewUser, null, 0, os);*/
			
			List<ResPull> pullList = this.reportDayService.pullList(reportDay);
			JSONArray ja = new JSONArray();
			if(StringUtils.isNotBlank(reportDay.getStartTime()) && 
					StringUtils.isNotBlank(reportDay.getEndTime()) && pullList != null) {
				List<String> dateList = StringUtil.getBetweenDates(reportDay.getStartTime(),reportDay.getEndTime());
				Map<String, Object> map = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				for(String date : dateList) {
					map = new HashMap<String,Object>();
					map.put("day", date);
					int bjTotal = 0;
					int hzTotal = 0;
					for(ResPull resPull: pullList) {
						if(map.get(resPull.getPreName())==null) {
							map.put(resPull.getPreName(), 0);
						}
						if(date.equals(resPull.getDay())) {
							map.put(resPull.getPreName(), resPull.getDayTotal());
							if(resPull.getCityName().equals("北京")) {
								bjTotal += resPull.getDayTotal();
							}else if(resPull.getCityName().equals("杭州")) {
								hzTotal += resPull.getDayTotal();
							}
						}else {
							
						}
					}
					map.put("bjTotal", bjTotal);
					map.put("hzTotal", hzTotal);
					map.put("total", bjTotal + hzTotal);
					ja.add(map);
				}
			}
			Map<String, String> headMap = titleMap(reportDay);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ExcelUtil.exportExcel("现场拉新", headMap, ja, null, 0, os);
			
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
	
	
	public Map<String,String> titleMap(ReqReportDay reportDay){
		List<ResPull> pullList = this.reportDayService.pullList(reportDay);
		Map<String,String> map = new LinkedHashMap<String, String>();
		map.put("day", "日期");
		map.put("total", "合计");
		if(CollectionUtils.isNotEmpty(pullList)) {
			for(ResPull resPull :pullList) {
				if(resPull.getCityName().equals("北京")) {
					if(!map.containsKey("bjTotal")) {
						ResTitle title3 = new ResTitle();
						map.put("bjTotal", resPull.getCityName()+"合计");
					}
				}
				if(resPull.getCityName().equals("杭州")) {
					if(!map.containsKey("hzTotal")) {
						map.put("hzTotal", resPull.getCityName()+"合计");
					}
				}
			}
			
			for(ResPull resPull :pullList) {
				if(!map.containsKey(resPull.getPreName())) {
					map.put(resPull.getPreName(), resPull.getPreName());
				}
			}
		}
		return map;
	}
	
	
	
}
