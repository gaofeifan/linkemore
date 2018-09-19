package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.common.client.CityClient;
import cn.linkmore.enterprise.controller.staff.response.ResCity;
import cn.linkmore.enterprise.service.CityService;
import cn.linkmore.prefecture.client.AdminAuthClient;
import cn.linkmore.prefecture.response.ResStaffCity;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.TokenUtil;
/**
 * @author   GFF
 * @Date     2018年9月17日
 * @Version  v2.0
 */
@Service
public class CityServiceImpl implements CityService {

	@Resource
	private RedisService redisService;
	@Resource
	private CityClient cityClient;
	@Resource
	private AdminAuthClient adminAuthClient; 
	@Override
	public List<ResCity> findStaffCity(HttpServletRequest request) {
		CacheUser cu = (CacheUser)this.redisService.get(RedisKey.STAFF_STAFF_AUTH_USER.key+TokenUtil.getKey(request));
		List<ResStaffCity> map = this.adminAuthClient.findStaffCitysByAdminId(cu.getId());
		List<ResCity> citys = new ArrayList<>();
		if(map == null) {
			return citys;
		}
		List<cn.linkmore.common.response.ResCity> list = this.cityClient.findSelectList();
		ResCity city = null;
		for (ResStaffCity entry : map) {
			for (cn.linkmore.common.response.ResCity resCity : list) {
				if(resCity.getId().equals(entry.getCityId())) {
					city = new ResCity();
					city.setCityName(resCity.getCityName());
					city.setId(resCity.getId());
					citys.add(city);
				}
			}
		}
		return citys;
		
	}
	
	
	
}
