package cn.linkmore.prefecture.fee;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.linkmore.prefecture.entity.StrategyBase;

/**
 * 时尚购物6小时封顶计费
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class SixHourTopFee {
	
	public final static String DAY_START = "00:00:00";
	public final static String DAY_END = "24:00:00";
	public final static String DAY_MORNING ="06:00:00";
	public final static String DAY_NIGHT ="18:00:00";
	private final static Logger log = LoggerFactory.getLogger(SixHourTopFee.class);
	public static Map<String, Object> getBilling(StrategyBase base, Date startDate, Date stopDate) throws ParseException {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("day", 0l);
		map.put("night", 0l);
		map.put("dayAmount", 0d);
		map.put("totalAmount", 0d);
		map.put("nightAmount", 0d);
		
		double totalFee = 0d;
		double topFee = (base.getTopDaily().intValue() / base.getTimelyLong()) * base.getBasePrice().doubleValue();
		double fee = 0d;
		//当前日期
		SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
		String startDay = sdfDay.format(startDate);
		String stopDay = sdfDay.format(stopDate); 
		//当前时间
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
		String startTime = sdfTime.format(startDate);
		String stopTime = sdfTime.format(stopDate); 
		
		//比较开始结束日期
		int dayFlag = stopDay.compareTo(startDay);
		int day =0;
		if(dayFlag>0) {
			day = getDiffDay(startDay,stopDay);
		}
		log.info("----------diff day of start {} and stop {} and day {}",startDay,stopDay,day);
		
		//日期比较
		SimpleDateFormat sdfCompare = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startDateStr = startDay +" 24:00:00";
		String endDateStr = stopDay +" 00:00:00";
		Date compareStartDate = sdfCompare.parse(startDateStr);
		Date compareEndDate = sdfCompare.parse(endDateStr);
		
		Long time = stopDate.getTime() - startDate.getTime();
		int actualTime = getMinTime(time);
		map.put("resideTime", actualTime);
		
		if (actualTime <= 30) {
			log.info("间隔时间<30m,免费停车");
			return map;
		} else if (actualTime <= 360) {
			totalFee = getFee(actualTime,base);
			log.info("间隔时间<6h,累计计费");
		} else {
			if (startDay.equals(stopDay)) {
				totalFee = topFee;
				log.info("间隔时间>6h 同一天封顶计费");
			} else {
				int startFlag = startTime.compareTo("18:00:00");
				int stopFlag = stopTime.compareTo("06:00:00");
				if (startFlag < 0 && stopFlag > 0) {
					//停车当日超过6h，封顶计费，次日停车超过6h 封顶计费
					totalFee = (day + 2) * topFee;
					log.info("----------停车当日超过6h，封顶计费，次日停车超过6h 封顶计费");
				}else if(startFlag < 0 && stopFlag < 0) {
					totalFee = (day + 1) * topFee;
					Long timeLong = stopDate.getTime()- compareEndDate.getTime();
					int stop = getMinTime(timeLong);
					fee = getFee(stop,base);
					totalFee  += fee;
					log.info("----------停车当日超过6h，封顶计费，次日停车不超过6h,次日时间累计计费");
				}else if (startFlag > 0 && stopFlag > 0) {
					totalFee = (day + 1) * topFee;
					Long timeLong = compareStartDate.getTime()-startDate.getTime();
					int start = getMinTime(timeLong);
					fee = getFee(start,base);
					totalFee  += fee;
					log.info("----------停车当日不超过6h，当日累计计费，次日停车超过6h 封顶计费");
				}else {
					totalFee = day * topFee;
					Long timeLong = stopDate.getTime()- startDate.getTime() - day * 24 * 60 * 60 * 1000;
					int startStop = getMinTime(timeLong);
					fee = getFee(startStop,base);
					totalFee  += fee;
					log.info("----------停车当日不超过6h，次日停车不超过6h 累计2段时间计费，不封顶");
				}
			}
		}
		map.put("totalAmount", totalFee);
		return map;
	}
	
	private static double getFee(int actualTime, StrategyBase base) {
		int fee = actualTime % base.getTimelyLong().intValue() == 0 ? 
				  actualTime / base.getTimelyLong().intValue():
				  actualTime / base.getTimelyLong().intValue() + 1;	
		return fee * base.getBasePrice().doubleValue();
	}

	private static int getMinTime(Long time) {
		int min =  (int) (time % (1000 * 60) == 0 ? time / (1000 * 60) : time / (1000 * 60) + 1);
		return min;
	}

	/**
	 * 计算2个时间之间相差的完整天数
	 * @param startDay
	 * @param stopDay
	 * @return
	 */
	private static int getDiffDay(String startDay, String stopDay) {
		startDay = startDay +" " + DAY_END;
		stopDay = stopDay +" " + DAY_START;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date startDate = sdf.parse(startDay);
			Date stopDate = sdf.parse(stopDay);
			int days = (int) ((stopDate.getTime() - startDate.getTime()) / (1000*3600*24));
			return days;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void main(String[] args) throws ParseException {
		StrategyBase base = new StrategyBase();
		base.setFreeMins(30);
		base.setBeginTime("06:00:00");
		base.setEndTime("18:00:00");
		base.setTimelyLong(60);
		base.setBasePrice(new BigDecimal(5d));
		base.setTopDaily(360);
		
		List<Start> list = new ArrayList<Start>();
		//同一日超过30min
		String startString1 = "2018-05-26 20:00:01";
		String endString1 = "2018-05-26 20:30:02";
		Start start1 = new Start(startString1, endString1);
		
		//同一日超过360min
		String startString2 = "2018-05-26 10:00:01";
		String endString2 = "2018-05-26 20:30:02";
		Start start2 = new Start(startString2, endString2);
		
		//双日封底计费
		String startString3 = "2018-05-26 17:00:01";
		String endString3 = "2018-05-27 07:30:02";
		Start start3 = new Start(startString3, endString3);
		
		//双日封底计费,间隔完整1天
		String startString4 = "2018-05-26 17:00:01";
		String endString4 = "2018-05-28 07:30:02";
		Start start4 = new Start(startString4, endString4);
		
		//次日封底计费，当日累计计费,间隔完整1天
		String startString5 = "2018-05-26 18:00:10";
		String endString5 = "2018-05-28 07:30:02";
		Start start5 = new Start(startString5, endString5);
		//当日封底计费，次日累计计费,间隔完整1天
		String startString6 = "2018-05-26 17:00:10";
		String endString6 = "2018-05-28 04:00:02";
		Start start6 = new Start(startString6, endString6);
		
		//当日次日累计不超过计费,间隔完整1天
		String startString7 = "2018-05-26 23:00:10";
		String endString7 = "2018-05-28 05:00:02";
		Start start7 = new Start(startString7, endString7);
		//当日次日累计超过6j计费,间隔完整1天
		String startString8 = "2018-05-26 22:00:01";
		String endString8 = "2018-05-28 05:00:02";
		Start start8 = new Start(startString8, endString8);
		list.add(start1);
		list.add(start2);
		list.add(start3);
		list.add(start4);
		list.add(start5);
		list.add(start6);
		list.add(start7);
		list.add(start8);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(Start start:list) {
			Date startDate = sdf.parse(start.getStartDay());
			Date endDate = sdf.parse(start.getEndDay());
			Map<String, Object> map = getBilling(base, startDate, endDate);
			log.info("----------map="+map);
		}
		
	}
}

class Start{
	private String startDay;
	private String endDay;
	public Start(String startDay,String endDay) {
		this.startDay = startDay;
		this.endDay = endDay;
	}
	public String getStartDay() {
		return startDay;
	}
	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}
	public String getEndDay() {
		return endDay;
	}
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}
}
