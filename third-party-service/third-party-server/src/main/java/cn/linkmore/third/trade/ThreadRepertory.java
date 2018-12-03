package cn.linkmore.third.trade;

import java.util.Map;

/**
 * @Description    线程本地存储
 * @Author           changlei
 * @Date               2018.11.28
 */ 
public class ThreadRepertory {

	public static ThreadLocal<Map<String, Object>> holder = new ThreadLocal<Map<String, Object>>();

	public static void removeParm(){
		holder.remove();
	}
	
	public static Map<String, Object> getParm(){
		 return holder.get();
	}
	
	public static void setParm(Map<String, Object> parm){
		holder.set(parm);
	}
		
	
}
