package cn.linkmore.third.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.linkmore.third.request.ReqLocate;
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
 

	/**
	 * 主函数，测试请求
	 * 
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  ResLocate getInfo(ReqLocate req) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("location", req.getLatitude() + "," + req.getLongitude());
		parameters.put("key", KEYS[COUNT++ % KEYS.length]); 
		ResLocate res = null;
		try { 
			Map<String,Object> object = null;
			res = new ResLocate();
			String result = HttpUtil.sendGet(LOCATE_URL, parameters) ;
			Map<String, Object> map = JsonUtil.toObject(result, Map.class);
			object = (Map<String, Object>) map.get("result");
			res.setCity(object.get("city").toString());
			res.setProvince(object.get("province").toString());
			res.setDistrict(object.get("district").toString());
			res.setNation(object.get("nation").toString());
			res.setStreet(object.get("street").toString());
			object = (Map<String, Object>) object.get("ad_info");
			res.setAdcode(object.get("adcode").toString());
			res.setName(object.get("name").toString()); 
		} catch (Exception e) {
			
		}
		return res;
	}
}
