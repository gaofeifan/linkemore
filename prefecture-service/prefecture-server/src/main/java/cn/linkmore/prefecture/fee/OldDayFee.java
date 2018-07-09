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
public class OldDayFee {
	public final static String DAY_START = "00:00:00";
	public final static String DAY_END = "24:00:00"; 
	
	public static Map<String,Object> getBilling(StrategyBase base, Date startDate, Date stopDate){ 
		Map<String,Object> map = new HashMap<String,Object>();
//		startDate = new Date(startDate.getTime()-base.getFreeMins()*60*1000L);
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
					nightTime = OldDayFee.getSpaceTime(startTime, base.getBeginTime()); 
					dayTime = OldDayFee.getSpaceTime(base.getBeginTime(), stopTime);  
				}else if(startIndex==1&&endIndex==2){
					//---------10:00----------------22:00------------ 
					//---------------------s----------------------e--
					nightTime = OldDayFee.getSpaceTime(base.getEndTime(), stopTime); 
					dayTime = OldDayFee.getSpaceTime(startTime, base.getEndTime());  
				} else{
					//---------10:00----------------22:00------------ 
					//----s---------------------------------------e-- 
					nightTime = OldDayFee.getSpaceTime(startTime, base.getBeginTime()); 
					nightTime += OldDayFee.getSpaceTime(base.getEndTime(), stopTime);  
					dayTime = OldDayFee.getSpaceTime(base.getBeginTime(), base.getEndTime()); 
				}  
				
			}
			//---------10:00----------------22:00------------
			//----s---e--------------------------------------
			//----------------s----------e-------------------
			//------------------------------------s--------e-
			else if(startIndex==endIndex){
				int time = OldDayFee.getSpaceTime(startTime, stopTime);
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
					nightTime = OldDayFee.getSpaceTime(base.getEndTime(), OldDayFee.DAY_END);
					nightTime += OldDayFee.getSpaceTime(OldDayFee.DAY_START, stopTime); 
					dayTime = OldDayFee.getSpaceTime(startTime, base.getEndTime());  
				}else if(endIndex==0&&startIndex==2){
					nightTime = OldDayFee.getSpaceTime(startTime, OldDayFee.DAY_END);
					nightTime += OldDayFee.getSpaceTime(OldDayFee.DAY_START, stopTime); 
				}else if(endIndex==1&&startIndex==2){
					nightTime = OldDayFee.getSpaceTime(startTime, OldDayFee.DAY_END);
					nightTime += OldDayFee.getSpaceTime(OldDayFee.DAY_START, base.getBeginTime()); 
					dayTime = OldDayFee.getSpaceTime(base.getBeginTime(), stopTime);  
					
				}
				
			}
			//---------10:00----------------22:00------------
			//----e---s--------------------------------------
			//----------------e----------s-------------------
			//------------------------------------e--------s-
			else if(startIndex==endIndex){
				 if(endIndex==0){
					 nightTime = OldDayFee.getSpaceTime(OldDayFee.DAY_START, stopTime);
					 nightTime += OldDayFee.getSpaceTime(startTime, base.getBeginTime());
					 nightTime += OldDayFee.getSpaceTime(base.getEndTime(), OldDayFee.DAY_END); 
					 dayTime = OldDayFee.getSpaceTime(base.getBeginTime(), base.getEndTime()); 
				 }else if(endIndex==1){
					 nightTime = OldDayFee.getSpaceTime(OldDayFee.DAY_START, base.getBeginTime());
					 nightTime += OldDayFee.getSpaceTime(base.getEndTime(), OldDayFee.DAY_END); 
					 dayTime = OldDayFee.getSpaceTime(base.getBeginTime(), stopTime);
					 dayTime += OldDayFee.getSpaceTime(startTime, base.getEndTime()); 
					 
				 }else if(endIndex==2){
					 nightTime = OldDayFee.getSpaceTime(OldDayFee.DAY_START, base.getBeginTime());
					 nightTime += OldDayFee.getSpaceTime(base.getEndTime(), stopTime);
					 nightTime += OldDayFee.getSpaceTime(startTime, OldDayFee.DAY_END); 
					 dayTime = OldDayFee.getSpaceTime(base.getBeginTime(), base.getEndTime()); 
				 }
			}
		}  
		 
		int completeDayTime = OldDayFee.getSpaceTime(base.getBeginTime(), base.getEndTime());
		int completeNightTime = 24*60 - completeDayTime;  
		long dayOver = dayTime%base.getTimelyLong();
		long nightOver = nightTime%base.getNightTimelyLong();
		long over = dayOver + nightOver;
		double completeDayAmount = 0d;
		double completeNightAmount = 0d; 
		if(day>0){  
			completeDayAmount =( completeDayTime/base.getTimelyLong())*base.getBasePrice().doubleValue();
			completeNightAmount = ( completeNightTime/base.getNightTimelyLong())*base.getNightPrice().doubleValue();
			amount = (completeDayAmount+completeNightAmount)*day;
		}
		dayAmount = (dayTime/base.getTimelyLong())*base.getBasePrice().doubleValue(); 
		nightAmount = (nightTime/base.getNightTimelyLong())*base.getNightPrice().doubleValue();  
		if(over>=base.getTimelyLong()){
			dayAmount += base.getBasePrice().doubleValue();
		}else if(over>base.getNightTimelyLong()){
			nightAmount += base.getNightPrice().doubleValue();
		}else if(dayOver>0&&base.getBasePrice().doubleValue()>=base.getNightPrice().doubleValue()) {
			dayAmount += base.getBasePrice().doubleValue();
		}else if(nightOver>0&&base.getBasePrice().doubleValue()<base.getNightPrice().doubleValue()){
			nightAmount += base.getNightPrice().doubleValue();
		}else if(over>0&&base.getBasePrice().doubleValue()>=base.getNightPrice().doubleValue()&&base.getNightPrice().doubleValue()>0){
			dayAmount += base.getBasePrice().doubleValue();
		}else if(over>0&&base.getBasePrice().doubleValue()<base.getNightPrice().doubleValue()&&base.getBasePrice().doubleValue()>0){
			nightAmount += base.getNightPrice().doubleValue();
		}   
		dayTime = dayTime+completeDayTime*day;
		nightTime = nightTime+completeNightTime*day; 
		amount += dayAmount+nightAmount;
		map.put("day", dayTime); 
		map.put("night", nightTime); 
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
	
	public static void mains(String[] args) throws ParseException{
		StrategyBase base = new StrategyBase();
		base.setFreeMins(15);
		base.setBeginTime("00:00:00");
		base.setEndTime("10:00:00");
		base.setTimelyLong(60);
		base.setBasePrice(new BigDecimal(6D));
		base.setNightPrice(new BigDecimal(0D));
		base.setFirstHour(new BigDecimal(6D));
		base.setNightTimelyLong(60); 
		String startString = "2018-06-26 17:51:38";
		String endString = "2018-06-27 07:54:16";
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
