package cn.linkmore.coupon.response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResWeekTime{ 
	private Integer start;
	private Integer end;
	private String time;
	private List<ResTime> times;
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getEnd() {
		return end;
	}
	public void setEnd(Integer end) {
		this.end = end;
	}
	public List<ResTime> getTimes() {
		return times;
	}
	public void setTimes(List<ResTime> times) {
		this.times = times;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
		ObjectMapper mapper = new ObjectMapper();  
		try {
			times = mapper.readValue(time, new TypeReference<List<ResTime>>() {});
		} catch (IOException e) {}  
	}
	
	
	public boolean check(){
		boolean flag =  false;
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
	    cal.setTime(now);
	    int w = cal.get(Calendar.DAY_OF_WEEK)-1; 
	    if(w==0){
	    	w = 7;
	    }
	    if(w>=start&&w<=end){ 
	    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	 	    String dt = sdf.format(now);
	 	    for(ResTime tb:times){
	 	    	if(dt.compareTo(tb.getStartTime())>=0&&dt.compareTo(tb.getEndTime())<=0){
	 	    		flag = true; 
	 	    	}
	 	    }
	    } 
		return flag;
	} 
}
