package cn.linkmore.third.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
	private static final String LOCATE_URL = "http://apis.map.qq.com/ws/geocoder/v1/";
	private static int COUNT = 0;
	private static final String[] KEYS = { "XAOBZ-GSILF-MLMJY-J2NNW-7VF55-CABCP", "2BOBZ-ZXSKD-YNH4N-HQKWH-W5CVZ-DYFUL",
			"XVHBZ-PG7WP-T7JDF-LIHON-SCAY3-IZFBP", "SFPBZ-K5J3X-TGN4X-ZQBTW-UVVHK-4CBHP",
			"RBZBZ-BD4WD-NNV4Z-HLC7N-6RWVS-CFFKJ", "QY2BZ-5NR3X-H6F43-7RADN-WBJTH-E6B67" };
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 主函数，测试请求
	 * 
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  ResLocate getInfo(String longitude,String latitude) {
		log.info("locate service get info longitude:{},latitude:{}",longitude,latitude);
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("location", latitude + "," + longitude);
		String key = KEYS[COUNT++ % KEYS.length];
		parameters.put("key", key); 
		ResLocate res = null;
		try { 
			Map<String,Object> object = null;
			res = new ResLocate();
			String result = HttpUtil.sendGet(LOCATE_URL, parameters) ; 
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
			log.info("tencent map locate failure key:{}",key);
		}
		return res;
	}
}
