package cn.linkmore.prefecture.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.linkmore.prefecture.entity.StrategyBase; 
/**
 * 计费策略
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class WonderFee {

	
	/**
	 * 1.获取每段时间的累积值
	 * 2.计算每段区间时间的金额
	 * 2.s首小时去除
	 * 3.累积各个区间数据
	 * 4.
	 * 
	 */
	
	/**
	 * 获取分断时间信息
	 * @param base
	 * @param startDate
	 * @param stopDate
	 * @return
	 * 
	 * 1.总时间小于 24小时
	 *  1.1 时间小于 24小时
	 *  1.2 
	 *  1.3 
	 *  1.4 
	 * 2.总是时间大于 24小时
	 * 3.
	 * 
	 */
	private static Map<String, Object> splitTimeBySections(StrategyBase base, Date startDate, Date stopDate) {
		
		if( base.getType() == StrategyBase.TYPE_TOP_DAILY_24H_FRIST_XHORS)
		{
			Map<String,Object> m = new HashMap<>();
			try{
				// 基础价格
				double basePrice = base.getBasePrice().doubleValue();
				//封顶时长
				long topDailyTime = base.getTopDaily()*60;//秒
				//周期时长
				long daySec =24*60*60;
				//免费时长
				long freeTime =base.getFreeMins()*60;
				
				// 计费单位
				Integer timelyUnit = base.getTimelyLong()*60;
				
				//前N小时， 或首小时 的秒数
				Integer firstHourLong = base.getFirstHourLong()*timelyUnit;
				
				//实际预约占用时间 秒
				long totalTime = (stopDate.getTime()-startDate.getTime())/1000 + freeTime;
				long n = totalTime/daySec;
				long innerDayTime = totalTime%daySec;
				Map<String, Object> timeMap = new HashMap<>();
				timeMap.put("totalTime", totalTime);
				timeMap.put("type", base.getType());
				if(n<=0)
				{
					timeMap = getTOPDaily24HFristNHorsTimeMap(base,totalTime);
					timeMap.put("totalTime", totalTime);
					timeMap.put("type", base.getType());
					
					return timeMap;
				}
				else 
				{
					m.put("freeTime",  0L);
					m.put("firstNTime", 0L);
					m.put("baseTime",  topDailyTime*n+innerDayTime);
					
					return m;
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		return null;
	}


	/**
	 * 
	 * @param base
	 * @param startDate
	 * @param stopDate
	 * @return
	 * @throws Exception 
	 */
	private static Map<String, Object> getTOPDaily24HFristNHorsTimeMap(StrategyBase base,Long totalTime){
		
		//封顶时长
		long topDailyTime = base.getTopDaily()*60;//秒
		//周期时长
		int daySec =24*60*60;
		//免费时长
		long freeTime =base.getFreeMins()*60;
		// 计时单位
		Integer timelyUnit = base.getTimelyLong()*60;
		
		//前N小时， 或首小时 的秒数
		Long firstNHourTime = base.getFirstHourLong()*timelyUnit*1l;
		
		// 计费时长在 首N小时外，封顶时长内
		// 由于封顶时长 和首N小时 存在这个关系
		/**
		 *  由于封顶的是金额，转化为时间*基础价格 故存在
		 *  计费时长在 首N小时外，封顶时长内 存在一个时长X满足
		 *  首N小时费 +  X 小时费 = 封顶时长费
		 *  base.getFirstHour() + X /base.getTimelyLong() * b.basePrice = topDailyTime/base.getTimelyLong()
		 */
		double topFeeTime = (topDailyTime/base.getTimelyLong()/60 * base.getBasePrice().doubleValue() -base.getFirstHour().doubleValue()) /base.getBasePrice().doubleValue() *base.getTimelyLong()*60 + firstNHourTime;	
		
		Map<String,Object> m = new HashMap<>();
		
		
		/**
		 *  4. 总时长 totalTime
		 *  0. 类型 type
		 *  1. 免费时长 30 分钟 freeTime
		 *  2. 首N收费时长 3小时 fristNTime
		 *  3. 基础收费时长   11小时， baseTime
		 */
		// 总时长在 免费时长内
		if(freeTime >= totalTime){
			m.put("freeTime",  totalTime);
			m.put("firstNTime", 0L);
			m.put("baseTime",  0L);
			
			return m;
		} 
		// 总时长在 免费时长外，首N小时内  (freeTime<firstNHourTime)
		else if (firstNHourTime >= totalTime  )
		{
			m.put("freeTime", 0L);
			m.put("firstNTime", totalTime);
			m.put("baseTime", 0l);
			
			return m;
		}
		// 总时长在 首N小时外，X单位时间内（封顶时长价格内 ）
		else if( firstNHourTime < totalTime && totalTime < topFeeTime )
		{
			m.put("freeTime", 0L);
			m.put("firstNTime",firstNHourTime);
			m.put("baseTime", totalTime - firstNHourTime );
			
			return m;
		}
		// 时间在  ，X单位时间外（封顶时长价格外 ）封顶时长外，计费周期内
		else if (topFeeTime <= totalTime && totalTime <=daySec )
		{
			m.put("freeTime",  0L);
			m.put("firstNTime",  0L);
			m.put("baseTime",  topDailyTime);
			
			return m;
		}else
		{
			try {
				throw new Exception(" Method: getTOPDaily24HFristNHorsTimeMap ->totalTime ileage 超出计费周期! ");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return m;
	}
	
	 

	/**
	 *  4. 总时长 totalTime
	 *  0. 类型 type
	 *  1. 免费时长 30 分钟 freeTime
	 *  2. 首N收费时长 3小时 fristNTime
	 *  3. 基础收费时长   11小时， baseTime
	 * @param Long 
	 */
	public static Map<String, Object> getTOPDaily24HFRISTXHORSMap(StrategyBase base, Date startDate, Date stopDate) {

		// 基础价格
		double basePrice = base.getBasePrice().doubleValue();
		double firstHourPrice = base.getFirstHour().doubleValue();
		//封顶时长 秒
		long topDailyTime = base.getTopDaily()*60;
		//周期时长 秒
		long daySec =24*60*60;
		
		//计费单位 秒
		Integer timelyLong = base.getTimelyLong()*60;
		
		//首N小时或首小时  秒 
		Integer firstHourLong = base.getFirstHourLong()*timelyLong;
		//免费时长 秒
		long freeTime = base.getFreeMins()*60;
		
		//实际预约占用时间 秒
		long totalTime = (stopDate.getTime()-startDate.getTime())/1000 + freeTime ;
		long n = totalTime/daySec;
		long inDayTime = totalTime%daySec;
		
		Map<String, Object> m = new HashMap<>();
		m.put("day", 0l);
		m.put("night", 0l);
		m.put("dayAmount", 0.00d);
		m.put("nightAmount", 0.00d);
		m.put("totalAmount", 0.00d);
		
		Map<String, Object> dayTimeMap = splitTimeBySections( base,startDate, stopDate);
		if( null != dayTimeMap  && !dayTimeMap.isEmpty()){
			
			Long freeTimes = (Long)dayTimeMap.get("freeTime");
			Long firstNTime = (Long)dayTimeMap.get("firstNTime");
			Long baseTime = (Long)dayTimeMap.get("baseTime");
			Long stopTime = (Long)dayTimeMap.get("totalTime");
			Integer type = (Integer)dayTimeMap.get("type");
			System.out.println(firstNTime );
			System.out.println(firstHourLong );
			boolean  bFlag = baseTime%timelyLong==0?true:false;
			
			if( baseTime>0  && firstNTime <=0 )// 有基础计时 且 需要累积首N小时费用
			{
				m.put("day", 0l);
				m.put("night", 0l);
				m.put("dayAmount", 0.00d);
				m.put("nightAmount", 0.00d);
				m.put("totalAmount", (baseTime/timelyLong+(bFlag?0:1))*basePrice);
				
			} else if (baseTime>0 ) // 有基础时间  且 需要累积首N小时费用
			{
				
				m.put("day", 0l);
				m.put("night", 0l);
				m.put("dayAmount", 0.00d);
				m.put("nightAmount", 0.00d);
				m.put("totalAmount",firstHourPrice + (baseTime/timelyLong+(bFlag?0:1))*basePrice);
			}
			else if( 0 < firstNTime  && firstNTime <= firstHourLong  ) // 有首N 小时
			{
				m.put("day", 0l);
				m.put("night", 0l);
				m.put("dayAmount", 0.00d);
				m.put("nightAmount", 0.00d);
				m.put("totalAmount", firstHourPrice);
			}else
			{
				m.put("day", 0l);
				m.put("night", 0l);
				m.put("dayAmount", 0.00d);
				m.put("nightAmount", 0.00d);
				m.put("totalAmount", 0.00d);
			}
			
			return m ;
		}
		
		return null;
	} 
}
