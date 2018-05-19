package cn.linkmore.prefecture.fee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.linkmore.prefecture.entity.StrategyBase; 
/**
 * 计费策略 - 国瑞城按时段计费
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class PeriodFee {
	public static Map<String,Object> getPeriodBilling(StrategyBase base, Date startDate, Date stopDate){ 
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
		SimpleDateFormat dsdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String startTime = sdf.format(startDate);
		String stopTime = sdf.format(stopDate);
		String startDay = dsdf.format(startDate);
		String stopDay = dsdf.format(stopDate);
		
		PeriodStrategy ps = new PeriodStrategy();
		ps.parse(base); 
		Map<String,TimePrice> tpmap  = new HashMap<String,TimePrice>();
		TimePrice tp = new TimePrice();
		tp.setTime(ps.getDiscount0Time());
		tp.setPrice(ps.getDiscount0Price());
		tp.setStart(ps.getDiscount0Start());
		tp.setEnd(ps.getDiscount0End());
		tpmap.put(tp.getEnd(), tp);
		
		tp = new TimePrice();
		tp.setTime(ps.getDiscount1Time());
		tp.setPrice(ps.getDiscount1Price());
		tp.setStart(ps.getDiscount1Start());
		tp.setEnd(ps.getDiscount1End());
		tpmap.put(tp.getEnd(), tp);
		  
		tp = new TimePrice();
		tp.setTime(ps.getDiscount2Time());
		tp.setPrice(ps.getDiscount2Price());
		tp.setStart(ps.getDiscount2Start());
		tp.setEnd(ps.getDiscount2End());
		tpmap.put(tp.getEnd(), tp);
		
		tp = new TimePrice();
		tp.setTime(ps.getCommonTime());
		tp.setPrice(ps.getCommonPrice());
		tp.setStart(ps.getCommonStart());
		tp.setEnd(ps.getCommonEnd());
		tpmap.put(tp.getEnd(), tp);
		
		tp = new TimePrice();
		tp.setTime(ps.getBusyTime());
		tp.setPrice(ps.getBusyPrice());
		tp.setStart(ps.getBusyStart());
		tp.setEnd(ps.getBusyEnd());
		tpmap.put(tp.getEnd(), tp);
		
		tp = new TimePrice();
		tp.setTime(1);
		tp.setPrice(0d);
		tp.setStart(ps.getFreeStart());
		tp.setEnd(ps.getFreeEnd());
		tpmap.put(tp.getEnd(), tp);
			
			
		List<String> timeList = new ArrayList<String>();
		timeList.add(ps.getDiscount0End());
		timeList.add(ps.getFreeEnd());
		timeList.add(ps.getDiscount1End());
		timeList.add(ps.getCommonEnd());
		timeList.add(ps.getBusyEnd());
		timeList.add(ps.getDiscount2End()); 
		timeList.add(startTime);
		Collections.sort(timeList);
		int startIndex = timeList.indexOf(startTime);
		timeList.remove(startIndex);
		timeList.add(stopTime);
		Collections.sort(timeList);
		int endIndex = timeList.indexOf(stopTime); 
		timeList.remove(endIndex);
		double amount = 0d;
		String key = null;
		if(stopDay.equals(startDay)){
			if(startIndex==endIndex){
				key = timeList.get(startIndex);
				tp = tpmap.get(key);
				amount = tp.getSpaceAmount(startTime, stopTime);
			}else{
				key = timeList.get(startIndex);
				tp = tpmap.get(key);
				amount += tp.getEndAmount(startTime);
				for(int i=startIndex+1;i<endIndex;i++){
					key = timeList.get(i);
					tp = tpmap.get(key);
					amount += tp.getCompleteAmount();
				} 
				key = timeList.get(endIndex);
				tp = tpmap.get(key);
				amount += tp.getStartAmount(stopTime);  
			}
			
		}else if(day==0){
			//不在一天，但未过整天
			//#--------S------#--E------------#
			//#--------|------#--|------------# 
			key = timeList.get(startIndex);
			tp = tpmap.get(key);
			amount += tp.getEndAmount(startTime);
			for(int i=startIndex + 1;i<timeList.size();i++){
				key = timeList.get(i);
				tp = tpmap.get(key);
				amount += tp.getCompleteAmount();
			} 
			for(int i=0;i<endIndex;i++){
				key = timeList.get(i);
				tp = tpmap.get(key);
				amount += tp.getCompleteAmount();
			}  
			key = timeList.get(endIndex);
			tp = tpmap.get(key);
			amount += tp.getStartAmount(stopTime);   
			
		}else{
			double complete = 0d;
			Set<String> ks = tpmap.keySet();
			for(String k:ks){
				tp = tpmap.get(k);
				complete += tp.getCompleteAmount();
			}
			if(startIndex<endIndex){
				//开始时间小于结束时间
				//#---S-----------#------------E--#
				//#---------------#---S--------E--#
				//#---|-----------#---|--------|--#
				key = timeList.get(startIndex);
				tp = tpmap.get(key);
				amount += tp.getEndAmount(startTime);
				for(int i=startIndex+1;i<endIndex;i++){
					key = timeList.get(i);
					tp = tpmap.get(key);
					amount += tp.getCompleteAmount();
				} 
				key = timeList.get(endIndex);
				tp = tpmap.get(key);
				amount += tp.getStartAmount(stopTime); 
				
			}else if(startIndex>endIndex){
				//#--------S------#--E------------#
				//#--------|------#--|------------#
				
				key = timeList.get(startIndex);
				tp = tpmap.get(key);
				amount += tp.getEndAmount(startTime);
				for(int i=startIndex + 1;i<timeList.size();i++){
					key = timeList.get(i);
					tp = tpmap.get(key);
					amount += tp.getCompleteAmount();
				} 
				for(int i=0;i<endIndex;i++){
					key = timeList.get(i);
					tp = tpmap.get(key);
					amount += tp.getCompleteAmount();
				}  
				key = timeList.get(endIndex);
				tp = tpmap.get(key);
				amount += tp.getStartAmount(stopTime);   
			}else if(startIndex==endIndex){
				key = timeList.get(startIndex);
				tp = tpmap.get(key);
				amount = tp.getSpaceAmount(startTime, stopTime);
			}
			amount += complete*day; 
		} 
		map.put("totalAmount", amount); 
		return map;
	} 
	public static Map<String,Object> getTimePrice(String detail){
		StrategyBase base = new StrategyBase();
		base.setFlexDetail(detail);
		base.setFreeMins(15);
		base.setId(0l);
		base.setName("");
		Map<String,Object> map  = new HashMap<String,Object>(); 
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String time = sdf.format(new Date());
		
		PeriodStrategy ps = new PeriodStrategy();
		ps.parse(base); 
		Map<String,TimePrice> tpmap  = new HashMap<String,TimePrice>();
		TimePrice tp = new TimePrice();
		tp.setTime(ps.getDiscount0Time());
		tp.setPrice(ps.getDiscount0Price());
		tp.setStart(ps.getDiscount0Start());
		tp.setEnd(ps.getDiscount0End());
		tpmap.put(tp.getEnd(), tp);
		
		tp = new TimePrice();
		tp.setTime(ps.getDiscount1Time());
		tp.setPrice(ps.getDiscount1Price());
		tp.setStart(ps.getDiscount1Start());
		tp.setEnd(ps.getDiscount1End());
		tpmap.put(tp.getEnd(), tp);
		  
		tp = new TimePrice();
		tp.setTime(ps.getDiscount2Time());
		tp.setPrice(ps.getDiscount2Price());
		tp.setStart(ps.getDiscount2Start());
		tp.setEnd(ps.getDiscount2End());
		tpmap.put(tp.getEnd(), tp);
		
		tp = new TimePrice();
		tp.setTime(ps.getCommonTime());
		tp.setPrice(ps.getCommonPrice());
		tp.setStart(ps.getCommonStart());
		tp.setEnd(ps.getCommonEnd());
		tpmap.put(tp.getEnd(), tp);
		
		tp = new TimePrice();
		tp.setTime(ps.getBusyTime());
		tp.setPrice(ps.getBusyPrice());
		tp.setStart(ps.getBusyStart());
		tp.setEnd(ps.getBusyEnd());
		tpmap.put(tp.getEnd(), tp);
		
		tp = new TimePrice();
		tp.setTime(1);
		tp.setPrice(0d);
		tp.setStart(ps.getFreeStart());
		tp.setEnd(ps.getFreeEnd());
		tpmap.put(tp.getEnd(), tp);
			
			
		List<String> timeList = new ArrayList<String>();
		timeList.add(ps.getDiscount0End());
		timeList.add(ps.getFreeEnd());
		timeList.add(ps.getDiscount1End());
		timeList.add(ps.getCommonEnd());
		timeList.add(ps.getBusyEnd());
		timeList.add(ps.getDiscount2End()); 
		timeList.add(time);
		Collections.sort(timeList);
		int index = timeList.indexOf(time); 
		timeList.remove(time);
		Collections.sort(timeList);
		tp = tpmap.get(timeList.get(index));
		
		map.put("price", tp.getPrice());
		map.put("time", tp.getTime());
		return map;
	}
	
	public static void mains(String[] args) throws ParseException{
		StrategyBase base = new StrategyBase();
		base.setFreeMins(10);
		String text = "01:00,05:00|00:00,01:00,1.0,15|05:00,09:00,1.0,15|22:00,24:00,1.0,15|09:00,13:00,1.5,15|13:00,22:00,2.5,15";
		base.setFlexDetail(text);
		String startString = "2017-11-06 14:08:06";
		String endString = "2017-11-06 14:11:06";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = sdf.parse(startString); 
		startDate = new Date(startDate.getTime()+base.getFreeMins()*60*1000l);
		System.out.println(sdf.format(startDate));
		Date endDate = sdf.parse(endString);
		Map<String,Object> map = getPeriodBilling(base,startDate,endDate);
		if(map!=null){
			Double amount = (Double)map.get("totalAmount");
			System.out.println("totalAmount:"+amount);
		}
	} 
}

class PeriodStrategy{ 
	private Long id;
	private String name;
	private Integer type;
	private Integer freeMins;
	
	private Integer status;
	
	private String creator;
	
	private String discount0Start;
	private String discount0End;
	private Double discount0Price;
	private Integer discount0Time;
	
	private String discount1Start;
	private String discount1End;
	private Double discount1Price;
	private Integer discount1Time;
	 
	private String discount2Start;
	private String discount2End;
	private Double discount2Price;
	private Integer discount2Time;
	
	
	private String commonStart;
	private String commonEnd;
	private Double commonPrice;
	private Integer commonTime;
	
	private String busyStart;
	private String busyEnd;
	private Double busyPrice;
	private Integer busyTime;
	
	
	private String freeStart;
	private String freeEnd;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getFreeMins() {
		return freeMins;
	}
	public void setFreeMins(Integer freeMins) {
		this.freeMins = freeMins;
	}
	public String getDiscount0Start() {
		return discount0Start;
	}
	public void setDiscount0Start(String discount0Start) {
		this.discount0Start = discount0Start;
	}
	public String getDiscount0End() {
		return discount0End;
	}
	public void setDiscount0End(String discount0End) {
		this.discount0End = discount0End;
	}
	public Double getDiscount0Price() {
		return discount0Price;
	}
	public void setDiscount0Price(Double discount0Price) {
		this.discount0Price = discount0Price;
	}
	public Integer getDiscount0Time() {
		return discount0Time;
	}
	public void setDiscount0Time(Integer discount0Time) {
		this.discount0Time = discount0Time;
	}
	public String getDiscount1Start() {
		return discount1Start;
	}
	public void setDiscount1Start(String discount1Start) {
		this.discount1Start = discount1Start;
	}
	public String getDiscount1End() {
		return discount1End;
	}
	public void setDiscount1End(String discount1End) {
		this.discount1End = discount1End;
	}
	public Double getDiscount1Price() {
		return discount1Price;
	}
	public void setDiscount1Price(Double discount1Price) {
		this.discount1Price = discount1Price;
	}
	public Integer getDiscount1Time() {
		return discount1Time;
	}
	public void setDiscount1Time(Integer discount1Time) {
		this.discount1Time = discount1Time;
	}
	public String getDiscount2Start() {
		return discount2Start;
	}
	public void setDiscount2Start(String discount2Start) {
		this.discount2Start = discount2Start;
	}
	public String getDiscount2End() {
		return discount2End;
	}
	public void setDiscount2End(String discount2End) {
		this.discount2End = discount2End;
	}
	public Double getDiscount2Price() {
		return discount2Price;
	}
	public void setDiscount2Price(Double discount2Price) {
		this.discount2Price = discount2Price;
	}
	public Integer getDiscount2Time() {
		return discount2Time;
	}
	public void setDiscount2Time(Integer discount2Time) {
		this.discount2Time = discount2Time;
	}
	public String getCommonStart() {
		return commonStart;
	}
	public void setCommonStart(String commonStart) {
		this.commonStart = commonStart;
	}
	public String getCommonEnd() {
		return commonEnd;
	}
	public void setCommonEnd(String commonEnd) {
		this.commonEnd = commonEnd;
	}
	public Double getCommonPrice() {
		return commonPrice;
	}
	public void setCommonPrice(Double commonPrice) {
		this.commonPrice = commonPrice;
	}
	public Integer getCommonTime() {
		return commonTime;
	}
	public void setCommonTime(Integer commonTime) {
		this.commonTime = commonTime;
	}
	public String getBusyStart() {
		return busyStart;
	}
	public void setBusyStart(String busyStart) {
		this.busyStart = busyStart;
	}
	public String getBusyEnd() {
		return busyEnd;
	}
	public void setBusyEnd(String busyEnd) {
		this.busyEnd = busyEnd;
	}
	public Double getBusyPrice() {
		return busyPrice;
	}
	public void setBusyPrice(Double busyPrice) {
		this.busyPrice = busyPrice;
	}
	public Integer getBusyTime() {
		return busyTime;
	}
	public void setBusyTime(Integer busyTime) {
		this.busyTime = busyTime;
	}
	public String getFreeStart() {
		return freeStart;
	}
	public void setFreeStart(String freeStart) {
		this.freeStart = freeStart;
	}
	public String getFreeEnd() {
		return freeEnd;
	}
	public void setFreeEnd(String freeEnd) {
		this.freeEnd = freeEnd;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}   
	public void parse(StrategyBase strategy){ 
		this.setId(strategy.getId());
		this.setFreeMins(strategy.getFreeMins());
		this.setName(strategy.getName());
		String[] split = strategy.getFlexDetail().split("\\|");
		String[] free = split[0].split(",");
		this.setFreeStart(free[0]);
		this.setFreeEnd(free[1]);
		String[] discount0 = split[1].split(",");
		this.setDiscount0Start(discount0[0]);
		this.setDiscount0End(discount0[1]);
		this.setDiscount0Price(Double.valueOf(discount0[2]));
		this.setDiscount0Time(Integer.valueOf(discount0[3]));
		String[] discount1 = split[2].split(",");
		this.setDiscount1Start(discount1[0]);
		this.setDiscount1End(discount1[1]);
		this.setDiscount1Price(Double.valueOf(discount1[2]));
		this.setDiscount1Time(Integer.valueOf(discount1[3]));
		String[] discount2 = split[3].split(",");
		this.setDiscount2Start(discount2[0]);
		this.setDiscount2End(discount2[1]);
		this.setDiscount2Price(Double.valueOf(discount2[2]));
		this.setDiscount2Time(Integer.valueOf(discount2[3]));
		String[] common = split[4].split(",");
		this.setCommonStart(common[0]);
		this.setCommonEnd(common[1]);
		this.setCommonPrice(Double.valueOf(common[2]));
		this.setCommonTime(Integer.valueOf(common[3]));
		String[] busy = split[5].split(",");
		this.setBusyStart(busy[0]);
		this.setBusyEnd(busy[1]);
		this.setBusyPrice(Double.valueOf(busy[2]));
		this.setBusyTime(Integer.valueOf(busy[3]));
	}
	
}
class TimePrice{
	private String start;
	private String end;
	private Double price;
	private Integer time;
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
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}  
	public  int getSpaceTime(String start,String end){
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
	public Double getCompleteAmount(){
		Double amount = 0d;
		int min = this.getSpaceTime(this.getStart(), this.getEnd());
		min = 1 + (min-1)/this.getTime();
		amount = this.getPrice()*min; 
		return amount;
	}
	public Double getStartAmount(String end){
		Double amount = 0d;
		int min = this.getSpaceTime(this.getStart(), end);
		min = 1 + (min-1)/this.getTime();
		amount = this.getPrice()*min;
		return amount;
	}
	public Double getEndAmount(String start){
		Double amount = 0d;
		int min = this.getSpaceTime(start, this.getEnd());
		min = 1 + (min-1)/this.getTime();
		amount = this.getPrice()*min;
		return amount;
	}
	public Double getSpaceAmount(String start,String end){
		Double amount = 0d;
		int min = this.getSpaceTime(start, end);
		min = 1 + (min-1)/this.getTime();
		amount = this.getPrice()*min;
		return amount;
	}
}