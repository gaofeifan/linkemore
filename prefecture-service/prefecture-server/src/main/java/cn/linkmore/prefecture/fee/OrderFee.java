package cn.linkmore.prefecture.fee;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.linkmore.prefecture.entity.StrategyBase;
/**
 * 计费策略
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class OrderFee {
	private static Logger log = LoggerFactory.getLogger(OrderFee.class);

	/**
	 * 
	 * 
	 * 计费策略 类型 1: 无封顶计费 计费策略 类型 2: 全天时间累计封顶有封顶 计费策略 类型 3: 分时段时间累计封顶 （有效封顶
	 * 白天或夜间封顶时长<时段时长） 计费策略 类型 4: 从开始计费时刻起以24小时（一天）为计费周期，m分钟免费，n元/每小时 ，满N小时天封顶
	 * 
	 * 新添加主要参数：免费时长 、每日累计时间 、白天时段封顶时长 、夜间时段封顶时长
	 * 
	 * 1.解决免费时长 2.解决封顶价
	 * 
	 */
	public static Map<String, Object> getMultipleParkingCost(StrategyBase base, Date startDate, Date stopDate) {

		// 白天时段 计费开始和结束时间
		// 免费时间毫秒数
		long freeTime = base.getFreeMins() * 60 * 1000;
		// 注意：重置了开始日期startDate
		startDate = new Date(startDate.getTime() + freeTime);
		Map<String,Object> result = null;
		switch(base.getType().intValue()){
			case StrategyBase.TYPE_DAY_FEE:result=DayFee.getBilling(base, startDate, stopDate);break;
			case StrategyBase.TYPE_TOP_NONE:result=getParkingCost(base, startDate, stopDate);break;
			case StrategyBase.TYPE_TOP_DAILY:result=getTOPDailyMap(base, startDate, stopDate);break;
			case StrategyBase.TYPE_TOP_SECTION:result= getTOPSectionMap(base, startDate, stopDate);break;
			case StrategyBase.TYPE_TOP_DAILY_24H:result=TimeTopFee.getBilling(base, startDate, stopDate);break;
			case StrategyBase.TYPE_TOP_DAILY_24H_FRIST_XHORS:result = WonderFee.getTOPDaily24HFRISTXHORSMap(base, startDate, stopDate);break;
			case StrategyBase.TYPE_PERIOD:result=PeriodFee.getPeriodBilling(base, startDate, stopDate);break;
			case  StrategyBase.TYPE_HUBIN:result=HubinFee.getBilling(base, startDate, stopDate);break;
			case  StrategyBase.TYPE_XICHENG:result=XichengFee.getBilling(base, startDate, stopDate);break;
			case StrategyBase.TYPE_OLD_DAY_FEE:result=OldDayFee.getBilling(base, startDate, stopDate);break;
		} 
		return result;
	}

	/**
	 * 每24小时为一计费单位的含免费时间长，封顶，计算
	 * 
	 * @param base
	 * @param startDate
	 * @param stopDate
	 * @return
	 */
	private static Map<String, Object> getTOPDaily24HMap(StrategyBase base, Date startDate, Date stopDate) {

		double basePrice = base.getBasePrice().doubleValue();
		long topDailyTime = base.getTopDaily() * 60;// 秒
		int daySec = 24 * 60 * 60;
		Integer timelyLong = base.getTimelyLong() * 60;
		Map<String, Object> m = new HashMap<>();
		long freeTime = base.getFreeMins() * 60;
		long resideTime = stopDate.getTime() / 1000 - startDate.getTime() / 1000 + freeTime;
		m.put("resideTime", resideTime);

		if (freeTime >= resideTime) {
			m.put("day", 0l);
			m.put("night", 0l);
			m.put("dayAmount", 0.00d);
			m.put("nightAmount", 0.00d);
			m.put("totalAmount", 0.00d);
			return m;
		} else if (freeTime < resideTime)

		{
			long feeTime = resideTime - freeTime;
			Integer days = (int) (feeTime / daySec);
			long indayTime = (feeTime % daySec);
			double topFeedays = topDailyTime / timelyLong * basePrice;
			double totalAmount = indayTime < topDailyTime ? topFeedays * days
					+ (indayTime % timelyLong == 0 ? (indayTime / timelyLong) : indayTime / timelyLong + 1) * basePrice
					: topFeedays * (days + 1);

			m.put("day", 0l);
			m.put("night", 0l);
			m.put("dayAmount", 0.00d);
			m.put("nightAmount", 0.00d);
			m.put("totalAmount", totalAmount);
			return m;
		}

		return null;
	}

	/**
	 * 计算费用 天封顶计费 白天计费基数必须与夜间计费基数相同
	 * 
	 * @param base
	 * @param startDate
	 * @param stopDate
	 * @return
	 */
	private static Map<String, Object> getTOPDailyMap(StrategyBase base, Date startDate, Date stopDate) {

		double firstHour = base.getFirstHour().doubleValue();
		double basePrice = base.getBasePrice().doubleValue();
		double nightPrice = base.getNightPrice().doubleValue();
		long topDailyTime = base.getTopDaily() * 60;// 秒
		int daySec = 24 * 60 * 60;
		Integer timelyLong = base.getTimelyLong() * 60;
		Map<String, Object> m = new HashMap<>();
		long freeTime = base.getFreeMins() * 60;
		long resideTime = stopDate.getTime() / 1000 - startDate.getTime() / 1000 + freeTime;
		m.put("resideTime", resideTime);
		// 停车时间小于免费时长
		if (startDate.getTime() >= stopDate.getTime()) {
			m.put("day", 0l);
			m.put("night", 0l);
			m.put("dayAmount", 0.00d);
			m.put("nightAmount", 0.00d);
			m.put("totalAmount", 0.00d);
			return m;
		}
		Map<String, Long> timeMap = getAllSplitTimes(base, startDate, stopDate);
		long myCase = timeMap.get("case");
		// 1、价格统一
		if (firstHour == basePrice && basePrice == nightPrice) {
			double allDayFee = 0.00d;
			m.put("dayAmount", 0.00d);
			m.put("nightAmount", 0.00d);
			if (myCase == 1l) {
				// 停留总时长
				long dayTime = timeMap.get("day");
				long nightTime = timeMap.get("night");
				m.put("day", dayTime);
				m.put("night", nightTime);
				boolean topAllDay = topDailyTime <= dayTime + nightTime;
				// 判断白天加夜间总时长是否超过topDailyTime
				// 如果没超过正常计算
				// 如果超过则夜间价格为 封顶价格-白天价格
				if (topAllDay) {
					allDayFee = getFee(basePrice, timelyLong, topDailyTime);
				} else {
					allDayFee = getFee(basePrice, timelyLong, (dayTime + nightTime));
				}
				m.put("totalAmount", allDayFee);
			} else {
				long days = timeMap.get("days");
				long dayTime1 = timeMap.get("dayTime1");
				long nightTime1 = timeMap.get("nightTime1");
				long dayTime2 = timeMap.get("dayTime2");
				long nightTime2 = timeMap.get("nightTime2");

				Integer n = days > 0 ? ((Long) (days / daySec)).intValue() : 0;
				double ndaysFee = n * (topDailyTime / timelyLong * basePrice);
				long startDayEnd = timeMap.get("startDayEnd");
				long startDayBegin = timeMap.get("startDayBegin");
				long dayTime = dayTime1 + dayTime2 + (startDayEnd - startDayBegin) * n;
				long nightTime = nightTime1 + nightTime2 + days - (startDayEnd - startDayBegin) * n;

				m.put("day", dayTime);
				m.put("night", nightTime);

				if (topDailyTime > dayTime1 + nightTime1) {
					long time1 = dayTime1 + nightTime1;
					allDayFee += getFee(basePrice, timelyLong, time1);
				} else {
					allDayFee += getFee(basePrice, timelyLong, topDailyTime);
				}

				if (topDailyTime > dayTime2 + nightTime2) {
					long time2 = dayTime2 + nightTime2;
					allDayFee += getFee(basePrice, timelyLong, time2);
				} else {
					allDayFee += getFee(basePrice, timelyLong, topDailyTime);
				}
				// 单位为秒
				allDayFee += ndaysFee;
				m.put("totalAmount", allDayFee);
			}

		} // 2、价格不统一
		else {
			System.err.println("由于计费策略价格不统一，暂时未实现该业务");
		}
		System.out.println("cc");
		return m;
	}

	private static double getFee(double basePrice, Integer timelyLong, long dayTime) {
		return dayTime % timelyLong == 0 ? dayTime / timelyLong * basePrice : (dayTime / timelyLong + 1) * basePrice;
	}

	/*
	 * 1、if(时段单价相等)统一处理 计算开始时间-结束时间中间
	 * 
	 * 
	 * 2、时段单价不等
	 * 
	 * 停车时间段s、 封顶时长t、 白天时段d、夜间时段n 白天开始封顶费用dTopfee Calendar c1 c2 时间
	 * c1=getCalendarEnd(s1) c2=getCalendarStart(s2) if(c1=c2) { tfee =Aa(s1
	 * -c1)+Aa(c2-s2) } else { tfee=Aa(s1 -c1)+Aa(c2-s2) + (c1~c2)/24 * dTopfee
	 * }
	 * 
	 * Aa 不足一天计费计算 d2==n1 1) s1!=s2 d1<=s1,s2 <=d2 ; n1<=s1,s2<=n2; d1<=s1<d2 &&
	 * n1<s2<n2; n1<=s1<n2 && d1<s2<d2;
	 * 
	 */
	// 天内封顶计费计算
	/**
	 * 筛选分类时间 1.) 开始和结束时间同属同一天同一个时间段 7:00 19:00 24:00 7:00 7:00
	 * ___|__b1__e1__|___b2___=___e2____|_______|_______=_______|___
	 * 
	 * 2.)开始和结束属于同一天但不属于同一个时间端 7:00 19:00 24:00 7:00 ___|__b1
	 * ,b2____|____e1__=___e2____|__
	 * 
	 * 3.) 开始时间当天 和结束时间当天均为不满天的时间段，中见跨度为0~n天 7:00 19:00 24:00 7:00 19:00 24:00
	 * 7:00 19：00 7:00
	 * ___|__b1____|____b2__=___b3____|_______|_______=_______|__e1___|_e2=_e3_|___
	 * |<--- n天 --->|
	 * 
	 * @param base
	 * @param startDate
	 * @param stopDate
	 * @return
	 */
	private static Map<String, Long> getAllSplitTimes(StrategyBase base, Date startDate, Date stopDate) {

		long startStamp = startDate.getTime();
		long stopStamp = stopDate.getTime();
		// 07:00
		long startDayBegin = getSectionTime(startStamp, base.getBeginTime());
		// 19:00
		long startDayEnd = getSectionTime(startStamp, base.getEndTime());
		// 07:00
		long stopDayBegin = getSectionTime(stopStamp, base.getBeginTime());
		// 19:00
		long stopDayEnd = getSectionTime(stopStamp, base.getEndTime());
		Map<String, Long> startMap = getStartSectionTimes(startStamp, startDayBegin, startDayEnd);
		Map<String, Long> stopMap = getStopSectionTimes(stopStamp, stopDayBegin, stopDayEnd);
		long dailyBegin = startMap.get("nextDayBegin");
		long dailyEnd = stopMap.get("startDayBegin");
		long daysTime = dailyEnd - dailyBegin;
		Map<String, Long> m = new HashMap<>();
		// 停车开始时间为白天收费时段
		if (startMap.get("case") == 1l) {
			m.put("startCase", 1l);
		} else {
			m.put("startCase", 2l);
		}
		if (daysTime < 0) {// 开始结束时间在同一天
			long startCase = startMap.get("case");
			long stopCase = stopMap.get("case");
			if (startCase == stopCase) {
				long time = stopStamp - startStamp;
				if (startCase == 1l) {
					// 白天时间 time , 夜间为0
					m.put("case", 1l);
					m.put("days", 0l);
					m.put("day", time / 1000);
					m.put("night", 0l);
					m.put("dayTime1", 0l);
					m.put("nightTime1", 0l);
					m.put("dayTime2", 0l);
					m.put("nightTime2", 0l);
					m.put("startDayBegin", stopMap.get("startDayBegin") / 1000);
					m.put("startDayEnd", stopMap.get("startDayEnd") / 1000);
				} else {
					// 白天时间 为0 ，夜间 time;
					m.put("case", 1l);
					m.put("days", 0l);
					m.put("day", 0l);
					m.put("night", time / 1000);
					m.put("dayTime1", 0l);
					m.put("nightTime1", 0l);
					m.put("dayTime2", 0l);
					m.put("nightTime2", 0l);
					m.put("startDayBegin", stopMap.get("startDayBegin") / 1000);
					m.put("startDayEnd", stopMap.get("startDayEnd") / 1000);
				}
			} else {
				long stopDayEnd1 = stopMap.get("startDayEnd");
				m.put("case", 1l);
				m.put("days", 0l);
				m.put("day", (stopDayEnd1 - startStamp) / 1000);
				m.put("night", (stopStamp - stopDayEnd1) / 1000);
				m.put("dayTime1", 0l);
				m.put("nightTime1", 0l);
				m.put("dayTime2", 0l);
				m.put("nightTime2", 0l);
				m.put("startDayBegin", stopMap.get("startDayBegin") / 1000);
				m.put("startDayEnd", stopMap.get("startDayEnd") / 1000);
			}
		} else if (daysTime >= 0)// 有整天的情况
		{
			m.put("case", 2l);
			m.put("days", daysTime / 1000);
			m.put("day", 0l);
			m.put("night", 0l);
			m.put("dayTime1", startMap.get("dayTime") / 1000);
			m.put("nightTime1", startMap.get("nightTime") / 1000);
			m.put("dayTime2", stopMap.get("dayTime") / 1000);
			m.put("nightTime2", stopMap.get("nightTime") / 1000);
			m.put("startDayBegin", stopMap.get("startDayBegin") / 1000);
			m.put("startDayEnd", stopMap.get("startDayEnd") / 1000);

		}
		return m;
	}

	private static Map<String, Long> getStopSectionTimes(long stopStamp, long stopDayBegin, long stopDayEnd) {

		long dayLong = 24 * 60 * 60 * 1000;
		Map<String, Long> m = new HashMap<>();

		// 1, 07:00=<stopStamp<19:00
		if (stopDayBegin <= stopStamp && stopStamp <= stopDayEnd) {
			m.put("case", 1l);
			m.put("startStamp", stopStamp);
			m.put("startDayBegin", stopDayBegin);
			m.put("startDayEnd", stopDayEnd);
			m.put("nextDayBegin", stopDayBegin + dayLong);
			m.put("dayTime", stopStamp - stopDayBegin);
			m.put("nightTime", 0l);
		} // 24:00>23:00 s >19:00
		else if (stopDayEnd <= stopStamp) {
			m.put("case", 2l);
			m.put("startStamp", stopStamp);
			m.put("startDayBegin", stopDayBegin);
			m.put("startDayEnd", stopDayEnd);
			m.put("nextDayBegin", stopDayBegin + dayLong);
			m.put("dayTime", stopDayEnd - stopDayBegin);
			m.put("nightTime", stopStamp - stopDayEnd);
		} // s1:00 <7:00
		else if (stopStamp <= stopDayBegin) {
			m.put("case", 2l);
			m.put("startStamp", stopStamp);
			m.put("startDayBegin", stopDayBegin - dayLong);
			m.put("startDayEnd", stopDayEnd - dayLong);
			m.put("nextDayBegin", stopDayBegin);
			m.put("dayTime", stopDayEnd - stopDayBegin);
			// m.put("nightTime",stopStamp-stopDayEnd+dayLong);
			m.put("nightTime", stopStamp - (stopDayEnd - dayLong));
		}
		return m;
	}

	private static Map<String, Long> getStartSectionTimes(long startStamp, long startDayBegin, long startDayEnd) {
		long dayLong = 24 * 60 * 60 * 1000;

		Map<String, Long> m = new HashMap<>();
		// 1, 07:00=<startStamp<19:00
		if (startDayBegin <= startStamp && startStamp <= startDayEnd) {
			m.put("case", 1l);
			m.put("startStamp", startStamp);
			m.put("startDayBegin", startDayBegin);
			m.put("startDayEnd", startDayEnd);
			m.put("nextDayBegin", startDayBegin + dayLong);
			m.put("dayTime", startDayEnd - startStamp);
			m.put("nightTime", startDayBegin + dayLong - startDayEnd);
		} // 2, 19:00<startStamp<次07:00
			// 24:00> s >19:00
		else if (startDayEnd <= startStamp) {
			m.put("case", 2l);
			m.put("startStamp", startStamp);
			m.put("startDayBegin", startDayBegin);
			m.put("startDayEnd", startDayEnd);
			m.put("nextDayBegin", startDayBegin + dayLong);
			m.put("dayTime", 0l);
			m.put("nightTime", startDayBegin + dayLong - startStamp);
		}
		// 24：00< s1:00 <7:00
		else if (startStamp <= startDayBegin)

		{
			m.put("case", 2l);
			m.put("startStamp", startStamp);
			m.put("startDayBegin", startDayBegin - dayLong);
			m.put("startDayEnd", startDayEnd - dayLong);
			m.put("nextDayBegin", startDayBegin);
			m.put("dayTime", 0l);
			m.put("nightTime", startDayBegin - startStamp);
		}

		return m;
	}

	/**
	 * 获取某时间戳指定时间点时间戳
	 * 
	 * @param startDate
	 * @param endTime
	 * @return
	 */
	private static long getSectionTime(long startStamp, String endTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(startStamp);
		String[] times = endTime.split(":");
		cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(times[0]));
		cal.set(Calendar.MINUTE, Integer.valueOf(times[1]));
		cal.set(Calendar.SECOND, Integer.valueOf(times[2]));
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	// 分时段封顶计费计算
	private static Map<String, Object> getTOPSectionMap(StrategyBase base, Date startDate, Date stopDate) {
		double firstHour = base.getFirstHour().doubleValue();
		double basePrice = base.getBasePrice().doubleValue();
		double nightPrice = base.getNightPrice().doubleValue();
		double topDayTime = null == base.getTopDay() ? 0 : base.getTopDay() * 60;
		long topNightTime = null == base.getTopNight() ? 0 : base.getTopNight() * 60;// 秒
		int daySec = 24 * 60 * 60;
		Integer timelyLong = base.getTimelyLong() * 60;
		Integer nightTimelyLong = base.getNightTimelyLong() * 60;
		Map<String, Object> m = new HashMap<>();
		long freeTime = base.getFreeMins() * 60;
		// 停留总时长
		long resideTime = stopDate.getTime() / 1000 - startDate.getTime() / 1000 + freeTime;
		m.put("resideTime", resideTime);

		// 停车时间小于免费时长
		if (startDate.getTime() >= stopDate.getTime()) {
			m.put("day", 0l);
			m.put("night", 0l);
			m.put("dayAmount", 0.00d);
			m.put("nightAmount", 0.00d);
			m.put("totalAmount", 0.00d);
			return m;
		}
		Map<String, Long> timeMap = getAllSplitTimes(base, startDate, stopDate);
		// 计算夜间计费时间段时长
		long startStamp = startDate.getTime();
		long stopStamp = stopDate.getTime();
		// 07:00
		long timeDayBegin = getSectionTime(startStamp, base.getBeginTime());
		// 19:00
		long timeDayEnd = getSectionTime(startStamp, base.getEndTime());
		// 一个夜间计费时间段时长 单位：秒
		long oneNightUnit = (24 * 60 * 60 * 1000 - (timeDayEnd - timeDayBegin)) / 1000;
		long myCase = timeMap.get("case");
		// 1、价格统一
		if (firstHour == basePrice && topDayTime == 0) {
			if (myCase == 1l) {
				long dayTime = timeMap.get("day");
				long nightTime = timeMap.get("night");
				if (topNightTime > nightTime) {
					// double dayTimeFee = getFee(basePrice, timelyLong,
					// dayTime);//dayTime%timelyLong==0?dayTime/timelyLong*basePrice:(dayTime/timelyLong+1)*basePrice;
					// double nightTimeFee = getFee(nightPrice, nightTimelyLong,
					// nightTime);//nightTime%timelyLong==0?nightTime/timelyLong*basePrice:(nightTime/timelyLong+1)*basePrice;
					double totalFee = getFee(nightPrice, nightTimelyLong, nightTime + dayTime);// nightTime%timelyLong==0?nightTime/timelyLong*basePrice:(nightTime/timelyLong+1)*basePrice;

					m.put("day", dayTime);
					m.put("night", nightTime);
					// m.put("dayAmount", dayTimeFee);
					// m.put("nightAmount", nightTimeFee);
					// m.put("totalAmount", dayTimeFee+ nightTimeFee);
					m.put("dayAmount", 0d);
					m.put("nightAmount", 0d);
					m.put("totalAmount", totalFee);
				} else if (topNightTime <= nightTime) {
					double dayTimeFee = getFee(basePrice, timelyLong, dayTime);// dayTime%timelyLong==0?dayTime/timelyLong*basePrice:(dayTime/timelyLong+1)*basePrice;
					double nightTimeFee = getFee(nightPrice, nightTimelyLong, topNightTime);// topNightTime%timelyLong==0?topNightTime/timelyLong*basePrice:(topNightTime/timelyLong+1)*basePrice;

					m.put("day", dayTime);
					m.put("night", nightTime);
					m.put("dayAmount", dayTimeFee);
					m.put("nightAmount", nightTimeFee);
					m.put("totalAmount", dayTimeFee + nightTimeFee);
				}
			} else
			// 有整天
			{

				long days = timeMap.get("days");
				long dayTime1 = timeMap.get("dayTime1");
				long nightTime1 = timeMap.get("nightTime1");
				long dayTime2 = timeMap.get("dayTime2");
				long nightTime2 = timeMap.get("nightTime2");

				double dayTimeFee = 0d;
				double nightTimeFee = 0d;
				// if(topNightTime>nightTime1)
				// {
				// dayTimeFee =getFee(basePrice, timelyLong, dayTime1);
				// nightTimeFee = getFee(nightPrice, nightTimelyLong,
				// nightTime1);
				// }
				// else
				// {
				// dayTimeFee =getFee(basePrice, timelyLong, dayTime1);
				// nightTimeFee = getFee(basePrice, nightTimelyLong,
				// topNightTime);
				// }
				//
				// if(topNightTime>nightTime2)
				// {
				// dayTimeFee +=getFee(basePrice, timelyLong, dayTime2);
				// nightTimeFee += getFee(nightPrice, nightTimelyLong,
				// nightTime2);
				// }
				// else
				// {
				// dayTimeFee +=getFee(basePrice, timelyLong, dayTime2);
				// nightTimeFee += getFee(nightPrice, nightTimelyLong,
				// topNightTime);
				// }
				// dayTimeFee += getFee(basePrice, timelyLong,
				// dayTime1+dayTime2);
				// 大于一个夜间单位分开两端计算
				if (nightTime1 + nightTime2 > oneNightUnit) {
					nightTimeFee += getFee(nightPrice, nightTimelyLong, topNightTime);
					// 剩余大于封顶时长
					if (topNightTime <= ((nightTime1 + nightTime2) - oneNightUnit)) {
						nightTimeFee += getFee(nightPrice, nightTimelyLong, topNightTime);
						dayTimeFee += getFee(basePrice, timelyLong, dayTime1 + dayTime2);
					} else {
						nightTimeFee += getFee(nightPrice, nightTimelyLong,
								(nightTime1 + nightTime2) - oneNightUnit + dayTime1 + dayTime2);
						// dayTimeFee += getFee(basePrice, timelyLong,
						// dayTime1+dayTime2);
					}
				} else {
					if (topNightTime <= (nightTime1 + nightTime2)) {
						nightTimeFee += getFee(nightPrice, nightTimelyLong, topNightTime);
						dayTimeFee += getFee(basePrice, timelyLong, dayTime1 + dayTime2);
					} else {
						nightTimeFee += getFee(nightPrice, nightTimelyLong,
								nightTime1 + nightTime2 + dayTime1 + dayTime2);
					}
				}
				Integer n = days > 0 ? ((Long) (days / daySec)).intValue() : 0;
				long startDayEnd = timeMap.get("startDayEnd");
				long startDayBegin = timeMap.get("startDayBegin");
				long nDayTime = (startDayEnd - startDayBegin) * n;
				long nNightTime = days * n - nDayTime;

				dayTimeFee += getFee(basePrice, timelyLong, nDayTime);
				// 夜间封顶价格
				nightTimeFee += n * getFee(nightPrice, nightTimelyLong, topNightTime);

				m.put("day", dayTime1 + dayTime2 + nDayTime);
				m.put("night", nightTime1 + nightTime2 + nNightTime);
				m.put("dayAmount", dayTimeFee);
				m.put("nightAmount", nightTimeFee);
				m.put("totalAmount", dayTimeFee + nightTimeFee);
			}
		} else {
			System.err.println("由于计费策略价格不统一 或白天有计费封顶价格，暂时未实现该业务");
		}

		return m;
	}

	/*
	 * 计算订单价格 <p>1.获取订单 开始时间和结束时间， <b> 停车时长 eb= 结束时间（end） - 开始时间（begin）</p>
	 * <p>1.1停车时间在一天内</p> <p>1.1.1开始结束在白天时间段，或者在夜晚时间段内</p> eb
	 * <p>1.1.2开始结束在白天开始时间， 夜晚结束时间段内</p> <p>1.1.3开始结束在白天结束时间， 夜晚开始时间段内</p>
	 * <p>1.2 停车时长大于一天时间</p> 24<eb=end - begin (eb/24)*(day+night)+
	 * 
	 * 回到1过程计算 begin+(eb%24)
	 * 
	 * 
	 * 并计算开始时间在是否在白天计费时间内，结束时间是否在
	 */
	/**
	 * 无封顶计费计算
	 * 
	 * @param base
	 * @param startDate
	 * @param stopDate
	 * @return Map<String, Object>
	 * 
	 *         key value remark day Long 白天计费时长 night Long 夜间计费时长 dayAmount
	 *         double 白天消费金额 nightAmount double 夜间消费金额 totalAmount double 总消费金额
	 *         resideTime Long 时间停留时长 freeTime Integer 免费停留时长
	 * 
	 */
	public static Map<String, Object> getParkingCost(StrategyBase base, Date startDate, Date stopDate) {
		Map<String, Object> costMap = new HashMap<String, Object>();
		Double firstHour = base.getFirstHour().doubleValue();// 首小时内计费价
		Double basePrice = base.getBasePrice().doubleValue();// 基本计价-- 首小时外的计费价
		Integer timelyLong = base.getTimelyLong() * 60;// 白天时间基数
		Integer nightTimelyLong = base.getNightTimelyLong() * 60;// 夜间时间基数
		Double nightPrice = base.getNightPrice().doubleValue();

		int daySec = 24 * 60 * 60;
		Map<String, Object> m = new HashMap<>();
		long freeTime = base.getFreeMins() * 60;
		// 停车总时长
		long resideTime = stopDate.getTime() / 1000 - startDate.getTime() / 1000 + freeTime;
		m.put("resideTime", resideTime);
		// 停车时间小于免费时长
		if (startDate.getTime() >= stopDate.getTime()) {
			m.put("day", 0l);
			m.put("night", 0l);
			m.put("dayAmount", 0.00d);
			m.put("nightAmount", 0.00d);
			m.put("totalAmount", 0.00d);
			return m;
		}
		Map<String, Long> timeMap = getAllSplitTimes(base, startDate, stopDate);
		// 1停车开始结束时间为同一个计费时间段 2 停车时间垮天
		long myCase = timeMap.get("case");
		long startCase = timeMap.get("startCase");
		// startCase为1是则停车开始时间为白天 否则停车开始时间为夜间
		// 白天是计算首小时计费
		long days = timeMap.get("days");
		long day = timeMap.get("day");
		long night = timeMap.get("night");
		long dayTime1 = timeMap.get("dayTime1");
		long nightTime1 = timeMap.get("nightTime1");
		long dayTime2 = timeMap.get("dayTime2");
		long nightTime2 = timeMap.get("nightTime2");
		long dayTotal = days + day + dayTime1 + dayTime2;
		long nightTotal = night + nightTime1 + nightTime2;
		double dayAmount = 0.0d;
		double nightAmount = 0.0d;
		// 停车开始结束时间为同一个计费时间段
		if (1l == myCase) {
			// 停车开始时间为白天
			if (1l == startCase) {
				if (day >= 1 * 60 * 60) {
					dayAmount += getFee(firstHour, timelyLong, (1 * 60 * 60));
					dayAmount += getFee(basePrice, timelyLong, day - (1 * 60 * 60));
				} else {
					dayAmount += getFee(firstHour, timelyLong, day);
				}
			}
		} else {
			// 停车时间垮天
			// 停车开始时间为白天
			if (1l == startCase) {
				if (dayTime1 >= 1 * 60 * 60) {
					dayAmount += getFee(firstHour, timelyLong, (1 * 60 * 60));
					dayAmount += getFee(basePrice, timelyLong, dayTime1 - (1 * 60 * 60));
					if (dayTime2 >= 1 * 60 * 60) {
						dayAmount += getFee(firstHour, timelyLong, (1 * 60 * 60));
						dayAmount += getFee(basePrice, timelyLong, dayTime2 - (1 * 60 * 60));
					} else {
						dayAmount += getFee(basePrice, timelyLong, dayTime2);
					}
				} else {
					dayAmount += getFee(firstHour, timelyLong, dayTime1);
					// 第二天计费
					if (dayTime2 >= 1 * 60 * 60) {
						dayAmount += getFee(firstHour, timelyLong, (1 * 60 * 60));
						dayAmount += getFee(basePrice, timelyLong, dayTime2 - (1 * 60 * 60));
					} else {
						dayAmount += getFee(basePrice, timelyLong, dayTime2);
					}
				}
			} else {
				if (dayTime2 >= 1 * 60 * 60) {
					dayAmount += getFee(firstHour, timelyLong, (1 * 60 * 60));
					dayAmount += getFee(basePrice, timelyLong, dayTime2 - (1 * 60 * 60));
				} else {
					dayAmount += getFee(basePrice, timelyLong, dayTime2);
				}
			}

		}
		nightAmount += getFee(nightPrice, nightTimelyLong, nightTotal);
		long nDay = days / daySec;
		long startDayBegin = timeMap.get("startDayBegin");
		long startDayEnd = timeMap.get("startDayEnd");
		long oneDayTime = startDayEnd - startDayBegin;
		long oneNightTime = daySec - oneDayTime;
		double oneDayFee = getFee(firstHour, timelyLong, (1 * 60 * 60))
				+ getFee(firstHour, timelyLong, oneDayTime - (1 * 60 * 60));
		double oneNightFee = getFee(firstHour, nightTimelyLong, oneNightTime);
		dayAmount += nDay * oneDayFee;
		nightAmount += nDay * oneNightFee;
		costMap.put("day", dayTotal);
		costMap.put("night", nightTotal);
		costMap.put("dayAmount", dayAmount);
		costMap.put("nightAmount", nightAmount);
		costMap.put("totalAmount", (dayAmount + nightAmount));
		costMap.put("resideTime", resideTime);
		costMap.put("freeTime", freeTime);

		return costMap;
	}

}
