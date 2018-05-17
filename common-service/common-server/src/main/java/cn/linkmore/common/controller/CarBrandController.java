package cn.linkmore.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.linkmore.common.response.ResCarBrandBean;
import cn.linkmore.common.response.ResCarFirmBean;
import cn.linkmore.util.HttpUtils;



/**
 * @Version 2.0
 * @author  GFF
 * @Date     2018年5月11日
 */
@RestController
@RequestMapping("car_brand")
public class CarBrandController {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	public static final String CAR_BRAND_LIST = "car_brand_list";
	//API域名
    private final static String HOST = "http://jisucxdq.market.alicloudapi.com";
	//阿里云市场里，购买服务后的code
    private final static String APP_CODE = "ad0d38987d534d0692e15b67a9fb168c";
    //一级品牌url
    private final static String CarBrandPath = "/car/brand";
    //二级三级品牌url
    private final static String CarListPath = "/car/carlist";
    
    private final static String Method = "GET";
    
    private  Logger log = LoggerFactory.getLogger(getClass());
    
	@RequestMapping(method = RequestMethod.GET)
	public Object list(HttpServletRequest request) {
		return redisTemplate.opsForValue().get(CAR_BRAND_LIST);
	}
	
	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public Map<String,Object> load(){
		log.info("请求车辆品牌接口,请耐心等待......");
		//请求状态  0成功，1请求中，2失败, 方便后台页面控制 请求按钮
		redisTemplate.opsForValue().set("car_brand_status", 1);
		Map<String,Object> msg = new HashMap<String,Object>();
 	    Map<String, String> headers = new HashMap<String, String>();
 	    headers.put("Authorization", "APPCODE " + APP_CODE);
 	    Map<String, String> querys = new HashMap<String, String>();
 	    try {
 	    	HttpResponse response = HttpUtils.doGet(HOST, CarBrandPath, Method, headers, querys);
 	    	String jsonString = EntityUtils.toString(response.getEntity());
 	    	Map maps = (Map)JSON.parse(jsonString);
 	    	JSONArray arr = (JSONArray)maps.get("result");
 	    	List<ResCarBrandBean> brandList = JSONObject.parseArray(arr.toJSONString(),ResCarBrandBean.class);
 	    	
 	    	//获取车牌品牌的id
 	    	Map<String, String> querys2 = new HashMap<String, String>();
 	    	//结果集list
 	    	List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
 	    	for (ResCarBrandBean carBrand : brandList) {
 	    		//根据品牌id获取具体的 厂商品牌（一汽、上汽）
				querys2.put("parentid", carBrand.getId());
				HttpResponse response2 = HttpUtils.doGet(HOST, CarListPath, Method, headers, querys2);
				//必须休眠，容易丢数据[休眠后3秒后， 写出文件需要好几分钟]
				Thread.sleep(3000);
				String jsonString2 = EntityUtils.toString(response2.getEntity());
				Map maps2 = (Map)JSON.parse(jsonString2);
				
				//有的汽车品牌无数据，网络原因 如请求数据丢失将出现空指针异常
				Object obj = maps2.get("result");
				if(!obj.equals("")){
					JSONArray arry = (JSONArray) obj;
					List<ResCarFirmBean> carFirmList = JSONObject.parseArray(arry.toJSONString(),ResCarFirmBean.class);
					if(carFirmList.size() != 0){
						//一级品牌拼装
						Map<String,Object> m = new HashMap<String,Object>();
						m.put("id", carBrand.getId());
		 	    		m.put("name",carBrand.getName());
		 	    		m.put("initial", carBrand.getInitial());
		 	    		m.put("parentid", carBrand.getParentid());
		 	    		m.put("childlist", carFirmList);
		 	    		resultList.add(m);
					}
				}
			}
 	    	redisTemplate.opsForValue().set("car_brand_list", JSON.toJSON(resultList));
 	    	//将成功状态存入redis
 	    	redisTemplate.opsForValue().set("car_brand_status", 0);
	    	msg.put("message", true);
	    	log.info("车辆品牌数据请求成功......");
 	    } catch (NullPointerException n) {
 	    	redisTemplate.opsForValue().set("car_brand_status", 2);
			msg.put("message", "请求数据丢失，请稍后重试");
			log.info("数据丢失，车辆品牌数据请求失败......");
		} catch (Exception e) {
 	    	redisTemplate.opsForValue().set("car_brand_status", 2);
			msg.put("message", "连接失败，请稍后重试");
			log.info("连接失败，车辆品牌数据请求失败......");
		}
 	    return msg;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Object list(){
		Object obj = redisTemplate.opsForValue().get("car_brand_list");
		return obj;
	}
	
}
