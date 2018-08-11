package cn.linkmore.prefecture.fee;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.linkmore.prefecture.entity.StrategyBase;

public class GzWdl {
	static String beginTime;
	static String endTime;
	static Integer freeMins; // （单位分钟）
	static BigDecimal firstHour; // 价格（单位）
	static BigDecimal basePrice; // 白天价格（单位元）
	static BigDecimal nightPrice; // 夜间价格 （单位元）
	static Integer timelyLong; // （单位分钟）
	static Integer nightTimelyLong; // （单位分钟）
	static Integer topDaily; // 全天封顶价格
	static Integer topNight; // 夜间封顶价格
	static Integer topDay; // 白天封顶价格没有则不计算

	public static void main(String[] args) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			StrategyBase base = new StrategyBase();

			base.setFreeMins(15);
			base.setBeginTime("00:00:00");
			base.setEndTime("10:00:00");
			base.setTimelyLong(60);
			base.setBasePrice(new BigDecimal(6));
			base.setNightPrice(new BigDecimal(8));
			base.setFirstHour(new BigDecimal(28));
			base.setNightTimelyLong(60);
			base.setTopNight(10);
			base.setTopDaily(400);

			String startString = "2018-05-24 08:20:00";
			String endString = "2018-05-24 19:06:00";
			Date startDate = sdf.parse(startString);
			Date endDate = sdf.parse(endString);

			System.out.println(getBilling(base, startDate, endDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, Object> getBilling(StrategyBase base, Date start, Date stop)  {

		beginTime = base.getBeginTime();
		endTime = base.getEndTime();
		freeMins = base.getFreeMins(); // （单位分钟）
		firstHour = base.getFirstHour(); // 价格（单位）
		basePrice = base.getBasePrice(); // 白天价格（单位元）
		nightPrice = base.getNightPrice(); // 夜间价格 （单位元）
		timelyLong = base.getTimelyLong(); // （单位分钟）
		nightTimelyLong = base.getNightTimelyLong(); // （单位分钟）
		topDaily = base.getTopDaily(); // 全天封顶价格
		topNight = base.getTopNight(); // 夜间封顶价格

		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

		Long stopDate = stop.getTime();
		Long startDate = start.getTime();

		System.out.println("实际停车时间为" + (stopDate / 60000 - startDate / 60000));

		// 转换为时分秒后的停车时间(11:00:00);
		String startDate1 = formatter.format(startDate);
		String stopDate1 = formatter.format(stopDate);

		BigDecimal b1 = new BigDecimal(startDate);
		BigDecimal b2 = new BigDecimal(stopDate);

		float day = b2.subtract(b1).floatValue() / (1000 * 60 * 60 * 24);

		// 跨越天数
		float day1 = ((float) Math.round(day * 100) / 100);
		Long day2 = (stopDate - startDate) / (1000 * 60 * 60 * 24); // 向下取整的天数

		float xx = day1; // 正好为整数天 所加天数减1
		int yy = (int) xx;
		if (xx - (float) yy == 0) {
			if (day2 >= 1) {
				 // 24小时不算跨天
			}
		}

		System.out.println(day + "---" + day1 + "----跨天=》" + day2);

		Long daytime = 0L; // 停车总白天
		Long nighttime = 0L; // 停车总夜晚

		Long dealDayTime = 0L; // 零散时间段白 不包括第二段
		Long dealNightTime = 0L; // 零散时间段黑 不包括第二段

		Long AAA = 0L; // 零散时间段白 包括第二段
		Long BBB = 0L; // 零散时间段黑 包括第二段

		Long bday = null;
		bday = tampValue(beginTime, endTime);
		Long bnight = 1000 * 60 * 60 * 24 - bday; // 设置的夜晚区段时间(毫秒)

		int where = 0; // 首小时所在区间

	
		 if( !stopDate.equals( startDate)  ) {
		// 计算停车时间
		if (isIn(startDate1, beginTime, endTime) && isIn(stopDate1, beginTime, endTime)) {
			System.out.println("开始时间在白天，结束时间在白天");

			// 经历1个白天
			if (!isTwo(startDate1, stopDate1)) {
				System.out.println(tampValue(startDate1, stopDate1) );
				daytime = tampValue(startDate1, stopDate1) + day2 * bday; // 白天时长
				nighttime = 0l + day2 * bnight; // 夜间时长

				AAA = tampValue(startDate1, stopDate1);
				BBB = 0L;

			} else {// 经历2个白天
				daytime = tampValue(startDate1, endTime) + tampValue(beginTime, stopDate1) + day2 * bday;
				nighttime = bnight + day2 * bnight;

				AAA = tampValue(startDate1, endTime) + tampValue(beginTime, stopDate1);
				BBB = bnight;

			}
		} else if (isIn(startDate1, beginTime, endTime) && !isIn(stopDate1, beginTime, endTime)) {
			System.out.println("开始时间在白天，结束时间在夜间");
			daytime = tampValue(startDate1, endTime) + day2 * bday;
			nighttime = tampValue(endTime, stopDate1) + day2 * bnight;

			AAA = tampValue(startDate1, endTime);
			BBB = tampValue(endTime, stopDate1);

		} else if (!isIn(startDate1, beginTime, endTime) && !isIn(stopDate1, beginTime, endTime)) {
			System.out.println("开始时间在夜间，结束时间在夜间");
			// 经历1个黑天
			if (!isTwo(startDate1, stopDate1)) {
				daytime = 0l + day2 * bday;
				nighttime = tampValue(startDate1, stopDate1) + day2 * bnight;

				AAA = 0L;
				BBB = tampValue(startDate1, stopDate1);

			} else {// 经历2个黑天
				daytime = bday + day2 * bday;
				nighttime = tampValue(startDate1, beginTime) + tampValue(stopDate1, endTime) + day2 * bnight;

				AAA = bday;
				BBB = tampValue(startDate1, beginTime) + tampValue(stopDate1, endTime);

			}
		} else if (!isIn(startDate1, beginTime, endTime) && isIn(stopDate1, beginTime, endTime)) {
			System.out.println("开始时间在夜间，结束时间在白天");

			daytime = tampValue(beginTime, stopDate1) + day2 * bday;
			nighttime = tampValue(startDate1, beginTime) + day2 * bnight;

			AAA = tampValue(beginTime, stopDate1);
			BBB = tampValue(startDate1, beginTime);

		}
	 }
		// 计费
		Map<String, Object> result = deail(daytime, nighttime, day2, bday, bnight, AAA, BBB);

		map.put("day", Math.round( (double) daytime / 60000));
		map.put("night", Math.round( (double) nighttime / 60000));
		map.put("dayAmount", result.get("dayAmount"));
		map.put("totalAmount", result.get("countAmount"));
		map.put("nightAmount", result.get("nightAmount"));
		return map;

	}

	public static Map<String, Object> deail(Long daytime, Long nighttime, long cross, Long bday, Long bnight, Long AAA,
			Long BBB) {

		Map<String, Object> map = new HashMap();

		BigDecimal dayAmount = null;
		BigDecimal nightAmount = null;
		BigDecimal countAmount = null;

		BigDecimal AlldayAmount = new BigDecimal(0.00);
		BigDecimal AnightAmount = new BigDecimal(0.00);

		Long alltime = (daytime + nighttime); // 总停车时间(单位分钟)
		
		System.out.println( "计算的总时间（ms）"+alltime );
		System.out.println( "计算的白天分钟--"+ Math.round( (double) daytime / 60000) +"计算的夜间分钟"+ Math.round( (double) nighttime / 60000));
		
		bday =Math.round( (double) bday / 60000)  ; // 白天区段分钟		
		bnight =Math.round( (double) bnight / 60000); // 夜间区段分钟
		
		Long Day =Math.round( (double) AAA / (1000 * 60)); // 零散白天时长（单位分钟）
		Long Night =Math.round( (double) BBB / (1000 * 60)); // 零散夜间时长（单位分钟）
		

		// 1总时间与免费时长比较
		if (alltime < freeMins*1000 * 60) {
			dayAmount = new BigDecimal(0.00).setScale(2);
			nightAmount = new BigDecimal(0.00).setScale(2);
			countAmount = dayAmount.add(nightAmount);
			// 1总时间与免费时长比较
		} else if (alltime >= freeMins*1000 * 60) {
			
			dayAmount = basePrice.multiply(new BigDecimal(Math.ceil((double) Day / (double) timelyLong))).setScale(2);
			nightAmount = nightPrice.multiply(new BigDecimal(Math.ceil((double) Night / (double) nightTimelyLong)))
					.setScale(2);
			
			// 全天白
			BigDecimal A = basePrice.multiply(new BigDecimal(Math.ceil((double) bday / (double) timelyLong)))
					.setScale(2);
			// 全天夜
			BigDecimal B = nightPrice.multiply(new BigDecimal(Math.ceil((double) bnight / (double) nightTimelyLong)))
					.setScale(2);
			
			
			// 零散的总金额大于封顶
						if (dayAmount.add(nightAmount).compareTo(new BigDecimal(topDaily)) == 1) {
							//零散白天与封顶夜间小于全天封顶
							if(dayAmount.add(new BigDecimal(topNight)).compareTo(new BigDecimal(topDaily)) == -1) {
								
								countAmount = dayAmount.add(new BigDecimal(topNight)
										.add(new BigDecimal(topDaily).multiply(new BigDecimal(cross)).setScale(2)));
							}else {
								countAmount = new BigDecimal(topDaily).multiply(new BigDecimal(cross + 1)).setScale(2);
							}
							// 零散的不大于封顶
						} else {
							// 全天24小时大于峰顶
							if (A.add(B).compareTo(new BigDecimal(topDaily)) == 1) {
								// 零散夜间大于封顶
								if (nightAmount.compareTo(new BigDecimal(topNight)) == 1) {
									// 封顶零散夜间+实际零散白天+ 24小时封顶
									countAmount = dayAmount.add(new BigDecimal(topNight)
											.add(new BigDecimal(topDaily).multiply(new BigDecimal(cross)).setScale(2)));
									// 零散夜间不大于封顶
								} else {
									// 实际零散夜间+实际零散白天+ 24小时封顶
									countAmount = dayAmount.add(nightAmount)
											.add(new BigDecimal(topDaily).multiply(new BigDecimal(cross)).setScale(2));
								}
								// 全天24小时小于封顶
							} else {
								// 零散夜间大于封顶
								if (nightAmount.compareTo(new BigDecimal(topNight)) == 1) {
									// 封顶零散夜间*（n+1）+实际零散白天
									countAmount = dayAmount.add(A.multiply(new BigDecimal(cross)).setScale(2))
											.add(new BigDecimal(topNight).multiply(new BigDecimal(cross + 1)).setScale(2));
									// 零散夜间不大于封顶
								} else {
									//夜间大于封顶
									if(B.compareTo(new BigDecimal(topNight)) == 1) {
										// 实际零散夜间+实际零散白天+实际24小时
										countAmount = dayAmount.add(nightAmount)
												.add(A.add(new BigDecimal(topNight)).multiply(new BigDecimal(cross)).setScale(2)        );
									}else {
										// 实际零散夜间+实际零散白天+实际24小时
										countAmount = dayAmount.add(nightAmount)
												.add(A.add(B).multiply(new BigDecimal(cross)).setScale(2));
									}
								}
							}
						}
						
						if(countAmount.compareTo(firstHour)==-1 ) {
							countAmount = firstHour;
						}
/*
			dayAmount = basePrice.multiply(new BigDecimal(Day / timelyLong)).setScale(2);
			nightAmount = nightPrice.multiply(new BigDecimal(Night / nightTimelyLong)).setScale(2);
			residual = Day % timelyLong + Night % timelyLong;

			// 模计算
			if (residual >= timelyLong && residual >= nightTimelyLong) {
				if (basePrice.compareTo(nightPrice) == 1) {
					dayAmount = dayAmount.add(new BigDecimal(timelyLong));
				} else {
					nightAmount = nightAmount.add(new BigDecimal(nightTimelyLong));
				}
			} else if (residual >= timelyLong && residual < nightTimelyLong) {
				dayAmount = dayAmount.add(new BigDecimal(timelyLong));
			} else if (residual < timelyLong && residual >= nightTimelyLong) {
				nightAmount = nightAmount.add(new BigDecimal(nightTimelyLong));
			}

			// 全天白
			BigDecimal A = basePrice.multiply(new BigDecimal(bday / timelyLong)).setScale(2);
			// 全天夜
			BigDecimal B = nightPrice.multiply(new BigDecimal(bnight / nightTimelyLong)).setScale(2);

			residual = bday % timelyLong + bnight % timelyLong;

			if (residual >= timelyLong && residual >= nightTimelyLong) {
				if (basePrice.compareTo(nightPrice) == 1) {
					A = A.add(new BigDecimal(timelyLong));
				} else {
					B = B.add(new BigDecimal(nightTimelyLong));
				}
			} else if (residual >= timelyLong && residual < nightTimelyLong) {
				A = A.add(new BigDecimal(timelyLong));
			} else if (residual < timelyLong && residual >= nightTimelyLong) {
				B = B.add(new BigDecimal(nightTimelyLong));
			}

			// 零散夜时间大于封顶 夜间费用为封顶值
			if (nightAmount.compareTo(new BigDecimal(topNight)) == 1) {
				AnightAmount = new BigDecimal(topNight).setScale(2).multiply(new BigDecimal(cross + 1));
				// 24小时零散时间内为封顶 白天最大值只能为 封顶值减去夜间封顶值
				if (dayAmount.add(nightAmount).compareTo(new BigDecimal(topDaily)) == 1) {
					AlldayAmount = new BigDecimal(topDaily).subtract(nightAmount).setScale(2)
							.multiply(new BigDecimal(cross + 1));
				} else {
					// 全天计时为封顶值
					if (A.add(B).compareTo(new BigDecimal(topDaily)) == 1) {
						AlldayAmount = dayAmount.add(new BigDecimal(topDaily).subtract(nightAmount).setScale(2)
								.multiply(new BigDecimal(cross)));
					} else {
						AlldayAmount = dayAmount.add(A.setScale(2).multiply(new BigDecimal(cross)));
					}
				}
			} else if (nightAmount.compareTo(new BigDecimal(topNight)) == -1 ||nightAmount.compareTo(new BigDecimal(topNight)) == 0 ) { // 零散夜间时间没大于封顶值
				if (B.compareTo(new BigDecimal(topNight)) == -1 || B.compareTo(new BigDecimal(topNight)) == 0) { // 整个夜间没大于封顶值
					if (dayAmount.add(nightAmount).compareTo(new BigDecimal(topDaily)) == 1) { // 零散总时间大于封顶值
						AnightAmount = nightAmount.multiply(new BigDecimal(cross + 1));
						AlldayAmount = new BigDecimal(topDaily).subtract(nightAmount).setScale(2)
								.multiply(new BigDecimal(cross + 1));
					} else {// 零散总时间小于封顶值
						if (A.add(B).compareTo(new BigDecimal(topDaily)) == 1) { // 全天大于封顶值
							AnightAmount = nightAmount.add(B.multiply(new BigDecimal(cross)));
							AlldayAmount = dayAmount
									.add((new BigDecimal(topDaily)).subtract(B).multiply(new BigDecimal(cross)));
						} else { // 全天小于封顶值
							AnightAmount = nightAmount.add(B.multiply(new BigDecimal(cross)));
							AlldayAmount = dayAmount.add(A.multiply(new BigDecimal(cross)));
						}
					}
				} else if (B.compareTo(new BigDecimal(topNight)) == 1) { // 整个夜间大于封顶值
					if (dayAmount.add(nightAmount).compareTo(new BigDecimal(topDaily)) == 1) { // 零散总时间大于封顶值
						AnightAmount = nightAmount.multiply(new BigDecimal(cross + 1));
						AlldayAmount = new BigDecimal(topDaily).subtract(nightAmount).setScale(2)
								.multiply(new BigDecimal(cross + 1));
					} else {// 零散总时间小于封顶值
						if (A.add(B).compareTo(new BigDecimal(topDaily)) == 1) { // 全天大于封顶值
							AnightAmount = nightAmount.add(new BigDecimal(topDaily).multiply(new BigDecimal(cross)));
							AlldayAmount = dayAmount.add((new BigDecimal(topDaily)).subtract(new BigDecimal(topDaily))
									.multiply(new BigDecimal(cross)));
						} else { // 全天小于封顶值
							AnightAmount = nightAmount.add(B.multiply(new BigDecimal(cross)));
							AlldayAmount = dayAmount.add(A.multiply(new BigDecimal(cross)));
						}
					}
				}
			}
			countAmount = AlldayAmount.add(AnightAmount); // 零散的总时间
*/		
		}
		System.out.println("总金额"+countAmount);
		map.put("dayAmount", AlldayAmount);
		map.put("nightAmount", AnightAmount);
		map.put("countAmount", countAmount);

		return map;
	}

	// 是否跨2段
	public static boolean isTwo(String bg, String ed) {
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		boolean isTwo = false;
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = df.parse(bg);
			 d2 = df.parse(ed);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long ts = d1.getTime() - d2.getTime();
		if (ts > 0) {
			isTwo = true;
		}
		return isTwo;
	}

	// 结束与开始时段之间的时间（毫秒）
	public static long tampValue(String s2, String s1)  {
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = df.parse(s1);
			d2 = df.parse(s2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long ts = d1.getTime() - d2.getTime();
		if (ts < 0) {
			ts = 1000 * 60 * 60 * 24 - Math.abs(ts);
		} else if (ts == 0) {
			ts = 0;
		}
		return ts;
	}

	public static boolean isIn(String nowTime1, String startTime1, String endTime1) {

		String format = "HH:mm:ss";
		Date nowTime = null;
		Date startTime = null;
		Date endTime = null;
		if("00:00:00".equals( endTime1  ) ) {
			endTime1 = "24:00:00";
		}
		try {
			nowTime = new SimpleDateFormat(format).parse(nowTime1);
			startTime = new SimpleDateFormat(format).parse(startTime1);
			endTime = new SimpleDateFormat(format).parse(endTime1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
			return true;
		}

		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(startTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}

	public static Map<String, Object> getBilling1(StrategyBase base, Long startDate, Long stopDate)
			throws ParseException {

		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

		System.out.println("实际停车时间为" + (stopDate / 60000 - startDate / 60000));

		// 转换为时分秒后的停车时间(11:00:00);
		String startDate1 = formatter.format(startDate);
		String stopDate1 = formatter.format(stopDate);

		BigDecimal b1 = new BigDecimal(startDate);
		BigDecimal b2 = new BigDecimal(stopDate);

		float day = b2.subtract(b1).floatValue() / (1000 * 60 * 60 * 24);

		// 跨越天数
		float day1 = ((float) Math.round(day * 100) / 100);
		Long day2 = (stopDate - startDate) / (1000 * 60 * 60 * 24); // 向下取整的天数

		float xx = day1; // 正好为整数天 所加天数减1
		int yy = (int) xx;
		if (xx - (float) yy == 0) {
			if (day2 >= 1) {
				day2--; // 24小时不算跨天
			}
		}

		System.out.println(day + "---" + day1 + "----跨天=》" + day2);

		Long daytime = 0L; // 停车总白天
		Long nighttime = 0L; // 停车总夜晚

		Long dealDayTime = 0L; // 零散时间段白 不包括第二段
		Long dealNightTime = 0L; // 零散时间段黑 不包括第二段

		Long AAA = 0L; // 零散时间段白 包括第二段
		Long BBB = 0L; // 零散时间段黑 包括第二段

		Long bday = tampValue(beginTime, endTime); // 设置的白天区段时间(毫秒)
		Long bnight = 1000 * 60 * 60 * 24 - bday; // 设置的夜晚区段时间(毫秒)

		int where = 0; // 首小时所在区间

		if (startDate1 == stopDate1) {
			return map;
		}

		// 计算停车时间
		if (isIn(startDate1, beginTime, endTime) && isIn(stopDate1, beginTime, endTime)) {
			System.out.println("开始时间在白天，结束时间在白天");
			where = 1;
			// 经历1个白天
			if (!isTwo(startDate1, stopDate1)) {
				daytime = tampValue(startDate1, stopDate1) + day2 * bday; // 白天时长
				nighttime = 0l + day2 * bnight; // 夜间时长

				dealDayTime = tampValue(startDate1, stopDate1);
				dealNightTime = 0L;

				AAA = dealDayTime;
				BBB = dealNightTime;

			} else {// 经历2个白天
				daytime = tampValue(startDate1, endTime) + tampValue(beginTime, stopDate1) + day2 * bday;
				nighttime = bnight + day2 * bnight;

				dealDayTime = tampValue(startDate1, endTime);
				dealNightTime = bnight;

				AAA = tampValue(startDate1, endTime) + tampValue(beginTime, stopDate1);
				BBB = bnight;

			}
		} else if (isIn(startDate1, beginTime, endTime) && !isIn(stopDate1, beginTime, endTime)) {
			System.out.println("开始时间在白天，结束时间在夜间");
			where = 1;
			daytime = tampValue(startDate1, endTime) + day2 * bday;
			nighttime = tampValue(endTime, stopDate1) + day2 * bnight;

			dealDayTime = tampValue(startDate1, endTime);
			dealNightTime = tampValue(endTime, stopDate1);

			AAA = tampValue(startDate1, endTime);
			BBB = tampValue(endTime, stopDate1);

		} else if (!isIn(startDate1, beginTime, endTime) && isIn(stopDate1, beginTime, endTime)) {
			System.out.println("开始时间在夜间，结束时间在夜间");
			where = 2;
			// 经历1个黑天
			if (!isTwo(startDate1, stopDate1)) {
				daytime = 0l + day2 * bday;
				nighttime = tampValue(startDate1, stopDate1) + day2 * bnight;

				dealDayTime = 0L;
				dealNightTime = tampValue(startDate1, stopDate1);

				AAA = dealDayTime;
				BBB = tampValue(startDate1, stopDate1);

			} else {// 经历2个黑天
				daytime = bday + day2 * bday;
				nighttime = tampValue(startDate1, beginTime) + tampValue(stopDate1, endTime) + day2 * bnight;

				dealDayTime = bday;
				dealNightTime = tampValue(startDate1, beginTime);

				AAA = bday;
				BBB = tampValue(startDate1, beginTime) + tampValue(stopDate1, endTime);

			}
		} else if (!isIn(startDate1, beginTime, endTime) && !isIn(stopDate1, beginTime, endTime)) {
			System.out.println("开始时间在夜间，结束时间在白天");
			where = 2;
			daytime = tampValue(beginTime, stopDate1) + day2 * bday;
			nighttime = tampValue(startDate1, beginTime) + day2 * bnight;

			dealDayTime = tampValue(beginTime, stopDate1);
			dealNightTime = tampValue(startDate1, beginTime);

		}
		System.out.println("计算的停车时间为" + "白>" + daytime / 60000 + "夜>" + nighttime / 60000 + "共>"
				+ (daytime / 60000 + nighttime / 60000));

		// 计费
		Map<String, Object> result = deail(daytime, nighttime, day2, bday, bnight, AAA, BBB);

		map.put("day", daytime / 60000);
		map.put("night", nighttime / 60000);
		map.put("dayAmount", result.get("dayAmount"));
		map.put("totalAmount", result.get("countAmount"));
		map.put("nightAmount", result.get("nightAmount"));
		return map;

	}

	public static Map<String, Object> deailHaveFirst(Long daytime, Long nighttime, long Day2, int who, Long baitian,
			Long heitian, Long bday, Long bnight, Long AAA, Long BBB) {

		AAA = AAA / 60000;
		BBB = BBB / 60000;

		Map<String, Object> map = new HashMap();

		BigDecimal dayAmount = null;
		BigDecimal nightAmount = null;
		BigDecimal countAmount = null;

		bday = bday / 60000; // 白天区段分钟
		bnight = bnight / 60000; // 夜间区段分钟

		Long alltime = (daytime + nighttime) / (1000 * 60); // 总停车时间(单位分钟)
		Long Day = baitian / (1000 * 60); // 零散白天时长（单位分钟）
		Long Night = heitian / (1000 * 60); // 零散夜间时长（单位分钟）
		Long residual = 0L;

		System.out.println("-------计费--------");
		System.out.println("总白天" + daytime / 60000 + "总夜间" + nighttime / 60000);
		System.out.println("零散白天" + Day + "零散夜间" + Night);

		// 1总时间与免费时长比较
		if (alltime < freeMins) {
			dayAmount = new BigDecimal(0.00).setScale(2);
			nightAmount = new BigDecimal(0.00).setScale(2);
			countAmount = dayAmount.add(nightAmount);
			// 1总时间与免费时长比较
		} else if (alltime >= freeMins) {

			// 2 小于一小时
			if (alltime <= 60) {
				dayAmount = basePrice.multiply(new BigDecimal(Day / timelyLong)).setScale(2);
				nightAmount = basePrice.multiply(new BigDecimal(Night / timelyLong)).setScale(2);
				residual = Day % timelyLong + Night % timelyLong;

				// 2大于一小时
			} else if (alltime > 60) {
				// 3首小时在白天
				if (who == 1) {
					// 4白天大于一小时
					if (Day > 60) {
						dayAmount = firstHour.multiply(new BigDecimal(60 / timelyLong)).setScale(2)
								.add(basePrice.multiply(new BigDecimal((AAA - 60) / timelyLong)).setScale(2));

						nightAmount = nightPrice.multiply(new BigDecimal(Night / nightTimelyLong)).setScale(2);

						residual = 60 % timelyLong + (AAA - 60) % timelyLong + (Night % nightTimelyLong);

						// 4白天小于一小时
					} else if (Day < 60) {

						dayAmount = firstHour.multiply(new BigDecimal(Day / timelyLong)).setScale(2)
								.add(basePrice.multiply(new BigDecimal((AAA - Day) / timelyLong)).setScale(2));

						nightAmount = firstHour.multiply(new BigDecimal((60 - Day) / timelyLong)).setScale(2)
								.add(nightPrice.multiply(new BigDecimal((Night - 60 - Day) / nightTimelyLong)))
								.setScale(2);

						residual = Day % timelyLong + (AAA - Day) % timelyLong + ((60 - Day) % nightTimelyLong)
								+ (Night - 60 - Day) % nightTimelyLong;

					}
					// 3首小时在夜间时间
				} else if (who == 2) {
					// 4黑天大于一小时
					if (Night >= 60) {

						nightAmount = firstHour.multiply(new BigDecimal(60 / timelyLong)).setScale(2)
								.add(nightPrice.multiply(new BigDecimal((BBB - 60) / nightTimelyLong))).setScale(2);

						dayAmount = basePrice.multiply(new BigDecimal(Day / timelyLong)).setScale(2);

						residual = 60 % timelyLong + (BBB - 60) % timelyLong + (Day % nightTimelyLong);

						// 4黑天小于一小时
					} else if (Night < 60) {

						nightAmount = firstHour.multiply(new BigDecimal(Night / timelyLong)).setScale(2)
								.add(nightPrice.multiply(new BigDecimal((BBB - Night) / nightTimelyLong)).setScale(2));

						dayAmount = firstHour.multiply(new BigDecimal((60 - Night) / timelyLong)).setScale(2)
								.add(basePrice.multiply(new BigDecimal((AAA - (60 - Night)) / timelyLong))).setScale(2);

						residual = Night % timelyLong + (BBB - Night) % nightTimelyLong + ((60 - Night) % timelyLong)
								+ (AAA - (60 - Night)) % timelyLong;

					}
				}
			}
		}

		// 模计算
		if (residual >= timelyLong && residual >= nightTimelyLong) {
			if (basePrice.compareTo(nightPrice) == 1) {
				dayAmount.add(new BigDecimal(timelyLong));
			} else {
				nightAmount.add(new BigDecimal(nightTimelyLong));
			}
		} else if (residual >= timelyLong && residual < nightTimelyLong) {
			dayAmount.add(new BigDecimal(timelyLong));
		} else if (residual < timelyLong && residual >= nightTimelyLong) {
			nightAmount.add(new BigDecimal(nightTimelyLong));
		}

		countAmount = dayAmount.add(nightAmount); // 零散的总时间

		countAmount = dayAmount.add(nightAmount);

		// 零散的全天封顶
		int b = countAmount.compareTo(new BigDecimal(topDaily));

		if (b == 1) {

		} else {
			// 零散封顶处理
			if (dayAmount.compareTo(new BigDecimal(topDay)) == 1 && topDay != -1) {
				dayAmount = new BigDecimal(topDay).setScale(2);
			}
			// 零散封顶处理
			if (nightAmount.compareTo(new BigDecimal(topNight)) == -1 && topNight != -1) {
				nightAmount = new BigDecimal(topNight).setScale(2);
			}
		}

		// 全天白
		BigDecimal A = basePrice.multiply(new BigDecimal(bday / timelyLong)).setScale(2);
		// 全天夜
		BigDecimal B = nightPrice.multiply(new BigDecimal(bnight / nightTimelyLong)).setScale(2);

		int a = (A.add(B)).compareTo(new BigDecimal(topDaily)); // 全天大于24小时封顶价格

		if (a == 1) {

		} else {
			// 白天封顶价格处理
			if (A.compareTo(new BigDecimal(topDay)) == 1 && topDay != -1) {
				dayAmount = dayAmount.add(new BigDecimal(topDay).setScale(2).multiply(new BigDecimal(Day2)));
			} else {
				dayAmount = dayAmount.add(A.multiply(new BigDecimal(Day2)));
			}
			// 夜间封顶价格处理
			if (B.compareTo(new BigDecimal(topNight)) == 1 && topNight != -1) {
				nightAmount = nightAmount.add(new BigDecimal(topNight).setScale(2).multiply(new BigDecimal(Day2)));
			} else {
				nightAmount = nightAmount.add(B.multiply(new BigDecimal(Day2)));
			}
		}

		countAmount = dayAmount.add(nightAmount);

		map.put("dayAmount", dayAmount);
		map.put("nightAmount", nightAmount);
		map.put("countAmount", countAmount);

		return map;
	}
}
