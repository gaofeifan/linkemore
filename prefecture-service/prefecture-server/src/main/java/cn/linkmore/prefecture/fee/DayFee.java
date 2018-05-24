package cn.linkmore.prefecture.fee;

import java.math.BigDecimal;
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
 * 按天计费[无封顶]
 * @author liwenlong
 * @version 2.0
 *
 */
public class DayFee {
	public final static String DAY_START = "00:00:00";
	public final static String DAY_END = "24:00:00"; 
	
	public static Map<String,Object> getBilling(StrategyBase base, Date startDate, Date stopDate){ 
		Map<String,Object> map = new HashMap<String,Object>();
		startDate = new Date(startDate.getTime()-base.getFreeMins());
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
		
		List<String> timeList = new ArrayList<String>();
		timeList.add(base.getBeginTime());
		timeList.add(base.getEndTime());  
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
					//---------10:00----------------22:00------------ 
					//----s----------------e------------------------- 
					nightTime = DayFee.getSpaceTime(startTime, base.getBeginTime()); 
					dayTime = DayFee.getSpaceTime(base.getBeginTime(), stopTime);  
				}else if(startIndex==1&&endIndex==2){
					//---------10:00----------------22:00------------ 
					//---------------------s----------------------e--
					nightTime = DayFee.getSpaceTime(base.getEndTime(), stopTime); 
					dayTime = DayFee.getSpaceTime(startTime, base.getEndTime());  
				} else{
					//---------10:00----------------22:00------------ 
					//----s---------------------------------------e-- 
					nightTime = DayFee.getSpaceTime(startTime, base.getBeginTime()); 
					nightTime += DayFee.getSpaceTime(base.getEndTime(), stopTime);  
					dayTime = DayFee.getSpaceTime(base.getBeginTime(), base.getEndTime()); 
				}  
				
			}
			//---------10:00----------------22:00------------
			//----s---e--------------------------------------
			//----------------s----------e-------------------
			//------------------------------------s--------e-
			else if(startIndex==endIndex){
				int time = DayFee.getSpaceTime(startTime, stopTime);
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
			
			//---------10:00----------------22:00------------
			//----e----------------s-------------------------
			//----e---------------------------------------s--
			//---------------------e----------------------s--
			if(startIndex>endIndex){
				if(endIndex==0&&startIndex==1){
					nightTime = DayFee.getSpaceTime(base.getEndTime(), DayFee.DAY_END);
					nightTime += DayFee.getSpaceTime(DayFee.DAY_START, stopTime); 
					dayTime = DayFee.getSpaceTime(startTime, base.getEndTime());  
				}else if(endIndex==0&&startIndex==2){
					nightTime = DayFee.getSpaceTime(startTime, DayFee.DAY_END);
					nightTime += DayFee.getSpaceTime(DayFee.DAY_START, stopTime); 
				}else if(endIndex==1&&startIndex==2){
					nightTime = DayFee.getSpaceTime(startTime, DayFee.DAY_END);
					nightTime += DayFee.getSpaceTime(DayFee.DAY_START, base.getBeginTime()); 
					dayTime = DayFee.getSpaceTime(base.getBeginTime(), base.getEndTime());  
				}
				
			}
			//---------10:00----------------22:00------------
			//----e---s--------------------------------------
			//----------------e----------s-------------------
			//------------------------------------e--------s-
			else if(startIndex==endIndex){
				 if(endIndex==0){
					 nightTime = DayFee.getSpaceTime(DayFee.DAY_START, stopTime);
					 nightTime += DayFee.getSpaceTime(startTime, base.getBeginTime());
					 nightTime += DayFee.getSpaceTime(base.getEndTime(), DayFee.DAY_END); 
					 dayTime = DayFee.getSpaceTime(base.getBeginTime(), base.getEndTime()); 
				 }else if(endIndex==1){
					 nightTime = DayFee.getSpaceTime(DayFee.DAY_START, base.getBeginTime());
					 nightTime += DayFee.getSpaceTime(base.getEndTime(), DayFee.DAY_END); 
					 dayTime = DayFee.getSpaceTime(base.getBeginTime(), stopTime);
					 dayTime += DayFee.getSpaceTime(startTime, base.getEndTime()); 
					 
				 }else if(endIndex==2){
					 nightTime = DayFee.getSpaceTime(DayFee.DAY_START, base.getBeginTime());
					 nightTime += DayFee.getSpaceTime(base.getEndTime(), stopTime);
					 nightTime += DayFee.getSpaceTime(startTime, DayFee.DAY_END); 
					 dayTime = DayFee.getSpaceTime(base.getBeginTime(), base.getEndTime()); 
				 }
			}
		}  
		 
		int completeDayTime = DayFee.getSpaceTime(base.getBeginTime(), base.getEndTime());
		int completeNightTime = 24*60 - completeDayTime;
		
		long over = dayTime%base.getTimelyLong()+nightTime%base.getNightTimelyLong(); 
		double completeDayAmount = 0d;
		double completeNightAmount = 0d; 
		if(day>0){ 
			completeDayAmount =( (completeDayTime-1)/base.getTimelyLong()+1)*base.getBasePrice().doubleValue();
			completeNightAmount = ( (completeNightTime-1)/base.getNightTimelyLong()+1)*base.getNightPrice().doubleValue();
			amount = (completeDayAmount+completeNightAmount)*day;
		}  
		dayAmount = (dayTime/base.getTimelyLong())*base.getBasePrice().doubleValue(); 
		nightAmount = (nightTime/base.getNightTimelyLong())*base.getNightPrice().doubleValue();  
		
		
		if(over>base.getTimelyLong()) {
			dayAmount += base.getBasePrice().doubleValue();
		}else if(over>base.getNightTimelyLong()) {
			nightAmount += base.getNightPrice().doubleValue();
		}
		 
		if(base.getFirstHour()!=null) {
			if(startIndex==1&&base.getBasePrice().doubleValue()<base.getFirstHour().doubleValue()) { 
				dayAmount += base.getFirstHour().doubleValue()-base.getBasePrice().doubleValue();
			}else if(base.getNightPrice().doubleValue()<base.getFirstHour().doubleValue()) {
				nightAmount +=  base.getFirstHour().doubleValue() -  base.getNightPrice().doubleValue();
			}
		} 
		
		amount += dayAmount+nightAmount;
		map.put("day", dayTime+completeDayTime*day); 
		map.put("night", nightTime+completeNightTime*day); 
		map.put("dayAmount", completeDayAmount+dayAmount);
		map.put("totalAmount", amount); 
		map.put("nightAmount", completeNightAmount+nightAmount); 
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
	
	public static void main(String[] args) throws ParseException{
		StrategyBase base = new StrategyBase();
		base.setFreeMins(15);
		base.setBeginTime("10:00:00");
		base.setEndTime("22:00:00");
		base.setTimelyLong(15);
		base.setBasePrice(new BigDecimal(1.25));
		base.setNightPrice(new BigDecimal(2.5));
		base.setNightTimelyLong(30); 
		String startString = "2017-11-06 09:08:06";
		String endString = "2017-11-07 14:11:06";
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
