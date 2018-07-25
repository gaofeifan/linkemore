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
}
