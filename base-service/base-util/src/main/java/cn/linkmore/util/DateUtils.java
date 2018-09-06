package cn.linkmore.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class DateUtils {
	
	
	/**
	 *  获取开始日期和结束日期之间的日期
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param flag 
	 * @return
	 */
	public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate, boolean flag) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Date> lDate = new ArrayList<>(); 
		lDate.add(beginDate);//把开始时间加入集合
		Calendar cal = Calendar.getInstance(); //使用给定的 Date 设置此 Calendar 的时间 
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue)
		{
			//根据日历的规则，为给定的日历字段添加或减去指定的时间量 
			cal.add(Calendar.DAY_OF_MONTH, 1);
			// 测试此日期是否在指定日期之后 
			if (endDate.after(cal.getTime()))
			{ 
				lDate.add(cal.getTime());
			}else{ 
					break;
				} 
			}
			if(!sdf.format(beginDate).equals(sdf.format(endDate))){
				lDate.add(endDate);//把结束时间加入集合 
			}
			if(flag){
				return listSort(lDate);
			}
			return lDate;
		}
	
	private static List<Date> listSort(List<Date> date){
		List<Date> lDate = new ArrayList<>(); 
		for (int i = date.size()-1; i >=0; i--) {
			lDate.add(date.get(i));
		}
		return lDate;
	}
	
	/**
     * 获取过去第几天的日期(- 操作) 或者 未来 第几天的日期( + 操作)
     *
     * @param past
     * @return
     */
    public static String getPast2String(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }
    
    /**
     * @Description  
     * @Author   GFF 
     * @Date       2018年3月27日
     * @Param    DateUtils
     * @Return   String
     */
    public static Date getPast2String(int past ,Calendar calendar) {
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        return today;
    }
   
    /**
     * 
     * 获取过去第几天的日期(- 操作) 或者 未来 第几天的日期( + 操作)
     * @param past
     * @return
     */
    public static Date getPast2Date(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        return today;
    }
    
    public static Date parseString2Date(String date){
    	Date result = null;
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	try {
			result = sdf.parse(date);
		} catch (ParseException e) {
		}
    	return result ;
    }
    
    /**
     * 获取两个时间段内的天数 返回double类型的天数 date1小于date2
     * @param date1
     * @param date2
     * @return
     */
    public static double daysBetween(Date date1,Date date2){
		Calendar cal = Calendar.getInstance();    
        cal.setTime(date1);
		double time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		double time2 = cal.getTimeInMillis();
		double between_days = (time2-time1)/(1000*3600*24);
        return between_days;
    }
    
    /**
	   * 获取距离当前时间
	   * 
	   * @param year
	   * @param month
	   * @param day
	   * @param hour
	   * @param min
	   * @param sec
	   * @return
	   */
	  public static Date getDate(Date date, int year, int month, int day, int hour, int min, int sec) {
	    Calendar c = Calendar.getInstance();
	    c.setTime(date);
	    c.add(Calendar.YEAR, year);
	    c.add(Calendar.MONTH, month);
	    c.add(Calendar.DAY_OF_MONTH, day);
	    c.add(Calendar.HOUR, hour);
	    c.add(Calendar.MINUTE, min);
	    c.add(Calendar.SECOND, sec);
	    return c.getTime();
	  }
	  
	  public static String converter(Date date , String dateFormat){
		  if(StringUtils.isBlank(dateFormat)){
			  dateFormat = "yyyy-MM-dd";
		  }
		  SimpleDateFormat s = new SimpleDateFormat(dateFormat);
		  return s.format(date);
	  }
	  
	  public static Date convert(Date date , String dateFormat){
		  if(StringUtils.isBlank(dateFormat)){
			  dateFormat = "yyyy-MM-dd";
		  }
		  SimpleDateFormat s = new SimpleDateFormat(dateFormat);
		  try {
			return s.parse( s.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return null;
	  }
	  public static Date convert(String date , String dateFormat){
		  if(StringUtils.isBlank(dateFormat)){
			  dateFormat = "yyyy-MM-dd";
		  }
		  SimpleDateFormat s = new SimpleDateFormat(dateFormat);
		  try {
			  return s.parse(date);
		  } catch (ParseException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
		  return null;
	  }
	  
	  /**
		 * @Description: 获取格式为yyyy-MM-dd HH:mm:ss的当前时间
		 * @return Date 当前时间
		 
		 * @Time 2016年8月1日 上午9:56:36
		 */
		public static Date getCurrentDateTime() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(new Date());
			try {
				Date date = sdf.parse(time);
				return date;
			} catch (ParseException e) {
				return null;
			}
		}

		/**
		 * @Description: 获取格式为yyyy-MM-dd的当前时间
		 * @return Date 当前时间
		 
		 * @Time 2016年8月1日 上午9:56:36
		 */
		public static Date getCurrentDate() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String time = sdf.format(new Date());
			try {
				Date date = sdf.parse(time);
				return date;
			} catch (ParseException e) {
				return null;
			}
		}
		
		/**
		 * @Description  计算两个时间内的时长
		 * @Author   GFF 
		 * @Version  v2.0
		 */
		public static String getDuration(Date startDate , Date endDate) {
			if(startDate == null) {
				startDate = new Date();
			}
			if(endDate == null) {
				throw new RuntimeException("结束时间不能为null");
			}
			long time = startDate.getTime() - endDate.getTime();
			long days = time / (1000 * 60 * 60 * 24);  
			if(days > 1) {
				return days + "天前";
			}else if(days == 1) {
				return days + "天";
			}
		    long hours = (time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);  
		    if(hours == 1) {
		    	return hours + "小时";
		    }else if(hours > 1) {
		    	return hours + "小时前";
		    }
		    long minutes = (time % (1000 * 60 * 60)) / (1000 * 60);  
			return minutes + "分钟";
		}
		
		 /**
		 * @Description  获取两个时间段的时分秒
		 * @Author   GFF 
		 * @Version  v2.0
		 */
		public static String getDurationDetail(Date currentTime,Date firstTime){
	          long diff = currentTime.getTime() - firstTime.getTime();
	          Calendar  currentTimes =dataToCalendar(currentTime);
	          Calendar  firstTimes =dataToCalendar(firstTime);
	          int year = currentTimes.get(Calendar.YEAR) - firstTimes.get(Calendar.YEAR);//获取年
	          int month = currentTimes.get(Calendar.MONTH) - firstTimes.get(Calendar.MONTH);
	          int day = currentTimes.get(Calendar.DAY_OF_MONTH) - firstTimes.get(Calendar.DAY_OF_MONTH); 
	          if (day < 0) {
	             month -= 1;
	             currentTimes.add(Calendar.MONTH, -1);
	             day = day + currentTimes.getActualMaximum(Calendar.DAY_OF_MONTH);//获取日
	          }
	          if (month < 0) {
	             month = (month + 12) % 12;//获取月
	             year--;
	          }      
	          long days = diff / (1000 * 60 * 60 * 24);           
	          long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60); //获取时 
	          long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);  //获取分钟
	          long s=(diff/1000-days*24*60*60-hours*60*60-minutes*60);//获取秒
	          StringBuilder sb = new StringBuilder();
	          if(days != 0) {
	        	  sb.append(day).append("天");
	          }
	          if(hours != 0) {
	        	  sb.append(hours).append("时");
	          }
	          if(minutes != 0) {
	        	  sb.append(minutes).append("分");
	          }
	          if(s != 0) {
	        	  sb.append(s).append("秒");
	          }
	          return sb.toString();
	    }  
		 
		 //Date类型转Calendar类型
	    public static Calendar dataToCalendar(Date date) {
	          Calendar calendar = Calendar.getInstance();
	          calendar.setTime(date);
	          return calendar;
	    }
		
		/**
		 * @Description  获取月
		 * @Author   GFF 
		 * @Version  v2.0
		 */
		public static int getMonth(Date date) {
			Calendar c = Calendar.getInstance();
			return c.get(Calendar.MONTH)+1;
		}
		

		public static Date format(String decode) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return format.parse(decode);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		/**
		 *  获取上个月时间
		 */ 
		public static Date getLastMonth(Date date) {
			Calendar calendar = Calendar.getInstance();   
			calendar.add(Calendar.MONTH, -1);
			return calendar.getTime();
		}
		/**
		 *  获取指定天数
		 */ 
		public static Date getDateByDay(int date) {
			Calendar calendar = Calendar.getInstance();   
			calendar.add(Calendar.DAY_OF_MONTH, date);
			return calendar.getTime();
		}
		public static Date getDateByDay(Date date,int num) {
			if(date == null) {
				return getDateByDay(num);
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, num);
			return calendar.getTime();
		}
		/**
		 *  获取指定天数
		 */ 
		public static Date getDateByMonth(int date) {
			Calendar calendar = Calendar.getInstance();   
			calendar.add(Calendar.MONTH, date);
			return calendar.getTime();
		}
		/**
		 *  获取指定天数
		 */ 
		public static Date getDateByMonth(Date date ,int num) {
			if(date == null) {
				return getDateByMonth(num);
			}
			Calendar calendar = Calendar.getInstance();  
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, num);
			return calendar.getTime();
		}
		/**
		 *  获取指定天数
		 */ 
		public static Date getDateByYear(int date) {
			Calendar calendar = Calendar.getInstance();   
			calendar.add(Calendar.YEAR, date);
			return calendar.getTime();
		}
		/**
		 *  获取指定天数
		 */ 
		public static Date getDateByYear(Date date,int num) {
			if(date == null) {
				return getDateByYear(num);
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.YEAR, num);
			return calendar.getTime();
		}
		
		/**
		 * @Description  通过当前时间获取指定年，月，日前的数据集
		 * @Author   GFF 
		 * @Version  v2.0
		 * date 时间  默认为当前时间   ,calendar calendar类中的 year month day ,amount 之前天数
		 */
		public static List<Date> getDates(Date date, int calendar,int amount){
 			Calendar instance = Calendar.getInstance();
			Date endDate = new Date();
			Date NowDate = null;
			if(date == null) {
				instance = Calendar.getInstance();
				NowDate = instance.getTime();
			}else {
				NowDate = date;
			}
			int number = 0;
			date = convert(date, null);
			switch (calendar) {
			case Calendar.YEAR:
				endDate = getDateByYear(date, -amount);
				break;
			case Calendar.MONTH:
				endDate = getDateByMonth(date, -amount);
				break;
			case Calendar.DAY_OF_MONTH:
				endDate = getDateByDay(date, -amount);
				break;
			default:
				break;
			}
			List<Date> dates = new ArrayList<>();
			while (!(NowDate.getTime() == endDate.getTime())) {
				instance.setTime(getDateByDay(NowDate,-1));
				NowDate = convert(instance.getTime(), null);
				dates.add(NowDate);
				number++;
				if(number > 366) {
					break;
				}
			}
			return dates;
		}
		
		
		/**
		 * @Description 获取指定年月日的数值 
		 * @Author   GFF 
		 * @Version  v2.0
		 *  date 默认当前时间  ,filed Calendar.year Calendar.month Calendar.day
		 */
		public static int getFieldDataByDate(Date date , int field) {
			Calendar calendar = Calendar.getInstance();
			if(date != null) {
				calendar.setTime(date);
			}
			if(field == calendar.MONTH) {
				return calendar.get(field)+1;
			}
			return calendar.get(field);
		}
}
