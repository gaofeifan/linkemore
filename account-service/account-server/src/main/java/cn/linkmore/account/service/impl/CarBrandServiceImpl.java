package cn.linkmore.account.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import cn.linkmore.account.service.CarBrandService;

@Service
public class CarBrandServiceImpl implements CarBrandService{
	
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
    
	@Override
	public Object findList() {
		return redisTemplate.opsForValue().get(CAR_BRAND_LIST);
	}
	

}
