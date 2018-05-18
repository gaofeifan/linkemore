package cn.linkmore.prefecture.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.druid.support.json.JSONUtils;

import cn.linkmore.prefecture.entity.StrategyBase;
/**
 * 西城广场计费
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class XichengFee {
	
//	private static  Logger log = LoggerFactory.getLogger(XichengFee.class);
	
	public final static String DAY_START = "00:00:00";
	public final static String DAY_END = "24:00:00";
	
	 
	
	public static Map<String,Object> getBilling(StrategyBase bases, Date startDate, Date stopDate){ 
		Map<String,Object> map = new HashMap<String,Object>();
		Long day = (stopDate.getTime()-startDate.getTime())/(24*60*60*1000l);
		map.put("day", 0l); 
		map.put("night", 0l); 
		map.put("dayAmount", 0d);
		map.put("totalAmount", 0d); 
		map.put("nightAmount", 0d);
		if(stopDate.getTime()-startDate.getTime()<0){
			return map;
		} 
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String startTime = sdf.format(startDate);
		String stopTime = sdf.format(stopDate); 
		
		/**
		 * flag =0 不处理
		 * flag >0   不跨天处理
		 * flag <0  跨天处理
		 */ 
		Xicheng xc = new Xicheng();
		xc.parse(bases);
		List<String> timeList = new ArrayList<String>();
		timeList.add(xc.getStart());
		timeList.add(xc.getEnd());  
		timeList.add(startTime);
		Collections.sort(timeList);
		int startIndex = timeList.indexOf(startTime);
		timeList.remove(startIndex);
		timeList.add(stopTime);
		Collections.sort(timeList);
		int endIndex = timeList.indexOf(stopTime); 
		timeList.remove(endIndex);
		int flag = stopTime.compareTo(startTime);  
		double amount = 0d;
		long dayTime = 0;
		long nightTime = 0; 
		double dayAmount = 0d;
		double nightAmount = 0d; 
		if(flag >0){ 
			/**
			 * 非跨天
			 */
			if(startIndex<endIndex){ 
				if(startIndex==0&&endIndex==1){ 
					//---------08:00----------------23:00------------ 
					//----s----------------e------------------------- 
					nightTime = XichengFee.getSpaceTime(startTime, xc.getStart()); 
					dayTime = XichengFee.getSpaceTime(xc.getStart(), stopTime);  
				}else if(startIndex==1&&endIndex==2){
					//---------08:00----------------23:00------------ 
					//---------------------s----------------------e--
					nightTime = XichengFee.getSpaceTime(xc.getEnd(), stopTime);  
					dayTime = XichengFee.getSpaceTime(startTime, xc.getEnd());  
				} else{
					//---------08:00----------------23:00------------ 
					//----s---------------------------------------e-- 
					nightTime = XichengFee.getSpaceTime(startTime, xc.getStart()); 
					nightTime += XichengFee.getSpaceTime(xc.getEnd(), stopTime);  
					dayTime = XichengFee.getSpaceTime(xc.getStart(), xc.getEnd());  
				}  
				
			}
			//---------08:00----------------23:00------------
			//----s---e--------------------------------------
			//----------------s----------e-------------------
			//------------------------------------s--------e-
			else if(startIndex==endIndex){
				int time = XichengFee.getSpaceTime(startTime, stopTime);
				if(startIndex==0||startIndex==2){
					nightTime = time; 
				} else{
					dayTime = time; 
				} 
			}
			
		}else if(flag <0){ 
			/**
			 * 跨天
			 */ 
			//---------08:00----------------23:00------------
			//----e----------------s-------------------------
			//----e---------------------------------------s--
			//---------------------e----------------------s--
			if(startIndex>endIndex){
				if(endIndex==0&&startIndex==1){
					nightTime = XichengFee.getSpaceTime(xc.getEnd(), XichengFee.DAY_END);
					nightTime += XichengFee.getSpaceTime(XichengFee.DAY_START, stopTime); 
					dayTime = XichengFee.getSpaceTime(startTime, xc.getEnd());  
				}else if(endIndex==0&&startIndex==2){
					nightTime = XichengFee.getSpaceTime(startTime, XichengFee.DAY_END);
					nightTime += XichengFee.getSpaceTime(XichengFee.DAY_START, stopTime); 
				}else if(endIndex==1&&startIndex==2){ 
					nightTime = XichengFee.getSpaceTime(startTime, XichengFee.DAY_END);
					nightTime += XichengFee.getSpaceTime(XichengFee.DAY_START, xc.getStart()); 
					dayTime = XichengFee.getSpaceTime(xc.getStart(),stopTime);   
				}
				
			}
			//---------08:00----------------23:00------------
			//----e---s--------------------------------------
			//----------------e----------s-------------------
			//------------------------------------e--------s-
			else if(startIndex==endIndex){ 
				 if(endIndex==0){
					 nightTime = XichengFee.getSpaceTime(XichengFee.DAY_START, stopTime);
					 nightTime += XichengFee.getSpaceTime(startTime, xc.getStart());
					 nightTime += XichengFee.getSpaceTime(xc.getEnd(), XichengFee.DAY_END); 
					 dayTime = XichengFee.getSpaceTime(xc.getStart(), xc.getEnd()); 
				 }else if(endIndex==1){
					 nightTime = XichengFee.getSpaceTime(XichengFee.DAY_START, xc.getStart());
					 nightTime += XichengFee.getSpaceTime(xc.getEnd(), XichengFee.DAY_END); 
					 dayTime = XichengFee.getSpaceTime(xc.getStart(), stopTime);
					 dayTime += XichengFee.getSpaceTime(startTime, xc.getEnd()); 
					 
				 }else if(endIndex==2){
					 nightTime = XichengFee.getSpaceTime(XichengFee.DAY_START, xc.getStart());
					 nightTime += XichengFee.getSpaceTime(xc.getEnd(), stopTime);
					 nightTime += XichengFee.getSpaceTime(startTime, XichengFee.DAY_END); 
					 dayTime = XichengFee.getSpaceTime(xc.getStart(), xc.getEnd()); 
				 }
			} 
		} 
		int completeDayTime = XichengFee.getSpaceTime(xc.getStart(), xc.getEnd());
		int completeNightTime = 24*60 - completeDayTime; 
		if(day>0){ 
			amount = xc.getTopFee()*day;
		}  
		Long dayHour = 0l;
		if(dayTime!=0){
			dayHour = (dayTime-1)/xc.getTime() +1;
		} 
		Long nightHour = (nightTime-1)/xc.getTime() +1;
		Long hour =( dayTime + nightTime -1)/xc.getTime() + 1;  
		if(nightHour>0){
			if(dayHour+nightHour>hour){
				if(nightHour>1){
					nightAmount = xc.getNightFee();
				}  
			}else{
				nightAmount = xc.getNightFee(); 
			} 
		} 
		dayAmount = dayHour*xc.getPrice(); 
		if(dayAmount+nightAmount>xc.getTopFee()){
			amount += xc.getTopFee(); 
		}else{
			amount += dayAmount + nightAmount;
		}  
		map.put("day", dayTime+completeDayTime*day); 
		map.put("night", nightTime+completeNightTime*day); 
		map.put("dayAmount", dayAmount);
		map.put("totalAmount", amount); 
		map.put("nightAmount", nightAmount); 
		return map;
	}
	 
	public static int getSpaceTime(String start,String end){
		int time = 0;
		String[] starts = start.split("\\:");
		String[] ends = end.split("\\:");
		int startHour = Integer.valueOf(starts[0]);
		int startMin = Integer.valueOf(starts[1]); 
		int endHour = Integer.valueOf(ends[0]);
		int endMin = Integer.valueOf(ends[1]); 
		time = (endHour-startHour)*60+(endMin-startMin); 
		return time;
	}
	public static void mains(String[] args) throws ParseException{
		StrategyBase base = new StrategyBase();
		base.setFreeMins(15); 
		base.setFlexDetail("08:00:00|23:00:00|60|5|60|5");
		String startString = "2017-11-24 23:01:00";
		String endString = "2017-11-25 11:46:06";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = sdf.parse(startString); 
		startDate = new Date(startDate.getTime()+base.getFreeMins()*60*1000l); 
		Date endDate = sdf.parse(endString);
		Map<String,Object> map = getBilling(base,startDate,endDate);
		if(map!=null){ 
			System.out.println(JSONUtils.toJSONString(map));
		}
	} 
	
}
class Xicheng{
	private String start;
	private String end;
	private Double price;
	private Integer time;
	private Double topFee;
	private Double nightFee;
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getTopFee() {
		return topFee;
	}
	public void setTopFee(Double topFee) {
		this.topFee = topFee;
	}
	public Double getNightFee() {
		return nightFee;
	}
	public void setNightFee(Double nightFee) {
		this.nightFee = nightFee;
	}  
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public void parse(StrategyBase strategy){  
		String[] split = strategy.getFlexDetail().split("\\|");
		this.setStart(split[0]);
		this.setEnd(split[1]);
		this.setTime(Integer.valueOf(split[2]));
		this.setPrice(Double.valueOf(split[3]));
		this.setTopFee(Double.valueOf(split[4]));
		this.setNightFee(Double.valueOf(split[5]));
	}
}
