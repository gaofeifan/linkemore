package cn.linkmore.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.common.client.CityClient;
import cn.linkmore.third.client.LocateClient;
import cn.linkmore.user.response.ResCity;
import cn.linkmore.user.service.CityService;

/**
 * Service实现 - 城市
 * 
 * @author liwenlong
 *
 */
@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityClient cityClient;
	
	@Autowired
	private LocateClient locateClient;
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<ResCity> list(String longitude,String latitude) {
		List<cn.linkmore.common.response.ResCity> list = this.cityClient.list(0, 10);
		List<ResCity> res = null;
		ResCity rc = null;
		Map<String,ResCity> rcMap = new HashMap<String,ResCity>();
		if (CollectionUtils.isNotEmpty(list)) {
			res = new ArrayList<ResCity>();
			for (cn.linkmore.common.response.ResCity re : list) {
				rc = new ResCity();
				rc.setId(re.getId()); 
				rc.setName(re.getName());
				rc.setLongitude(re.getLongitude());
				rc.setLatitude(re.getLatitude());
				res.add(rc);
				rcMap.put(re.getCode().substring(0,4), rc);
			}
		}
		if (!rcMap.isEmpty()) {
			rc = null;
			cn.linkmore.third.request.ReqLocate req = new cn.linkmore.third.request.ReqLocate();
			req.setLongitude(longitude);
			req.setLatitude( latitude); 
			cn.linkmore.third.response.ResLocate info = this.locateClient.get(longitude,latitude);
			if(info!=null&&info.getAdcode()!=null) { 
				rc = rcMap.get(info.getAdcode().substring(0, 4));
				if(rc!=null) {
					rc.setStatus(ResCity.STATUS_CHECKED);
				}
			}
			if(rc==null) {
				rc = res.get(0);
				rc.setStatus(ResCity.STATUS_ASSIGN);
			}
		} 
		return res;
	} 
}
