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
import cn.linkmore.user.service.UserService;
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
	@Resource
	private UserService userService;
	@Override
	public List<ResVechicleMark> selectResList(HttpServletRequest request) {
		ResUser user = userService.getCache(request);
		List<ResVechicleMark> list = vehicleMarkClient.list(user.getId());
		return list;
	}

	@Override
	public void save(cn.linkmore.user.request.ReqVehicleMark bean, HttpServletRequest request) {
		ResUser ru = this.userService.getCache(request);
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
		return this.vehicleMarkClient.findById(id);
	}

	
	
}
