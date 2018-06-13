package cn.linkmore.account.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.linkmore.account.dao.cluster.VehicleMarkManageClusterMapper;
import cn.linkmore.account.dao.master.VehicleMarkManageMasterMapper;
import cn.linkmore.account.entity.VehicleMarkManage;
import cn.linkmore.account.request.ReqVehMarkIdAndUserId;
import cn.linkmore.account.request.ReqVehicleMark;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResVechicleMark;
import cn.linkmore.account.service.UserService;
import cn.linkmore.account.service.VehicleMarkManageService;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.TokenUtil;
/**
 * @author   GFF
 * @Date     2018年5月21日
 * @Version  v2.0
 */
@Service
public class VehicleMarkManageServiceImpl implements VehicleMarkManageService {

	@Resource
	private RedisService redisService;
	@Resource
	private VehicleMarkManageClusterMapper vehicleMarkManageClusterMapper;
	@Resource
	private VehicleMarkManageMasterMapper vehicleMarkManageMasterMapper;
	@Resource
	private UserService userService;
	@Override
	public List<VehicleMarkManage> findByUserId(Long userId) {
		return this.vehicleMarkManageClusterMapper.findByUserId(userId);
	}

	@Override
	@Transactional
	public void save(ReqVehicleMark bean) {
		List<VehicleMarkManage> list = this.findByUserId(bean.getUserId());
		if(list.size() < 3){
			//检查车牌号是否已经存在
			List<String> fieldVlaue = ObjectUtils.findFieldVlaue(list, "vehMark", new String[]{"vehMark"}, new String[] {bean.getVehMark()});
			if(fieldVlaue != null && fieldVlaue.size() > 0){
				throw new BusinessException(StatusEnum.ACCOUNT_PLATE_EXISTS);
			}else{
				VehicleMarkManage manage = new VehicleMarkManage();
				manage.setVehUserId(bean.getUserId().toString());
				manage.setVehMark(bean.getVehMark());
				manage.setCreateTime(new Date());
				manage.setUpdateTime(new Date());
				vehicleMarkManageMasterMapper.insertSelective(manage);
				return;
			}
		}
		throw new BusinessException(StatusEnum.ACCOUNT_PLATE_LIMIT);
	}

	@Override
	@Transactional
	public void deleteById( ReqVehMarkIdAndUserId v) {
		List<ResVechicleMark> list = this.findResList(v.getUserId());
		for (ResVechicleMark resVechicleMark : list) {
			if(resVechicleMark.getId().equals(v.getVehMarkId())) {
				this.vehicleMarkManageMasterMapper.deleteById(v.getVehMarkId());
				return;
			}
		}
		throw new RuntimeException("该账户下没有此车牌号");
	}


	@Override
	public List<ResVechicleMark> selectResList(HttpServletRequest request) {
		List<ResVechicleMark> resList = this.findResList(getCache(request).getId());
		return resList;
	}

	@Override
	public void save(cn.linkmore.account.controller.app.request.ReqVehicleMark bean, HttpServletRequest request) {
		bean.setUserId(this.getCache(request).getId());
		ReqVehicleMark mark = ObjectUtils.copyObject(bean,new ReqVehicleMark());
		this.save(mark);
	}

	@Override
	public void deleteById(Long id, HttpServletRequest request) {
		ReqVehMarkIdAndUserId v = new ReqVehMarkIdAndUserId();
		v.setUserId(getCache(request).getId());
		v.setVehMarkId(id);
		this.deleteById(v);
	}
	
	private ResUser getCache(HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		return (ResUser)this.redisService.get(RedisKey.USER_APP_AUTH_USER.key+key); 
	}

	@Override
	public List<ResVechicleMark> findResList(Long userId) {
		return this.vehicleMarkManageClusterMapper.findResList(userId);
	}

	@Override
	public ResVechicleMark findById(Long id) {
		return this.vehicleMarkManageClusterMapper.findById(id);
	}
	
}
