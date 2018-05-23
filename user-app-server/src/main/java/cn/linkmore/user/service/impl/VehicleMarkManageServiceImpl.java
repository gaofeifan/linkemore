package cn.linkmore.user.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.linkmore.account.client.VehicleMarkClient;
import cn.linkmore.account.request.ReqVehicleMark;
import cn.linkmore.account.response.ResVechicleMark;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.redis.RedisService;
import cn.linkmore.user.common.UserCache;
import cn.linkmore.user.response.ResUser;
import cn.linkmore.user.service.VehicleMarkManageService;
import cn.linkmore.util.ObjectUtils;
/**
 * @author   GFF
 * @Date     2018年5月21日
 * @Version  v2.0
 */
@Service
public class VehicleMarkManageServiceImpl implements VehicleMarkManageService {

	@Resource
	private VehicleMarkClient vehicleMarkClient;
	@Resource
	private RedisService redisService;
	@Override
	public List<ResVechicleMark> selectResList(HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER+key); 
		List<ResVechicleMark> list = vehicleMarkClient.list(ru.getId());
		return list;
	}

	@Override
	public void save(cn.linkmore.user.request.ReqVehicleMark bean, HttpServletRequest request) {
		String key = UserCache.getCacheKey(request);
		ResUser ru = (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER+key); 
		bean.setUserId(ru.getId());
		ReqVehicleMark mark = ObjectUtils.copyObject(bean,new ReqVehicleMark());
		vehicleMarkClient.create(mark);
	}

	@Override
	public void deleteById(Long id) {
		vehicleMarkClient.delete(id);
	}

	@Override
	public ResVechicleMark selectById(Long id) {
		return this.selectById(id);
	}

	
	
}
