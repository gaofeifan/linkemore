package cn.linkmore.prefecture.fee;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.linkmore.prefecture.entity.StrategyBase;

/**
 * 杭州24小时封顶计费
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class TimeTopFee {
	public static Map<String,Object> getBilling(StrategyBase base, Date startDate, Date stopDate){ 
		Map<String,Object> map = new HashMap<String,Object>(); 
		map.put("day", 0l); 
		map.put("night", 0l); 
		map.put("dayAmount", 0d);
		map.put("totalAmount", 0d); 
		map.put("nightAmount", 0d); 
		Date orderDate = startDate; 
		Long day = (stopDate.getTime()-orderDate.getTime())/(24*60*60*1000l);
		Long time = (stopDate.getTime()-orderDate.getTime())%(24*60*60*1000l); 
		map.put("resideTime", (stopDate.getTime()-orderDate.getTime())/(60*1000l));
		if(stopDate.getTime()-startDate.getTime()<0){
			return map;
		}  
		double totalFee = 0d; 
		double topFee = (base.getTopDaily().intValue()/base.getTimelyLong())*base.getBasePrice().doubleValue();
		if(day>=1){
			totalFee = day*topFee;
		}else{
			time = time -1;
		} 
		double fee = 0d; 
		if(time>0){ 
			fee = ((time/(base.getTimelyLong().intValue()*1000l*60))+1)*base.getBasePrice().doubleValue();
		}
		if(fee>topFee){
			totalFee += topFee;
		}else{
			totalFee += fee;
		}
		map.put("totalAmount", totalFee);  
		return map;
	}
	public static void mains(String[] args) throws ParseException{
		StrategyBase base = new StrategyBase();
		base.setFreeMins(15);
		base.setBeginTime("10:00:00");
		base.setEndTime("22:00:00");
		base.setTimelyLong(15);
		base.setBasePrice(new BigDecimal(1.5d));
		base.setTopDaily(1440);
		String startString = "2018-05-26 12:54:38";
		String endString = "2018-05-26 13:11:11";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = sdf.parse(startString); 
		startDate = new Date(startDate.getTime()+base.getFreeMins()*60*1000l); 
		Date endDate = sdf.parse(endString);
		Long min = (endDate.getTime() - startDate.getTime() )/(60*1000L);
		System.out.println(min);
		Map<String,Object> map = getBilling(base,startDate,endDate); 
	} 
}
