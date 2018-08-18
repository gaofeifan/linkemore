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
public class TianyangTopFee {

	public final static String DAY_START = "00:00:00";
	public final static String DAY_END = "24:00:00";
	public final static String DAY_MORNING = "06:00:00";
	public final static String DAY_NIGHT = "18:00:00";
	private final static Logger log = LoggerFactory.getLogger(TianyangTopFee.class);

	public static Map<String, Object> getBilling(StrategyBase base, Date startDate, Date stopDate) {
		// 此处开始时间-免费时长 因30分钟免费，但过30分钟按一小时计费对应orderFee 34-36 line
		long freeTime = base.getFreeMins() * 60 * 1000;
		startDate = new Date(startDate.getTime() - freeTime);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("day", 0l);
		map.put("night", 0l);
		map.put("dayAmount", 0d);
		map.put("totalAmount", 0d);
		map.put("nightAmount", 0d);
		try {
			double totalFee = 0d;
			double topFee = (base.getTopDaily().intValue() / base.getTimelyLong()) * base.getBasePrice().doubleValue();
			double fee = 0d;
			// 当前日期
			SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
			String startDay = sdfDay.format(startDate);
			String stopDay = sdfDay.format(stopDate);
			// 当前时间
			SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
			String startTime = sdfTime.format(startDate);
			String stopTime = sdfTime.format(stopDate);

			// 比较开始结束日期
			int dayFlag = stopDay.compareTo(startDay);
			int day = 0;
			if (dayFlag > 0) {
				day = getDiffDay(startDate, stopDate);
			}

			Long time = stopDate.getTime() - startDate.getTime();
			int actualTime = getMinTime(time);
			map.put("resideTime", actualTime);

			if (actualTime <= 30) {
				log.info("间隔时间<30m,免费停车");
				return map;
			} else if (actualTime <= 360) {
				totalFee = getFee(actualTime, base);
				log.info("间隔时间<6h,累计计费");
			} else {
				if (startDay.equals(stopDay)) {
					totalFee = topFee;
					log.info("间隔时间>6h 同一天封顶计费");
				} else {
					totalFee = day * topFee;
					Long timeLong = stopDate.getTime() - startDate.getTime() - day * 24 * 60 * 60 * 1000;
					if(timeLong >= 6 * 60 * 60 * 1000) {
						totalFee += topFee;
						log.info("完整天数除外，超过6h封顶计费");
					}else {
						int startStop = getMinTime(timeLong);
						fee = getFee(startStop, base);
						totalFee += fee;
						log.info("完整天数除外，不超过6h累计计费");
					}
				}
			}
			map.put("totalAmount", totalFee);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	private static double getFee(int actualTime, StrategyBase base) {
		int fee = actualTime % base.getTimelyLong().intValue() == 0 ? actualTime / base.getTimelyLong().intValue()
				: actualTime / base.getTimelyLong().intValue() + 1;
		return fee * base.getBasePrice().doubleValue();
	}

	private static int getMinTime(Long time) {
		int min = (int) (time % (1000 * 60) == 0 ? time / (1000 * 60) : time / (1000 * 60) + 1);
		return min;
	}

	/**
	 * 计算2个时间之间相差的完整天数
	 * 
	 * @param startDay
	 * @param stopDay
	 * @return
	 */
	private static int getDiffDay(Date startDate, Date stopDate) {
		int days = (int) ((stopDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24));
		return days;
	}

	public static void main(String[] args) throws ParseException {
		StrategyBase base = new StrategyBase();
		base.setFreeMins(30);
		base.setTimelyLong(60);
		base.setBasePrice(new BigDecimal(5d));
		base.setTopDaily(360);

		List<Start1> list = new ArrayList<Start1>();
		// 不超过30分钟
		String startString1 = "2018-05-26 20:00:01";
		String endString1 = "2018-05-26 20:30:00";
		Start1 start1 = new Start1(startString1, endString1);

		// 同一日不超过360min
		String startString2 = "2018-05-26 10:00:01";
		String endString2 = "2018-05-26 14:30:02";
		Start1 start2 = new Start1(startString2, endString2);

		// 同一日超过360min
		String startString3 = "2018-05-26 10:00:01";
		String endString3 = "2018-05-26 17:30:02";
		Start1 start3 = new Start1(startString3, endString3);

		// 隔日超过24h
		String startString4 = "2018-05-26 00:00:01";
		String endString4 = "2018-05-27 00:00:02";
		Start1 start4 = new Start1(startString4, endString4);

		// 间隔不超过24h
		String startString5 = "2018-05-26 18:00:10";
		String endString5 = "2018-05-27 17:30:02";
		Start1 start5 = new Start1(startString5, endString5);
		// 当日封底计费，次日累计计费,间隔完整1天
		String startString6 = "2018-05-26 10:00:10";
		String endString6 = "2018-05-28 14:00:11";
		Start1 start6 = new Start1(startString6, endString6);
		
		list.add(start1);
		list.add(start2);
		list.add(start3);
		list.add(start4);
		list.add(start5);
		list.add(start6);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Start1 start : list) {
			Date startDate = sdf.parse(start.getStartDay());
			startDate = new Date(startDate.getTime() + base.getFreeMins() * 60 * 1000l);
			Date endDate = sdf.parse(start.getEndDay());
			Map<String, Object> map = getBilling(base, startDate, endDate);
			log.info("start={}----------map={}",start.getStartDay() , map);
		}

	}
}

class Start1 {
	private String startDay;
	private String endDay;

	public Start1(String startDay, String endDay) {
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
