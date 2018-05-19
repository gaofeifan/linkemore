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
 * 湖滨计费
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class HubinFee {
	
	public final static String DAY_START = "00:00:00";
	public final static String DAY_END = "24:00:00";
	
	 
	
	public static Map<String,Object> getBilling(StrategyBase base, Date startDate, Date stopDate){ 
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
		long feeTime = 0;
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
					nightTime = HubinFee.getSpaceTime(startTime, base.getBeginTime());
					feeTime = nightTime;
					if(nightTime>base.getTopNight()){
						feeTime = base.getTopNight();
					}
					dayTime = HubinFee.getSpaceTime(base.getBeginTime(), stopTime); 
					feeTime += dayTime;
				}else if(startIndex==1&&endIndex==2){
					//---------10:00----------------22:00------------ 
					//---------------------s----------------------e--
					nightTime = HubinFee.getSpaceTime(base.getEndTime(), stopTime);
					feeTime = nightTime;
					if(nightTime>base.getTopNight()){
						feeTime = base.getTopNight();
					} 
					dayTime = HubinFee.getSpaceTime(startTime, base.getEndTime()); 
					feeTime += dayTime;
				} else{
					//---------10:00----------------22:00------------ 
					//----s---------------------------------------e-- 
					nightTime = HubinFee.getSpaceTime(startTime, base.getBeginTime()); 
					nightTime += HubinFee.getSpaceTime(base.getEndTime(), stopTime); 
					feeTime = nightTime;
					if(nightTime>base.getTopNight()	){
						feeTime =base.getTopNight();
					} 
					dayTime = HubinFee.getSpaceTime(base.getBeginTime(), base.getEndTime()); 
					feeTime += dayTime;
				}  
				
			}
			//---------10:00----------------22:00------------
			//----s---e--------------------------------------
			//----------------s----------e-------------------
			//------------------------------------s--------e-
			else if(startIndex==endIndex){
				int time = HubinFee.getSpaceTime(startTime, stopTime);
				if(startIndex==0||startIndex==2){
					nightTime = time;
					feeTime = nightTime;
					if(nightTime>base.getTopNight()){
						feeTime = base.getTopNight();
					}
				} else{
					dayTime = time;
					feeTime = dayTime;
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
					nightTime = HubinFee.getSpaceTime(base.getEndTime(), HubinFee.DAY_END);
					nightTime += HubinFee.getSpaceTime(HubinFee.DAY_START, stopTime);
					feeTime = nightTime;
					if(nightTime>base.getTopNight()){
						feeTime = base.getTopNight();
					}
					dayTime = HubinFee.getSpaceTime(startTime, base.getEndTime()); 
					feeTime += dayTime;
				}else if(endIndex==0&&startIndex==2){
					nightTime = HubinFee.getSpaceTime(startTime, HubinFee.DAY_END);
					nightTime += HubinFee.getSpaceTime(HubinFee.DAY_START, stopTime);
					feeTime = nightTime;
					if(nightTime>base.getTopNight()){
						feeTime = base.getTopNight();
					} 
				}else if(endIndex==1&&startIndex==2){
					nightTime = HubinFee.getSpaceTime(startTime, HubinFee.DAY_END);
					nightTime += HubinFee.getSpaceTime(HubinFee.DAY_START, base.getBeginTime());
					feeTime = nightTime;
					if(nightTime>base.getTopNight()){
						feeTime = base.getTopNight();
					}
					dayTime = HubinFee.getSpaceTime(base.getBeginTime(), base.getEndTime()); 
					feeTime += dayTime;
				}
				
			}
			//---------10:00----------------22:00------------
			//----e---s--------------------------------------
			//----------------e----------s-------------------
			//------------------------------------e--------s-
			else if(startIndex==endIndex){
				 if(endIndex==0){
					 nightTime = HubinFee.getSpaceTime(HubinFee.DAY_START, stopTime);
					 nightTime += HubinFee.getSpaceTime(startTime, base.getBeginTime());
					 nightTime += HubinFee.getSpaceTime(base.getEndTime(), HubinFee.DAY_END);
					 feeTime = nightTime;
					 if(nightTime>base.getTopNight()){
						 feeTime = base.getTopNight();
					 }
					 dayTime = HubinFee.getSpaceTime(base.getBeginTime(), base.getEndTime());
					 feeTime += dayTime;
				 }else if(endIndex==1){
					 nightTime = HubinFee.getSpaceTime(HubinFee.DAY_START, base.getBeginTime());
					 nightTime += HubinFee.getSpaceTime(base.getEndTime(), HubinFee.DAY_END);
					 feeTime = nightTime;
					 if(nightTime>base.getTopNight()){
						 feeTime = base.getTopNight();
					 }
					 dayTime = HubinFee.getSpaceTime(base.getBeginTime(), stopTime);
					 dayTime += HubinFee.getSpaceTime(startTime, base.getEndTime());
					 feeTime += dayTime;
					 
				 }else if(endIndex==2){
					 nightTime = HubinFee.getSpaceTime(HubinFee.DAY_START, base.getBeginTime());
					 nightTime += HubinFee.getSpaceTime(base.getEndTime(), stopTime);
					 nightTime += HubinFee.getSpaceTime(startTime, HubinFee.DAY_END);
					 feeTime = nightTime;
					 if(nightTime>base.getTopNight()){
						 feeTime = base.getTopNight();
					 }
					 dayTime = HubinFee.getSpaceTime(base.getBeginTime(), base.getEndTime());
					 feeTime = dayTime;
				 }
			}
		} 
		int completeDayTime = HubinFee.getSpaceTime(base.getBeginTime(), base.getEndTime());
		int completeNightTime = 24*60 - completeDayTime;
		
		if(day>0){
			dayTime += day*completeDayTime;
			nightTime += day*completeNightTime;
			amount =( (completeDayTime-1)/base.getTimelyLong()+1)*base.getBasePrice().doubleValue();
			if(completeNightTime>base.getTopNight()){
				amount += ( (base.getTopNight()-1)/base.getTimelyLong()+1)*base.getBasePrice().doubleValue();
			}else{
				amount += ( (completeNightTime-1)/base.getTimelyLong()+1)*base.getBasePrice().doubleValue();
			}
			amount = amount*day;
		} 
		amount += ((feeTime-1)/base.getTimelyLong()+1)*base.getBasePrice().doubleValue(); 
		dayAmount = ((dayTime-1)/base.getTimelyLong()+1)*base.getBasePrice().doubleValue(); 
		nightAmount = amount - dayAmount;
		map.put("day", dayTime); 
		map.put("night", nightTime); 
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
		base.setBeginTime("10:00:00");
		base.setEndTime("22:00:00");
		base.setTimelyLong(60);
		base.setBasePrice(new BigDecimal(10));
		base.setTopNight(360);
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
