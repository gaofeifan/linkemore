package cn.linkmore.prefecture.fee;

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
		long freeTime =base.getFreeMins()*60*1000; 
		Date orderDate = new Date(startDate.getTime()-freeTime);
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
			time = time - base.getFreeMins()*60*1000l-1;
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
}
