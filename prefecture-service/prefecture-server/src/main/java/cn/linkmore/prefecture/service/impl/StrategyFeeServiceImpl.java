package cn.linkmore.prefecture.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.prefecture.dao.cluster.StrategyFeeClusterMapper;
import cn.linkmore.prefecture.entity.StrategyStall;
import cn.linkmore.prefecture.response.ResStrategyFee;
import cn.linkmore.prefecture.service.StrategyFeeService;
import cn.linkmore.util.HttpUtils;
import net.sf.json.JSONObject;
@Service
public class StrategyFeeServiceImpl implements StrategyFeeService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private StrategyFeeClusterMapper strategyFeeClusterMapper;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private DateTimeFormatter dtf_date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat sdf_date= new SimpleDateFormat("yyyy-MM-dd");
	//@Value("${strategyFeeURL}")

	@Value("${strategyfee.url.fee}")
	private String strategyFeeURL;//="http://192.168.1.76:8086/charge/api/reckon_charge_price";
	
	@Value("${strategyfee.url.detail}")
	private String strategyDetailURL;//="http://192.168.1.76:8086/charge/api/reckon_charge_price";
	
	@Value("${strategyfee.fee-code}")
	private String strategyFeeCode;//="987656";
	
	@Value("${strategyfee.fee-secret}")
	private String strategyFeesSecret;//="99876505523";	
	
	/**
	 * 获取费用列表
	 */
	@Override
	public List<ResStrategyFee> findList() {
		return this.strategyFeeClusterMapper.findList();

	}

	/**
	 * 计算总金额
	 * 1 根据车位id找到对应的分时策略
	 * 2 分时策略如果是按日期段的，比较查询出对应的计费策略code,拆分时间段
	 * 3 分时策略如果是按周期段的, 比较查询出对应的计费策略code,拆分时间段
	 * 4 将 计费策略与时间段的 list,从接口获取金额，最后汇总出总金额.
	 */
	@Override
	public Map<String, Object> amount(Map<String, Object> param) {
		long stallId=Long.parseLong(String.valueOf(param.get("stallId")));
		String plateNo=String.valueOf(param.get("plateNo"));
		String startTime=String.valueOf(param.get("startTime"));
		String endTime=String.valueOf(param.get("endTime"));

		List<StrategyStall> listStrategyStall = strategyFeeClusterMapper.findStrategyStallList(stallId);
		List<StrategyStall> listStrategyStallRequest=new ArrayList<StrategyStall>();
		Map<String, Object> resultMap=new HashMap<String, Object>();
		double chargePrice=0D;
		if (CollectionUtils.isNotEmpty(listStrategyStall)) {
			if (listStrategyStall.get(0).getDatetype()==1) {
				//按日期段
				for(StrategyStall strategyStall:listStrategyStall) {
					String sameDateStr=getTheSame(formatStartDateTime(strategyStall.getBeginDate()),formatEndDateTime(strategyStall.getEndDate()),formatStartDateTime(startTime),formatEndDateTime(endTime));
					if(StringUtils.isNotEmpty(sameDateStr)) {
						String [] sameDate=sameDateStr.split("#");
						StrategyStall strategyStallRequest=new StrategyStall();
						strategyStallRequest.setBeginDate(sameDate[0]);
						strategyStallRequest.setEndDate(sameDate[1]);
						strategyStallRequest.setParkCode(strategyStall.getParkCode());
						listStrategyStallRequest.add(strategyStallRequest);
					}
				}
				
			}else {
				//按周期段
				List<String[]> listDateTime=splitDateTime(startTime,endTime);
				for(String[] arrayDateTime: listDateTime) {
					for(StrategyStall strategyStall:listStrategyStall) {
						/*
						System.out.printf("isAcross(%s,%s,%s,%s)=%s%n",
								sdf.format(strategyStall.getStartDate()),formatEndDateTime(sdf_date.format(strategyStall.getStopDate())),arrayDateTime[0],arrayDateTime[1]
								,isAcross(sdf.format(strategyStall.getStartDate()),formatEndDateTime(sdf_date.format(strategyStall.getStopDate())),arrayDateTime[0],arrayDateTime[1])
								);
						*/
						if (isAcross(sdf.format(strategyStall.getStartDate()),formatEndDateTime(sdf_date.format(strategyStall.getStopDate())),arrayDateTime[0],arrayDateTime[1])) {
							int week= getWeek(arrayDateTime[0]);
							if( week>= Integer.parseInt(strategyStall.getBeginDate()) && week<= Integer.parseInt(strategyStall.getEndDate())) {
								StrategyStall strategyStallRequest=new StrategyStall();
								strategyStallRequest.setBeginDate(arrayDateTime[0]);
								strategyStallRequest.setEndDate(arrayDateTime[1]);
								strategyStallRequest.setParkCode(strategyStall.getParkCode());
								listStrategyStallRequest.add(strategyStallRequest);
							}
						}
					}
				}
			}

			
			if(CollectionUtils.isNotEmpty(listStrategyStallRequest)) {
				//System.out.println(listStrategyStallRequest.size());
				for(StrategyStall strategyStall:listStrategyStallRequest) {
					double price=getFee(plateNo,strategyStall.getParkCode()
							,formatStartDateTime(strategyStall.getBeginDate())
							,formatEndDateTime(strategyStall.getEndDate()) );
					//System.out.println(price);
					log.error("{},{},{},{}=>{}",plateNo,strategyStall.getParkCode()
							,formatStartDateTime(strategyStall.getBeginDate())
							,formatEndDateTime(strategyStall.getEndDate()),price );
					
					if(price != -1D) {
						chargePrice+=price;
					}else {
						chargePrice=-1;
						break;
					}
				}
			}else {
				chargePrice=0D;
			}
			//resultMap.put("chargePrice", chargePrice);
		}

		if (chargePrice == -1D) {
			throw new BusinessException(StatusEnum.ORDER_FEE_ERROR);
		}

		resultMap.put("chargePrice", chargePrice);
		return resultMap;
	}
	
	private String formatStartDateTime(String dateTime) {
		return dateTime.length()<=10?dateTime+" 00:00:00":dateTime;
	}
	
	private String formatEndDateTime(String dateTime) {
		return dateTime.length()<=10?dateTime+" 23:59:59":dateTime;
	}
	
	private int getWeek(String ds) {
		Date d=null;
		try {
			d = sdf_date.parse(ds);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        Calendar c=Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.DAY_OF_WEEK)==1? 7:c.get(Calendar.DAY_OF_WEEK)-1;
	}

	/**
	 * 按天分割日期段
	 * @param beginDateTime
	 * @param endDateTime
	 * @return
	 */
	private  List<String[]> splitDateTime(String beginDateTime,String endDateTime){
		beginDateTime=formatStartDateTime(beginDateTime);
		endDateTime=formatEndDateTime(endDateTime);
		String[] d_begin=beginDateTime.split(" ");
		String[] d_end=endDateTime.split(" ");
		LocalDate d_beginDate=LocalDate.parse(d_begin[0]);
		LocalDate d_endDate=LocalDate.parse(d_end[0]);
		List<String[]> listRes=new ArrayList<String[]>();
		while(d_endDate.isAfter(d_beginDate.plusDays(-1)) ) {
			String [] d =new String[2];
			d[0]=d_beginDate.format(dtf_date);
			d[1]=d[0];
			listRes.add(d);
			d_beginDate=d_beginDate.plusDays(1);
		}
		int len=listRes.size();
		for (int i=0;i<len;i++) {
			if (i==0) {
				listRes.get(i)[0] = listRes.get(i)[0] + " " + d_begin[1];
				listRes.get(i)[1]=listRes.get(i)[1]+" 23:59:59";
			}else if(i>=len-1) {
				listRes.get(i)[0]=listRes.get(i)[0]+" 00:00:00";
				listRes.get(i)[1] = listRes.get(i)[1] + " " + d_end[1];
			}else {
				listRes.get(i)[0]=listRes.get(i)[0]+" 00:00:00";
				listRes.get(i)[1]=listRes.get(i)[1]+" 23:59:59";
			}
		}
		return listRes;
	}

	//取两个时间段的交集时间段
	private  String  getTheSame(String beginDate1,String endDate1,String beginDate2,String endDate2) {
		if (isAcross(beginDate1,endDate1,beginDate2,endDate2)) {
			String [] a= {beginDate1,endDate1,beginDate2,endDate2};
			Arrays.sort(a);
			return a[1]+"#"+a[2];
		}
		return null;
	}
	
	private double getFee(String plateNo,String parkCode,String beginTime,String endTime) {
		//log.error("\n{},{},{},{}",plateNo,parkCode,beginTime,endTime);
		String res=httpGetFee(plateNo,parkCode,beginTime,endTime);
		//log.error("\nres={}",res);
		JSONObject obj = JSONObject.fromObject(res);
		if(obj.has("code")) {
			if(StringUtils.equalsIgnoreCase("200", obj.getString("code")) ) {
				if(obj.has("returnData")) {
					JSONObject returnDataObj=obj.getJSONObject("returnData");
					if(returnDataObj.has("chargePrice")) {
						return returnDataObj.getDouble("chargePrice");
					}
				}
			}
		}
		return -1D;
	}

	private String httpGetFee(String plateNo,String parkCode,String beginTime,String endTime) {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json; charset=utf-8");
		Map<String,String> mapBody=new TreeMap<String, String>();
		mapBody.put("code", strategyFeeCode);
		mapBody.put("timestamp",String.valueOf(new Date().getTime()));
		mapBody.put("plateNo", plateNo);
		mapBody.put("parkCode", parkCode);
		mapBody.put("beginTime", beginTime);
		mapBody.put("endTime", endTime);
		mapBody.put("sign", "324");
		JSONObject json = JSONObject.fromObject(mapBody);
		try {
			HttpResponse r=HttpUtils.doPost(strategyFeeURL, "", "", headers, null, json.toString());
			return EntityUtils.toString(r.getEntity(),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 判断两个时间段是否有交叉
	 * @param beginDate1
	 * @param endDate1
	 * @param beginDate2
	 * @param endDate2
	 * @return
	 */
	private boolean isAcross(String beginDate1,String endDate1,String beginDate2,String endDate2) {
		if( myDateTimeDiff(endDate1,beginDate2) >= 0 || myDateTimeDiff(endDate2,beginDate1) >= 0 ){
			return false;
		}
		return true;
	}

	private long myTimeDiff(String beginTime,String endTime) {
	    LocalDateTime d_beginTime = LocalDateTime.parse("2018-01-01 "+beginTime, dtf);
	    LocalDateTime d_endTime = LocalDateTime.parse("2018-01-01 "+endTime, dtf);
	   // System.out.printf("beginTime:%s,endTime:%s,diff=%s\n","2018-01-01 "+beginTime,"2018-01-01 "+endTime,Duration.between(beginDate, endDate).getSeconds());
		return Duration.between(d_beginTime, d_endTime).getSeconds();
	}

	private long myDateDiff(String beginDate,String endDate) {
		LocalDate d_beginDate = LocalDate.parse(beginDate);
		LocalDate d_endDate = LocalDate.parse(endDate);
		return ChronoUnit.DAYS.between(d_beginDate, d_endDate);
	}

	private long myDateTimeDiff(String beginDate,String endDate) {
		//System.out.printf("%s,%s\n",beginDate,endDate);
		LocalDateTime d_beginDate = LocalDateTime.parse(formatStartDateTime(beginDate),dtf);
		LocalDateTime d_endDate = LocalDateTime.parse(formatEndDateTime(endDate),dtf);
		return ChronoUnit.SECONDS.between(d_beginDate, d_endDate);
	}
	
	/**
	 * 根据groupid和当前时间查出对应的策略
	 */
	@Override
	public String info(Map<String, Object> param) {
		//long strategyGroupId=Long.parseLong(String.valueOf(param.get("strategyGroupId")));
		//String searchDateTime=String.valueOf(param.get("searchDateTime"));
		List<StrategyStall> listStrategyStall = strategyFeeClusterMapper.findStrategyFeeList(param);
		//Map<String, Object> resultMap=new HashMap<String, Object>();
		if(CollectionUtils.isNotEmpty(listStrategyStall) && listStrategyStall.size()>0) {
			String parkCode=null;
			if(listStrategyStall.get(0).getDatetype()==1) {
				//按日期
				parkCode=listStrategyStall.get(0).getParkCode();
			}else {
				//按星期
				int week=getWeek(String.valueOf(param.get("searchDateTime")));
				for(StrategyStall strategyStall:listStrategyStall) {
					if( week>=Integer.parseInt(strategyStall.getBeginDate()) &&  week<=Integer.parseInt(strategyStall.getEndDate()) ) {
						parkCode=strategyStall.getParkCode();
						break;
					}
				}
			}
			//调用接口
			if(StringUtils.isNotEmpty(parkCode)) {
				Map<String, String> headers=new HashMap<String, String>();
				headers.put("Content-Type", "application/json; charset=utf-8");
				Map<String,String> mapBody=new TreeMap<String, String>();
				mapBody.put("code", strategyFeeCode);
				mapBody.put("timestamp",String.valueOf(new Date().getTime()));
				mapBody.put("parkCode", parkCode);
				mapBody.put("sign", "324");
				JSONObject json = JSONObject.fromObject(mapBody);
				try {
					HttpResponse r=HttpUtils.doPost(strategyDetailURL, "", "", headers, null, json.toString());
					return EntityUtils.toString(r.getEntity(),"UTF-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
}
