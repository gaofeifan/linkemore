package cn.linkmore.third.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import cn.linkmore.third.response.ResLocate;
import cn.linkmore.third.service.LocateService;
import cn.linkmore.util.HttpUtil;
import cn.linkmore.util.JsonUtil;

/**
 * Service实现 - 定位
 * 
 * @author liwenlong
 * @version 2.0
 */
@Service
public class LocateServiceImpl implements LocateService {
	private static final String LOCATE_URL = "https://apis.map.qq.com/ws/geocoder/v1/";
	private static int COUNT = 0;
	private static final String[] KEYS = { 
			"HZLBZ-W4CEI-OABGH-5SA7T-VS7G2-TNF5K", "I2LBZ-7P7L4-T3IUX-X447A-LJGXH-QHBZW",
			"DJTBZ-S2Z6X-Y2N4B-ZZYM2-FECFH-BOBOE", "UUGBZ-WHBL4-D3KUY-X5YPO-576XF-WZFBO",
			"LACBZ-ORVEX-J7E42-T2GTH-LIA3S-X5B2P" };
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 主函数，测试请求
	 * 
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  ResLocate getInfo(String longitude,String latitude) {
		log.info("------------------locate service get info longitude:{},latitude:{}",longitude,latitude);
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("location", latitude + "," + longitude);
		String key = KEYS[COUNT++ % KEYS.length];
		parameters.put("key", key); 
		ResLocate res = null;
		try { 
			Map<String,Object> object = null;
			res = new ResLocate();
			log.info("------------------locate param :{}",JSON.toJSON(parameters));
			String result = HttpUtil.sendGet(LOCATE_URL, parameters) ; 
			log.info("------------------locate result:{}",result);
			Map<String,String> address = null;
			Map<String,Object> info = null;
			Map<String, Object> map = JsonUtil.toObject(result, Map.class);
			object = (Map<String, Object>) map.get("result");
			address = (Map<String, String>) object.get("address_component"); 
			res.setCity(address.get("city").toString());
			res.setProvince(address.get("province").toString());
			res.setDistrict(address.get("district").toString());
			res.setNation(address.get("nation").toString());
			res.setStreet(address.get("street").toString());
			info = (Map<String, Object>) object.get("ad_info"); 
			res.setAdcode(info.get("adcode").toString());
			res.setName(info.get("name").toString()); 
		} catch (Exception e) {
			log.info("exception = {}", e.getMessage());
			log.info("tencent map locate failure key:{}",key);
		}
		return res;
	}

	@Override
	public String distance(String oriLng, String oriLat, String desLng, String desLat) {
		Map<String, String> params = new HashMap<>();  
        params.put("output","json");
        String key = KEYS[COUNT++ % KEYS.length]; 
        params.put("ak","KvbAhccGqKgrVGX7ZhkUWTR6dHM1ihlc");
        params.put("sn","TGIxKgS4jqCGlIXmzxpqnZEux8DICTwC");
        params.put("origins",oriLat+","+oriLng+"|"+oriLat+","+oriLng);
        params.put("destinations",desLat+","+desLng+"|"+desLat+","+desLng); 
        String result = HttpUtil.sendGet("http://api.map.baidu.com/routematrix/v2/driving", params);
        log.info(result);
        JSONArray jsonArray = com.alibaba.fastjson.JSONObject.parseObject(result).getJSONArray("result");
        
        String distanceString = jsonArray.getJSONObject(0).getJSONObject("distance").getString("text");
        return distanceString; 
	} 
}
